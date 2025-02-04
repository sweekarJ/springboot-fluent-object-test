import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) // Use a random port
public class PfxSslTest {

    @LocalServerPort
    private int port;

    private static final String PFX_FILE_PATH = "path/to/your/certificate.pfx"; // Replace with your PFX file path
    private static final String PFX_PASSWORD = "your_pfx_password"; // Replace with your PFX password

    @BeforeAll
    public static void setup() throws Exception {
        // Load the KeyStore
        KeyStore keyStore = KeyStore.getInstance("PKCS12"); // PFX is usually PKCS12
        try (FileInputStream fis = new FileInputStream(new File(PFX_FILE_PATH))) {
            keyStore.load(fis, PFX_PASSWORD.toCharArray());
        }

        // Create SSLContext
        SSLContext sslContext = SSLContextBuilder.create()
                .loadKeyStore(keyStore, PFX_PASSWORD.toCharArray())
                .build();

        // Create HttpClient
        HttpClient httpClient = HttpClients.custom()
                .setSSLContext(sslContext)
                .build();

        // Configure RestAssured
        RestAssured.config = RestAssuredConfig.config()
                .httpClient(HttpClientConfig.httpClient().using(httpClient));
    }


    @Test
    void testSslEndpoint() {
        given()
                .port(port) // Important: Use the random port
                .when()
                .get("/your/ssl/endpoint") // Replace with your SSL endpoint
                .then()
                .statusCode(200)
                .body(equalTo("Expected response")); // Replace with your expected response
    }
}
