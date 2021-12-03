package com.unzer.paylater.communication.standard;

import java.io.IOException;
import java.io.InputStream;
import java.net.ProxySelector;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpRequest;
import org.apache.http.HttpRequestInterceptor;
import org.apache.http.HttpResponse;
import org.apache.http.HttpResponseInterceptor;
import org.apache.http.RequestLine;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.routing.HttpRoutePlanner;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.SystemDefaultCredentialsProvider;
import org.apache.http.impl.conn.DefaultSchemePortResolver;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultRoutePlanner;
import org.apache.http.impl.io.EmptyInputStream;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;

import com.unzer.paylater.communication.CommunicatorConfiguration;
import com.unzer.paylater.communication.Connection;
import com.unzer.paylater.communication.PooledConnection;
import com.unzer.paylater.communication.RequestHeader;
import com.unzer.paylater.communication.ResponseHandler;
import com.unzer.paylater.communication.ResponseHeader;
import com.unzer.paylater.exception.CommunicationException;
import com.unzer.paylater.logging.CommunicatorLogger;
import com.unzer.paylater.logging.LogMessageBuilder;
import com.unzer.paylater.logging.RequestLogMessageBuilder;
import com.unzer.paylater.logging.ResponseLogMessageBuilder;

/**
 * {@link Connection} implementation based on {@link HttpClient}.
 */
public class UnzerConnection implements PooledConnection {

    private static final Charset CHARSET = StandardCharsets.UTF_8;

    private static final String REQUEST_ID_ATTRIBUTE = UnzerConnection.class.getName() + ".requestId";
    private static final String START_TIME_ATTRIBUTE = UnzerConnection.class.getName() + ".startTme";

    // CloseableHttpClient is marked to be thread safe
    protected final CloseableHttpClient httpClient;
    // RequestConfig is marked to be immutable
    protected final RequestConfig requestConfig;
    // PoolingHttpClientConnectionManager, the implementation used, is marked to be thread safe
    private final HttpClientConnectionManager connectionManager;
    private volatile CommunicatorLogger communicatorLogger;

    /**
     * Creates a new connection with the given timeouts, the default number of maximum connections, and the default HTTPS protocols.
     *
     * @see CommunicatorConfiguration#DEFAULT_MAX_CONNECTIONS
     * @see CommunicatorConfiguration#DEFAULT_HTTPS_PROTOCOLS
     */
    public UnzerConnection(int connectTimeout, int readTimeout) {
        this(connectTimeout, readTimeout, CommunicatorConfiguration.DEFAULT_MAX_CONNECTIONS);
    }

    /**
     * Creates a new connection with the given timeouts and number of maximum connections, and the default HTTPS protocols.
     * <p>
     *
     * @see CommunicatorConfiguration#DEFAULT_HTTPS_PROTOCOLS
     */
    public UnzerConnection(int connectTimeout, int readTimeout, int maxConnections) {
        this(connectTimeout, readTimeout, maxConnections, (Set<String>) null);
    }

    /**
     * Creates a new connection with the given timeouts, number of maximum connections, and HTTPS protocols.
     */
    public UnzerConnection(int connectTimeout, int readTimeout, int maxConnections, Set<String> httpsProtocols) {
        this(connectTimeout, readTimeout, maxConnections, createSSLConnectionSocketFactory(httpsProtocols));
    }

    /**
     * Creates a new connection with the given timeouts, number of maximum connections, and SSL connection socket factory.
     * This constructor can be used in case none of the other constructors can be used due to SSL issues,
     * to provide a fully customizable SSL connection socket factory.
     */
    public UnzerConnection(int connectTimeout, int readTimeout, int maxConnections, SSLConnectionSocketFactory sslConnectionSocketFactory) {

        if (sslConnectionSocketFactory == null) {
            throw new IllegalArgumentException("sslConnectionSocketFactory is required");
        }
        requestConfig = createRequestConfig(connectTimeout, readTimeout);
        connectionManager = createHttpClientConnectionManager(maxConnections, sslConnectionSocketFactory);
        httpClient = createHttpClient();
    }

