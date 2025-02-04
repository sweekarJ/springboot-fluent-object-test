import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.core.io.ClassPathResource;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.httpclient.protocol.Protocol;

public class PfxHttpClient {

    public static void main(String[] args) throws Exception {
        // ... (certificate loading code - same as before)

        // Create a trust manager that does not validate certificate chains (INSECURE - REPLACE IN PRODUCTION)
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

        Protocol.registerProtocol("https", new Protocol("https", new MySecureProtocolSocketFactory(sc), 443)); // Use custom socket factory

        HttpClient client = new HttpClient();

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

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import java.io.IOException;
import java.net.Socket;
import org.apache.commons.httpclient.protocol.SecureProtocolSocketFactory;

class MySecureProtocolSocketFactory implements SecureProtocolSocketFactory {

    private SSLContext sslContext = null;

    public MySecureProtocolSocketFactory(SSLContext sslContext) {
        this.sslContext = sslContext;
    }

    public Socket createSocket(String host, int port) throws IOException {
        SSLSocketFactory factory = sslContext.getSocketFactory();
        return factory.createSocket(host, port);
    }

    public Socket createSocket(String host, int port, java.net.InetAddress localAddress, int localPort) throws IOException {
        SSLSocketFactory factory = sslContext.getSocketFactory();
        return factory.createSocket(host, port, localAddress, localPort);
    }


    public Socket createSocket(Socket socket, String host, int port, boolean autoClose) throws IOException {
        SSLSocketFactory factory = sslContext.getSocketFactory();
        return factory.createSocket(socket, host, port, autoClose);
    }

    public boolean equals(Object obj) {
        return (obj == this);
    }

    public int hashCode() {
        return this.getClass().hashCode();
    }
}
