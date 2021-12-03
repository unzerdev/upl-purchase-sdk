package com.unzer.paylater.communication;

import java.io.InputStream;
import java.util.List;

/**
 * An interface for handling responses from the Unzer Pay Later platform.
 */
public interface ResponseHandler<R> {

    /**
     * @param statusCode The HTTP status code that was returned by the Unzer Pay Later platform.
     * @param bodyStream The raw response body that was returned by the Unzer Pay Later platform. Note that it will be closed outside of this method.
     * @param headers The headers that were returned by the Unzer Pay Later platform. Never {@code null}.
     */
    R handleResponse(int statusCode, InputStream bodyStream, List<ResponseHeader> headers);
}
