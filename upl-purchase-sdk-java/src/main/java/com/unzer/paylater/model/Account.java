/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 *
 * This class is based on the Unzer Pay Later OpenAPI specification, version 1.0.0.
 */
package com.unzer.paylater.model;

import java.util.Objects;

/**
 * Represents a bank account of a consumer. Contains holder information and different types of routing information.
 */
public class Account {

    private String holder;
    private Country country;
    private Sepa sepa;
    private Eft eft;
    private Ach ach;
    private Bacs bacs;

    /**
     * @return holder
     */
    public String getHolder() {
        return holder;
    }
    public void setHolder(String holder) {
        this.holder = holder;
    }
    public Account withHolder(String holder) {
        this.holder = holder;
        return this;
    }

    /**
     * @return country
     */
    public Country getCountry() {
        return country;
    }
    public void setCountry(Country country) {
        this.country = country;
    }
    public Account withCountry(Country country) {
        this.country = country;
        return this;
    }

    /**
     * @return sepa
     */
    public Sepa getSepa() {
        return sepa;
    }
    public void setSepa(Sepa sepa) {
        this.sepa = sepa;
    }
    public Account withSepa(Sepa sepa) {
        this.sepa = sepa;
        return this;
    }

    /**
     * @return eft
     */
    public Eft getEft() {
        return eft;
    }
    public void setEft(Eft eft) {
        this.eft = eft;
    }
    public Account withEft(Eft eft) {
        this.eft = eft;
        return this;
    }

    /**
     * @return ach
     */
    public Ach getAch() {
        return ach;
    }
    public void setAch(Ach ach) {
        this.ach = ach;
    }
    public Account withAch(Ach ach) {
        this.ach = ach;
        return this;
    }

    /**
     * @return bacs
     */
    public Bacs getBacs() {
        return bacs;
    }
    public void setBacs(Bacs bacs) {
        this.bacs = bacs;
    }
    public Account withBacs(Bacs bacs) {
        this.bacs = bacs;
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
        Account account = (Account) o;
        return Objects.equals(this.holder, account.holder)
                && Objects.equals(this.country, account.country)
                && Objects.equals(this.sepa, account.sepa)
                && Objects.equals(this.eft, account.eft)
                && Objects.equals(this.ach, account.ach)
                && Objects.equals(this.bacs, account.bacs);
    }

    @Override
    public int hashCode() {
        return Objects.hash(holder, country, sepa, eft, ach, bacs);
    }

    @Override
    public String toString() {
        return "class Account {\n"
                + "        holder: " + toIndentedString(holder) + "\n"
                + "        country: " + toIndentedString(country) + "\n"
                + "        sepa: " + toIndentedString(sepa) + "\n"
                + "        eft: " + toIndentedString(eft) + "\n"
                + "        ach: " + toIndentedString(ach) + "\n"
                + "        bacs: " + toIndentedString(bacs) + "\n"
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

