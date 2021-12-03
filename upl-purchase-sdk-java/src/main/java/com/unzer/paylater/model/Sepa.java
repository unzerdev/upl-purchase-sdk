/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 *
 * This class is based on the Unzer Pay Later OpenAPI specification, version 1.0.0.
 */
package com.unzer.paylater.model;

import java.util.Objects;

public class Sepa {

    private String iban;
    private String bic;

    /**
     * @return iban
     */
    public String getIban() {
        return iban;
    }
    public void setIban(String iban) {
        this.iban = iban;
    }
    public Sepa withIban(String iban) {
        this.iban = iban;
        return this;
    }

    /**
     * @return bic
     */
    public String getBic() {
        return bic;
    }
    public void setBic(String bic) {
        this.bic = bic;
    }
    public Sepa withBic(String bic) {
        this.bic = bic;
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
        Sepa sepa = (Sepa) o;
        return Objects.equals(this.iban, sepa.iban)
                && Objects.equals(this.bic, sepa.bic);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iban, bic);
    }

    @Override
    public String toString() {
        return "class Sepa {\n"
                + "        iban: " + toIndentedString(iban) + "\n"
                + "        bic: " + toIndentedString(bic) + "\n"
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

