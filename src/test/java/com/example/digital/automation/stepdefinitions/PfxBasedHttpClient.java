import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.SSLContexts;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;

public class PfxBasedHttpClient {
    public static void main(String[] args) throws Exception {
        String certPath = "certs/your-cert.pfx"; // Ensure it's in `src/main/resources/certs/`
        String certPassword = "your-cert-passphrase";
        String url = "https://your-secure-api.com";

        // Load PFX certificate from classpath
        ClassPathResource resource = new ClassPathResource(certPath);
        InputStream certStream = resource.getInputStream();

        // Load KeyStore with PFX
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(certStream, certPassword.toCharArray());
        certStream.close();

        // Create SSLContext
        SSLContext sslContext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, certPassword.toCharArray())
                .loadTrustMaterial(null, new TrustSelfSignedStrategy()) // Accept self-signed certs
                .build();

        // Create Apache HttpClient with SSL
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(socketFactory)
                .build();

        // Configure RestTemplate to use SSL
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate(factory);

        // Prepare request headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        // Prepare request body (form parameters)
        Map<String, String> body = new HashMap<>();
        body.put("grant_type", "client_credentials");
        body.put("client_id", "your-client-id");

        HttpEntity<Map<String, String>> requestEntity = new HttpEntity<>(body, headers);

        // Perform POST request
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

        // Print response
        System.out.println("Response: " + response.getBody());
    }
}
