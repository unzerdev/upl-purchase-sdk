package com.unzer.paylater.api;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHost;
import org.junit.Assert;
import org.junit.Test;

import com.unzer.paylater.communication.ResponseHeader;
import com.unzer.paylater.communication.standard.UnzerBaseCommunicationTest;
import com.unzer.paylater.exception.ApiException;
import com.unzer.paylater.exception.AuthorizationException;
import com.unzer.paylater.exception.ReferenceException;
import com.unzer.paylater.exception.ValidationException;
import com.unzer.paylater.model.Amount;
import com.unzer.paylater.model.Consumer;
import com.unzer.paylater.model.Currency;
import com.unzer.paylater.model.InitializePurchaseRequest;
import com.unzer.paylater.model.OperationResult;
import com.unzer.paylater.model.OperationStatus;
import com.unzer.paylater.model.Person;

/**
 * Tests for handling API exceptions for the Pay Later API.
 * These tests are mostly intended as examples. The responses are based on actual API calls to the Unzer Pay Later test environment.
 */
public class ApiExceptionTest extends UnzerBaseCommunicationTest {

    /**
     * Test initializing a purchase without sending a body.
     */
    @Test
    public void missingBodyTest() throws Exception {
        Map<String, String> responseHeaders = getResponseHeaders();
        HttpHost host = setup("/purchase/initialize", jsonResponse("missingBody.json", 400, responseHeaders));

        try (PurchaseLifecycleApi api = createApi(host)) {
            api.initializePurchase(null, "aSecretKey");
            Assert.fail("The test should not reach this point.");
        } catch (ValidationException e) {
            verifyException(ValidationException.class, 400, "3.1.0", e, OperationStatus.NOK);
        }
    }

    /**
     * Test initializing a purchase without required fields.
     */
    @Test
    public void missingFieldsTest() throws Exception {
        InitializePurchaseRequest request = new InitializePurchaseRequest();
        Map<String, String> responseHeaders = getResponseHeaders();
        HttpHost host = setup("/purchase/initialize", jsonResponse("missingField.json", 400, responseHeaders));

        try (PurchaseLifecycleApi api = createApi(host)) {
            api.initializePurchase(request, "aSecretKey");
            Assert.fail("The test should not reach this point.");
        } catch (ValidationException e) {
            verifyException(ValidationException.class, 400, "3.1.0", e);
        }
    }

    /**
     * Test authorizing a purchase with invalid customer data.
     * Personal data is validated during the authorization flow.
     */
    @Test
    public void invalidCustomerDataTest() throws Exception {
        InitializePurchaseRequest request = new InitializePurchaseRequest()
                .withPurchaseAmount(new Amount(50000L, Currency.EUR))
                .withConsumer(new Consumer()
                        .withPerson(new Person()
                                .withFirstName("🙂")));
        Map<String, String> responseHeaders = getResponseHeaders();
        HttpHost host = setup("/purchase/initialize", jsonResponse("invalidCustomerData.json", 400, responseHeaders));

        try (PurchaseLifecycleApi api = createApi(host)) {
            api.initializePurchase(request, "aSecretKey");
            Assert.fail("The test should not reach this point.");
        } catch (ValidationException e) {
            verifyException(ValidationException.class, 400, "3.1.0", e);
        }
    }

    /**
     * Test initializing a purchase without required fields.
     */
    @Test
    public void missingPathParameterTest() throws Exception {
        Map<String, String> responseHeaders = getResponseHeaders();
        HttpHost host = setup("/purchase/info/", jsonResponse("missingPathParameter.json", 404, responseHeaders));

        try (PurchaseLifecycleApi api = createApi(host)) {
            api.getPurchase("", "aSecretKey");
            Assert.fail("The test should not reach this point.");
        } catch (ReferenceException e) {
            verifyException(ReferenceException.class, 404, "5.1.0", e);
        }
    }

    /**
     * Test calling an API without an authorization.
     */
    @Test
    public void missingAuthorizationTest() throws Exception {
        Map<String, String> responseHeaders = getResponseHeaders();
        responseHeaders.put("WWW-Authenticate", "Bearer realm=\"oauth\", error=\"unauthorized\", error_description=\"Full authentication is required to access this resource\"");
        HttpHost host = setup("/purchase/info/1", jsonResponse("missingAuthorization.json", 401, responseHeaders));

        try (PurchaseLifecycleApi api = createApi(host)) {
            api.getPurchase("1", null);
            Assert.fail("The test should not reach this point.");
        } catch (AuthorizationException e) {
            verifyException(AuthorizationException.class, 401, "4.5.0", e);
        }
    }