    private static SSLConnectionSocketFactory createSSLConnectionSocketFactory(Set<String> httpsProtocols) {
        SSLContext sslContext = SSLContexts.createDefault();
        HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.getDefaultHostnameVerifier();

        // Default to HTTPS
        Set<String> supportedProtocols = httpsProtocols == null || httpsProtocols.isEmpty()
                ? CommunicatorConfiguration.DEFAULT_HTTPS_PROTOCOLS
                : httpsProtocols;

        return new SSLConnectionSocketFactory(sslContext, supportedProtocols.toArray(new String[0]), null, hostnameVerifier);
    }

    private RequestConfig createRequestConfig(int connectTimeout, int socketTimeout) {
        return RequestConfig.custom()
                            .setSocketTimeout(socketTimeout)
                            .setConnectTimeout(connectTimeout)
                            .build();
    }

    private HttpClientConnectionManager createHttpClientConnectionManager(int maxConnections, SSLConnectionSocketFactory sslConnectionSocketFactory) {
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", sslConnectionSocketFactory)
                .build();

        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        connectionManager.setDefaultMaxPerRoute(maxConnections);
        connectionManager.setMaxTotal(maxConnections + 20);
        return connectionManager;
    }

    private CloseableHttpClient createHttpClient() {

        // add support for system properties
        HttpRoutePlanner routePlanner = new SystemDefaultRoutePlanner(DefaultSchemePortResolver.INSTANCE, ProxySelector.getDefault());
        CredentialsProvider credentialsProvider = new SystemDefaultCredentialsProvider();

        // add logging - last for requests, first for responses
        LoggingInterceptor loggingInterceptor = new LoggingInterceptor();

        return HttpClients.custom()
                          .setConnectionManager(connectionManager)
                          .addInterceptorLast((HttpRequestInterceptor) loggingInterceptor)
                          .addInterceptorFirst((HttpResponseInterceptor) loggingInterceptor)
                          .setRoutePlanner(routePlanner)
                          .setDefaultCredentialsProvider(credentialsProvider)
                          .build();
    }

    @Override
    public void close() throws IOException {
        httpClient.close();
    }

    @Override
    public <R> R post(URI uri, List<RequestHeader> requestHeaders, String body, ResponseHandler<R> responseHandler) {

        HttpEntity requestEntity = createRequestEntity(body);
        return post(uri, requestHeaders, requestEntity, responseHandler);
    }

    private <R> R post(URI uri, List<RequestHeader> requestHeaders, HttpEntity requestEntity, ResponseHandler<R> responseHandler) {

        HttpPost httpPost = new HttpPost(uri);
        httpPost.setConfig(requestConfig);
        addHeaders(httpPost, requestHeaders);
        if (requestEntity != null) {
            httpPost.setEntity(requestEntity);
        }
        return executeRequest(httpPost, responseHandler);
    }

    @Override
    public <R> R get(URI uri, List<RequestHeader> requestHeaders, ResponseHandler<R> responseHandler) {

        HttpGet httpGet = new HttpGet(uri);
        httpGet.setConfig(requestConfig);
        addHeaders(httpGet, requestHeaders);
        return executeRequest(httpGet, responseHandler);
    }

    @Override
    public <R> R put(URI uri, List<RequestHeader> requestHeaders, String body, ResponseHandler<R> responseHandler) {

        HttpEntity requestEntity = createRequestEntity(body);
        return put(uri, requestHeaders, requestEntity, responseHandler);
    }

    private <R> R put(URI uri, List<RequestHeader> requestHeaders, HttpEntity requestEntity, ResponseHandler<R> responseHandler) {

        HttpPut httpPut = new HttpPut(uri);
        httpPut.setConfig(requestConfig);
        addHeaders(httpPut, requestHeaders);
        if (requestEntity != null) {
            httpPut.setEntity(requestEntity);
        }
        return executeRequest(httpPut, responseHandler);
    }

