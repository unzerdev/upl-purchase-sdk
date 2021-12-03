/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 *
 * This class is based on the Unzer Pay Later OpenAPI specification, version 1.0.0.
 */
package com.unzer.paylater.model;

import java.time.OffsetDateTime;
import java.util.Objects;

public class OperationInformation {

    private String operationId;
    private OperationStatus status;
    private OffsetDateTime processingDate;
    private Amount operationAmount;

    /**
     * @return operationId
     */
    public String getOperationId() {
        return operationId;
    }
    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }
    public OperationInformation withOperationId(String operationId) {
        this.operationId = operationId;
        return this;
    }

    /**
     * @return status
     */
    public OperationStatus getStatus() {
        return status;
    }
    public void setStatus(OperationStatus status) {
        this.status = status;
    }
    public OperationInformation withStatus(OperationStatus status) {
        this.status = status;
        return this;
    }

    /**
     * @return processingDate
     */
    public OffsetDateTime getProcessingDate() {
        return processingDate;
    }
    public void setProcessingDate(OffsetDateTime processingDate) {
        this.processingDate = processingDate;
    }
    public OperationInformation withProcessingDate(OffsetDateTime processingDate) {
        this.processingDate = processingDate;
        return this;
    }

    /**
     * @return operationAmount
     */
    public Amount getOperationAmount() {
        return operationAmount;
    }
    public void setOperationAmount(Amount operationAmount) {
        this.operationAmount = operationAmount;
    }
    public OperationInformation withOperationAmount(Amount operationAmount) {
        this.operationAmount = operationAmount;
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
        OperationInformation operationInformation = (OperationInformation) o;
        return Objects.equals(this.operationId, operationInformation.operationId)
                && Objects.equals(this.status, operationInformation.status)
                && Objects.equals(this.processingDate, operationInformation.processingDate)
                && Objects.equals(this.operationAmount, operationInformation.operationAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationId, status, processingDate, operationAmount);
    }

    @Override
    public String toString() {
        return "class OperationInformation {\n"
                + "        operationId: " + toIndentedString(operationId) + "\n"
                + "        status: " + toIndentedString(status) + "\n"
                + "        processingDate: " + toIndentedString(processingDate) + "\n"
                + "        operationAmount: " + toIndentedString(operationAmount) + "\n"
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

