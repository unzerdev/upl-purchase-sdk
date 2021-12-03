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

/**
 * Properties of a natural person.
 */
public class Person {

    private String salutation;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private String socialId;
    private Occupation occupation;

    /**
     * @return salutation
     */
    public String getSalutation() {
        return salutation;
    }
    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }
    public Person withSalutation(String salutation) {
        this.salutation = salutation;
        return this;
    }

    /**
     * @return firstName
     */
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public Person withFirstName(String firstName) {
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
    public Person withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    /**
     * @return birthdate
     */
    public LocalDate getBirthdate() {
        return birthdate;
    }
    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }
    public Person withBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
        return this;
    }

    /**
     * @return socialId
     */
    public String getSocialId() {
        return socialId;
    }
    public void setSocialId(String socialId) {
        this.socialId = socialId;
    }
    public Person withSocialId(String socialId) {
        this.socialId = socialId;
        return this;
    }

    /**
     * @return occupation
     */
    public Occupation getOccupation() {
        return occupation;
    }
    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }
    public Person withOccupation(Occupation occupation) {
        this.occupation = occupation;
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
        Person person = (Person) o;
        return Objects.equals(this.salutation, person.salutation)
                && Objects.equals(this.firstName, person.firstName)
                && Objects.equals(this.lastName, person.lastName)
                && Objects.equals(this.birthdate, person.birthdate)
                && Objects.equals(this.socialId, person.socialId)
                && Objects.equals(this.occupation, person.occupation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(salutation, firstName, lastName, birthdate, socialId, occupation);
    }

    @Override
    public String toString() {
        return "class Person {\n"
                + "        salutation: " + toIndentedString(salutation) + "\n"
                + "        firstName: " + toIndentedString(firstName) + "\n"
                + "        lastName: " + toIndentedString(lastName) + "\n"
                + "        birthdate: " + toIndentedString(birthdate) + "\n"
                + "        socialId: " + toIndentedString(socialId) + "\n"
                + "        occupation: " + toIndentedString(occupation) + "\n"
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

