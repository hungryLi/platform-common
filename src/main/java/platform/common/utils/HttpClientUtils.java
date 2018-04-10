package platform.common.utils;

import java.util.List;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程安全的httpclient util工具类
 * 
 * @author 
 * @since 2014年7月9日
 */
public class HttpClientUtils {

    public static abstract class HttpResponseHandler {

        public abstract void handle(HttpResponse response) throws Throwable;

        public abstract void exceptionCaught(HttpRequestBase req, Throwable ex);

        public void httpGet(String url) {
            httpGet(SimpleHttpClient.default_http_client, url);
        }

        public void httpGet(HttpClient client, String url) {
            HttpGet httpGet = null;
            long before = System.currentTimeMillis();
            Throwable ex = null;
            try {
                httpGet = new HttpGet(url);
                HttpResponse response = null;
                try {
                    response = client.execute(httpGet);
                } catch (Throwable e1) {
                    exceptionCaught(httpGet, e1);
                    throw e1;
                }
                handle(response);
            } catch (Throwable e) {
                ex = e;
            } finally {
                long span = System.currentTimeMillis() - before;
                if (ex != null) {
                    logger.error("httpGet  -ERROR- [{}]url:{}", new Object[] {
                        HumanReadableUtils.timeSpan(span),
                        url,
                        ex
                    });
                } else if (span >= slow_threshold) {
                    logger.warn("httpGet  -SLOW-  [{}]url:{}", new Object[] {
                        HumanReadableUtils.timeSpan(span),
                        url
                    });
                } else {
                    logger.debug("httpGet  -OK-    [{}]url:{}", new Object[] {
                        HumanReadableUtils.timeSpan(span),
                        url
                    });
                }
                if (null != httpGet) {
                    httpGet.abort();
                }
            }
        }

        public void httpPost(String url, String postContent) {
            httpPost(SimpleHttpClient.default_http_client, url, postContent, CharsetTools.UTF_8.name());
        }