    @Override
    public <R> R delete(URI uri, List<RequestHeader> requestHeaders, ResponseHandler<R> responseHandler) {

        HttpDelete httpDelete = new HttpDelete(uri);
        httpDelete.setConfig(requestConfig);
        addHeaders(httpDelete, requestHeaders);
        return executeRequest(httpDelete, responseHandler);
    }

    private HttpEntity createRequestEntity(String body) {
        return body != null ? new JsonEntity(body, CHARSET) : null;
    }

    protected void addHeaders(HttpRequestBase httpRequestBase, List<RequestHeader> requestHeaders) {
        if (requestHeaders != null) {
            for (RequestHeader requestHeader : requestHeaders) {
                httpRequestBase.addHeader(new BasicHeader(requestHeader.getName(), requestHeader.getValue()));
            }
        }
    }

    @SuppressWarnings("resource")
    protected <R> R executeRequest(HttpUriRequest request, ResponseHandler<R> responseHandler) {

        final String requestId = UUID.randomUUID().toString();
        final long startTime = System.currentTimeMillis();

        HttpContext context = new BasicHttpContext();
        context.setAttribute(REQUEST_ID_ATTRIBUTE, requestId);
        context.setAttribute(START_TIME_ATTRIBUTE, startTime);

        boolean logRuntimeExceptions = true;

        try {
            CloseableHttpResponse httpResponse = httpClient.execute(request, context);
            HttpEntity entity = httpResponse.getEntity();
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            List<ResponseHeader> headers = getHeaders(httpResponse);

            try (InputStream bodyStream = getBodyStream(entity)) {
                // do not log runtime exceptions that originate from the response handler, those are not communication errors
                logRuntimeExceptions = false;

                return responseHandler.handleResponse(statusCode, bodyStream, headers);
            }
        } catch (IOException e) {
            logError(requestId, e, startTime);
            throw new CommunicationException(e);
        } catch (CommunicationException e) {
            logError(requestId, e, startTime);
            throw e;
        } catch (RuntimeException e) {
            if (logRuntimeExceptions) {
                logError(requestId, e, startTime);
            }
            throw e;
        }
    }

    protected List<ResponseHeader> getHeaders(HttpResponse httpResponse) {
        Header[] headers = httpResponse.getAllHeaders();
        List<ResponseHeader> result = new ArrayList<>(headers.length);
        for (Header header : headers) {
            result.add(new ResponseHeader(header.getName(), header.getValue()));
        }
        return result;
    }

    private InputStream getBodyStream(HttpEntity entity) throws IOException {
        return entity != null && entity.getContent() != null
                ? entity.getContent()
                : EmptyInputStream.INSTANCE;
    }

    @Override
    public void closeIdleConnections(long idleTime, TimeUnit timeUnit) {
        connectionManager.closeIdleConnections(idleTime, timeUnit);
    }

    @Override
    public void closeExpiredConnections() {
        connectionManager.closeExpiredConnections();
    }

    @Override
    public void enableLogging(CommunicatorLogger communicatorLogger) {
        if (communicatorLogger == null) {
            throw new IllegalArgumentException("communicatorLogger is required");
        }
        this.communicatorLogger = communicatorLogger;
    }

    @Override
    public void disableLogging() {
        this.communicatorLogger = null;
    }

    // logging code

    private void logRequest(final HttpRequest request, final String requestId, final CommunicatorLogger logger) {

        try {
            RequestLine requestLine = request.getRequestLine();
            String method = requestLine.getMethod();
            String uri = requestLine.getUri();

            final RequestLogMessageBuilder logMessageBuilder = new RequestLogMessageBuilder(requestId, method, uri);
            logMessageBuilder.addHeaders(request.getAllHeaders());
            if (request instanceof HttpEntityEnclosingRequest) {

                final HttpEntityEnclosingRequest entityEnclosingRequest = (HttpEntityEnclosingRequest) request;

                HttpEntity entity = entityEnclosingRequest.getEntity();

                if (entity != null) {
                    String contentType = getContentType(entity, request.getFirstHeader(HttpHeaders.CONTENT_TYPE));
                    // Due to privacy concerns the actual request body (which can contain personal data) is not logged.
                    logMessageBuilder.setBody("****", contentType);
                }
            }

            logger.log(logMessageBuilder.getMessage());

        } catch (Exception e) {
            logger.log(String.format("An error occurred trying to log request '%s'", requestId), e);
        }
    }

