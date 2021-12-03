package com.unzer.paylater.exception;

import com.unzer.paylater.model.OperationResult;

/**
 * Represents an error response from the Unzer Pay Later platform when authorization failed.
 */
@SuppressWarnings("serial")
public class AuthorizationException extends ApiException {

    public AuthorizationException(ResponseException e, OperationResult operationResult) {
        super(e, operationResult);
    }
}
