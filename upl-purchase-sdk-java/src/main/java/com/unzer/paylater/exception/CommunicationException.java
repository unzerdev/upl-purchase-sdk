package com.unzer.paylater.exception;

/**
 * Indicates an exception regarding the communication with the Unzer Pay Later platform such as a connection exception.
 */
@SuppressWarnings("serial")
public class CommunicationException extends RuntimeException {

    public CommunicationException(Exception e) {
        super(e);
    }
}
