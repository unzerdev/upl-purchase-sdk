/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 *
 * This class is based on the Unzer Pay Later OpenAPI specification, version 1.0.0.
 */
package com.unzer.paylater.model;

import java.time.LocalDate;
import java.util.Objects;

public class DeliveryInformation {

    private LocalDate expectedShippingDate;
    private LogisticsProvider logisticsProvider;
    private String trackingNumber;

    /**
     * @return expectedShippingDate
     */
    public LocalDate getExpectedShippingDate() {
        return expectedShippingDate;
    }
    public void setExpectedShippingDate(LocalDate expectedShippingDate) {
        this.expectedShippingDate = expectedShippingDate;
    }
    public DeliveryInformation withExpectedShippingDate(LocalDate expectedShippingDate) {
        this.expectedShippingDate = expectedShippingDate;
        return this;
    }

    /**
     * @return logisticsProvider
     */
    public LogisticsProvider getLogisticsProvider() {
        return logisticsProvider;
    }
    public void setLogisticsProvider(LogisticsProvider logisticsProvider) {
        this.logisticsProvider = logisticsProvider;
    }
    public DeliveryInformation withLogisticsProvider(LogisticsProvider logisticsProvider) {
        this.logisticsProvider = logisticsProvider;
        return this;
    }

    /**
     * The tracking number of the logistics provider.
     *
     * @return trackingNumber
     */
    public String getTrackingNumber() {
        return trackingNumber;
    }
    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
    public DeliveryInformation withTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
        return this;
    }

    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DeliveryInformation deliveryInformation = (DeliveryInformation) o;
        return Objects.equals(this.expectedShippingDate, deliveryInformation.expectedShippingDate)
                && Objects.equals(this.logisticsProvider, deliveryInformation.logisticsProvider)
                && Objects.equals(this.trackingNumber, deliveryInformation.trackingNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(expectedShippingDate, logisticsProvider, trackingNumber);
    }

    @Override
    public String toString() {
        return "class DeliveryInformation {\n"
                + "        expectedShippingDate: " + toIndentedString(expectedShippingDate) + "\n"
                + "        logisticsProvider: " + toIndentedString(logisticsProvider) + "\n"
                + "        trackingNumber: " + toIndentedString(trackingNumber) + "\n"
                + "}";
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        return o == null
                ? "null"
                : o.toString().replace("\n", "\n        ");
    }
}

