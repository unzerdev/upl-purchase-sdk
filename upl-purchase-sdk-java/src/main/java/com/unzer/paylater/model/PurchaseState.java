/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 *
 * This class is based on the Unzer Pay Later OpenAPI specification, version 1.0.0.
 */
package com.unzer.paylater.model;

public enum PurchaseState {

    INITIALIZED("INITIALIZED"),
    PRECHECKED("PRECHECKED"),
    DECLINED("DECLINED"),
    AUTHORIZED("AUTHORIZED"),
    AUTHORIZATION_PENDING("AUTHORIZATION_PENDING"),
    CANCELLED("CANCELLED"),
    FULFILLMENT("FULFILLMENT"),
    BLOCKED("BLOCKED"),
    TIMED_OUT("TIMED_OUT"),
    CLOSED("CLOSED");

    private String value;

    PurchaseState(String value) {
        this.value = value;
    }

    public static PurchaseState fromValue(String value) {
        for (PurchaseState b : PurchaseState.values()) {
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

