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
 * Consumer verification possibilities.
 */
public class ConsumerVerification {

    private String initializeUrl;
    private String verifyUrl;
    private Boolean consumerDataAvailable;

    /**
     * @return initializeUrl
     */
    public String getInitializeUrl() {
        return initializeUrl;
    }
    public void setInitializeUrl(String initializeUrl) {
        this.initializeUrl = initializeUrl;
    }
    public ConsumerVerification withInitializeUrl(String initializeUrl) {
        this.initializeUrl = initializeUrl;
        return this;
    }

    /**
     * @return verifyUrl
     */
    public String getVerifyUrl() {
        return verifyUrl;
    }
    public void setVerifyUrl(String verifyUrl) {
        this.verifyUrl = verifyUrl;
    }
    public ConsumerVerification withVerifyUrl(String verifyUrl) {
        this.verifyUrl = verifyUrl;
        return this;
    }

    /**
     * Flag to state that consumer data are available.
     *
     * @return consumerDataAvailable
     */
    public Boolean getConsumerDataAvailable() {
        return consumerDataAvailable;
    }
    public void setConsumerDataAvailable(Boolean consumerDataAvailable) {
        this.consumerDataAvailable = consumerDataAvailable;
    }
    public ConsumerVerification withConsumerDataAvailable(Boolean consumerDataAvailable) {
        this.consumerDataAvailable = consumerDataAvailable;
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
        ConsumerVerification consumerVerification = (ConsumerVerification) o;
        return Objects.equals(this.initializeUrl, consumerVerification.initializeUrl)
                && Objects.equals(this.verifyUrl, consumerVerification.verifyUrl)
                && Objects.equals(this.consumerDataAvailable, consumerVerification.consumerDataAvailable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(initializeUrl, verifyUrl, consumerDataAvailable);
    }

    @Override
    public String toString() {
        return "class ConsumerVerification {\n"
                + "        initializeUrl: " + toIndentedString(initializeUrl) + "\n"
                + "        verifyUrl: " + toIndentedString(verifyUrl) + "\n"
                + "        consumerDataAvailable: " + toIndentedString(consumerDataAvailable) + "\n"
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

