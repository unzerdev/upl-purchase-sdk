/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 *
 * This class is based on the Unzer Pay Later OpenAPI specification, version 1.0.0.
 */
package com.unzer.paylater.model;

public enum PaymentMethod {

    DIRECT_DEBIT("DIRECT_DEBIT"),
    BANK_TRANSFER("BANK_TRANSFER");

    private String value;

    PaymentMethod(String value) {
        this.value = value;
    }

    public static PaymentMethod fromValue(String value) {
        for (PaymentMethod b : PaymentMethod.values()) {
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

