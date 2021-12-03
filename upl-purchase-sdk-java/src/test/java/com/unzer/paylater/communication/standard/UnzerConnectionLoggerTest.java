package com.unzer.paylater.communication.standard;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpHost;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.unzer.paylater.api.LegalDocumentsApi;
import com.unzer.paylater.api.PurchaseLifecycleApi;
import com.unzer.paylater.exception.CommunicationException;
import com.unzer.paylater.exception.NotFoundException;
import com.unzer.paylater.exception.ValidationException;
import com.unzer.paylater.logging.CommunicatorLogger;
import com.unzer.paylater.model.Amount;
import com.unzer.paylater.model.Currency;
import com.unzer.paylater.model.InitializePurchaseRequest;
import com.unzer.paylater.model.OperationStatus;
import com.unzer.paylater.model.PurchaseOperationResponse;
import com.unzer.paylater.model.ResponseWithAuthorization;

@RunWith(MockitoJUnitRunner.class)
public class UnzerConnectionLoggerTest extends UnzerBaseCommunicationTest {

    @Test
    public void testGet() throws Exception {
        HttpHost host = setup("/purchase/info/1", okJsonResponse("purchase.json"));
        TestLogger logger = new TestLogger();

		try (PurchaseLifecycleApi api = createApi(host)) {
            api.enableLogging(logger);
            PurchaseOperationResponse purchase = api.getPurchase("1", "aSecretKey");

			Assert.assertNotNull(purchase);
			Assert.assertNotNull(purchase.getResult());
			Assert.assertEquals(OperationStatus.OK, purchase.getResult().getStatus());
		}

		Assert.assertEquals(2, logger.entries.size());

		TestLoggerEntry requestEntry = logger.entries.get(0);

		Assert.assertNotNull(requestEntry.message);
		Assert.assertNull(requestEntry.thrown);

		TestLoggerEntry responseEntry = logger.entries.get(1);

		Assert.assertNotNull(responseEntry.message);
		Assert.assertNull(responseEntry.thrown);

		assertRequestAndResponse(requestEntry.message, responseEntry.message, "getPurchaseInfo");
    }

    @Test
    public void testPost() throws Exception {
        HttpHost host = setup("/purchase/initialize", okJsonResponseWithAccessToken("purchase.json", "an_access_token"));
		TestLogger logger = new TestLogger();

        InitializePurchaseRequest request = new InitializePurchaseRequest()
                .withPurchaseAmount(new Amount()
                        .withAmount(50000L)
                        .withCurrency(Currency.EUR));

		try (PurchaseLifecycleApi api = createApi(host)) {
		    api.enableLogging(logger);

            ResponseWithAuthorization<PurchaseOperationResponse> response = api.initializePurchase(request, "aSecretKey");

			Assert.assertNotNull(response);
			Assert.assertNotNull(response.getAuthorization());
			Assert.assertNotNull(response.getResponse());
			Assert.assertNotNull(response.getResponse().getPurchase());
			Assert.assertNotNull(response.getResponse().getPurchase().getPurchaseId());
		}

		Assert.assertEquals(2, logger.entries.size());

		TestLoggerEntry requestEntry = logger.entries.get(0);

		Assert.assertNotNull(requestEntry.message);
		Assert.assertNull(requestEntry.thrown);

		TestLoggerEntry responseEntry = logger.entries.get(1);

		Assert.assertNotNull(responseEntry.message);
		Assert.assertNull(responseEntry.thrown);

		assertRequestAndResponse(requestEntry.message, responseEntry.message, "initializePurchase");
    }

