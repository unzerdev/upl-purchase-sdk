package com.unzer.paylater.communication.standard;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Map;

import org.apache.http.HttpException;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.localserver.LocalServerTestBase;
import org.apache.http.protocol.HttpRequestHandler;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import com.unzer.paylater.Factory;
import com.unzer.paylater.api.LegalDocumentsApi;
import com.unzer.paylater.api.PurchaseLifecycleApi;
import com.unzer.paylater.communication.Communicator;
import com.unzer.paylater.communication.CommunicatorConfiguration;

@RunWith(MockitoJUnitRunner.class)
public abstract class UnzerBaseCommunicationTest extends LocalServerTestBase {

    @Mock HttpRequestHandler requestHandler;

    // Initialization methods.

    protected HttpHost setup(String path, Answer<Void> response) throws Exception {
        setupRequestHandler(response);
        serverBootstrap.registerHandler(path, requestHandler);
        return start();
    }

    private void setupRequestHandler(Answer<Void> answer) throws IOException, HttpException {
        Mockito.doAnswer(answer)
               .when(requestHandler).handle(Matchers.any(), Matchers.any(), Matchers.any());
    }

    private URI toURI(HttpHost host) throws URISyntaxException {
        return new URI(host.getSchemeName(), null, host.getHostName(), host.getPort(), null, null, null);
    }

    @SuppressWarnings("resource")
    protected Communicator createCommunicator(HttpHost host) throws URISyntaxException {
        return createCommunicator(host, CommunicatorConfiguration.DEFAULT_CONNECT_TIMEOUT, CommunicatorConfiguration.DEFAULT_READ_TIMEOUT);
    }

    @SuppressWarnings("resource")
    protected Communicator createCommunicator(HttpHost host, int connectionTimeout, int readTimeout) throws URISyntaxException {
        URI apiEndpoint = toURI(host);
        UnzerConnection connection = new UnzerConnection(connectionTimeout, readTimeout);
        return Factory.createCommunicator(apiEndpoint, connection);
    }

    protected PurchaseLifecycleApi createApi(HttpHost host) throws URISyntaxException {
        Communicator communicator = createCommunicator(host);
        return new PurchaseLifecycleApi(communicator);
    }

    protected PurchaseLifecycleApi createApi(HttpHost host, int connectionTimeout, int readTimeout) throws URISyntaxException {
        Communicator communicator = createCommunicator(host, connectionTimeout, readTimeout);
        return new PurchaseLifecycleApi(communicator);
    }

    protected LegalDocumentsApi createHtmlApi(HttpHost host) throws URISyntaxException {
        Communicator communicator = createCommunicator(host);
        return new LegalDocumentsApi(communicator);
    }

    // Response methods.

    /**
     * @param resource Resource filename, relative to the implementing class.
     * @return a 200 {@link Answer} that yields the response body of the given resource.
     */
    protected Answer<Void> okJsonResponse(final String resource) {
        return jsonResponse(resource, 200);
    }

    /**
     * @param resource Resource filename, relative to the implementing class.
     * @return a 200 {@link Answer} that yields the response body of the given resource and an access_token header.
     */
    protected Answer<Void> okJsonResponseWithAccessToken(final String resource, String accessToken) {
        return jsonResponse(resource, 200, Collections.singletonMap("access_token", accessToken));
    }

    /**
     * @param resource Resource filename, relative to the implementing class.
     * @return an {@link Answer} that yields the response body of the given resource with the specified status code.
     */
    protected Answer<Void> jsonResponse(final String resource, final int statusCode) {
        return jsonResponse(resource, statusCode, Collections.emptyMap());
    }

    /**
     * @param resource Resource filename, relative to the implementing class.
     * @return an {@link Answer} that yields the response body of the given resource with the specified status code and response headers.
     */
    protected Answer<Void> jsonResponse(final String resource, final int statusCode, final Map<String, String> additionalHeaders) {
        return invocation -> {
            HttpResponse response = invocation.getArgumentAt(1, HttpResponse.class);

            response.setStatusCode(statusCode);
            response.setHeader(HttpHeaders.CONTENT_TYPE, "application/json");

            response.setHeader("Dummy", null);

            for (Map.Entry<String, String> entry : additionalHeaders.entrySet()) {
                response.setHeader(entry.getKey(), entry.getValue());
            }

            response.setEntity(new InputStreamEntity(getClass().getResourceAsStream(resource)));

            return null;
        };
    }

    protected Answer<Void> htmlResponse(final String resource, final int statusCode) {
        return invocation -> {
            HttpResponse response = invocation.getArgumentAt(1, HttpResponse.class);

            response.setStatusCode(statusCode);
            response.setHeader(HttpHeaders.CONTENT_TYPE, "text/html");

            response.setHeader("Dummy", null);

            for (Map.Entry<String, String> entry : Collections.<String, String>emptyMap().entrySet()) {
                response.setHeader(entry.getKey(), entry.getValue());
            }

            response.setEntity(new InputStreamEntity(UnzerConnectionLoggerTest.class.getResourceAsStream(resource)));

            return null;
        };
    }

}
