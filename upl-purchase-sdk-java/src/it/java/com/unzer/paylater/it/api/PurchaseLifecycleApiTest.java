package com.unzer.paylater.it.api;

import org.junit.Assert;
import org.junit.Test;

import com.unzer.paylater.api.PurchaseLifecycleApi;
import com.unzer.paylater.model.OperationResult;
import com.unzer.paylater.model.OperationStatus;
import com.unzer.paylater.model.PaymentOption;
import com.unzer.paylater.model.ProductType;
import com.unzer.paylater.model.PurchaseInformation;
import com.unzer.paylater.model.PurchaseOperationResponse;
import com.unzer.paylater.model.ResponseWithAuthorization;

/**
 * API tests for PurchaseLifecycleApi
 */
public class PurchaseLifecycleApiTest extends BaseApiTest {

    public static final String DEFAULT_SECRET_KEY = getSecretKeyInvoice();

    /**
     * Initializes an installments purchase.
     */
    @Test
    public void initializePurchaseInstallmentsTest() {
        String secretKey = getSecretKeyInstallments();
        ResponseWithAuthorization<PurchaseOperationResponse> response = super.initializePurchase(getPurchaseLifecycleApi(), secretKey);
        validateInitializePurchaseResponse(response, ProductType.INSTALLMENT);
    }

    /**
     * Initializes an invoice purchase.
     */
    @Test
    public void initializePurchaseInvoiceTest() {
        String secretKey = getSecretKeyInvoice();
        ResponseWithAuthorization<PurchaseOperationResponse> response = super.initializePurchase(getPurchaseLifecycleApi(), secretKey);
        validateInitializePurchaseResponse(response, ProductType.INVOICE);
    }

    /**
     * Test retrieving a purchase using the secret key.
     */
    @Test
    public void getPurchaseTest() {
        PurchaseLifecycleApi purchaseLifecycleApi = getPurchaseLifecycleApi();

        ResponseWithAuthorization<PurchaseOperationResponse> purchaseResponse = super.initializePurchase(purchaseLifecycleApi, DEFAULT_SECRET_KEY);
        String purchaseId = extractPurchaseId(purchaseResponse);

        PurchaseOperationResponse response = purchaseLifecycleApi.getPurchase(purchaseId, DEFAULT_SECRET_KEY);

        assertResultIsOk(response.getResult());

        PurchaseInformation purchase = response.getPurchase();
        Assert.assertNotNull(purchase);
        Assert.assertEquals(purchaseId, purchase.getPurchaseId());
    }

    /**
     * Test retrieving a purchase using the authorization header.
     */
    @Test
    public void getPurchaseWithAuthorizationTest() {
        PurchaseLifecycleApi purchaseLifecycleApi = getPurchaseLifecycleApi();

        ResponseWithAuthorization<PurchaseOperationResponse> purchaseResponse = super.initializePurchase(purchaseLifecycleApi, DEFAULT_SECRET_KEY);
        String purchaseId = extractPurchaseId(purchaseResponse);
        String authorization = purchaseResponse.getAuthorization();

        PurchaseOperationResponse response = purchaseLifecycleApi.getPurchaseWithAuthorization(purchaseId, authorization);

        assertResultIsOk(response.getResult());

        PurchaseInformation purchase = response.getPurchase();
        Assert.assertNotNull(purchase);
        Assert.assertEquals(purchaseId, purchase.getPurchaseId());
    }

    private void validateInitializePurchaseResponse(ResponseWithAuthorization<PurchaseOperationResponse> response, ProductType productType) {
        assertResultIsOk(response.getResponse().getResult());

        PurchaseInformation purchase = response.getResponse().getPurchase();
        Assert.assertNotNull(purchase);
        Assert.assertNotNull(purchase.getPurchaseId());
        Assert.assertNotNull(purchase.getPaymentOptions());
        Assert.assertFalse(purchase.getPaymentOptions().isEmpty());
        for (PaymentOption option : purchase.getPaymentOptions()) {
            Assert.assertEquals(productType, option.getProductType());
        }
    }

    private String extractPurchaseId(ResponseWithAuthorization<PurchaseOperationResponse> purchaseResponse) {
        Assert.assertNotNull(purchaseResponse.getResponse());

        OperationResult initializeResult = purchaseResponse.getResponse().getResult();
        Assert.assertNotNull(initializeResult);
        Assert.assertEquals(OperationStatus.OK, initializeResult.getStatus());

        PurchaseInformation purchase = purchaseResponse.getResponse().getPurchase();
        Assert.assertNotNull(purchase);
        Assert.assertNotNull(purchase.getPurchaseId());
        return purchase.getPurchaseId();
    }
}
