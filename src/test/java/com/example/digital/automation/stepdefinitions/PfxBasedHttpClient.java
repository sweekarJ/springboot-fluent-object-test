import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.springframework.core.io.ClassPathResource;
import java.io.InputStream;
import java.security.KeyStore;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.security.KeyManagerFactory;

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

            KeyManagerFactory kmf = KeyManagerFactory.getInstance("SunX509");
            kmf.init(keyStore, certPassword.toCharArray());
            TrustManagerFactory tmf = TrustManagerFactory.getInstance("SunX509");
            tmf.init(keyStore);

            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(kmf.getKeyManagers(), tmf.getTrustManagers(), null);

            HttpClient client = HttpClient.newBuilder()
                    .sslContext(sslContext)
                    .build();

            Map<String, String> body = new HashMap<>();
            body.put("grant_type", "client_credentials");
            body.put("client_id", "your-client-id");
            body.put("client_secret", "your-client-secret");


            String requestBody = body.entrySet().stream()
                    .map(entry -> entry.getKey() + "=" + entry.getValue())
                    .reduce((s1, s2) -> s1 + "&" + s2)
                    .orElse("");

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.fromString(requestBody, StandardCharsets.UTF_8))
                    .build();

            HttpResponse<String> response = client.send(request, java.net.http.HttpResponse.BodyHandlers.ofString());

            System.out.println("Response: " + response.body());

        }
    }
}
