/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 *
 * This class is based on the Unzer Pay Later OpenAPI specification, version 1.0.0.
 */
package com.unzer.paylater.model;

import java.util.Objects;

public class MerchantReference {

    private String orderId;
    private String customerId;
    private String invoiceId;

    /**
     * @return orderId
     */
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public MerchantReference withOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    /**
     * @return customerId
     */
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public MerchantReference withCustomerId(String customerId) {
        this.customerId = customerId;
        return this;
    }

    /**
     * @return invoiceId
     */
    public String getInvoiceId() {
        return invoiceId;
    }
    public void setInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
    }
    public MerchantReference withInvoiceId(String invoiceId) {
        this.invoiceId = invoiceId;
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
        MerchantReference merchantReference = (MerchantReference) o;
        return Objects.equals(this.orderId, merchantReference.orderId)
                && Objects.equals(this.customerId, merchantReference.customerId)
                && Objects.equals(this.invoiceId, merchantReference.invoiceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, customerId, invoiceId);
    }

    @Override
    public String toString() {
        return "class MerchantReference {\n"
                + "        orderId: " + toIndentedString(orderId) + "\n"
                + "        customerId: " + toIndentedString(customerId) + "\n"
                + "        invoiceId: " + toIndentedString(invoiceId) + "\n"
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

