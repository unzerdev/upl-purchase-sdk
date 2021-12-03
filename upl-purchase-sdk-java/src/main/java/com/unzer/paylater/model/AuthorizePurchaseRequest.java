/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 *
 * This class is based on the Unzer Pay Later OpenAPI specification, version 1.0.0.
 */
package com.unzer.paylater.model;

import java.util.Objects;

public class AuthorizePurchaseRequest {

    private String purchaseId;
    private MethodType method;
    private String phone;
    private String successUrl;
    private String callbackUrl;

    public AuthorizePurchaseRequest() { }

    /**
     * This constructor contains all fields required by the API.
     *
     * @param purchaseId PurchaseId received from initializePurchase or authorizePurchase response.
     * @param method
     */
    public AuthorizePurchaseRequest(String purchaseId, MethodType method) { 
        this.purchaseId = purchaseId;
        this.method = method;
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
    public AuthorizePurchaseRequest withPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
        return this;
    }

    /**
     * @return method
     */
    public MethodType getMethod() {
        return method;
    }
    public void setMethod(MethodType method) {
        this.method = method;
    }
    public AuthorizePurchaseRequest withMethod(MethodType method) {
        this.method = method;
        return this;
    }

    /**
     * If method 'SMS' is chosen, a phone number must be provided and will receive a message to start the verify process.
     *
     * @return phone
     */
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public AuthorizePurchaseRequest withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    /**
     * Redirect URL for the merchant after finishing the flow.
     *
     * @return successUrl
     */
    public String getSuccessUrl() {
        return successUrl;
    }
    public void setSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
    }
    public AuthorizePurchaseRequest withSuccessUrl(String successUrl) {
        this.successUrl = successUrl;
        return this;
    }

    /**
     * After successfully finishing the flow, this URL will receive a callback to indicate completion to the merchant.
     *
     * @return callbackUrl
     */
    public String getCallbackUrl() {
        return callbackUrl;
    }
    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }
    public AuthorizePurchaseRequest withCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
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
        AuthorizePurchaseRequest authorizePurchaseRequest = (AuthorizePurchaseRequest) o;
        return Objects.equals(this.purchaseId, authorizePurchaseRequest.purchaseId)
                && Objects.equals(this.phone, authorizePurchaseRequest.phone)
                && Objects.equals(this.method, authorizePurchaseRequest.method)
                && Objects.equals(this.successUrl, authorizePurchaseRequest.successUrl)
                && Objects.equals(this.callbackUrl, authorizePurchaseRequest.callbackUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(purchaseId, phone, method, successUrl, callbackUrl);
    }

    @Override
    public String toString() {
        return "class AuthorizePurchaseRequest {\n"
                + "        purchaseId: " + toIndentedString(purchaseId) + "\n"
                + "        phone: " + toIndentedString(phone) + "\n"
                + "        method: " + toIndentedString(method) + "\n"
                + "        successUrl: " + toIndentedString(successUrl) + "\n"
                + "        callbackUrl: " + toIndentedString(callbackUrl) + "\n"
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

