package com.unzer.paylater.webhook;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.unzer.paylater.communication.Marshaller;
import com.unzer.paylater.model.PurchaseOperationResponse;

/**
 * Helper for decrypting Unzer Pay Later platform webhook messages. Thread-safe.<p>
 * This class can be re-used by instantiating it with a {@link Marshaller}, or its static methods can be used for decryption.
 */
public class WebhookDecrypter {

    private static final Charset CHARSET = StandardCharsets.UTF_8;

    private final Marshaller marshaller;

    public WebhookDecrypter(Marshaller marshaller) {
        this.marshaller = marshaller;
    }

    /**
     * Decrypts the contents of a webhook message.
     *
     * @param message The unmarshalled {@link WebhookMessage} you received on your callback URL.
     * @param secretKey your default secret key (supplied by the Unzer integration team).
     * @param marshaller the {@link Marshaller} to use for decoding the unencrypted webhook message to a {@link PurchaseOperationResponse}.
     * @return the decrypted {@link PurchaseOperationResponse}, or null if no message was provided.
     */
    public static PurchaseOperationResponse decrypt(WebhookMessage message, String secretKey, Marshaller marshaller) {
        return message != null
                ? decrypt(message.getInfoResponseMessage(), secretKey, marshaller)
                : null;
    }

    /**
     * Decrypts the contents of a webhook message.
     *
     * @param infoResponseMessage content of the JSON property 'infoResponseMessage' in the {@link WebhookMessage}.
     * @param secretKey your default secret key (supplied by the Unzer integration team).
     * @param marshaller the {@link Marshaller} to use for decoding the unencrypted webhook message to a {@link PurchaseOperationResponse}.
     * @return the decrypted {@link PurchaseOperationResponse}.
     */
    public static PurchaseOperationResponse decrypt(String infoResponseMessage, String secretKey, Marshaller marshaller) {
        try {
            byte[] strToDecryptBytes = Base64.getDecoder().decode(infoResponseMessage);
            ByteBuffer byteBuffer = ByteBuffer.wrap(strToDecryptBytes);

            Cipher cipher = getCipher(byteBuffer, secretKey);

            byte[] encryptedText = new byte[byteBuffer.remaining()];
            byteBuffer.get(encryptedText);

            String decryptedMessage = new String(cipher.doFinal(encryptedText), CHARSET);
            return marshaller.unmarshal(decryptedMessage, PurchaseOperationResponse.class);
        } catch (Exception e) {
            throw new WebhookDecryptionException("Decryption of webhook message failed.", e);
        }
    }

    /**
     * Creates a cipher based on the secret key and the data in the message.
     */
    private static Cipher getCipher(ByteBuffer byteBuffer, String secretKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, InvalidAlgorithmParameterException {

        //First byte specifies IV length (IV is 12 bytes long)
        int ivLength = byteBuffer.get();
        if (ivLength != 12) { // check input parameter
            throw new WebhookDecryptionException("invalid iv length: " + ivLength);
        }

        //Next 12 bytes are the IV
        byte[] iv = new byte[ivLength];
        byteBuffer.get(iv);

        byte[] unhashedSecretKey = secretKey.getBytes(CHARSET);
        MessageDigest sha = MessageDigest.getInstance("SHA-256");
        byte[] hashedSecretKey = sha.digest(unhashedSecretKey);

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        SecretKeySpec secretKeySpec = new SecretKeySpec(hashedSecretKey, "AES");
        GCMParameterSpec ivParameterSpec = new GCMParameterSpec(128, iv);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        return cipher;
    }

    /**
     * Decrypts the contents of a webhook message.
     *
     * @param message The unmarshalled {@link WebhookMessage} you received on your callback URL.
     * @param secretKey your default secret key (supplied by the Unzer integration team).
     * @return null if no message was provided, or the {@link PurchaseOperationResponse} decrypted from the {@link WebhookMessage#getInfoResponseMessage()}.
     */
    public PurchaseOperationResponse decrypt(WebhookMessage message, String secretKey) {
        return decrypt(message, secretKey, marshaller);
    }

    /**
     * Decrypts the contents of a webhook message.
     *
     * @param infoResponseMessage content of the JSON property 'infoResponseMessage' in the webhook message.
     * @param secretKey your default secret key (supplied by the Unzer integration team).
     * @return the {@link PurchaseOperationResponse} decrypted from the {@link WebhookMessage#getInfoResponseMessage()}.
     */
    public PurchaseOperationResponse decrypt(String infoResponseMessage, String secretKey) {
        return decrypt(infoResponseMessage, secretKey, marshaller);
    }
}
