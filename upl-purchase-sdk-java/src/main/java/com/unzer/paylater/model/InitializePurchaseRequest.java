/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 *
 * This class is based on the Unzer Pay Later OpenAPI specification, version 1.0.0.
 */
package com.unzer.paylater.model;

import java.util.Objects;

public class InitializePurchaseRequest {

    private Amount purchaseAmount;
    private Consumer consumer;
    private MerchantReference merchantReference;
    private String additionalInformation;

    public InitializePurchaseRequest() { }

    /**
     * This constructor contains all fields required by the API.
     *
     * @param purchaseAmount
     */
    public InitializePurchaseRequest(Amount purchaseAmount) { 
        this.purchaseAmount = purchaseAmount;
    }

    /**
     * @return purchaseAmount
     */
    public Amount getPurchaseAmount() {
        return purchaseAmount;
    }
    public void setPurchaseAmount(Amount purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }
    public InitializePurchaseRequest withPurchaseAmount(Amount purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
        return this;
    }

    /**
     * @return consumer
     */
    public Consumer getConsumer() {
        return consumer;
    }
    public void setConsumer(Consumer consumer) {
        this.consumer = consumer;
    }
    public InitializePurchaseRequest withConsumer(Consumer consumer) {
        this.consumer = consumer;
        return this;
    }

    /**
     * @return merchantReference
     */
    public MerchantReference getMerchantReference() {
        return merchantReference;
    }
    public void setMerchantReference(MerchantReference merchantReference) {
        this.merchantReference = merchantReference;
    }
    public InitializePurchaseRequest withMerchantReference(MerchantReference merchantReference) {
        this.merchantReference = merchantReference;
        return this;
    }

    /**
     * Additional information provided as a key value map.  Shop information, when a merchant has multiple shops this assigns a specific transaction to a specific shop: - PAYOLUTION_SHOP_ID - PAYOLUTION_SHOP_NAME - PAYOLUTION_SHOP_LEGAL_NAME  Customer registration, input for risk, increases acceptance rate: - PAYOLUTION_CUSTOMER_REGISTRATION_DATE - PAYOLUTION_CUSTOMER_REGISTRATION_LEVEL  Basket content, input for risk, increases acceptance rate: - PAYOLUTION_ITEM_DESCR_1 - PAYOLUTION_ITEM_PRICE_1 - PAYOLUTION_ITEM_TAX_1  Fulfillment dates, delays due date for customer: - PAYOLUTION_FULFILLMENT_START - PAYOLUTION_FULFILLMENT_END
     *
     * @return additionalInformation
     */
    public String getAdditionalInformation() {
        return additionalInformation;
    }
    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }
    public InitializePurchaseRequest withAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
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
        InitializePurchaseRequest initializePurchaseRequest = (InitializePurchaseRequest) o;
        return Objects.equals(this.purchaseAmount, initializePurchaseRequest.purchaseAmount)
                && Objects.equals(this.consumer, initializePurchaseRequest.consumer)
                && Objects.equals(this.merchantReference, initializePurchaseRequest.merchantReference)
                && Objects.equals(this.additionalInformation, initializePurchaseRequest.additionalInformation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(purchaseAmount, consumer, merchantReference, additionalInformation);
    }

    @Override
    public String toString() {
        return "class InitializePurchaseRequest {\n"
                + "        purchaseAmount: " + toIndentedString(purchaseAmount) + "\n"
                + "        consumer: " + toIndentedString(consumer) + "\n"
                + "        merchantReference: " + toIndentedString(merchantReference) + "\n"
                + "        additionalInformation: " + toIndentedString(additionalInformation) + "\n"
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

