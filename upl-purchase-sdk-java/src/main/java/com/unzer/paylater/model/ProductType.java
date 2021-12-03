/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 *
 * This class is based on the Unzer Pay Later OpenAPI specification, version 1.0.0.
 */
package com.unzer.paylater.model;

public enum ProductType {

    INVOICE("INVOICE"),
    INSTALLMENT("INSTALLMENT"),
    MONTHLY_INVOICE("MONTHLY_INVOICE");

    private String value;

    ProductType(String value) {
        this.value = value;
    }

    public static ProductType fromValue(String value) {
        for (ProductType b : ProductType.values()) {
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

