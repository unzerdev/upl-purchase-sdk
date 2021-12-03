/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 *
 * This class is based on the Unzer Pay Later OpenAPI specification, version 1.0.0.
 */
package com.unzer.paylater.model;

import java.time.LocalDate;
import java.util.Objects;

public class Payment {

    private LocalDate dueDate;
    private Amount paymentAmount;

    /**
     * @return dueDate
     */
    public LocalDate getDueDate() {
        return dueDate;
    }
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    public Payment withDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    /**
     * @return paymentAmount
     */
    public Amount getPaymentAmount() {
        return paymentAmount;
    }
    public void setPaymentAmount(Amount paymentAmount) {
        this.paymentAmount = paymentAmount;
    }
    public Payment withPaymentAmount(Amount paymentAmount) {
        this.paymentAmount = paymentAmount;
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
        Payment payment = (Payment) o;
        return Objects.equals(this.dueDate, payment.dueDate)
                && Objects.equals(this.paymentAmount, payment.paymentAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dueDate, paymentAmount);
    }

    @Override
    public String toString() {
        return "class Payment {\n"
                + "        dueDate: " + toIndentedString(dueDate) + "\n"
                + "        paymentAmount: " + toIndentedString(paymentAmount) + "\n"
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

