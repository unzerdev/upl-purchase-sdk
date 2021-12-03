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
 * Represents a postal address of a consumer.
 */
public class Address {

    private String street;
    private String houseNumber;
    private String additionalInfo;
    private String zipCode;
    private String city;
    private Country countryCode;
    private String state;

    /**
     * @return street
     */
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public Address withStreet(String street) {
        this.street = street;
        return this;
    }

    /**
     * @return houseNumber
     */
    public String getHouseNumber() {
        return houseNumber;
    }
    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }
    public Address withHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
        return this;
    }

    /**
     * @return additionalInfo
     */
    public String getAdditionalInfo() {
        return additionalInfo;
    }
    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
    public Address withAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
        return this;
    }

    /**
     * @return zipCode
     */
    public String getZipCode() {
        return zipCode;
    }
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
    public Address withZipCode(String zipCode) {
        this.zipCode = zipCode;
        return this;
    }

    /**
     * @return city
     */
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public Address withCity(String city) {
        this.city = city;
        return this;
    }

    /**
     * @return countryCode
     */
    public Country getCountryCode() {
        return countryCode;
    }
    public void setCountryCode(Country countryCode) {
        this.countryCode = countryCode;
    }
    public Address withCountryCode(Country countryCode) {
        this.countryCode = countryCode;
        return this;
    }

    /**
     * @return state
     */
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public Address withState(String state) {
        this.state = state;
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
        Address address = (Address) o;
        return Objects.equals(this.street, address.street)
                && Objects.equals(this.houseNumber, address.houseNumber)
                && Objects.equals(this.additionalInfo, address.additionalInfo)
                && Objects.equals(this.zipCode, address.zipCode)
                && Objects.equals(this.city, address.city)
                && Objects.equals(this.countryCode, address.countryCode)
                && Objects.equals(this.state, address.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, houseNumber, additionalInfo, zipCode, city, countryCode, state);
    }

    @Override
    public String toString() {
        return "class Address {\n"
                + "        street: " + toIndentedString(street) + "\n"
                + "        houseNumber: " + toIndentedString(houseNumber) + "\n"
                + "        additionalInfo: " + toIndentedString(additionalInfo) + "\n"
                + "        zipCode: " + toIndentedString(zipCode) + "\n"
                + "        city: " + toIndentedString(city) + "\n"
                + "        countryCode: " + toIndentedString(countryCode) + "\n"
                + "        state: " + toIndentedString(state) + "\n"
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

