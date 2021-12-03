/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 *
 * This class is based on the Unzer Pay Later OpenAPI specification, version 1.0.0.
 */
package com.unzer.paylater.api;

import java.util.ArrayList;
import java.util.List;

import com.unzer.paylater.communication.Communicator;
import com.unzer.paylater.communication.HttpMethod;
import com.unzer.paylater.communication.RequestHeader;
import com.unzer.paylater.exception.ResponseException;
import com.unzer.paylater.model.CapturePurchaseRequest;
import com.unzer.paylater.model.InitializePurchaseRequest;
import com.unzer.paylater.model.PurchaseOperationResponse;
import com.unzer.paylater.model.RefundPurchaseRequest;
import com.unzer.paylater.model.ResponseWithAuthorization;

public class PurchaseLifecycleApi extends BaseApi {

    public PurchaseLifecycleApi(Communicator communicator) {
        super(communicator);
    }

    /**
     * Confirm a capture(=shipping) of the purchased goods.
     *
     * @param capturePurchaseRequest Contains all data needed to process a capture(=shipping) of purchased goods.
     * @param unzerPlSecretKey Secret key which can be requested from your account manager. Only use this for server-to-server communication.
     * @return PurchaseOperationResponse - PurchaseLifecycle endpoints always return the same object with the latest state of the purchase and different fields populated.PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. 
     */
    public PurchaseOperationResponse capturePurchase(CapturePurchaseRequest capturePurchaseRequest, String unzerPlSecretKey) {
        String uri = "/purchase/capture";

        List<RequestHeader> headerParams = new ArrayList<>();
        addHeaderParam(headerParams, "unzer-pl-secret-key", unzerPlSecretKey);

        try {
            return communicator.execute(
                    HttpMethod.POST,
                    uri,
                    headerParams,
                    capturePurchaseRequest,
                    PurchaseOperationResponse.class);
        } catch (ResponseException e) {
            throw createException(e);
        }
    }

    /**
     * Query for a purchase for a given purchaseId.
     *
     * @param purchaseId PurchaseId received from initializePurchase or authorizePurchase response.
     * @param authorization The access token received from the initialize request. Provide this for client-side requests in the Bearer format.
     * @return PurchaseOperationResponse - PurchaseLifecycle endpoints always return the same object with the latest state of the purchase and different fields populated.PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. 
     */
    public PurchaseOperationResponse getPurchaseWithAuthorization(String purchaseId, String authorization) {
        String uri = "/purchase/info/{purchaseId}";
        uri = populateUri(uri, "purchaseId", purchaseId);

        List<RequestHeader> headerParams = new ArrayList<>();
        addHeaderParam(headerParams, "Authorization", authorization);

        try {
            return communicator.execute(
                    HttpMethod.GET,
                    uri,
                    headerParams,
                    null,
                    PurchaseOperationResponse.class);
        } catch (ResponseException e) {
            throw createException(e);
        }
    }

    /**
     * Query for a purchase for a given purchaseId.
     *
     * @param purchaseId PurchaseId received from initializePurchase or authorizePurchase response.
     * @param unzerPlSecretKey Secret key which can be requested from your account manager. Only use this for server-to-server communication.
     * @return PurchaseOperationResponse - PurchaseLifecycle endpoints always return the same object with the latest state of the purchase and different fields populated.PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. 
     */
    public PurchaseOperationResponse getPurchase(String purchaseId, String unzerPlSecretKey) {
        String uri = "/purchase/info/{purchaseId}";
        uri = populateUri(uri, "purchaseId", purchaseId);

        List<RequestHeader> headerParams = new ArrayList<>();
        addHeaderParam(headerParams, "unzer-pl-secret-key", unzerPlSecretKey);

        try {
            return communicator.execute(
                    HttpMethod.GET,
                    uri,
                    headerParams,
                    null,
                    PurchaseOperationResponse.class);
        } catch (ResponseException e) {
            throw createException(e);
        }
    }

    /**
     * Initializes a purchase for a given amount and returns a response with all pre-configured (non-binding) payment options.
     *
     * @param initializePurchaseRequest Contains the data needed to initialize a purchase.
     * @param unzerPlSecretKey Secret key which can be requested from your account manager. Only use this for server-to-server communication.
     * @return PurchaseOperationResponse - PurchaseLifecycle endpoints always return the same object with the latest state of the purchase and different fields populated.  In addition, the initialize operation returns a single-purchase authentication token in the response header <<access_token>>. This token has to be used by client-side callers. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. 
     */
    public ResponseWithAuthorization<PurchaseOperationResponse> initializePurchase(InitializePurchaseRequest initializePurchaseRequest, String unzerPlSecretKey) {
        String uri = "/purchase/initialize";

        List<RequestHeader> headerParams = new ArrayList<>();
        addHeaderParam(headerParams, "unzer-pl-secret-key", unzerPlSecretKey);

        try {
            return communicator.executeWithAuthorizationHeader(
                    HttpMethod.POST,
                    uri,
                    headerParams,
                    initializePurchaseRequest,
                    PurchaseOperationResponse.class);
        } catch (ResponseException e) {
            throw createException(e);
        }
    }

    /**
     * Refund part of or the full purchase amount in case consumer returned purchased goods.
     *
     * @param refundPurchaseRequest All data needed to process a refund of a purchase.
     * @param unzerPlSecretKey Secret key which can be requested from your account manager. Only use this for server-to-server communication.
     * @return PurchaseOperationResponse - PurchaseLifecycle endpoints always return the same object with the latest state of the purchase and different fields populated.PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. 
     */
    public PurchaseOperationResponse refundPurchase(RefundPurchaseRequest refundPurchaseRequest, String unzerPlSecretKey) {
        String uri = "/purchase/refund";

        List<RequestHeader> headerParams = new ArrayList<>();
        addHeaderParam(headerParams, "unzer-pl-secret-key", unzerPlSecretKey);

        try {
            return communicator.execute(
                    HttpMethod.POST,
                    uri,
                    headerParams,
                    refundPurchaseRequest,
                    PurchaseOperationResponse.class);
        } catch (ResponseException e) {
            throw createException(e);
        }
    }
}
