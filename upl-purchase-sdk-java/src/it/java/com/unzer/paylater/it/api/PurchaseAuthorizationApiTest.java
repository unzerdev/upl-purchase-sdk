package com.unzer.paylater.it.api;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.unzer.paylater.model.AuthorizePurchaseRequest;
import com.unzer.paylater.model.MethodType;
import com.unzer.paylater.model.PurchaseInformation;
import com.unzer.paylater.model.PurchaseOperationResponse;
import com.unzer.paylater.model.ResponseWithAuthorization;

/**
 * API tests for PurchaseAuthorizationApi
 */
public class PurchaseAuthorizationApiTest extends BaseApiTest {

    /**
     * Authorize a purchase with SMS and a secret key.
     */
    @Test
    public void authorizePaylaterWithSMSTest() {
        String secretKey = getSecretKeyInvoice();
        ResponseWithAuthorization<PurchaseOperationResponse> responseWrapper = super.initializePurchase(super.getPurchaseLifecycleApi(), secretKey);
        String purchaseId = responseWrapper.getResponse().getPurchase().getPurchaseId();

        AuthorizePurchaseRequest purchaseAuthorizationRequest = new AuthorizePurchaseRequest()
                .withPurchaseId(purchaseId)
                .withMethod(MethodType.SMS)
                .withPhone("+4300000000000")
                .withSuccessUrl("https://example.com/successUrl")
                .withCallbackUrl("https://example.com/callbackUrl");

        PurchaseOperationResponse response = getPurchaseAuthorizationApi().authorizePayLater(purchaseAuthorizationRequest, secretKey);
        validateAuthorizationResponse(purchaseId, response);
    }

    /**
     * Authorize a purchase with URL and a secret key.
     */
    @Test
    public void authorizePaylaterWithURLTest() {
        String secretKey = getSecretKeyInstallments();
        ResponseWithAuthorization<PurchaseOperationResponse> responseWrapper = super.initializePurchase(super.getPurchaseLifecycleApi(), secretKey);
        String purchaseId = responseWrapper.getResponse().getPurchase().getPurchaseId();

        PurchaseOperationResponse response = super.authorizePurchase(purchaseId, secretKey);
        validateAuthorizationResponse(purchaseId, response);
    }

    /**
     * Authorize a purchase with URL and authentication via an authorization header.
     */
    @Test
    public void authorizePaylaterWithAuthorizationTest() {
        String secretKey = getSecretKeyInvoice();
        ResponseWithAuthorization<PurchaseOperationResponse> responseWrapper = super.initializePurchase(getPurchaseLifecycleApi(), secretKey);
        String authorization = responseWrapper.getAuthorization();
        String purchaseId = responseWrapper.getResponse().getPurchase().getPurchaseId();

        AuthorizePurchaseRequest purchaseAuthorizationRequest = new AuthorizePurchaseRequest()
                .withPurchaseId(purchaseId)
                .withMethod(MethodType.URL)
                .withSuccessUrl("https://example.com/successUrl")
                .withCallbackUrl("https://example.com/callbackUrl");

        PurchaseOperationResponse response = getPurchaseAuthorizationApi().authorizePayLaterWithAuthorization(purchaseAuthorizationRequest, authorization);
        validateAuthorizationResponse(purchaseId, response);
    }

    private void validateAuthorizationResponse(String purchaseId, PurchaseOperationResponse response) {
        assertResultIsOk(response.getResult());

        PurchaseInformation purchase = response.getPurchase();
        Assert.assertNotNull(purchase);
        Assert.assertEquals(purchaseId, purchase.getPurchaseId());

        Map<String, String> metaData = purchase.getMetaData();
        Assert.assertNotNull(metaData);
        Assert.assertFalse(metaData.isEmpty());
        Assert.assertTrue(metaData.containsKey("INSTORE_SELFSERVICE_AUTH_URL"));
    }
}