        public void httpPost(HttpClient client, String url, String postContent, String defaultCharset) {
            HttpPost httpPost = null;
            long before = System.currentTimeMillis();
            Throwable ex = null;
            try {
                httpPost = new HttpPost(url);
                HttpEntity entity = new StringEntity(postContent, defaultCharset);
                httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
                httpPost.setEntity(entity);
                HttpResponse response = null;
                try {
                    response = client.execute(httpPost);
                } catch (Throwable e1) {
                    exceptionCaught(httpPost, e1);
                    throw e1;
                }
                handle(response);
            } catch (Throwable e) {
                ex = e;
            } finally {
                long span = System.currentTimeMillis() - before;
                if (ex != null) {
                    logger.error("httpPost -ERROR- [{}]url:{},postContent:{}", new Object[] {
                        HumanReadableUtils.timeSpan(span),
                        url,
                        postContent,
                        ex
                    });
                } else if (span >= slow_threshold) {
                    logger.warn("httpPost -SLOW-  [{}]url:{},postContent:{}", new Object[] {
                        HumanReadableUtils.timeSpan(span),
                        url,
                        postContent
                    });
                } else {
                    logger.debug("httpPost -OK-  [{}]url:{},postContent:{}", new Object[] {
                        HumanReadableUtils.timeSpan(span),
                        url,
                        postContent
                    });
                }
                if (null != httpPost) {
                    httpPost.abort();
                }
            }
        }
    }

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);
    private static final int slow_threshold = 1000;

    public static int httpHead(String url) {
        return httpHead(url, CharsetTools.UTF_8.name());
    }

    public static int httpHead(String url, String defaultCharset) {
        return httpHead(SimpleHttpClient.default_http_client, url, defaultCharset);
    }

    public static int httpHead(HttpClient client, String url, String defaultCharset) {
        HttpGet httpGet = null;
        StatusLine sl = null;
        long before = System.currentTimeMillis();
        Exception ex = null;
        try {
            httpGet = new HttpGet(url);
            HttpResponse response = client.execute(httpGet);
            sl = response.getStatusLine();

            return sl.getStatusCode();
        } catch (Exception e) {
            ex = e;
        } finally {
            long span = System.currentTimeMillis() - before;
            if (ex != null) {
                logger.error("httpHead -ERROR- [{}]url:{}", new Object[] {
                    HumanReadableUtils.timeSpan(span),
                    url,
                    ex
                });
            } else if (span >= slow_threshold) {
                logger.warn("httpHead -SLOW-  [{}]url:{}", new Object[] {
                    HumanReadableUtils.timeSpan(span),
                    url
                });
            } else {
                logger.debug("httpHead -OK-    [{}]url:{}", new Object[] {
                    HumanReadableUtils.timeSpan(span),
                    url
                });
            }
            if (null != httpGet) {
                httpGet.abort();
            }
        }
        return Integer.MIN_VALUE;
    }

    public static String httpGet(HttpClient client, String url, String defaultCharset) {
        return httpGet(client, url, defaultCharset, null);
    }

    public static String httpGet(HttpClient client, String url, String defaultCharset, List<Header> headers) {
        HttpGet httpGet = null;
        String result = "";
        long before = System.currentTimeMillis();
        Throwable ex = null;
        try {
            httpGet = new HttpGet(url);

            if (headers != null && headers.size() > 0) {
                for (Header header : headers) {
                    httpGet.setHeader(header);
                }
            }

            HttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            result = EntityUtils.toString(entity, defaultCharset);
            return result;
        } catch (Throwable e) {
            ex = e;
        } finally {
            long span = System.currentTimeMillis() - before;
            if (ex != null) {
                logger.error("httpGet  -ERROR- [{}]url:{}", new Object[] {
                    HumanReadableUtils.timeSpan(span),
                    url,
                    ex
                });
            } else if (span >= slow_threshold) {
                logger.warn("httpGet  -SLOW-  [{}]url:{}", new Object[] {
                    HumanReadableUtils.timeSpan(span),
                    url
                });
            } else {
                logger.debug("httpGet  -OK-    [{}]url:{}", new Object[] {
                    HumanReadableUtils.timeSpan(span),
                    url
                });
            }
            if (null != httpGet) {
                httpGet.abort();
            }
        }
        return null;
    }

    public static byte[] httpGet(HttpClient client, String url) {
        HttpGet httpGet = null;
        byte[] result = null;
        long before = System.currentTimeMillis();
        Exception ex = null;
        try {
            httpGet = new HttpGet(url);
            HttpResponse response = client.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (entity.isStreaming()) {
                result = EntityUtils.toByteArray(entity);
            }
            return result;
        } catch (Exception e) {
            ex = e;
        } finally {
            long span = System.currentTimeMillis() - before;
            if (ex != null) {
                logger.error("httpGet  -ERROR- [{}]url:{}", new Object[] {
                    HumanReadableUtils.timeSpan(span),
                    url,
                    ex
                });
            } else if (span >= slow_threshold) {
                logger.warn("httpGet  -SLOW-  [{}]url:{}", new Object[] {
                    HumanReadableUtils.timeSpan(span),
                    url
                });
            } else {
                logger.debug("httpGet  -OK-    [{}]url:{}", new Object[] {
                    HumanReadableUtils.timeSpan(span),
                    url
                });
            }
            if (null != httpGet) {
                httpGet.abort();
            }
        }
        return null;
    }

    public static byte[] httpGetByteArray(String url) {
        return httpGet(SimpleHttpClient.default_http_client, url);
    }

    public static String httpGet(String url) {
        return httpGet(url, CharsetTools.UTF_8.name());
    }

    public static String httpGet(String url, List<Header> headers) {
        return httpGet(SimpleHttpClient.default_http_client, url, CharsetTools.UTF_8.name(), headers);
    }

    public static String httpGet(String url, String defaultCharset) {
        return httpGet(SimpleHttpClient.default_http_client, url, defaultCharset);
    }

    public static String httpPost(HttpClient client, String url, String postContent, String defaultCharset, List<Header> headers) {
        HttpPost httpPost = null;
        String content = "";
        long before = System.currentTimeMillis();
        Throwable ex = null;
        try {
            httpPost = new HttpPost(url);
            HttpEntity entity = new StringEntity(postContent, defaultCharset);
            httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
            httpPost.setEntity(entity);

            if (headers != null && headers.size() > 0) {
                for (Header header : headers) {
                    httpPost.setHeader(header);
                }
            }

            HttpResponse response = client.execute(httpPost);
            content = EntityUtils.toString(response.getEntity(), defaultCharset);
            return content;
        } catch (Throwable e) {
            ex = e;
        } finally {
            long span = System.currentTimeMillis() - before;
            if (ex != null) {
                logger.error("httpPost -ERROR- [{}]url:{},postContent:{}", new Object[] {
                    HumanReadableUtils.timeSpan(span),
                    url,
                    postContent,
                    ex
                });
            } else if (span >= slow_threshold) {
                logger.warn("httpPost -SLOW-  [{}]url:{},postContent:{}", new Object[] {
                    HumanReadableUtils.timeSpan(span),
                    url,
                    postContent
                });
            } else {
                logger.debug("httpPost -OK-  [{}]url:{},postContent:{}", new Object[] {
                    HumanReadableUtils.timeSpan(span),
                    url,
                    postContent
                });
            }
            if (null != httpPost) {
                httpPost.abort();
            }
        }
        return null;
    }

    public static String httpPost(HttpClient client, String url, String postContent, String defaultCharset) {
        return httpPost(client, url, postContent, defaultCharset, null);
    }

    public static String httpPost(String url, String postContent) {
        return httpPost(SimpleHttpClient.default_http_client, url, postContent, CharsetTools.UTF_8.name());
    }

    public static String httpPost(String url, String postContent, List<Header> headers) {
        return httpPost(SimpleHttpClient.default_http_client, url, postContent, CharsetTools.UTF_8.name(), headers);
    }

    public static String httpPost(String url, String postContent, String defaultCharset) {
        return httpPost(SimpleHttpClient.default_http_client, url, postContent, defaultCharset);
    }

    /**
     * 简单重试方法
     * 
     * @param url
     * @param attempts
     * @param sleepSec
     * @return
     */
    public static String httpGet(String url, int attempts, int sleepSec) {
        for (int i = 0; i < attempts; i++) {
            String r = httpGet(url, CharsetTools.UTF_8.name());
            if (r != null) {
                return r;
            }
            try {
                Thread.sleep(sleepSec * 1000);
            } catch (InterruptedException e) {
                // e.printStackTrace();
            }
        }
        return null;
    }

    public static int httpHead(String url, int attempts, int sleepSec) {
        for (int i = 0; i < attempts; i++) {
            int r = httpHead(url, CharsetTools.UTF_8.name());
            if (r != Integer.MIN_VALUE) {
                return r;
            }
            try {
                Thread.sleep(sleepSec * 1000);
            } catch (InterruptedException e) {
                // e.printStackTrace();
            }
        }
        return Integer.MIN_VALUE;
    }
}
