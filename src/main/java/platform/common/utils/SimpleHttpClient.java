package platform.common.utils;

import java.nio.charset.Charset;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

/**
 * simple http client 线程安全
 * 
 * @author XiusongHU
 * @since 2014年4月23日
 */
@SuppressWarnings("deprecation")
public class SimpleHttpClient extends DefaultHttpClient {

    private static int simplehttpClientConnectionTimeout = 8000;
    private static int simplehttpClientSoTimeout = 8000;

    private int socketBufferSize = 8192;
    private boolean tcpNoDelay = true;
    private boolean useExpectContinue = false;
    private Charset contentCharset = Charset.forName("UTF-8");
    private HttpVersion version = HttpVersion.HTTP_1_1;

    public static final HttpClient default_http_client = SimpleHttpClient.getInstance(simplehttpClientConnectionTimeout, simplehttpClientSoTimeout);
    private static ConcurrentHashMap<String, SimpleHttpClient> http_client_cache = new ConcurrentHashMap<String, SimpleHttpClient>(2);
    private static int port = 80;
    private static int sslPort = 443;
    private static int defaultMaxPerRoute = 200;
    private static int maxTotal = 5000;
    private static int connPoolTimeToLiveSec = -1;// 默认是一直保存
    public static final ClientConnectionManager default_cm = new_default_cm();

    public static SimpleHttpClient getInstance(int soTimeout, int connTimeout) {
        String pattern = soTimeout + ":" + connTimeout;
        if (http_client_cache == null) {
            http_client_cache = new ConcurrentHashMap<String, SimpleHttpClient>(2);
        }
        SimpleHttpClient client = http_client_cache.get(pattern);
        if (client == null) {
            client = new SimpleHttpClient(soTimeout, connTimeout);
            http_client_cache.putIfAbsent(pattern, client);
        }
        return client;
    }

    private static ClientConnectionManager new_default_cm() {
        SchemeRegistry registry = new SchemeRegistry();
        registry.register(new Scheme("http", port, PlainSocketFactory.getSocketFactory()));
        registry.register(new Scheme("https", sslPort, SSLSocketFactory.getSocketFactory()));
        PoolingClientConnectionManager manager = new PoolingClientConnectionManager(registry, connPoolTimeToLiveSec, TimeUnit.SECONDS);
        manager.setMaxTotal(maxTotal);
        manager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        return manager;
    }

    public SimpleHttpClient() {
    }

    public SimpleHttpClient(int timeout) {
        simplehttpClientSoTimeout = simplehttpClientConnectionTimeout = timeout;
    }

    public SimpleHttpClient(int soTimeout, int connectionTimeout) {
        simplehttpClientSoTimeout = soTimeout;
        simplehttpClientConnectionTimeout = connectionTimeout;
    }

    /**
     * 使用ThreadSafeClientConnManager
     */
    @Override
    protected ClientConnectionManager createClientConnectionManager() {
        return default_cm;
    }

    @Override
    protected HttpParams createHttpParams() {
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, version);
        HttpProtocolParams.setContentCharset(params, contentCharset.name());
        HttpProtocolParams.setUseExpectContinue(params, useExpectContinue);
        HttpConnectionParams.setTcpNoDelay(params, tcpNoDelay);
        HttpConnectionParams.setSocketBufferSize(params, socketBufferSize);
        HttpConnectionParams.setConnectionTimeout(params, simplehttpClientConnectionTimeout);
        HttpConnectionParams.setSoTimeout(params, simplehttpClientSoTimeout);
        return params;
    }

    public int getConnectionTimeout() {
        return simplehttpClientConnectionTimeout;
    }

    public int getSoTimeout() {
        return simplehttpClientSoTimeout;
    }
}
