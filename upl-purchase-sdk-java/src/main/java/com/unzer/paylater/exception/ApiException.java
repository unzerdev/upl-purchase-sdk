package com.unzer.paylater.exception;

import java.util.List;

import com.unzer.paylater.communication.ResponseHeader;
import com.unzer.paylater.model.OperationResult;

/**
 * Represents an error response from the Unzer Pay Later platform which contains an ID and a list of errors.
 */
@SuppressWarnings("serial")
public class ApiException extends RuntimeException {

    private final String errorCode;
    private final int responseStatusCode;
    private final List<ResponseHeader> responseHeaders;
    private final String responseBody;
    private final OperationResult operationResult;

    public ApiException(ResponseException e, OperationResult operationResult) {
        super(operationResult != null ? operationResult.getStatusMessage() : null, e);
        this.errorCode = operationResult != null ? operationResult.getStatusCode() : null;
        this.responseStatusCode = e.getResponseStatusCode();
        this.responseHeaders = e.getHeaders();
        this.responseBody = e.getBody();
        this.operationResult = operationResult;
    }

    /**
     * @return The error code received from the Unzer Pay Later platform if available.
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @return The HTTP status code that was returned by the Unzer Pay Later platform.
     */
    public int getResponseStatusCode() {
        return responseStatusCode;
    }

    /**
     * @return the response headers that were returned by the Unzer Pay Later platform.
     */
    public List<ResponseHeader> getResponseHeaders() {
        return responseHeaders;
    }

    /**
     * @return The raw response body that was returned by the Unzer Pay Later platform.
     */
    public String getResponseBody() {
        return responseBody;
    }

    /**
     * @return The operation result received from the Unzer Pay Later platform if available.
     */
    public OperationResult getOperationResult() {
        return operationResult;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(super.toString());
        if (responseStatusCode > 0) {
            sb.append("; responseStatusCode=").append(responseStatusCode);
        }
        if (responseBody != null && responseBody.length() > 0) {
            sb.append("; responseBody='").append(responseBody).append("'");
        }
        return sb.toString();
    }
}
