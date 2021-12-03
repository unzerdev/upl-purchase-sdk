/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 *
 * This class is based on the Unzer Pay Later OpenAPI specification, version 1.0.0.
 */
package com.unzer.paylater.model;

public enum RefundReason {

    CUSTOMER_REFUND("CUSTOMER_REFUND"),
    MERCHANT_TECHNICAL_PROBLEM("MERCHANT_TECHNICAL_PROBLEM"),
    REFUND_OBLIGINGNESS("REFUND_OBLIGINGNESS"),
    MERCHANT_CAN_NOT_DELIVER_GOODS("MERCHANT_CAN_NOT_DELIVER_GOODS");

    private String value;

    RefundReason(String value) {
        this.value = value;
    }

    public static RefundReason fromValue(String value) {
        for (RefundReason b : RefundReason.values()) {
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

