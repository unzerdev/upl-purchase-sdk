/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 *
 * This class is based on the Unzer Pay Later OpenAPI specification, version 1.0.0.
 */
package com.unzer.paylater.model;

/**
 * Method with which the end user is redirected to the Instore Selfservice application.
 */
public enum MethodType {

    SMS("SMS"),
    URL("URL");

    private String value;

    MethodType(String value) {
        this.value = value;
    }

    public static MethodType fromValue(String value) {
        for (MethodType b : MethodType.values()) {
            if (b.value.equals(value)) {
                return b;
            }
        }
        throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}

