package com.unzer.paylater.communication;

import java.io.Closeable;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.unzer.paylater.exception.ApiException;
import com.unzer.paylater.exception.CommunicationException;
import com.unzer.paylater.exception.MarshallerSyntaxException;
import com.unzer.paylater.exception.ResponseException;
import com.unzer.paylater.logging.LoggingCapable;
import com.unzer.paylater.model.ResponseWithAuthorization;

/**
 * Used to communicate with the Unzer Pay Later platform web services.
 * <p>
 * It contains all the logic to transform a request object to a HTTP request and
 * a HTTP response to a response object.
 * <p>
 * Thread-safe.
 */
public interface Communicator extends Closeable, LoggingCapable {

    /**
     * @param httpMethod The type of Http Method to execute.
     * @param relativePath The path to call, relative to the base URI.
     * @param requestHeaders An optional set of request headers.
     * @param requestBody The optional request body to send.
     * @param responseType The type of response to return.
     * @throws CommunicationException when an exception occurred communicating with the Unzer Pay Later platform
     * @throws ResponseException when an error response was received from the Unzer Pay Later platform
     * @throws ApiException when an error response was received from the Unzer Pay Later platform which contained a list of errors
     */
    <O> O execute(HttpMethod httpMethod, String relativePath, List<RequestHeader> requestHeaders, Object requestBody, Class<O> responseType);

    <O> ResponseWithAuthorization<O> executeWithAuthorizationHeader(HttpMethod httpMethod, String relativePath, List<RequestHeader> requestHeaders, Object requestBody, Class<O> responseType);

    /**
     * @param relativePath The path to call, relative to the base URI.
     * @param requestHeaders An set of request headers.
     * @param requestBody The optional request body to send.
     * @param responseType The type of response to return.
     * @throws CommunicationException when an exception occurred communicating with the Unzer Pay Later platform
     * @throws ResponseException when an error response was received from the Unzer Pay Later platform
     * @throws ApiException when an error response was received from the Unzer Pay Later platform which contained a list of errors
     */
    <O> O post(String relativePath, List<RequestHeader> requestHeaders, Object requestBody, Class<O> responseType);

    <O> ResponseWithAuthorization<O> postWithAuthorization(String relativePath, List<RequestHeader> requestHeaders, Object requestBody,
            Class<O> responseType);

    /**
     * @param relativePath The path to call, relative to the base URI.
     * @param requestHeaders An set of request headers.
     * @param responseType The type of response to return.
     * @throws CommunicationException when an exception occurred communicating with the Unzer Pay Later platform
     * @throws ResponseException when an error response was received from the Unzer Pay Later platform
     * @throws ApiException when an error response was received from the Unzer Pay Later platform which contained a list of errors
     */
    <O> O get(String relativePath, List<RequestHeader> requestHeaders, Class<O> responseType);

    <O> ResponseWithAuthorization<O> getWithAuthorization(String relativePath, List<RequestHeader> requestHeaders, Class<O> responseType);

    /**
     * @param relativePath The path to call, relative to the base URI.
     * @param requestHeaders An set of request headers.
     * @param requestBody The optional request body to send.
     * @param responseType The type of response to return.
     * @throws CommunicationException when an exception occurred communicating with the Unzer Pay Later platform
     * @throws ResponseException when an error response was received from the Unzer Pay Later platform
     * @throws ApiException when an error response was received from the Unzer Pay Later platform which contained a list of errors
     */
    <O> O put(String relativePath, List<RequestHeader> requestHeaders, Object requestBody, Class<O> responseType);

    <O> ResponseWithAuthorization<O> putWithAuthorization(String relativePath, List<RequestHeader> requestHeaders, Object requestBody, Class<O> responseType);

    /**
     * @param relativePath The path to call, relative to the base URI.
     * @param requestHeaders An set of request headers.
     * @param responseType The type of response to return.
     * @throws CommunicationException when an exception occurred communicating with the Unzer Pay Later platform
     * @throws ResponseException when an error response was received from the Unzer Pay Later platform
     * @throws ApiException when an error response was received from the Unzer Pay Later platform which contained a list of errors
     */
    <O> O delete(String relativePath, List<RequestHeader> requestHeaders, Class<O> responseType);

    <O> ResponseWithAuthorization<O> deleteWithAuthorization(String relativePath, List<RequestHeader> requestHeaders, Class<O> responseType);

    Connection getConnection();

    Marshaller getMarshaller();

    /**
     * Unmarshal a JSON string to a response object.
     *
     * @param type The response object type.
     * @throws MarshallerSyntaxException if the JSON is not a valid representation for an object of the given type
     */
    <T> T unmarshal(String responseJson, Class<T> type);

    /**
     * Utility method that delegates the call to this communicator's session's connection if that's an instance of
     * {@link PooledConnection}. If not this method does nothing.
     *
     * @see PooledConnection#closeIdleConnections(long, TimeUnit)
     */
    void closeIdleConnections(long idleTime, TimeUnit timeUnit);

    /**
     * Utility method that delegates the call to this communicator's session's connection if that's an instance of
     * {@link PooledConnection}. If not this method does nothing.
     *
     * @see PooledConnection#closeExpiredConnections()
     */
    void closeExpiredConnections();
}
