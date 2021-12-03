/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 *
 * This class is based on the Unzer Pay Later OpenAPI specification, version 1.0.0.
 */
package com.unzer.paylater.model;

import java.util.Objects;

public class CapturePurchaseRequest {

    private Amount fulfillmentAmount;
    private String purchaseId;
    private String orderId;
    private Boolean closePurchase;
    private DeliveryInformation deliveryInformation;

    public CapturePurchaseRequest() { }

    /**
     * This constructor contains all fields required by the API.
     *
     * @param fulfillmentAmount
     */
    public CapturePurchaseRequest(Amount fulfillmentAmount) { 
        this.fulfillmentAmount = fulfillmentAmount;
    }

    /**
     * @return fulfillmentAmount
     */
    public Amount getFulfillmentAmount() {
        return fulfillmentAmount;
    }
    public void setFulfillmentAmount(Amount fulfillmentAmount) {
        this.fulfillmentAmount = fulfillmentAmount;
    }
    public CapturePurchaseRequest withFulfillmentAmount(Amount fulfillmentAmount) {
        this.fulfillmentAmount = fulfillmentAmount;
        return this;
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
    public CapturePurchaseRequest withPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
        return this;
    }

    /**
     * OrderId received after the consumer has completed the transaction from getPurchase response or callback message.
     *
     * @return orderId
     */
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public CapturePurchaseRequest withOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }

    /**
     * This flag indicates if the purchase can be closed.
     *
     * @return closePurchase
     */
    public Boolean getClosePurchase() {
        return closePurchase;
    }
    public void setClosePurchase(Boolean closePurchase) {
        this.closePurchase = closePurchase;
    }
    public CapturePurchaseRequest withClosePurchase(Boolean closePurchase) {
        this.closePurchase = closePurchase;
        return this;
    }

    /**
     * @return deliveryInformation
     */
    public DeliveryInformation getDeliveryInformation() {
        return deliveryInformation;
    }
    public void setDeliveryInformation(DeliveryInformation deliveryInformation) {
        this.deliveryInformation = deliveryInformation;
    }
    public CapturePurchaseRequest withDeliveryInformation(DeliveryInformation deliveryInformation) {
        this.deliveryInformation = deliveryInformation;
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
        CapturePurchaseRequest capturePurchaseRequest = (CapturePurchaseRequest) o;
        return Objects.equals(this.purchaseId, capturePurchaseRequest.purchaseId)
                && Objects.equals(this.orderId, capturePurchaseRequest.orderId)
                && Objects.equals(this.fulfillmentAmount, capturePurchaseRequest.fulfillmentAmount)
                && Objects.equals(this.closePurchase, capturePurchaseRequest.closePurchase)
                && Objects.equals(this.deliveryInformation, capturePurchaseRequest.deliveryInformation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(purchaseId, orderId, fulfillmentAmount, closePurchase, deliveryInformation);
    }

    @Override
    public String toString() {
        return "class CapturePurchaseRequest {\n"
                + "        purchaseId: " + toIndentedString(purchaseId) + "\n"
                + "        orderId: " + toIndentedString(orderId) + "\n"
                + "        fulfillmentAmount: " + toIndentedString(fulfillmentAmount) + "\n"
                + "        closePurchase: " + toIndentedString(closePurchase) + "\n"
                + "        deliveryInformation: " + toIndentedString(deliveryInformation) + "\n"
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