    @Test
    public void testErrorResponseLogging() throws Exception {
        // an exception is thrown after logging the received error response
        HttpHost host = setup("/purchase/initialize", jsonResponse("initializePurchase.failure.json", 400));

        TestLogger logger = new TestLogger();

        InitializePurchaseRequest request = new InitializePurchaseRequest();

		try (PurchaseLifecycleApi api = createApi(host)) {
            api.enableLogging(logger);
			api.initializePurchase(request, "aSecretKey");

			Assert.fail("expected ValidationException");

		} catch (@SuppressWarnings("unused") ValidationException e) {
			// expected
		}

		Assert.assertEquals(2, logger.entries.size());

		TestLoggerEntry requestEntry = logger.entries.get(0);

		Assert.assertNotNull(requestEntry.message);
		Assert.assertNull(requestEntry.thrown);

		TestLoggerEntry responseEntry = logger.entries.get(1);

		Assert.assertNotNull(responseEntry.message);
		Assert.assertNull(responseEntry.thrown);

		assertRequestAndResponse(requestEntry.message, responseEntry.message, "initializePurchase.failure");
    }

    @Test
    public void testNonJson() throws Exception {
        // an HTML response is received.
        HttpHost host = setup("/purchase/legaldocuments/termsandconditions/1", htmlResponse("termsandconditions.html", 200));

		TestLogger logger = new TestLogger();

		try (LegalDocumentsApi api = createHtmlApi(host)) {
            api.enableLogging(logger);
            String termsAndConditions = api.getTermsAndConditions("1", "aSecretKey");
            Assert.assertNotNull(termsAndConditions);
        }

		Assert.assertEquals(2, logger.entries.size());

		TestLoggerEntry requestEntry = logger.entries.get(0);

		Assert.assertNotNull(requestEntry.message);
		Assert.assertNull(requestEntry.thrown);

		TestLoggerEntry responseEntry = logger.entries.get(1);

		Assert.assertNotNull(responseEntry.message);
		Assert.assertNull(responseEntry.thrown);

		assertRequestAndResponse(requestEntry.message, responseEntry.message, "termsandconditions");
    }

    @Test
    public void testNonJsonError() throws Exception {
        // an HTML error response is received.
        HttpHost host = setup("/purchase/info/1", htmlResponse("notFound.html", 404));

		TestLogger logger = new TestLogger();

		try (PurchaseLifecycleApi api = createApi(host)) {
            api.enableLogging(logger);
			api.getPurchase("1", "aSecretKey");

			Assert.fail("expected NotFoundException");

		} catch (@SuppressWarnings("unused") NotFoundException e) {
			// expected
		}

		Assert.assertEquals(2, logger.entries.size());

		TestLoggerEntry requestEntry = logger.entries.get(0);

		Assert.assertNotNull(requestEntry.message);
		Assert.assertNull(requestEntry.thrown);

		TestLoggerEntry responseEntry = logger.entries.get(1);

		Assert.assertNotNull(responseEntry.message);
		Assert.assertNull(responseEntry.thrown);

		assertRequestAndResponse(requestEntry.message, responseEntry.message, "getPurchaseInfo", "notFound");
    }

    @Test
    public void testReadTimeout() throws Exception {
        // an exception is thrown before logging the response.
        HttpHost host = setup("/purchase/info/1", delayedAnswer(htmlResponse("notFound.html", 404), 100));

		TestLogger logger = new TestLogger();

		try (PurchaseLifecycleApi api = createApi(host, 1000, 10)) {
            api.enableLogging(logger);
			api.getPurchase("1", "aSecretKey");

			Assert.fail("expected CommunicationException");

		} catch (@SuppressWarnings("unused") CommunicationException e) {
			// expected
		}

		Assert.assertEquals(2, logger.entries.size());

		TestLoggerEntry requestEntry = logger.entries.get(0);

		Assert.assertNotNull(requestEntry.message);
		Assert.assertNull(requestEntry.thrown);

		TestLoggerEntry errorEntry = logger.entries.get(1);

		Assert.assertNotNull(errorEntry.message);
		Assert.assertNotNull(errorEntry.thrown);

		assertRequestAndError(requestEntry.message, errorEntry.message, "getPurchaseInfo");

		Assert.assertEquals(SocketTimeoutException.class, errorEntry.thrown.getClass());
    }

    private void assertRequestAndResponse(String requestMessage, String responseMessage, String resourcePrefix) throws IOException {
        assertRequestAndResponse(requestMessage, responseMessage, resourcePrefix, resourcePrefix);
    }