    /**
     * Test calling an API with an invalid authorization.
     */
    @Test
    public void invalidAuthorizationTest() throws Exception {
        Map<String, String> responseHeaders = getResponseHeaders();
        HttpHost host = setup("/purchase/info/1", jsonResponse("invalidAuthorization.json", 401, responseHeaders));

        try (PurchaseLifecycleApi api = createApi(host)) {
            api.getPurchase("1", "anInvalidSecretKey");
            Assert.fail("The test should not reach this point.");
        } catch (AuthorizationException e) {
            verifyException(AuthorizationException.class, 401, "4.5.0", e);
        }
    }
    /**
     * Test calling an API with an invalid secret key.
     */
    @Test
    public void invalidSecretKeyTest() throws Exception {
        Map<String, String> responseHeaders = getResponseHeaders();
        HttpHost host = setup("/purchase/info/1", jsonResponse("invalidAuthorization.json", 401, responseHeaders));

        try (PurchaseLifecycleApi api = createApi(host)) {
            api.getPurchase("1", "anInvalidSecretKey");
            Assert.fail("The test should not reach this point.");
        } catch (AuthorizationException e) {
            verifyException(AuthorizationException.class, 401, "4.5.0", e);
        }
    }

    private void verifyException(Class<? extends ApiException> exceptionClass, int httpStatusCode, String unzerStatusCode, ApiException exception) {
        verifyException(exceptionClass, httpStatusCode, unzerStatusCode, exception, OperationStatus.ERROR);
    }

    private void verifyException(Class<? extends ApiException> exceptionClass, int httpStatusCode, String payLaterStatusCode, ApiException exception, OperationStatus payLaterStatus) {
        Assert.assertEquals(exceptionClass, exception.getClass());
        // Verify the exception's fields.
        Assert.assertNotNull(exception.getResponseBody());
        Assert.assertEquals(httpStatusCode, exception.getResponseStatusCode());
        Assert.assertEquals(payLaterStatusCode, exception.getErrorCode());
        Map<String, String> responseHeaders = getResponseHeaders();
        List<ResponseHeader> exceptionHeaders = exception.getResponseHeaders();
        responseHeaders.forEach((key, value) -> Assert.assertEquals(value, exceptionHeaders.stream()
                                                                                           .filter(h -> key.equals(h.getName()))
                                                                                           .findFirst()
                                                                                           .orElseThrow(() -> new AssertionError("Could not find '" + key + "' as response headers."))
                                                                                           .getValue()
        ));

        // Verify the operation result.
        OperationResult result = exception.getOperationResult();
        Assert.assertNotNull(result);
        Assert.assertNotNull(result.getStatusMessage());
        Assert.assertEquals(payLaterStatus, result.getStatus());
        Assert.assertEquals(payLaterStatusCode, result.getStatusCode());
    }

    /**
     * @return A collection of headers that the Unzer Pay Later API can return when an error occurs.
     */
    private Map<String, String> getResponseHeaders() {
        Map<String, String> responseHeaders = new LinkedHashMap<>();
        responseHeaders.put("X-Application-Context", "proxy:test:8443");
        responseHeaders.put("Expires", "0");
        responseHeaders.put("Access-Control-Allow-Headers", "x-requested-with, authorization, cache-control, X-HTTP-Method-Override, Content-Type, Accept, unzer-pl-tenant, unzer-pl-public-key, unzer-pl-secret-key");
        responseHeaders.put("X-Frame-Options", "DENY");
        responseHeaders.put("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        responseHeaders.put("Pragma", "no-cache");
        responseHeaders.put("Strict-Transport-Security", "max-age=31536000 ; includeSubDomains");
        responseHeaders.put("Access-Control-Expose-Headers", "access_token");
        responseHeaders.put("Access-Control-Allow-Origin", "*");
        responseHeaders.put("Access-Control-Max-Age", "3600");
        responseHeaders.put("X-Content-Type-Options", "nosniff");
        responseHeaders.put("X-XSS-Protection", "1; mode=block");
        responseHeaders.put("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate");
        return responseHeaders;
    }
}
