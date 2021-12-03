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
import com.unzer.paylater.model.AuthorizePurchaseRequest;
import com.unzer.paylater.model.PurchaseOperationResponse;

public class PurchaseAuthorizationApi extends BaseApi {

    public PurchaseAuthorizationApi(Communicator communicator) {
        super(communicator);
    }

    /**
     * Authorize a consumer to complete a transaction with our hosted solution. Can be started via SMS or URL. 
     *
     * @param authorizePurchaseRequest Contains everything needed to start the Authorization Process.
     * @param authorization The access token received from the initialize request. Provide this for client-side requests in the Bearer format.
     * @return PurchaseOperationResponse - PurchaseAuthorization endpoints always return the same object with different state of the purchase and different fields populated. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. 
     */
    public PurchaseOperationResponse authorizePayLaterWithAuthorization(AuthorizePurchaseRequest authorizePurchaseRequest, String authorization) {
        String uri = "/purchase/authorize/paylater";

        List<RequestHeader> headerParams = new ArrayList<>();
        addHeaderParam(headerParams, "Authorization", authorization);

        try {
            return communicator.execute(
                    HttpMethod.POST,
                    uri,
                    headerParams,
                    authorizePurchaseRequest,
                    PurchaseOperationResponse.class);
        } catch (ResponseException e) {
            throw createException(e);
        }
    }

    /**
     * Authorize a consumer to complete a transaction with our hosted solution. Can be started via SMS or URL. 
     *
     * @param authorizePurchaseRequest Contains everything needed to start the Authorization Process.
     * @param unzerPlSecretKey Secret key which can be requested from your account manager. Provide this for server-to-server communication.
     * @return PurchaseOperationResponse - PurchaseAuthorization endpoints always return the same object with different state of the purchase and different fields populated. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. 
     */
    public PurchaseOperationResponse authorizePayLater(AuthorizePurchaseRequest authorizePurchaseRequest, String unzerPlSecretKey) {
        String uri = "/purchase/authorize/paylater";

        List<RequestHeader> headerParams = new ArrayList<>();
        addHeaderParam(headerParams, "unzer-pl-secret-key", unzerPlSecretKey);

        try {
            return communicator.execute(
                    HttpMethod.POST,
                    uri,
                    headerParams,
                    authorizePurchaseRequest,
                    PurchaseOperationResponse.class);
        } catch (ResponseException e) {
            throw createException(e);
        }
    }
}
