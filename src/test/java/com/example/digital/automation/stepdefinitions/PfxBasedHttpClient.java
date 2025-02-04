import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.core.io.ClassPathResource;

import javax.net.ssl.SSLContext;
import java.io.InputStream;
import java.security.KeyStore;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

public class PfxHttpClient {

    public static void main(String[] args) throws Exception {
        String certPath = "certs/your-cert.pfx";
        String certPassword = "your-cert-passphrase";
        String url = "https://your-secure-api.com";

        ClassPathResource resource = new ClassPathResource(certPath);
        try (InputStream certStream = resource.getInputStream()) {
            if (certStream == null) {
                throw new RuntimeException("Certificate file not found: " + certPath);
            }

            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(certStream, certPassword.toCharArray());

            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            } };

            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());

            HttpClient client = new HttpClient();
            client.getHostConfiguration().setHost("your-secure-api.com", 443, new org.apache.commons.httpclient.protocol.Protocol("https", new org.apache.commons.httpclient.contrib.HttpSslProtocolSocketFactory(sc), 443));

            PostMethod method = new PostMethod(url);
            method.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");

            Map<String, String> body = new HashMap<>();
            body.put("grant_type", "client_credentials");
            body.put("client_id", "your-client-id");
            body.put("client_secret", "your-client-secret");

            NameValuePair[] data = body.entrySet().stream()
                    .map(entry -> new NameValuePair(entry.getKey(), entry.getValue()))
                    .toArray(NameValuePair[]::new);

            method.setRequestBody(data);

            int statusCode = client.executeMethod(method);

            if (statusCode != 200) {
                System.err.println("HTTP Status Code: " + statusCode);
            }

            String responseBody = method.getResponseBodyAsString();

            System.out.println("Response: " + responseBody);

            method.releaseConnection();
        }
    }
}
