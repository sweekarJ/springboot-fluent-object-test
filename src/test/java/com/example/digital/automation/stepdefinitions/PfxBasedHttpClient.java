import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

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

        if (certStream == null) {
            throw new RuntimeException("Certificate file not found: " + certPath);
        }

        // Load KeyStore with PFX
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        keyStore.load(certStream, certPassword.toCharArray());
        certStream.close();

        // Create SSLContext  -  ***IN PRODUCTION, REPLACE TrustAllStrategy***
        SSLContext sslContext = org.apache.http.ssl.SSLContextBuilder.create()  // Correct import for HttpClient 4
                .loadKeyMaterial(keyStore, certPassword.toCharArray())
                .loadTrustMaterial(org.apache.http.ssl.TrustAllStrategy.INSTANCE) // ***SECURITY RISK***
                .build();


        KeyStore trustStore = KeyStore.getInstance("JKS"); // Or PKCS12 if needed
        trustStore.load(null, null); // Initialize an empty truststore

        SSLSocketFactory sf = new SSLSocketFactory(sslContext);
        Scheme scheme = new Scheme("https", 443, sf);

        PoolingClientConnectionManager connectionManager = new PoolingClientConnectionManager();
        connectionManager.getSchemeRegistry().register(scheme);

        HttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .build();

        HttpPost httpPost = new HttpPost(url);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        Map<String, String> body = new HashMap<>();
        body.put("grant_type", "client_credentials");
        body.put("client_id", "your-client-id"); // Make sure these are correct
        body.put("client_secret", "your-client-secret"); //  If your API requires it

        String requestBody = body.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .reduce((s1, s2) -> s1 + "&" + s2)
                .orElse("");


        StringEntity entity = new StringEntity(requestBody, "UTF-8"); // Important: Specify UTF-8
        httpPost.setEntity(entity);

        for (Map.Entry<String, String> entry : headers.entrySet()) {
            httpPost.addHeader(entry.getKey(), entry.getValue());
        }

        HttpResponse response = httpClient.execute(httpPost);

        String responseBody = EntityUtils.toString(response.getEntity());

        System.out.println("Response: " + responseBody);

        httpClient.getConnectionManager().shutdown(); // Important: Close connections
    }
}
