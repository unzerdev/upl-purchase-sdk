package com.unzer.paylater.logging;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;

import org.apache.http.Header;

/**
 * A utility class to build log messages.
 */
public abstract class LogMessageBuilder {

    private final String requestId;
    private final StringBuilder headers;

    private String body;
    private String contentType;

    protected LogMessageBuilder(String requestId) {
        if (requestId == null || requestId.isEmpty()) {
            throw new IllegalArgumentException("requestId is required");
        }

        this.requestId = requestId;
        this.headers = new StringBuilder();
    }

    protected final String requestId() {
        return requestId;
    }

    protected final String headers() {
        return headers.toString();
    }

    protected final String body() {
        return body;
    }

    protected final String contentType() {
        return contentType;
    }

    public final void addHeaders(Header[] headers) {
        if (headers != null) {
            for (Header header : headers) {
                addHeader(header.getName(), header.getValue());
            }
        }
    }

    public final void addHeader(String name, String value) {

        if (headers.length() > 0) {
            headers.append(", ");
        }

        headers.append(name);
        headers.append("=\"");
        if (value != null) {
            String obfuscatedValue = LoggingUtil.obfuscateHeader(name, value);
            headers.append(obfuscatedValue);
        }
        headers.append("\"");
    }

    public final void setBody(String body, String contentType) {
        this.body = body;
        this.contentType = contentType;
    }

    public final void setBody(InputStream bodyStream, Charset charset, String contentType) throws IOException {
        setBody(new InputStreamReader(bodyStream), contentType);
    }

    public final void setBody(Reader bodyStream, String contentType) throws IOException {
        StringBuilder body = new StringBuilder();
        char[] buffer = new char[4096];
        int len;
        while ((len = bodyStream.read(buffer)) != -1) {
            body.append(buffer, 0, len);
        }
        setBody(body.toString(), contentType);
    }

    public abstract String getMessage();

    protected final String emptyIfNull(String value) {
        return value != null ? value : "";
    }
}
