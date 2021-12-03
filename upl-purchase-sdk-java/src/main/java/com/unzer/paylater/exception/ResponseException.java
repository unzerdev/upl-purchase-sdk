package com.unzer.paylater.exception;

import java.util.Collections;
import java.util.List;

import com.unzer.paylater.communication.ResponseHeader;

/**
 * Thrown when a response was received from the Unzer Pay Later platform which indicates an error.
 */
@SuppressWarnings("serial")
public class ResponseException extends RuntimeException {

    private final int statusCode;
    private final String body;
    private final List<ResponseHeader> headers;

    public ResponseException(int statusCode, String body, List<ResponseHeader> headers) {
        super("the Unzer Pay Later platform returned an error response");
        this.statusCode = statusCode;
        this.body = body;
        this.headers = headers != null ? Collections.unmodifiableList(headers) : Collections.emptyList();
    }

    /**
     * @return The HTTP status code that was returned by the Unzer Pay Later platform.
     */
    public int getResponseStatusCode() {
        return statusCode;
    }

    /**
     * @return The raw response body that was returned by the Unzer Pay Later platform.
     */
    public String getBody() {
        return body;
    }

    /**
     * @return The headers that were returned by the Unzer Pay Later platform. Never {@code null}.
     */
    public List<ResponseHeader> getHeaders() {
        return headers;
    }

    /**
     * @return The header with the given name, or {@code null} if there was no such header.
     */
    public ResponseHeader getHeader(String headerName) {
        return ResponseHeader.getHeader(headers, headerName);
    }

    /**
     * @return The value of the header with the given name, or {@code null} if there was no such header.
     */
    public String getHeaderValue(String headerName) {
        return ResponseHeader.getHeaderValue(headers, headerName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        if (statusCode > 0) {
            sb.append("; statusCode=").append(statusCode);
        }
        if (body != null && body.length() > 0) {
            sb.append("; responseBody='").append(body).append("'");
        }
        return sb.toString();
    }
}
