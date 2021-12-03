package com.unzer.paylater.it.api;

import java.io.IOException;
import java.time.LocalDate;

import org.junit.Ignore;
import org.junit.Test;

import com.unzer.paylater.Factory;
import com.unzer.paylater.api.PurchaseAuthorizationApi;
import com.unzer.paylater.api.PurchaseLifecycleApi;
import com.unzer.paylater.communication.Communicator;
import com.unzer.paylater.logging.SysOutCommunicatorLogger;
import com.unzer.paylater.model.Address;
import com.unzer.paylater.model.Amount;
import com.unzer.paylater.model.AuthorizePurchaseRequest;
import com.unzer.paylater.model.CapturePurchaseRequest;
import com.unzer.paylater.model.Consumer;
import com.unzer.paylater.model.Country;
import com.unzer.paylater.model.Currency;
import com.unzer.paylater.model.InitializePurchaseRequest;
import com.unzer.paylater.model.MethodType;
import com.unzer.paylater.model.Person;
import com.unzer.paylater.model.PurchaseInformation;
import com.unzer.paylater.model.PurchaseOperationResponse;
import com.unzer.paylater.model.RefundPurchaseRequest;
import com.unzer.paylater.model.ResponseWithAuthorization;

/**
 * This calls contains one test that serves only as an example.
 */
public class UnzerPayLaterTest extends BaseApiTest {

    /**
     * Test a full API flow, from the initialization of a purchase up till refunding it.
     * This test does not perform validations.
     */
    @Test
    @Ignore("This test can not run automatically, as there are manual steps involved with progressing a purchase after authorization.")
    public void fullApiFlowTest() throws IOException {

        //
        // Initialize communicator and API.
        //
        Communicator communicator = Factory.createCommunicator(getPropertiesFile());
        communicator.enableLogging(SysOutCommunicatorLogger.INSTANCE);
        PurchaseLifecycleApi lifecycleAPI = new PurchaseLifecycleApi(communicator);
        PurchaseAuthorizationApi authorizationAPI = new PurchaseAuthorizationApi(communicator);

        //
        // Initialize the purchase.
        // Only the purchase amount is required, consumer data is optional.
        // All provided consumer data is populated in the self-service authorisation website,
        // so the consumer will have less to fill in when completing the application.
        //
        Amount purchaseAmount = new Amount(50000L, Currency.EUR);
        InitializePurchaseRequest request = new InitializePurchaseRequest(purchaseAmount)
                .withConsumer(new Consumer()
                        .withEmail("instore-test@unzer.com")
                        .withPhone("123456789")
                        .withPerson(new Person()
                                .withFirstName("Ernst")
                                .withLastName("Muller")
                                .withBirthdate(LocalDate.parse("1989-08-22")))
                        .withBillingAddress(new Address()
                                .withCountryCode(Country.AT)
                                .withZipCode("5500")
                                .withCity("Bischofshofen")
                                .withStreet("Hauptstrasse")
                                .withHouseNumber("2")));

        ResponseWithAuthorization<PurchaseOperationResponse> initializeResponse = lifecycleAPI.initializePurchase(request, getDefaultSecretKey());

        PurchaseInformation purchase = initializeResponse.getResponse().getPurchase();
        String purchaseId = purchase.getPurchaseId();

        String authorization = initializeResponse.getAuthorization();

        //
        // Authorize the purchase.
        //
        AuthorizePurchaseRequest purchaseAuthorizationRequest = new AuthorizePurchaseRequest()
                .withPurchaseId(purchaseId)
                .withMethod(MethodType.URL)
                .withSuccessUrl("https://example.com/successUrl")
                .withCallbackUrl("https://example.com/callbackUrl");
        PurchaseOperationResponse authorizeResponse = authorizationAPI.authorizePayLaterWithAuthorization(purchaseAuthorizationRequest, authorization);

        //
        // At this point the consumer should be redirected to the self-service authorisation URL to complete the application.
        // The URL can be found in the metadata of the authorizePurchase response.
        //
        String authUrl = authorizeResponse.getPurchase().getMetaData().get("INSTORE_SELFSERVICE_AUTH_URL");

        //
        // Once the application has been completed the newly-created order ID can be retrieved.
        //
        PurchaseOperationResponse getPurchaseResponse = lifecycleAPI.getPurchase(purchaseId, getDefaultSecretKey());
        purchase = getPurchaseResponse.getPurchase();
        String orderId = purchase.getMerchantReference().getOrderId();

        //
        // The purchase-funds can be captures using the new order ID...
        //
        CapturePurchaseRequest captureWithOrderIdRequest = new CapturePurchaseRequest()
                .withOrderId(orderId)
                .withFulfillmentAmount(new Amount()
                        .withAmount(25000L)
                        .withCurrency(Currency.EUR));
        lifecycleAPI.capturePurchase(captureWithOrderIdRequest, getDefaultSecretKey());

        //
        // ... or the purchase ID.
        //
        CapturePurchaseRequest captureWithPurchaseIdRequest = new CapturePurchaseRequest()
                .withPurchaseId(purchaseId)
                .withFulfillmentAmount(new Amount()
                        .withAmount(25000L)
                        .withCurrency(Currency.EUR));
        lifecycleAPI.capturePurchase(captureWithPurchaseIdRequest, getDefaultSecretKey());

        //
        // If the funds are captured the can be refunded.
        //
        RefundPurchaseRequest refundRequest = new RefundPurchaseRequest()
                .withPurchaseId(purchaseId)
                .withRefundAmount(new Amount()
                        .withAmount(5000L)
                        .withCurrency(Currency.EUR));
        lifecycleAPI.refundPurchase(refundRequest, getDefaultSecretKey());

        communicator.close();
    }
}
