/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 *
 * This class is based on the Unzer Pay Later OpenAPI specification, version 1.0.0.
 */
package com.unzer.paylater.model;

import java.util.Objects;

public class PurchaseOperationResponse {

    private OperationResult result;
    private PurchaseInformation purchase;

    /**
     * @return result
     */
    public OperationResult getResult() {
        return result;
    }
    public void setResult(OperationResult result) {
        this.result = result;
    }
    public PurchaseOperationResponse withResult(OperationResult result) {
        this.result = result;
        return this;
    }

    /**
     * @return purchase
     */
    public PurchaseInformation getPurchase() {
        return purchase;
    }
    public void setPurchase(PurchaseInformation purchase) {
        this.purchase = purchase;
    }
    public PurchaseOperationResponse withPurchase(PurchaseInformation purchase) {
        this.purchase = purchase;
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
        PurchaseOperationResponse purchaseOperationResponse = (PurchaseOperationResponse) o;
        return Objects.equals(this.result, purchaseOperationResponse.result)
                && Objects.equals(this.purchase, purchaseOperationResponse.purchase);
    }

    @Override
    public int hashCode() {
        return Objects.hash(result, purchase);
    }

    @Override
    public String toString() {
        return "class PurchaseOperationResponse {\n"
                + "        result: " + toIndentedString(result) + "\n"
                + "        purchase: " + toIndentedString(purchase) + "\n"
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

