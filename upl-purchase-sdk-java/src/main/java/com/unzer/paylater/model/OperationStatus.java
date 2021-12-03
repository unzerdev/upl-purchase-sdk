/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 *
 * This class is based on the Unzer Pay Later OpenAPI specification, version 1.0.0.
 */
package com.unzer.paylater.model;

/**
 * Status of the operation.
 */
public enum OperationStatus {

    OK("OK"),
    NOK("NOK"),
    ERROR("ERROR"),
    PENDING("PENDING"),
    UNKNOWN("UNKNOWN");

    private String value;

    OperationStatus(String value) {
        this.value = value;
    }

    public static OperationStatus fromValue(String value) {
        for (OperationStatus b : OperationStatus.values()) {
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

