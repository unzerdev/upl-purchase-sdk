/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 *
 * This class is based on the Unzer Pay Later OpenAPI specification, version 1.0.0.
 */
package com.unzer.paylater.model;

import java.time.OffsetDateTime;
import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * Describes the result of an operation performed on a purchase.
 */
public class OperationResult {

    private String operationId;
    private OperationStatus status;
    private String statusCode;
    private String statusMessage;
    private OffsetDateTime processingStart;
    private OffsetDateTime processingEnd;

    /**
     * Unique identifier of the operation performed.
     *
     * @return operationId
     */
    public String getOperationId() {
        return operationId;
    }
    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }
    public OperationResult withOperationId(String operationId) {
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
    public OperationResult withStatus(OperationStatus status) {
        this.status = status;
        return this;
    }

    /**
     * Code:  * `0.0.0` - \"Operation performed sucessfully\"  * `0.0.1` - \"Duplicate request: Operation already performed sucessfully\"  * `1.0.0` - \"Operation performed sucessfully. Final result pending\"  * `2.0.0` - \"Operation permanently declined\"  * `2.1.0` - \"Operation declined (retryable)\"  * `2.1.1` - \"Customer has exceeded limit\"  * `3.0.0` - \"Missing field\"  * `3.1.0` - \"Invalid input data\"  * `4.0.0` - \"Incorrect workflow state\"  * `4.1.0` - \"Wrong purchase state\"  * `4.2.0` - \"Unknown reference\"  * `4.3.0` - \"Invalid product\"  * `4.4.0` - \"Duplicate request\"  * `4.5.0` - \"User not authorized\"  * `4.5.1` - \"User not authorized. Product inactive\"  * `5.0.0` - \"Internal error\"  * `5.1.0` - \"Processing service unavailable (retryable)\"  * `6.0.0` - \"Internal error: Operation result undefined\"
     *
     * @return statusCode
     */
    public String getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }
    public OperationResult withStatusCode(String statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    /**
     * A human-readable description giving additional information about the result status.
     *
     * @return statusMessage
     */
    public String getStatusMessage() {
        return statusMessage;
    }
    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }
    public OperationResult withStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
        return this;
    }

    /**
     * Timestamp when operation processing has started.
     *
     * @return processingStart
     */
    public OffsetDateTime getProcessingStart() {
        return processingStart;
    }
    public void setProcessingStart(OffsetDateTime processingStart) {
        this.processingStart = processingStart;
    }
    public OperationResult withProcessingStart(OffsetDateTime processingStart) {
        this.processingStart = processingStart;
        return this;
    }

    /**
     * Timestamp when operation processing has finished.
     *
     * @return processingEnd
     */
    public OffsetDateTime getProcessingEnd() {
        return processingEnd;
    }
    public void setProcessingEnd(OffsetDateTime processingEnd) {
        this.processingEnd = processingEnd;
    }
    public OperationResult withProcessingEnd(OffsetDateTime processingEnd) {
        this.processingEnd = processingEnd;
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
        OperationResult operationResult = (OperationResult) o;
        return Objects.equals(this.operationId, operationResult.operationId)
                && Objects.equals(this.status, operationResult.status)
                && Objects.equals(this.statusCode, operationResult.statusCode)
                && Objects.equals(this.statusMessage, operationResult.statusMessage)
                && Objects.equals(this.processingStart, operationResult.processingStart)
                && Objects.equals(this.processingEnd, operationResult.processingEnd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(operationId, status, statusCode, statusMessage, processingStart, processingEnd);
    }

    @Override
    public String toString() {
        return "class OperationResult {\n"
                + "        operationId: " + toIndentedString(operationId) + "\n"
                + "        status: " + toIndentedString(status) + "\n"
                + "        statusCode: " + toIndentedString(statusCode) + "\n"
                + "        statusMessage: " + toIndentedString(statusMessage) + "\n"
                + "        processingStart: " + toIndentedString(processingStart) + "\n"
                + "        processingEnd: " + toIndentedString(processingEnd) + "\n"
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

