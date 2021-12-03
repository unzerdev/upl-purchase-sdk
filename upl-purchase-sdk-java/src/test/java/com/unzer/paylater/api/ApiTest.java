package com.unzer.paylater.api;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.unzer.paylater.communication.Communicator;
import com.unzer.paylater.communication.RequestHeader;

@RunWith(MockitoJUnitRunner.class)
public class ApiTest {

    @Mock
    private Communicator communicator;

    /**
     * Verify that a method using a secret key for authentication sets the expected header.
     */
    @Test
    public void testSecretHeaders() {
        String requestHeaderValue = "mySecretKey";
        String securityHeaderName = "unzer-pl-secret-key";
        new LegalDocumentsApi(communicator).getTermsAndConditions("aPurchaseId", requestHeaderValue);
        verifyHeader(requestHeaderValue, securityHeaderName);
    }

    /**
     * Verify that a method using authorization for authentication sets the expected header.
     */
    @Test
    public void testAuthorizationHeaders() {
        String requestHeaderValue = "Bearer myAuthorization";
        String securityHeaderName = "Authorization";
        new LegalDocumentsApi(communicator).getTermsAndConditionsWithAuthorization("aPurchaseId", requestHeaderValue);
        verifyHeader(requestHeaderValue, securityHeaderName);
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private void verifyHeader(String mySecretKey, String securityHeaderName) {
        ArgumentCaptor<List> argumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(communicator).execute(any(), any(), argumentCaptor.capture(), any(), any());
        List<RequestHeader> passedHeaders = argumentCaptor.getValue();
        assert passedHeaders != null;
        String secretHeaderValue = passedHeaders.stream()
                                                .filter(h -> securityHeaderName.equals(h.getName()))
                                                .findFirst()
                                                .map(RequestHeader::getValue)
                                                .orElse(null);
        assert mySecretKey.equals(secretHeaderValue);
    }
}
