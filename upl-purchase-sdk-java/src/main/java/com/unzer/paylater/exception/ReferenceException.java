package com.unzer.paylater.exception;

import com.unzer.paylater.model.OperationResult;

/**
 * Represents an error response from the Unzer Pay Later platform when a non-existing or removed object is trying to be accessed.
 */
@SuppressWarnings("serial")
public class ReferenceException extends ApiException {

    public ReferenceException(ResponseException e, OperationResult operationResult) {
        super(e, operationResult);
    }
}
