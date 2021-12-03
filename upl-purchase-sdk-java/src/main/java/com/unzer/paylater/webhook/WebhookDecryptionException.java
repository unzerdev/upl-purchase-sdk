package com.unzer.paylater.webhook;

public class WebhookDecryptionException extends RuntimeException {

    public WebhookDecryptionException(String message) {
        super(message);
    }

    public WebhookDecryptionException(String message, Throwable cause) {
        super(message, cause);
    }
}
