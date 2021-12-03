package com.unzer.paylater.it.api;

import org.junit.Assert;
import org.junit.Test;

import com.unzer.paylater.model.PurchaseOperationResponse;
import com.unzer.paylater.model.ResponseWithAuthorization;

/**
 * API tests for LegalDocumentsApi
 */
public class LegalDocumentsApiTest extends BaseApiTest {

    /**
     * Retrieves the terms & conditions document with a secret key.
     */
    @Test
    public void termsandconditionsTest() {

        String secretKey = getDefaultSecretKey();
        ResponseWithAuthorization<PurchaseOperationResponse> responseWrapper = initializePurchase(getPurchaseLifecycleApi(), secretKey);
        String purchaseId = responseWrapper.getResponse().getPurchase().getPurchaseId();

        String response = getLegalDocumentsApi().getTermsAndConditions(purchaseId, secretKey);
        validateResponse(response);
    }

    /**
     * Retrieves the terms & conditions document with authentication via an authorization header.
     */
    @Test
    public void termsandconditionsWithAuthorizationTest() {

        String secretKey = getDefaultSecretKey();
        ResponseWithAuthorization<PurchaseOperationResponse> responseWrapper = initializePurchase(getPurchaseLifecycleApi(), secretKey);
        String purchaseId = responseWrapper.getResponse().getPurchase().getPurchaseId();
        String authorization = responseWrapper.getAuthorization();

        String response = getLegalDocumentsApi().getTermsAndConditionsWithAuthorization(purchaseId, authorization);
        validateResponse(response);
    }

    private void validateResponse(String response) {
        Assert.assertNotNull(response);
        Assert.assertTrue(response.startsWith("<html"));
        Assert.assertTrue(response.endsWith("</html>"));
    }
}
