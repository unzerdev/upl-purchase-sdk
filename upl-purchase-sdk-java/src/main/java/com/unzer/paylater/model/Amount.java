/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 *
 * This class is based on the Unzer Pay Later OpenAPI specification, version 1.0.0.
 */
package com.unzer.paylater.model;

import java.util.Objects;

public class Amount {

    private Long amount;
    private Currency currency;

    public Amount() { }

    /**
     * This constructor contains all fields required by the API.
     *
     * @param amount Amount in cents.
     * @param currency
     */
    public Amount(Long amount, Currency currency) { 
        this.amount = amount;
        this.currency = currency;
    }

    /**
     * Amount in cents.
     *
     * @return amount
     */
    public Long getAmount() {
        return amount;
    }
    public void setAmount(Long amount) {
        this.amount = amount;
    }
    public Amount withAmount(Long amount) {
        this.amount = amount;
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
    public Amount withCurrency(Currency currency) {
        this.currency = currency;
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
        Amount amount = (Amount) o;
        return Objects.equals(this.amount, amount.amount)
                && Objects.equals(this.currency, amount.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }

    @Override
    public String toString() {
        return "class Amount {\n"
                + "        amount: " + toIndentedString(amount) + "\n"
                + "        currency: " + toIndentedString(currency) + "\n"
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

