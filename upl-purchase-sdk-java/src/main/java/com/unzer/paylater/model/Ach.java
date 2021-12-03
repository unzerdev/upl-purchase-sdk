/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 *
 * This class is based on the Unzer Pay Later OpenAPI specification, version 1.0.0.
 */
package com.unzer.paylater.model;

import java.util.Objects;

public class Ach {

    private String accountNumber;
    private AchAccountType accountType;
    private String routingNumber;

    /**
     * @return accountNumber
     */
    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public Ach withAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    /**
     * @return accountType
     */
    public AchAccountType getAccountType() {
        return accountType;
    }
    public void setAccountType(AchAccountType accountType) {
        this.accountType = accountType;
    }
    public Ach withAccountType(AchAccountType accountType) {
        this.accountType = accountType;
        return this;
    }

    /**
     * @return routingNumber
     */
    public String getRoutingNumber() {
        return routingNumber;
    }
    public void setRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
    }
    public Ach withRoutingNumber(String routingNumber) {
        this.routingNumber = routingNumber;
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
        Ach ach = (Ach) o;
        return Objects.equals(this.accountNumber, ach.accountNumber)
                && Objects.equals(this.accountType, ach.accountType)
                && Objects.equals(this.routingNumber, ach.routingNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, accountType, routingNumber);
    }

    @Override
    public String toString() {
        return "class Ach {\n"
                + "        accountNumber: " + toIndentedString(accountNumber) + "\n"
                + "        accountType: " + toIndentedString(accountType) + "\n"
                + "        routingNumber: " + toIndentedString(routingNumber) + "\n"
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

