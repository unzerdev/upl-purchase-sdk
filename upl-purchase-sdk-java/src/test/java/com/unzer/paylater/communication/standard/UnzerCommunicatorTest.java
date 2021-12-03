package com.unzer.paylater.communication.standard;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.unzer.paylater.communication.Connection;
import com.unzer.paylater.communication.ResponseHandler;
import com.unzer.paylater.communication.ResponseHeader;
import com.unzer.paylater.model.ResponseWithAuthorization;

@RunWith(MockitoJUnitRunner.class)
public class UnzerCommunicatorTest {

    private static final String TEST_ENDPOINT_URI = "https://test.com";
    private static final URI BASE_URI = URI.create(TEST_ENDPOINT_URI);

    @Mock private Connection connection;

    @Test
    public void testToURI() {

        UnzerCommunicator communicator = new UnzerCommunicator(BASE_URI, connection, JsonMarshaller.INSTANCE);

        // Test with a relative path.
        URI uri = communicator.toAbsoluteURI("purchase/initialization");
        Assert.assertEquals(URI.create(TEST_ENDPOINT_URI + "/purchase/initialization"), uri);

        // Test with an absolute path.
        uri = communicator.toAbsoluteURI("/purchase/initialization");
        Assert.assertEquals(URI.create(TEST_ENDPOINT_URI + "/purchase/initialization"), uri);
    }

    /**
     * Verify that an access token is properly retrieved from a response.
     */
    @Test
    public void testExecuteWithAuthorization() {
        UnzerCommunicator communicator = new UnzerCommunicator(BASE_URI, connection, JsonMarshaller.INSTANCE);

        ResponseHandler<ResponseWithAuthorization<Void>> handler = communicator.responseHandlerWithAuthorization("/test/path", Void.class);

        String my_access_token = "my_access_token";
        ResponseHeader accessTokenHeader = new ResponseHeader("access_token", my_access_token);
        List<ResponseHeader> responseHeaders = Collections.singletonList(accessTokenHeader);

        String expectedAuthorization = "Bearer " + my_access_token;

        ResponseWithAuthorization<Void> responseWrapper = handler.handleResponse(200, new ByteArrayInputStream("".getBytes()), responseHeaders);
        Assert.assertEquals(expectedAuthorization, responseWrapper.getAuthorization());
    }
}
