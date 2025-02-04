import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.SecureRandom;

public class SslClientAuthTest {

    @Test
    public void testSslClientAuthentication() throws Exception {
        // Path to your .pfx file
        String pfxFilePath = "path/to/your/certificate.pfx";
        String pfxPassword = "your_pfx_password";

        // Load the .pfx file into a KeyStore
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        try (FileInputStream fis = new FileInputStream(pfxFilePath)) {
            keyStore.load(fis, pfxPassword.toCharArray());
        }

        // Initialize KeyManagerFactory with the KeyStore
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, pfxPassword.toCharArray());

        // Initialize TrustManagerFactory (you can use the default trust store)
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init((KeyStore) null);

        // Create and initialize SSLContext
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(keyManagerFactory.getKeyManagers(), trustManagerFactory.getTrustManagers(), new SecureRandom());

        // Configure RestAssured to use the SSLContext
        RestAssured.config = RestAssured.config().sslConfig(new SSLConfig().sslContext(sslContext));

        // Make the API call
        Response response = RestAssured.given()
                .baseUri("https://your-api-endpoint.com")
                .when()
                .get("/your-endpoint")
                .then()
                .extract()
                .response();

        // Print the response
        System.out.println(response.asString());
    }
}
