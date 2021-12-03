/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 *
 * This class is based on the Unzer Pay Later OpenAPI specification, version 1.0.0.
 */
package com.unzer.paylater.model;

import java.util.Objects;

public class RefundPurchaseRequest {

    private String purchaseId;
    private Amount refundAmount;
    private RefundReason reason;

    public RefundPurchaseRequest() { }

    /**
     * This constructor contains all fields required by the API.
     *
     * @param purchaseId PurchaseId received from initializePurchase or authorizePurchase response.
     * @param refundAmount
     */
    public RefundPurchaseRequest(String purchaseId, Amount refundAmount) { 
        this.purchaseId = purchaseId;
        this.refundAmount = refundAmount;
    }

    /**
     * PurchaseId received from initializePurchase or authorizePurchase response.
     *
     * @return purchaseId
     */
    public String getPurchaseId() {
        return purchaseId;
    }
    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }
    public RefundPurchaseRequest withPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
        return this;
    }

    /**
     * @return refundAmount
     */
    public Amount getRefundAmount() {
        return refundAmount;
    }
    public void setRefundAmount(Amount refundAmount) {
        this.refundAmount = refundAmount;
    }
    public RefundPurchaseRequest withRefundAmount(Amount refundAmount) {
        this.refundAmount = refundAmount;
        return this;
    }

    /**
     * @return reason
     */
    public RefundReason getReason() {
        return reason;
    }
    public void setReason(RefundReason reason) {
        this.reason = reason;
    }
    public RefundPurchaseRequest withReason(RefundReason reason) {
        this.reason = reason;
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
        RefundPurchaseRequest refundPurchaseRequest = (RefundPurchaseRequest) o;
        return Objects.equals(this.purchaseId, refundPurchaseRequest.purchaseId)
                && Objects.equals(this.refundAmount, refundPurchaseRequest.refundAmount)
                && Objects.equals(this.reason, refundPurchaseRequest.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(purchaseId, refundAmount, reason);
    }

    @Override
    public String toString() {
        return "class RefundPurchaseRequest {\n"
                + "        purchaseId: " + toIndentedString(purchaseId) + "\n"
                + "        refundAmount: " + toIndentedString(refundAmount) + "\n"
                + "        reason: " + toIndentedString(reason) + "\n"
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

