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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PaymentOption {

    private String optionId;
    private Country consumerCountry;
    private Currency currency;
    private ProductType productType;
    private List<PaymentMethod> supportedPaymentMethods;
    private Amount totalAmount;
    private Amount purchaseAmount;
    private Double interestRate;
    private Double effectiveInterestRate;
    private BigDecimal numberOfPayments;
    private List<Payment> payments;
    private List<Contract> contracts;

    /**
     * @return optionId
     */
    public String getOptionId() {
        return optionId;
    }
    public void setOptionId(String optionId) {
        this.optionId = optionId;
    }
    public PaymentOption withOptionId(String optionId) {
        this.optionId = optionId;
        return this;
    }

    /**
     * @return consumerCountry
     */
    public Country getConsumerCountry() {
        return consumerCountry;
    }
    public void setConsumerCountry(Country consumerCountry) {
        this.consumerCountry = consumerCountry;
    }
    public PaymentOption withConsumerCountry(Country consumerCountry) {
        this.consumerCountry = consumerCountry;
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
    public PaymentOption withCurrency(Currency currency) {
        this.currency = currency;
        return this;
    }

    /**
     * @return productType
     */
    public ProductType getProductType() {
        return productType;
    }
    public void setProductType(ProductType productType) {
        this.productType = productType;
    }
    public PaymentOption withProductType(ProductType productType) {
        this.productType = productType;
        return this;
    }

    /**
     * @return supportedPaymentMethods
     */
    public List<PaymentMethod> getSupportedPaymentMethods() {
        return supportedPaymentMethods;
    }
    public void setSupportedPaymentMethods(List<PaymentMethod> supportedPaymentMethods) {
        this.supportedPaymentMethods = supportedPaymentMethods;
    }
    public PaymentOption withSupportedPaymentMethods(List<PaymentMethod> supportedPaymentMethods) {
        this.supportedPaymentMethods = supportedPaymentMethods;
        return this;
    }
    public PaymentOption addSupportedPaymentMethods(PaymentMethod value) {
        if (this.supportedPaymentMethods == null) {
            this.supportedPaymentMethods = new ArrayList<>();
        }
        this.supportedPaymentMethods.add(value);
        return this;
    }

    /**
     * @return totalAmount
     */
    public Amount getTotalAmount() {
        return totalAmount;
    }
    public void setTotalAmount(Amount totalAmount) {
        this.totalAmount = totalAmount;
    }
    public PaymentOption withTotalAmount(Amount totalAmount) {
        this.totalAmount = totalAmount;
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
    public PaymentOption withPurchaseAmount(Amount purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
        return this;
    }

    /**
     * @return interestRate
     */
    public Double getInterestRate() {
        return interestRate;
    }
    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }
    public PaymentOption withInterestRate(Double interestRate) {
        this.interestRate = interestRate;
        return this;
    }

    /**
     * @return effectiveInterestRate
     */
    public Double getEffectiveInterestRate() {
        return effectiveInterestRate;
    }
    public void setEffectiveInterestRate(Double effectiveInterestRate) {
        this.effectiveInterestRate = effectiveInterestRate;
    }
    public PaymentOption withEffectiveInterestRate(Double effectiveInterestRate) {
        this.effectiveInterestRate = effectiveInterestRate;
        return this;
    }

    /**
     * @return numberOfPayments
     */
    public BigDecimal getNumberOfPayments() {
        return numberOfPayments;
    }
    public void setNumberOfPayments(BigDecimal numberOfPayments) {
        this.numberOfPayments = numberOfPayments;
    }
    public PaymentOption withNumberOfPayments(BigDecimal numberOfPayments) {
        this.numberOfPayments = numberOfPayments;
        return this;
    }

    /**
     * @return payments
     */
    public List<Payment> getPayments() {
        return payments;
    }
    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
    public PaymentOption withPayments(List<Payment> payments) {
        this.payments = payments;
        return this;
    }
    public PaymentOption addPayments(Payment value) {
        if (this.payments == null) {
            this.payments = new ArrayList<>();
        }
        this.payments.add(value);
        return this;
    }

    /**
     * @return contracts
     */
    public List<Contract> getContracts() {
        return contracts;
    }
    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }
    public PaymentOption withContracts(List<Contract> contracts) {
        this.contracts = contracts;
        return this;
    }
    public PaymentOption addContracts(Contract value) {
        if (this.contracts == null) {
            this.contracts = new ArrayList<>();
        }
        this.contracts.add(value);
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
        PaymentOption paymentOption = (PaymentOption) o;
        return Objects.equals(this.optionId, paymentOption.optionId)
                && Objects.equals(this.consumerCountry, paymentOption.consumerCountry)
                && Objects.equals(this.currency, paymentOption.currency)
                && Objects.equals(this.productType, paymentOption.productType)
                && Objects.equals(this.supportedPaymentMethods, paymentOption.supportedPaymentMethods)
                && Objects.equals(this.totalAmount, paymentOption.totalAmount)
                && Objects.equals(this.purchaseAmount, paymentOption.purchaseAmount)
                && Objects.equals(this.interestRate, paymentOption.interestRate)
                && Objects.equals(this.effectiveInterestRate, paymentOption.effectiveInterestRate)
                && Objects.equals(this.numberOfPayments, paymentOption.numberOfPayments)
                && Objects.equals(this.payments, paymentOption.payments)
                && Objects.equals(this.contracts, paymentOption.contracts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(optionId, consumerCountry, currency, productType, supportedPaymentMethods, totalAmount, purchaseAmount, interestRate, effectiveInterestRate, numberOfPayments, payments, contracts);
    }

    @Override
    public String toString() {
        return "class PaymentOption {\n"
                + "        optionId: " + toIndentedString(optionId) + "\n"
                + "        consumerCountry: " + toIndentedString(consumerCountry) + "\n"
                + "        currency: " + toIndentedString(currency) + "\n"
                + "        productType: " + toIndentedString(productType) + "\n"
                + "        supportedPaymentMethods: " + toIndentedString(supportedPaymentMethods) + "\n"
                + "        totalAmount: " + toIndentedString(totalAmount) + "\n"
                + "        purchaseAmount: " + toIndentedString(purchaseAmount) + "\n"
                + "        interestRate: " + toIndentedString(interestRate) + "\n"
                + "        effectiveInterestRate: " + toIndentedString(effectiveInterestRate) + "\n"
                + "        numberOfPayments: " + toIndentedString(numberOfPayments) + "\n"
                + "        payments: " + toIndentedString(payments) + "\n"
                + "        contracts: " + toIndentedString(contracts) + "\n"
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

