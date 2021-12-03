/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 *
 * This class is based on the Unzer Pay Later OpenAPI specification, version 1.0.0.
 */
package com.unzer.paylater.model;

import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Describes the current state of a purchase.
 */
public class PurchaseInformation {

    private String purchaseId;
    private PurchaseState state;
    private Currency currency;
    private Amount authorizedAmount;
    private Amount capturedAmount;
    private Amount remainingCaptureAmount;
    private Amount refundedAmount;
    private Amount remainingRefundableAmount;
    private Amount purchaseAmount;
    private Consumer consumer;
    private ConsumerVerification consumerVerification;
    private MerchantReference merchantReference;
    private PaymentInformation paymentInformation;
    private List<PaymentOption> paymentOptions;
    private List<OperationInformation> captures;
    private List<OperationInformation> refunds;
    private List<Document> documents;
    private Map<String, String> metaData;

    /**
     * @return purchaseId
     */
    public String getPurchaseId() {
        return purchaseId;
    }
    public void setPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
    }
    public PurchaseInformation withPurchaseId(String purchaseId) {
        this.purchaseId = purchaseId;
        return this;
    }

    /**
     * @return state
     */
    public PurchaseState getState() {
        return state;
    }
    public void setState(PurchaseState state) {
        this.state = state;
    }
    public PurchaseInformation withState(PurchaseState state) {
        this.state = state;
        return this;
    }

    /**
     * @return currency
     */
    public Currency getCurrency() {
        return currency;
    }
    public void setCurrency(Currency currency) {
        this.currency = currency;
    }
    public PurchaseInformation withCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    /**
     * @return authorizedAmount
     */
    public Amount getAuthorizedAmount() {
        return authorizedAmount;
    }
    public void setAuthorizedAmount(Amount authorizedAmount) {
        this.authorizedAmount = authorizedAmount;
    }
    public PurchaseInformation withAuthorizedAmount(Amount authorizedAmount) {
        this.authorizedAmount = authorizedAmount;
        return this;
    }

    /**
     * @return capturedAmount
     */
    public Amount getCapturedAmount() {
        return capturedAmount;
    }
    public void setCapturedAmount(Amount capturedAmount) {
        this.capturedAmount = capturedAmount;
    }
    public PurchaseInformation withCapturedAmount(Amount capturedAmount) {
        this.capturedAmount = capturedAmount;
        return this;
    }

    /**
     * @return remainingCaptureAmount
     */
    public Amount getRemainingCaptureAmount() {
        return remainingCaptureAmount;
    }
    public void setRemainingCaptureAmount(Amount remainingCaptureAmount) {
        this.remainingCaptureAmount = remainingCaptureAmount;
    }
    public PurchaseInformation withRemainingCaptureAmount(Amount remainingCaptureAmount) {
        this.remainingCaptureAmount = remainingCaptureAmount;
        return this;
    }

    /**
     * @return refundedAmount
     */
    public Amount getRefundedAmount() {
        return refundedAmount;
    }
    public void setRefundedAmount(Amount refundedAmount) {
        this.refundedAmount = refundedAmount;
    }
    public PurchaseInformation withRefundedAmount(Amount refundedAmount) {
        this.refundedAmount = refundedAmount;
        return this;
    }

    /**
     * @return remainingRefundableAmount
     */
    public Amount getRemainingRefundableAmount() {
        return remainingRefundableAmount;
    }
    public void setRemainingRefundableAmount(Amount remainingRefundableAmount) {
        this.remainingRefundableAmount = remainingRefundableAmount;
    }
    public PurchaseInformation withRemainingRefundableAmount(Amount remainingRefundableAmount) {
        this.remainingRefundableAmount = remainingRefundableAmount;
        return this;
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
    public PurchaseInformation withPurchaseAmount(Amount purchaseAmount) {
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
    public PurchaseInformation withConsumer(Consumer consumer) {
        this.consumer = consumer;
        return this;
    }

    /**
     * @return consumerVerification
     */
    public ConsumerVerification getConsumerVerification() {
        return consumerVerification;
    }
    public void setConsumerVerification(ConsumerVerification consumerVerification) {
        this.consumerVerification = consumerVerification;
    }
    public PurchaseInformation withConsumerVerification(ConsumerVerification consumerVerification) {
        this.consumerVerification = consumerVerification;
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
    public PurchaseInformation withMerchantReference(MerchantReference merchantReference) {
        this.merchantReference = merchantReference;
        return this;
    }

    /**
     * @return paymentInformation
     */
    public PaymentInformation getPaymentInformation() {
        return paymentInformation;
    }
    public void setPaymentInformation(PaymentInformation paymentInformation) {
        this.paymentInformation = paymentInformation;
    }
    public PurchaseInformation withPaymentInformation(PaymentInformation paymentInformation) {
        this.paymentInformation = paymentInformation;
        return this;
    }

    /**
     * @return paymentOptions
     */
    public List<PaymentOption> getPaymentOptions() {
        return paymentOptions;
    }
    public void setPaymentOptions(List<PaymentOption> paymentOptions) {
        this.paymentOptions = paymentOptions;
    }
    public PurchaseInformation withPaymentOptions(List<PaymentOption> paymentOptions) {
        this.paymentOptions = paymentOptions;
        return this;
    }
    public PurchaseInformation addPaymentOptions(PaymentOption value) {
        if (this.paymentOptions == null) {
            this.paymentOptions = new ArrayList<>();
        }
        this.paymentOptions.add(value);
        return this;
    }

    /**
     * Performed captures.
     *
     * @return captures
     */
    public List<OperationInformation> getCaptures() {
        return captures;
    }
    public void setCaptures(List<OperationInformation> captures) {
        this.captures = captures;
    }
    public PurchaseInformation withCaptures(List<OperationInformation> captures) {
        this.captures = captures;
        return this;
    }
    public PurchaseInformation addCaptures(OperationInformation value) {
        if (this.captures == null) {
            this.captures = new ArrayList<>();
        }
        this.captures.add(value);
        return this;
    }

    /**
     * Performed refunds.
     *
     * @return refunds
     */
    public List<OperationInformation> getRefunds() {
        return refunds;
    }
    public void setRefunds(List<OperationInformation> refunds) {
        this.refunds = refunds;
    }
    public PurchaseInformation withRefunds(List<OperationInformation> refunds) {
        this.refunds = refunds;
        return this;
    }
    public PurchaseInformation addRefunds(OperationInformation value) {
        if (this.refunds == null) {
            this.refunds = new ArrayList<>();
        }
        this.refunds.add(value);
        return this;
    }

    /**
     * Static documents.
     *
     * @return documents
     */
    public List<Document> getDocuments() {
        return documents;
    }
    public void setDocuments(List<Document> documents) {
        this.documents = documents;
    }
    public PurchaseInformation withDocuments(List<Document> documents) {
        this.documents = documents;
        return this;
    }
    public PurchaseInformation addDocuments(Document value) {
        if (this.documents == null) {
            this.documents = new ArrayList<>();
        }
        this.documents.add(value);
        return this;
    }

    /**
     * Additional information provided as a key value map.
     *
     * @return metaData
     */
    public Map<String, String> getMetaData() {
        return metaData;
    }
    public void setMetaData(Map<String, String> metaData) {
        this.metaData = metaData;
    }
    public PurchaseInformation withMetaData(Map<String, String> metaData) {
        this.metaData = metaData;
        return this;
    }
    public PurchaseInformation addMetaData(String key, String value) {
        if (this.metaData == null) {
            this.metaData = new HashMap<>();
        }
        this.metaData.put(key, value);
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
        PurchaseInformation purchaseInformation = (PurchaseInformation) o;
        return Objects.equals(this.purchaseId, purchaseInformation.purchaseId)
                && Objects.equals(this.state, purchaseInformation.state)
                && Objects.equals(this.currency, purchaseInformation.currency)
                && Objects.equals(this.authorizedAmount, purchaseInformation.authorizedAmount)
                && Objects.equals(this.capturedAmount, purchaseInformation.capturedAmount)
                && Objects.equals(this.remainingCaptureAmount, purchaseInformation.remainingCaptureAmount)
                && Objects.equals(this.refundedAmount, purchaseInformation.refundedAmount)
                && Objects.equals(this.remainingRefundableAmount, purchaseInformation.remainingRefundableAmount)
                && Objects.equals(this.purchaseAmount, purchaseInformation.purchaseAmount)
                && Objects.equals(this.consumer, purchaseInformation.consumer)
                && Objects.equals(this.consumerVerification, purchaseInformation.consumerVerification)
                && Objects.equals(this.merchantReference, purchaseInformation.merchantReference)
                && Objects.equals(this.paymentInformation, purchaseInformation.paymentInformation)
                && Objects.equals(this.paymentOptions, purchaseInformation.paymentOptions)
                && Objects.equals(this.captures, purchaseInformation.captures)
                && Objects.equals(this.refunds, purchaseInformation.refunds)
                && Objects.equals(this.documents, purchaseInformation.documents)
                && Objects.equals(this.metaData, purchaseInformation.metaData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(purchaseId, state, currency, authorizedAmount, capturedAmount, remainingCaptureAmount, refundedAmount, remainingRefundableAmount, purchaseAmount, consumer, consumerVerification, merchantReference, paymentInformation, paymentOptions, captures, refunds, documents, metaData);
    }

    @Override
    public String toString() {
        return "class PurchaseInformation {\n"
                + "        purchaseId: " + toIndentedString(purchaseId) + "\n"
                + "        state: " + toIndentedString(state) + "\n"
                + "        currency: " + toIndentedString(currency) + "\n"
                + "        authorizedAmount: " + toIndentedString(authorizedAmount) + "\n"
                + "        capturedAmount: " + toIndentedString(capturedAmount) + "\n"
                + "        remainingCaptureAmount: " + toIndentedString(remainingCaptureAmount) + "\n"
                + "        refundedAmount: " + toIndentedString(refundedAmount) + "\n"
                + "        remainingRefundableAmount: " + toIndentedString(remainingRefundableAmount) + "\n"
                + "        purchaseAmount: " + toIndentedString(purchaseAmount) + "\n"
                + "        consumer: " + toIndentedString(consumer) + "\n"
                + "        consumerVerification: " + toIndentedString(consumerVerification) + "\n"
                + "        merchantReference: " + toIndentedString(merchantReference) + "\n"
                + "        paymentInformation: " + toIndentedString(paymentInformation) + "\n"
                + "        paymentOptions: " + toIndentedString(paymentOptions) + "\n"
                + "        captures: " + toIndentedString(captures) + "\n"
                + "        refunds: " + toIndentedString(refunds) + "\n"
                + "        documents: " + toIndentedString(documents) + "\n"
                + "        metaData: " + toIndentedString(metaData) + "\n"
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

