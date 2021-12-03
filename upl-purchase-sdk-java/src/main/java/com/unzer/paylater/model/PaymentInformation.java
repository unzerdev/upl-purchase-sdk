/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 *
 * This class is based on the Unzer Pay Later OpenAPI specification, version 1.0.0.
 */
package com.unzer.paylater.model;

import java.util.Objects;

public class PaymentInformation {

    private String paymentReference;
    private Account account;
    private PaymentMethod paymentMethod;
    private PaymentOption confirmedPaymentOption;

    /**
     * @return paymentReference
     */
    public String getPaymentReference() {
        return paymentReference;
    }
    public void setPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
    }
    public PaymentInformation withPaymentReference(String paymentReference) {
        this.paymentReference = paymentReference;
        return this;
    }

    /**
     * @return account
     */
    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }
    public PaymentInformation withAccount(Account account) {
        this.account = account;
        return this;
    }

    /**
     * @return paymentMethod
     */
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }
    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public PaymentInformation withPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
        return this;
    }

    /**
     * @return confirmedPaymentOption
     */
    public PaymentOption getConfirmedPaymentOption() {
        return confirmedPaymentOption;
    }
    public void setConfirmedPaymentOption(PaymentOption confirmedPaymentOption) {
        this.confirmedPaymentOption = confirmedPaymentOption;
    }
    public PaymentInformation withConfirmedPaymentOption(PaymentOption confirmedPaymentOption) {
        this.confirmedPaymentOption = confirmedPaymentOption;
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
        PaymentInformation paymentInformation = (PaymentInformation) o;
        return Objects.equals(this.paymentReference, paymentInformation.paymentReference)
                && Objects.equals(this.account, paymentInformation.account)
                && Objects.equals(this.paymentMethod, paymentInformation.paymentMethod)
                && Objects.equals(this.confirmedPaymentOption, paymentInformation.confirmedPaymentOption);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paymentReference, account, paymentMethod, confirmedPaymentOption);
    }

    @Override
    public String toString() {
        return "class PaymentInformation {\n"
                + "        paymentReference: " + toIndentedString(paymentReference) + "\n"
                + "        account: " + toIndentedString(account) + "\n"
                + "        paymentMethod: " + toIndentedString(paymentMethod) + "\n"
                + "        confirmedPaymentOption: " + toIndentedString(confirmedPaymentOption) + "\n"
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

