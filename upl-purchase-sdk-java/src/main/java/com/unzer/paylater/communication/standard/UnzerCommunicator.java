package com.unzer.paylater.communication.standard;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpStatus;
import org.apache.http.client.utils.URIBuilder;

import com.unzer.paylater.communication.Communicator;
import com.unzer.paylater.communication.Connection;
import com.unzer.paylater.communication.HttpMethod;
import com.unzer.paylater.communication.Marshaller;
import com.unzer.paylater.communication.PooledConnection;
import com.unzer.paylater.communication.RequestHeader;
import com.unzer.paylater.communication.ResponseHandler;
import com.unzer.paylater.communication.ResponseHeader;
import com.unzer.paylater.exception.CommunicationException;
import com.unzer.paylater.exception.NotFoundException;
import com.unzer.paylater.exception.ResponseException;
import com.unzer.paylater.logging.CommunicatorLogger;
import com.unzer.paylater.model.PurchaseOperationResponse;
import com.unzer.paylater.model.ResponseWithAuthorization;

/**
 * Standard communicator used to communicate with the Unzer Pay Later platform web services.
 * <p>
 * It contains all the logic to transform a request object to a HTTP request and
 * a HTTP response to a response object.
 * <p>
 * Thread-safe.
 */
public class UnzerCommunicator implements Communicator {

    private static final Charset CHARSET = StandardCharsets.UTF_8;

    private final URI apiEndpoint;
    private final Connection connection;
    private final Marshaller marshaller;

    public UnzerCommunicator(URI apiEndpoint, Connection connection, Marshaller marshaller) {
        if (apiEndpoint == null) {
            throw new IllegalArgumentException("apiEndpoint is required");
        }
        if (apiEndpoint.getPath() != null && !apiEndpoint.getPath().isEmpty()) {
            throw new IllegalArgumentException("apiEndpoint should not contain a path");
        }
        if (apiEndpoint.getQuery() != null || apiEndpoint.getFragment() != null) {
            throw new IllegalArgumentException("apiEndpoint should not contain query or fragment");
        }
        if (connection == null) {
            throw new IllegalArgumentException("connection is required");
        }
        if (marshaller == null) {
            throw new IllegalArgumentException("marshaller is required");
        }
        this.apiEndpoint = apiEndpoint;
        this.connection = connection;
        this.marshaller = marshaller;
    }

    private static String extractAuthorization(List<ResponseHeader> headers) {
        return headers.stream()
                      .filter(h -> "access_token".equals(h.getName()))
                      .findFirst()
                      .map(responseHeader -> "Bearer " + responseHeader.getValue())
                      .orElse(null);
    }

    /**
     * Checks the status code and headers for errors and throws an exception if necessary.
     */
    protected static void throwExceptionIfNecessary(int statusCode, InputStream bodyStream, List<ResponseHeader> headers, String requestPath) {

        // status codes in the 100 or 300 range are not expected
        if (statusCode < 200 || statusCode >= 300) {
            String body = toString(bodyStream);

            if (body.isEmpty() || isJson(headers)) {
                throw new ResponseException(statusCode, body, headers);
            } else {
                ResponseException cause = new ResponseException(statusCode, body, headers);
                throw HttpStatus.SC_NOT_FOUND == statusCode
                        ? new NotFoundException("The requested resource was not found; invalid path: " + requestPath, cause)
                        : new CommunicationException(cause);
            }
        }
    }

    protected static void throwExceptionIfNecessary(int statusCode, PurchaseOperationResponse response, List<ResponseHeader> headers, String requestPath) {
        // Every successful response should have a 'result' object.
        if (response.getResult() == null) {
            String message = String.format("PurchaseOperationResponse did not contain expected field 'result'. Request path: '%s'. Response headers: %s",
                    headers,
                    requestPath);
            throw new IllegalStateException(message);
        }
    }

    private static String toString(InputStream bodyStream) {
        try {
            Reader reader = new InputStreamReader(bodyStream, CHARSET);
            StringBuilder body = new StringBuilder();
            char[] buffer = new char[4096];
            int len;
            while ((len = reader.read(buffer)) != -1) {
                body.append(buffer, 0, len);
            }
            return body.toString();
        } catch (IOException e) {
            throw new CommunicationException(e);
        }
    }

