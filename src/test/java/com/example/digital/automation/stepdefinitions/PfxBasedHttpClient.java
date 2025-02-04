import org.apache.http.client.HttpClient; // Note: HttpClient 4 import
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingClientConnectionManager; // Note: HttpClient 4 import
import org.apache.http.conn.scheme.Scheme; // Note: HttpClient 4 import
import org.apache.http.conn.ssl.SSLSocketFactory; // Note: HttpClient 4 import
import org.apache.http.client.methods.HttpPost; // Note: HttpClient 4 import
import org.apache.http.entity.StringEntity; // Note: HttpClient 4 import
import org.apache.http.impl.client.CloseableHttpClient; // Note: HttpClient 4 import
import org.apache.http.HttpResponse; // Note: HttpClient 4 import
import org.apache.http.util.EntityUtils;  // Note: HttpClient 4 import

import org.springframework.core.io.ClassPathResource;

import java.io.InputStream;
import java.security.KeyStore;

// ... other imports

public class PfxHttpClient {
    public static void main(String[] args) throws Exception {
        // ... (certificate loading code - same as before)

        // HttpClient 4 approach:
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

        // ... (headers and body - form URL encoded)
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
