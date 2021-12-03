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
 * Represents a consumer.
 */
public class Consumer {

    private Person person;
    private Company company;
    private Account bankAccount;
    private Address billingAddress;
    private DeliveryAddress deliveryAddress;
    private DeliveryType deliveryType;
    private Language language;
    private String phone;
    private String email;

    /**
     * @return person
     */
    public Person getPerson() {
        return person;
    }
    public void setPerson(Person person) {
        this.person = person;
    }
    public Consumer withPerson(Person person) {
        this.person = person;
        return this;
    }

    /**
     * @return company
     */
    public Company getCompany() {
        return company;
    }
    public void setCompany(Company company) {
        this.company = company;
    }
    public Consumer withCompany(Company company) {
        this.company = company;
        return this;
    }

    /**
     * @return bankAccount
     */
    public Account getBankAccount() {
        return bankAccount;
    }
    public void setBankAccount(Account bankAccount) {
        this.bankAccount = bankAccount;
    }
    public Consumer withBankAccount(Account bankAccount) {
        this.bankAccount = bankAccount;
        return this;
    }

    /**
     * @return billingAddress
     */
    public Address getBillingAddress() {
        return billingAddress;
    }
    public void setBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
    }
    public Consumer withBillingAddress(Address billingAddress) {
        this.billingAddress = billingAddress;
        return this;
    }

    /**
     * @return deliveryAddress
     */
    public DeliveryAddress getDeliveryAddress() {
        return deliveryAddress;
    }
    public void setDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }
    public Consumer withDeliveryAddress(DeliveryAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
        return this;
    }

    /**
     * @return deliveryType
     */
    public DeliveryType getDeliveryType() {
        return deliveryType;
    }
    public void setDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }
    public Consumer withDeliveryType(DeliveryType deliveryType) {
        this.deliveryType = deliveryType;
        return this;
    }

    /**
     * @return language
     */
    public Language getLanguage() {
        return language;
    }
    public void setLanguage(Language language) {
        this.language = language;
    }
    public Consumer withLanguage(Language language) {
        this.language = language;
        return this;
    }

    /**
     * @return phone
     */
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public Consumer withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Consumer withEmail(String email) {
        this.email = email;
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
        Consumer consumer = (Consumer) o;
        return Objects.equals(this.person, consumer.person)
                && Objects.equals(this.company, consumer.company)
                && Objects.equals(this.bankAccount, consumer.bankAccount)
                && Objects.equals(this.billingAddress, consumer.billingAddress)
                && Objects.equals(this.deliveryAddress, consumer.deliveryAddress)
                && Objects.equals(this.deliveryType, consumer.deliveryType)
                && Objects.equals(this.language, consumer.language)
                && Objects.equals(this.phone, consumer.phone)
                && Objects.equals(this.email, consumer.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(person, company, bankAccount, billingAddress, deliveryAddress, deliveryType, language, phone, email);
    }

    @Override
    public String toString() {
        return "class Consumer {\n"
                + "        person: " + toIndentedString(person) + "\n"
                + "        company: " + toIndentedString(company) + "\n"
                + "        bankAccount: " + toIndentedString(bankAccount) + "\n"
                + "        billingAddress: " + toIndentedString(billingAddress) + "\n"
                + "        deliveryAddress: " + toIndentedString(deliveryAddress) + "\n"
                + "        deliveryType: " + toIndentedString(deliveryType) + "\n"
                + "        language: " + toIndentedString(language) + "\n"
                + "        phone: " + toIndentedString(phone) + "\n"
                + "        email: " + toIndentedString(email) + "\n"
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

