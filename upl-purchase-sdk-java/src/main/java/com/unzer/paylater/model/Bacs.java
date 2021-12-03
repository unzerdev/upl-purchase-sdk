/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 *
 * This class is based on the Unzer Pay Later OpenAPI specification, version 1.0.0.
 */
package com.unzer.paylater.model;

import java.util.Objects;

public class Bacs {

    private String accountNumber;
    private String sortCode;

    /**
     * @return accountNumber
     */
    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    public Bacs withAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
        return this;
    }

    /**
     * @return sortCode
     */
    public String getSortCode() {
        return sortCode;
    }
    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }
    public Bacs withSortCode(String sortCode) {
        this.sortCode = sortCode;
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
        Bacs bacs = (Bacs) o;
        return Objects.equals(this.accountNumber, bacs.accountNumber)
                && Objects.equals(this.sortCode, bacs.sortCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber, sortCode);
    }

    @Override
    public String toString() {
        return "class Bacs {\n"
                + "        accountNumber: " + toIndentedString(accountNumber) + "\n"
                + "        sortCode: " + toIndentedString(sortCode) + "\n"
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

