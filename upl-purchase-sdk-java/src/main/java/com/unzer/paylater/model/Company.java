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
 * Representation of a company.
 */
public class Company {

    private String firstName;
    private String lastName;
    private String companyName;

    /**
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public Company withFirstName(String firstName) {
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
    public Company withLastName(String lastName) {
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
    public Company withCompanyName(String companyName) {
        this.companyName = companyName;
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
        Company company = (Company) o;
        return Objects.equals(this.firstName, company.firstName)
                && Objects.equals(this.lastName, company.lastName)
                && Objects.equals(this.companyName, company.companyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, companyName);
    }

    @Override
    public String toString() {
        return "class Company {\n"
                + "        firstName: " + toIndentedString(firstName) + "\n"
                + "        lastName: " + toIndentedString(lastName) + "\n"
                + "        companyName: " + toIndentedString(companyName) + "\n"
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

