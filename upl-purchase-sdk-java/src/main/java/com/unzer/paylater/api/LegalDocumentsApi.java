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

public class LegalDocumentsApi extends BaseApi {

    public LegalDocumentsApi(Communicator communicator) {
        super(communicator);
    }

    /**
     * Generates a terms-and-conditions document in html format.
     *
     * @param purchaseId The purchaseId received from the initialize request that started the verification process.
     * @param authorization The access token received from the initialize request. Provide this for client-side requests in the Bearer format.
     * @return String - Terms and conditions in HTML format.
     */
    public String getTermsAndConditionsWithAuthorization(String purchaseId, String authorization) {
        String uri = "/purchase/legaldocuments/termsandconditions/{purchaseId}";
        uri = populateUri(uri, "purchaseId", purchaseId);

        List<RequestHeader> headerParams = new ArrayList<>();
        addHeaderParam(headerParams, "Authorization", authorization);

        try {
            return communicator.execute(
                    HttpMethod.GET,
                    uri,
                    headerParams,
                    null,
                    String.class);
        } catch (ResponseException e) {
            throw createException(e);
        }
    }

    /**
     * Generates a terms-and-conditions document in html format.
     *
     * @param purchaseId The purchaseId received from the initialize request that started the verification process.
     * @param unzerPlSecretKey Secret key which can be requested from your account manager. Provide this for server-to-server communication.
     * @return String - Terms and conditions in HTML format.
     */
    public String getTermsAndConditions(String purchaseId, String unzerPlSecretKey) {
        String uri = "/purchase/legaldocuments/termsandconditions/{purchaseId}";
        uri = populateUri(uri, "purchaseId", purchaseId);

        List<RequestHeader> headerParams = new ArrayList<>();
        addHeaderParam(headerParams, "unzer-pl-secret-key", unzerPlSecretKey);

        try {
            return communicator.execute(
                    HttpMethod.GET,
                    uri,
                    headerParams,
                    null,
                    String.class);
        } catch (ResponseException e) {
            throw createException(e);
        }
    }
}
