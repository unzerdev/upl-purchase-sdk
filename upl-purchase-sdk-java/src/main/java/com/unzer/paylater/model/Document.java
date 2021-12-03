/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 *
 * This class is based on the Unzer Pay Later OpenAPI specification, version 1.0.0.
 */
package com.unzer.paylater.model;

import java.util.Objects;

public class Document {

    private String name;
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
    public Document withName(String name) {
        this.name = name;
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
    public Document withUrl(String url) {
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
        Document document = (Document) o;
        return Objects.equals(this.name, document.name)
                && Objects.equals(this.url, document.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url);
    }

    @Override
    public String toString() {
        return "class Document {\n"
                + "        name: " + toIndentedString(name) + "\n"
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