    private void assertRequestAndResponse(String requestMessage, String responseMessage, String requestResourcePrefix, String responseResourcePrefix) throws IOException {

        String requestId = assertRequest(requestMessage, requestResourcePrefix);
        assertResponse(responseMessage, responseResourcePrefix, requestId);
    }

    private void assertRequestAndError(String requestMessage, String errorMessage, String resourcePrefix) throws IOException {

        String requestId = assertRequest(requestMessage, resourcePrefix);
        assertError(errorMessage, requestId);
    }

    private String assertRequest(String requestMessage, String resourcePrefix) throws IOException {

        final String requestResource = resourcePrefix + ".request";

        Pattern requestPattern = Pattern.compile(normalizeLineBreaks(readResource(requestResource)), Pattern.DOTALL);

        Matcher requestMatcher = requestPattern.matcher(normalizeLineBreaks(requestMessage));
        Assert.assertTrue("request message does not match pattern. Pattern: \n<<<" + requestPattern + ">>>\nRequest:\n<<<" + requestMessage + ">>>", requestMatcher.matches());

        return requestMatcher.group(1);
    }

    private void assertResponse(String responseMessage, String resourcePrefix, String requestId) throws IOException {

        final String responseResource = resourcePrefix + ".response";
        Pattern responsePattern = Pattern.compile(normalizeLineBreaks(readResource(responseResource)), Pattern.DOTALL);

        Matcher responseMatcher = responsePattern.matcher(normalizeLineBreaks(responseMessage));
        Assert.assertTrue("response message does not match pattern. Pattern:\n<<<" + responsePattern + ">>>\nMessage:\n<<<" + responseMessage + ">>>", responseMatcher.matches());

        String responseRequestId = responseMatcher.group(1);
        if (requestId != null) {
            Assert.assertEquals("requestId of response does not match request. Expected:'" + requestId + "', actual:'" + responseRequestId + "'>>>'",
                    requestId, responseRequestId);
        }

    }

    // Mockito answer utility methods

    private void assertError(String errorMessage, String requestId) throws IOException {

        final String errorResource = "generic.error";
        Pattern errorPattern = Pattern.compile(normalizeLineBreaks(readResource(errorResource)), Pattern.DOTALL);

        Matcher errorMatcher = errorPattern.matcher(normalizeLineBreaks(errorMessage));
        Assert.assertTrue("error message '" + errorMessage + "' does not match pattern " + errorPattern, errorMatcher.matches());

        String errorRequestId = errorMatcher.group(1);
        if (requestId != null) {
            Assert.assertEquals("error requestId '" + errorRequestId + "' does not match earlier requestId '" + requestId + "'",
                    requestId, errorRequestId);
        }

    }

    // general utility methods

    private <T> Answer<T> delayedAnswer(final Answer<? extends T> answer, final int delay) {
        return invocation -> {
            Thread.sleep(delay);
            return answer.answer(invocation);
        };
    }

    private String readResource(String resource) throws IOException {

        StringBuilder result = new StringBuilder();
        try (Reader reader = new InputStreamReader(getClass().getResourceAsStream(resource), StandardCharsets.UTF_8)) {
            char[] buffer = new char[4096];
            int len;
            while ((len = reader.read(buffer)) != -1) {
                result.append(buffer, 0, len);
            }
        }
        return result.toString();
    }

    private String normalizeLineBreaks(String value) {
        // Normalize line breaks to always use the same, regardless of the operating system
        return value.replace("\r", "");
    }

    private static final class TestLogger implements CommunicatorLogger {

        private List<TestLoggerEntry> entries = new ArrayList<>();

        @Override
        public void log(String message) {
            log(message, null);
        }

        @Override
        public void log(String message, Throwable thrown) {
            entries.add(new TestLoggerEntry(message, thrown));
        }
    }

    private static final class TestLoggerEntry {

        private final String message;
        private final Throwable thrown;

        TestLoggerEntry(String message, Throwable thrown) {
            this.message = message;
            this.thrown = thrown;
        }
    }
}