    private void logResponse(final HttpResponse response, final String requestId, final long startTime, final CommunicatorLogger logger) {

        final long endTime = System.currentTimeMillis();
        final long duration = endTime - startTime;

        try {
            final int statusCode = response.getStatusLine().getStatusCode();

            final ResponseLogMessageBuilder logMessageBuilder = new ResponseLogMessageBuilder(requestId, statusCode, duration);
            logMessageBuilder.addHeaders(response.getAllHeaders());

            HttpEntity entity = response.getEntity();

            if (entity != null && !entity.isRepeatable()) {
                entity = new BufferedHttpEntity(entity);
                response.setEntity(entity);
            }

            String contentType = getContentType(entity, response.getFirstHeader(HttpHeaders.CONTENT_TYPE));

            if (statusCode >= 200 && statusCode < 300) {
                // Due to privacy concerns the response body (which can contain personal data) is not logged for success responses.
                logMessageBuilder.setBody("****", contentType);
            } else {
                // No personal data is returned in exception responses.
                setBody(logMessageBuilder, entity, contentType);
            }

            logger.log(logMessageBuilder.getMessage());

        } catch (Exception e) {
            logger.log(String.format("An error occurred trying to log response '%s'", requestId), e);
        }
    }

    private String getContentType(HttpEntity entity, Header defaultHeader) {

        Header contentTypeHeader = entity != null
                ? entity.getContentType()
                : null;
        if (contentTypeHeader == null) {
            contentTypeHeader = defaultHeader;
        }

        return contentTypeHeader != null
                ? contentTypeHeader.getValue()
                : null;
    }

    private void setBody(LogMessageBuilder logMessageBuilder, HttpEntity entity, String contentType) throws IOException {

        if (entity == null) {
            logMessageBuilder.setBody("", contentType);
        } else if (entity instanceof JsonEntity) {
            String body = ((JsonEntity) entity).getString();
            logMessageBuilder.setBody(body, contentType);
        } else {
            logMessageBuilder.setBody(entity.getContent(), CHARSET, contentType);
        }
    }

    private void logError(final String requestId, final Exception error, final long startTime) {

        if (communicatorLogger != null) {

            final String messageTemplate = "Error occurred for sent request (requestId='%s', %d ms)";

            final long endTime = System.currentTimeMillis();
            final long duration = endTime - startTime;

            final String message = String.format(messageTemplate, requestId, duration);

            communicatorLogger.log(message, error);
        }
    }

    private class LoggingInterceptor implements HttpRequestInterceptor, HttpResponseInterceptor {

        @Override
        public void process(HttpRequest request, HttpContext context) {

            final CommunicatorLogger logger = communicatorLogger;
            if (logger != null) {

                final String requestId = (String) context.getAttribute(REQUEST_ID_ATTRIBUTE);
                if (requestId != null) {
                    logRequest(request, requestId, logger);
                }
                // else the context was not sent through executeRequest
            }
        }

        @Override
        public void process(HttpResponse response, HttpContext context) {

            final CommunicatorLogger logger = communicatorLogger;
            if (logger != null) {

                final String requestId = (String) context.getAttribute(REQUEST_ID_ATTRIBUTE);
                final Long startTime = (Long) context.getAttribute(START_TIME_ATTRIBUTE);
                if (requestId != null && startTime != null) {
                    logResponse(response, requestId, startTime, logger);
                }
                // else the context was not sent through executeRequest
            }
        }
    }
}
