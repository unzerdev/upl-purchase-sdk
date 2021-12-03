package com.unzer.paylater.exception;

import com.unzer.paylater.model.OperationResult;

/**
 * Represents an error response from the Unzer Pay Later platform when validation of requests failed.
 */
@SuppressWarnings("serial")
public class ValidationException extends ApiException {

    public ValidationException(ResponseException e, OperationResult operationResult) {
        super(e, operationResult);
    }
}
