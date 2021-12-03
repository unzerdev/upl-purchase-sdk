/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 *
 * This class is based on the Unzer Pay Later OpenAPI specification, version 1.0.0.
 */
package com.unzer.paylater.model;

public enum DeliveryType {

    BILLING_ADDRESS("BILLING_ADDRESS"),
    ALTERNATIVE_DELIVERY_ADDRESS("ALTERNATIVE_DELIVERY_ADDRESS"),
    SHOP_PICKUP("SHOP_PICKUP"),
    POST_OFFICE_PICKUP("POST_OFFICE_PICKUP");

    private String value;

    DeliveryType(String value) {
        this.value = value;
    }

    public static DeliveryType fromValue(String value) {
        for (DeliveryType b : DeliveryType.values()) {
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

