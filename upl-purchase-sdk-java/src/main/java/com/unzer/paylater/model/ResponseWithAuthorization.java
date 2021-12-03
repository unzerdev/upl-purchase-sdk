package com.unzer.paylater.model;

public class ResponseWithAuthorization<O> {
    private final String authorization;
    private final O response;

    public ResponseWithAuthorization(String authorization, O response) {
        this.authorization = authorization;
        this.response = response;
    }

    public String getAuthorization() {
        return authorization;
    }

    public O getResponse() {
        return response;
    }
}
