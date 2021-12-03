/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 *
 * This class is based on the Unzer Pay Later OpenAPI specification, version 1.0.0.
 */
package com.unzer.paylater.model;

public enum LogisticsProvider {

    DHL("DHL"),
    POST("POST"),
    DPD("DPD"),
    GLS("GLS");

    private String value;

    LogisticsProvider(String value) {
        this.value = value;
    }

    public static LogisticsProvider fromValue(String value) {
        for (LogisticsProvider b : LogisticsProvider.values()) {
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