    private static boolean isJson(List<ResponseHeader> headers) {
        String contentType = getContentType(headers);
        return contentType == null || "application/json".equalsIgnoreCase(contentType) || contentType.toLowerCase().startsWith("application/json");
    }

    private static <O> boolean isString(List<ResponseHeader> headers, Class<O> responseType) {
        String contentType = getContentType(headers);
        return String.class.equals(responseType) && (contentType == null || contentType.startsWith("text"));
    }

    private static String getContentType(List<ResponseHeader> headers) {
        return ResponseHeader.getHeaderValue(headers, HttpHeaders.CONTENT_TYPE);
    }

    public <O> O execute(HttpMethod httpMethod, String relativePath, List<RequestHeader> requestHeaders, Object requestBody, Class<O> responseType) {
        switch (httpMethod) {
            case POST:
                return post(relativePath, requestHeaders, requestBody, responseType);
            case GET:
                return get(relativePath, requestHeaders, responseType);
            case PUT:
                return put(relativePath, requestHeaders, requestBody, responseType);
            case DELETE:
                return delete(relativePath, requestHeaders, responseType);
        }
        throw new IllegalArgumentException("unsupported http method: " + httpMethod);
    }

    @Override
    public <O> ResponseWithAuthorization<O> executeWithAuthorizationHeader(HttpMethod httpMethod, String relativePath, List<RequestHeader> requestHeaders, Object requestBody, Class<O> responseType) {
        switch (httpMethod) {
            case POST:
                return postWithAuthorization(relativePath, requestHeaders, requestBody, responseType);
            case GET:
                return getWithAuthorization(relativePath, requestHeaders, responseType);
            case PUT:
                return putWithAuthorization(relativePath, requestHeaders, requestBody, responseType);
            case DELETE:
                return deleteWithAuthorization(relativePath, requestHeaders, responseType);
        }
        throw new IllegalArgumentException("unsupported http method: " + httpMethod);
    }

    @Override
    public <O> O post(String relativePath, List<RequestHeader> requestHeaders, Object requestBody, Class<O> responseType) {
        return post(relativePath, requestHeaders, requestBody, defaultResponseHandler(relativePath, responseType));
    }

    @Override
    public <O> ResponseWithAuthorization<O> postWithAuthorization(String relativePath, List<RequestHeader> requestHeaders, Object requestBody, Class<O> responseType) {
        return post(relativePath, requestHeaders, requestBody, responseHandlerWithAuthorization(relativePath, responseType));
    }

    private <O> O post(String relativePath, List<RequestHeader> requestHeaders, Object requestBody, ResponseHandler<O> responseHandler) {
        URI uri = toAbsoluteURI(relativePath);

        if (requestHeaders == null) {
            requestHeaders = new ArrayList<>();
        }

        String requestJson = getRequestBodyAsJsonString(requestHeaders, requestBody);

        return connection.post(uri, requestHeaders, requestJson, responseHandler);
    }

    @Override
    public <O> O get(String relativePath, List<RequestHeader> requestHeaders, Class<O> responseType) {
        return get(relativePath, requestHeaders, defaultResponseHandler(relativePath, responseType));
    }

    @Override
    public <O> ResponseWithAuthorization<O> getWithAuthorization(String relativePath, List<RequestHeader> requestHeaders, Class<O> responseType) {
        return get(relativePath, requestHeaders, responseHandlerWithAuthorization(relativePath, responseType));
    }

    private <O> O get(String relativePath, List<RequestHeader> requestHeaders, ResponseHandler<O> responseHandler) {
        URI uri = toAbsoluteURI(relativePath);

        if (requestHeaders == null) {
            requestHeaders = new ArrayList<>();
        }

        return connection.get(uri, requestHeaders, responseHandler);
    }

    @Override
    public <O> O put(String relativePath, List<RequestHeader> requestHeaders, Object requestBody, Class<O> responseType) {
        return put(relativePath, requestHeaders, requestBody, defaultResponseHandler(relativePath, responseType));
    }

    @Override
    public <O> ResponseWithAuthorization<O> putWithAuthorization(String relativePath, List<RequestHeader> requestHeaders, Object requestBody, Class<O> responseType) {
        return put(relativePath, requestHeaders, requestBody, responseHandlerWithAuthorization(relativePath, responseType));
    }

