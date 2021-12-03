package com.unzer.paylater.exception;

import com.unzer.paylater.model.OperationResult;

/**
 * Represents an error response from the Unzer Pay Later platform when something went wrong at the platform or further downstream.
 */
@SuppressWarnings("serial")
public class UnzerException extends ApiException {

    public UnzerException(ResponseException e, OperationResult operationResult) {
        super(e, operationResult);
    }
}
