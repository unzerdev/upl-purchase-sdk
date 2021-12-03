/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 *
 * This class is based on the Unzer Pay Later OpenAPI specification, version 1.0.0.
 */
package com.unzer.paylater.model;

import java.util.Objects;

public class Eft {

    private String accountNumber;
    private String transitNumber;
    private String institutionId;

    /**
     * @return accountNumber
     */
    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public Eft withAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    /**
     * @return transitNumber
     */
    public String getTransitNumber() {
        return transitNumber;
    }
    public void setTransitNumber(String transitNumber) {
        this.transitNumber = transitNumber;
    }
    public Eft withTransitNumber(String transitNumber) {
        this.transitNumber = transitNumber;
        return this;
    }

    /**
     * @return institutionId
     */
    public String getInstitutionId() {
        return institutionId;
    }
    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }
    public Eft withInstitutionId(String institutionId) {
        this.institutionId = institutionId;
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
        Eft eft = (Eft) o;
        return Objects.equals(this.accountNumber, eft.accountNumber)
                && Objects.equals(this.transitNumber, eft.transitNumber)
                && Objects.equals(this.institutionId, eft.institutionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, transitNumber, institutionId);
    }

    @Override
    public String toString() {
        return "class Eft {\n"
                + "        accountNumber: " + toIndentedString(accountNumber) + "\n"
                + "        transitNumber: " + toIndentedString(transitNumber) + "\n"
                + "        institutionId: " + toIndentedString(institutionId) + "\n"
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