    private <O> O put(String relativePath, List<RequestHeader> requestHeaders, Object requestBody, ResponseHandler<O> responseHandler) {
        URI uri = toAbsoluteURI(relativePath);

        if (requestHeaders == null) {
            requestHeaders = new ArrayList<>();
        }

        String requestJson = getRequestBodyAsJsonString(requestHeaders, requestBody);

        return connection.put(uri, requestHeaders, requestJson, responseHandler);
    }

    @Override
    public <O> O delete(String relativePath, List<RequestHeader> requestHeaders, Class<O> responseType) {
        return delete(relativePath, requestHeaders, defaultResponseHandler(relativePath, responseType));
    }

    @Override
    public <O> ResponseWithAuthorization<O> deleteWithAuthorization(String relativePath, List<RequestHeader> requestHeaders, Class<O> responseType) {
        return delete(relativePath, requestHeaders, responseHandlerWithAuthorization(relativePath, responseType));
    }

    private <O> O delete(String relativePath, List<RequestHeader> requestHeaders, ResponseHandler<O> responseHandler) {
        URI uri = toAbsoluteURI(relativePath);

        if (requestHeaders == null) {
            requestHeaders = new ArrayList<>();
        }

        return connection.delete(uri, requestHeaders, responseHandler);
    }

    @Override
    public Connection getConnection() {
        return connection;
    }

    @Override
    public Marshaller getMarshaller() {
        return marshaller;
    }

    @Override
    public <T> T unmarshal(String responseJson, Class<T> type) {
        return marshaller.unmarshal(responseJson, type);
    }

    protected URI toAbsoluteURI(String path) {

        String absolutePath = path.startsWith("/")
                ? path
                : "/" + path;

        try {
            return new URIBuilder()
                    .setScheme(apiEndpoint.getScheme())
                    .setHost(apiEndpoint.getHost())
                    .setPort(apiEndpoint.getPort())
                    .setPath(absolutePath)
                    .build();
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Unable to construct URI", e);
        }
    }

    @SuppressWarnings("unchecked")
    private <O> ResponseHandler<O> defaultResponseHandler(String relativePath, Class<O> responseType) {
        return (statusCode, bodyStream, headers) -> {
            throwExceptionIfNecessary(statusCode, bodyStream, headers, relativePath);
            if (isJson(headers)) {
                O response = marshaller.unmarshal(bodyStream, responseType);
                if (responseType == PurchaseOperationResponse.class) {
                    throwExceptionIfNecessary(statusCode, (PurchaseOperationResponse) response, headers, relativePath);
                }
                return response;
            }
            if (isString(headers, responseType)) {
                return (O) toString(bodyStream);
            }
            throw new IllegalStateException("Could not convert response with content type '" + getContentType(headers) + "' to '" + responseType + "'.");
        };
    }

    protected <O> ResponseHandler<ResponseWithAuthorization<O>> responseHandlerWithAuthorization(String relativePath, Class<O> responseType) {
        return (statusCode, bodyStream, headers) -> {
            throwExceptionIfNecessary(statusCode, bodyStream, headers, relativePath);
            O response = marshaller.unmarshal(bodyStream, responseType);
            String authorizationValue = extractAuthorization(headers);
            return new ResponseWithAuthorization<>(authorizationValue, response);
        };
    }

    private String getRequestBodyAsJsonString(List<RequestHeader> requestHeaders, Object requestBody) {
        if (requestBody == null) {
            return null;
        }
        requestHeaders.add(new RequestHeader(HttpHeaders.CONTENT_TYPE, "application/json"));
        return marshaller.marshal(requestBody);
    }

    @Override
    public void close() throws IOException {
        connection.close();
    }

    @Override
    public void closeIdleConnections(long idleTime, TimeUnit timeUnit) {
        if (connection instanceof PooledConnection) {
            ((PooledConnection) connection).closeIdleConnections(idleTime, timeUnit);
        }
    }

    @Override
    public void closeExpiredConnections() {
        if (connection instanceof PooledConnection) {
            ((PooledConnection) connection).closeExpiredConnections();
        }
    }

    @Override
    public void enableLogging(CommunicatorLogger communicatorLogger) {
        connection.enableLogging(communicatorLogger);
    }

    @Override
    public void disableLogging() {
        connection.disableLogging();
    }
}
