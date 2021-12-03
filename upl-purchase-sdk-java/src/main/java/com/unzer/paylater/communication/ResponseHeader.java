package com.unzer.paylater.communication;

import java.util.List;

/**
 * A single response header. Immutable.
 */
public class ResponseHeader {

    private final String name;
    private final String value;

    public ResponseHeader(String name, String value) {
        if (name == null) {
            throw new IllegalArgumentException("name is required");
        }
        this.name = name;
        this.value = value;
    }

    /**
     * @return The header from the given list with the given name, or {@code null} if there was no such header.
     */
    public static ResponseHeader getHeader(List<ResponseHeader> headers, String headerName) {
        for (ResponseHeader header : headers) {
            if (header.getName().equalsIgnoreCase(headerName)) {
                return header;
            }
        }
        return null;
    }

    /**
     * @return The value of the header from the given list with the given name, or {@code null} if there was no such header.
     */
    public static String getHeaderValue(List<ResponseHeader> headers, String headerName) {
        ResponseHeader header = getHeader(headers, headerName);
        return header != null ? header.getValue() : null;
    }

    public String getName() {
        return name;
    }

    /**
     * @return The un-encoded value.
     */
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return getName() + ":" + getValue();
    }
}
