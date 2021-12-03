/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 *
 * This class is based on the Unzer Pay Later OpenAPI specification, version 1.0.0.
 */
package com.unzer.paylater.model;

import java.util.Objects;

public class Contract {

    private String name;
    private DocumentType type;
    private String id;
    private String url;

    /**
     * @return name
     */
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Contract withName(String name) {
        this.name = name;
        return this;
    }

    /**
     * @return type
     */
    public DocumentType getType() {
        return type;
    }
    public void setType(DocumentType type) {
        this.type = type;
    }
    public Contract withType(DocumentType type) {
        this.type = type;
        return this;
    }

    /**
     * @return id
     */
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Contract withId(String id) {
        this.id = id;
        return this;
    }

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public Contract withUrl(String url) {
        this.url = url;
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
        Contract contract = (Contract) o;
        return Objects.equals(this.name, contract.name)
                && Objects.equals(this.type, contract.type)
                && Objects.equals(this.id, contract.id)
                && Objects.equals(this.url, contract.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, id, url);
    }

    @Override
    public String toString() {
        return "class Contract {\n"
                + "        name: " + toIndentedString(name) + "\n"
                + "        type: " + toIndentedString(type) + "\n"
                + "        id: " + toIndentedString(id) + "\n"
                + "        url: " + toIndentedString(url) + "\n"
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

