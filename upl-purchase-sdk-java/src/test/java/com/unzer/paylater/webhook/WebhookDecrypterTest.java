package com.unzer.paylater.webhook;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.crypto.AEADBadTagException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import com.unzer.paylater.communication.Marshaller;
import com.unzer.paylater.communication.standard.JsonMarshaller;
import com.unzer.paylater.model.OperationResult;
import com.unzer.paylater.model.OperationStatus;
import com.unzer.paylater.model.PurchaseInformation;
import com.unzer.paylater.model.PurchaseOperationResponse;

@RunWith(MockitoJUnitRunner.class)
public class WebhookDecrypterTest {

    private static String key = "XWgtf4Hh0A1i1XenK5R4AJHKdMaVyX239XuUI8v2";

    /**
     * Test that a received webhook message can be decoded with the correct key.
     * This test uses the decryption method of a decrypter instance.
     */
    @Test
    public void testDecodingSuccess() {
        Marshaller marshaller = JsonMarshaller.INSTANCE;
        WebhookMessage message = getWebhookMessage(marshaller, "webhook-message.json");

        WebhookDecrypter decoder = new WebhookDecrypter(marshaller);
        PurchaseOperationResponse response = decoder.decrypt(message.getInfoResponseMessage(), key);

        Assert.assertNotNull(response);

        OperationResult result = response.getResult();
        Assert.assertNotNull(result);
        Assert.assertEquals(OperationStatus.OK, result.getStatus());
        Assert.assertEquals("0.0.0", result.getStatusCode());

        PurchaseInformation purchase = response.getPurchase();
        Assert.assertNotNull(purchase);
        Assert.assertNotNull(purchase.getPurchaseId());
    }

    /**
     * Test that a proper exception is thrown when the key does not match the received webhook message.
     * This test uses the static decryption method.
     */
    @Test
    public void testDecodingWithWrongKey() {
        Marshaller marshaller = JsonMarshaller.INSTANCE;
        WebhookMessage message = getWebhookMessage(marshaller, "webhook-message.json");

        try {
            WebhookDecrypter.decrypt(message.getInfoResponseMessage(), "aWrongKey", JsonMarshaller.INSTANCE);
            Assert.fail("The decryption should fail.");
        } catch (WebhookDecryptionException e) {
            Assert.assertNotNull(e.getCause());
            Assert.assertEquals(AEADBadTagException.class, e.getCause().getClass());
        }
    }

    private WebhookMessage getWebhookMessage(Marshaller marshaller, String resource) {
        String webhookMessageContent = new String(readResource(resource), StandardCharsets.UTF_8);
        WebhookMessage message = marshaller.unmarshal(webhookMessageContent, WebhookMessage.class);
        Assert.assertNotNull(message);
        Assert.assertNotNull(message.getInfoResponseMessage());
        return message;
    }

    private byte[] readResource(String resource) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        try (InputStream input = new BufferedInputStream(getClass().getResourceAsStream(resource))) {
            int b;
            while ((b = input.read()) != -1) {
                output.write(b);
            }
            return output.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("Could not read resource '" + resource + "', see cause.", e);
        }
    }
}
