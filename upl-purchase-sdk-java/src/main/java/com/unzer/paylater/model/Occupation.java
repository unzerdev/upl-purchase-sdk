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
 * Occupation of a person.
 */
public class Occupation {

    private String name;
    private String yearlyGrossSalary;
    private String employersName;
    private Address employersAddress;

    /**
     * @return name
     */
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Occupation withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @return yearlyGrossSalary
     */
    public String getYearlyGrossSalary() {
        return yearlyGrossSalary;
    }
    public void setYearlyGrossSalary(String yearlyGrossSalary) {
        this.yearlyGrossSalary = yearlyGrossSalary;
    }
    public Occupation withYearlyGrossSalary(String yearlyGrossSalary) {
        this.yearlyGrossSalary = yearlyGrossSalary;
        return this;
    }

    /**
     * @return employersName
     */
    public String getEmployersName() {
        return employersName;
    }
    public void setEmployersName(String employersName) {
        this.employersName = employersName;
    }
    public Occupation withEmployersName(String employersName) {
        this.employersName = employersName;
        return this;
    }

    /**
     * @return employersAddress
     */
    public Address getEmployersAddress() {
        return employersAddress;
    }
    public void setEmployersAddress(Address employersAddress) {
        this.employersAddress = employersAddress;
    }
    public Occupation withEmployersAddress(Address employersAddress) {
        this.employersAddress = employersAddress;
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
        Occupation occupation = (Occupation) o;
        return Objects.equals(this.name, occupation.name)
                && Objects.equals(this.yearlyGrossSalary, occupation.yearlyGrossSalary)
                && Objects.equals(this.employersName, occupation.employersName)
                && Objects.equals(this.employersAddress, occupation.employersAddress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, yearlyGrossSalary, employersName, employersAddress);
    }

    @Override
    public String toString() {
        return "class Occupation {\n"
                + "        name: " + toIndentedString(name) + "\n"
                + "        yearlyGrossSalary: " + toIndentedString(yearlyGrossSalary) + "\n"
                + "        employersName: " + toIndentedString(employersName) + "\n"
                + "        employersAddress: " + toIndentedString(employersAddress) + "\n"
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

