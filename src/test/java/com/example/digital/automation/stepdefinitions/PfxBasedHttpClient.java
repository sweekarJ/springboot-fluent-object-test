import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.TrustAllStrategy;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;

public class PfxHttpClient {
    public static void main(String[] args) throws Exception {
        String certPath = "certs/your-cert.pfx"; // Path inside `src/main/resources/certs/`
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
        SSLContext sslContext = SSLContextBuilder.create()
                .loadKeyMaterial(keyStore, certPassword.toCharArray())
                .loadTrustMaterial(new TrustAllStrategy()) // Trust all certificates
                .build();

        // Create SSL Socket Factory for HttpClient 5
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);

        // ✅ Correct method: Set SSLConnectionSocketFactory
        CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(socketFactory)  // ✅ Correct method for HttpClient 5.x
                .build();

        // Use HttpClient with RestTemplate
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
