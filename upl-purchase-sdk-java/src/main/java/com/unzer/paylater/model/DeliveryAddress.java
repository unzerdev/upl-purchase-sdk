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
 * Address where goods can be delivered to.
 */
public class DeliveryAddress {

    private String firstName;
    private String lastName;
    private String companyName;
    private Address address;

    /**
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public DeliveryAddress withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    /**
     * @return lastName
     */
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public DeliveryAddress withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * @return companyName
     */
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public DeliveryAddress withCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    /**
     * @return address
     */
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    public DeliveryAddress withAddress(Address address) {
        this.address = address;
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
        DeliveryAddress deliveryAddress = (DeliveryAddress) o;
        return Objects.equals(this.firstName, deliveryAddress.firstName)
                && Objects.equals(this.lastName, deliveryAddress.lastName)
                && Objects.equals(this.companyName, deliveryAddress.companyName)
                && Objects.equals(this.address, deliveryAddress.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, companyName, address);
    }

    @Override
    public String toString() {
        return "class DeliveryAddress {\n"
                + "        firstName: " + toIndentedString(firstName) + "\n"
                + "        lastName: " + toIndentedString(lastName) + "\n"
                + "        companyName: " + toIndentedString(companyName) + "\n"
                + "        address: " + toIndentedString(address) + "\n"
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

