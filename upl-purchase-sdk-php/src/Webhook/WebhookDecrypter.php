<?php

/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 *
 * This class is based on the Unzer Pay Later OpenAPI specification, version 1.0.0.
 */

declare(strict_types=1);

namespace Unzer\PayLater\Webhook;

use Unzer\PayLater\Communication\ResponseBuilder;
use Unzer\PayLater\Exception\BuilderException;
use Unzer\PayLater\Exception\WebhookDecrypterException;
use Unzer\PayLater\Model\PurchaseOperationResponse;

use function base64_decode;
use function base64_encode;
use function hash;
use function json_decode;
use function openssl_cipher_iv_length;
use function openssl_decrypt;
use function strlen;
use function substr;

use const OPENSSL_RAW_DATA;

class WebhookDecrypter
{
    /**
     * @param string $webhookMessage
     * @param string $unzerPlSecretKey
     * @return PurchaseOperationResponse
     * @throws WebhookDecrypterException
     */
    public function decrypt(string $webhookMessage, string $unzerPlSecretKey): PurchaseOperationResponse
    {
        try {
            /** @var PurchaseOperationResponse $purchaseOperationResponse */
            $purchaseOperationResponse = ResponseBuilder::build(
                PurchaseOperationResponse::class,
                json_decode($this->decryptMessage($webhookMessage, $unzerPlSecretKey), true)
            );
            return $purchaseOperationResponse;
        } catch (BuilderException $exception) {
            throw new WebhookDecrypterException($exception->getMessage(), $exception->getCode(), $exception);
        }
    }

    /**
     * @param string $webhookMessage
     * @param string $unzerPlSecretKey
     * @return string
     * @throws WebhookDecrypterException
     */
    protected function decryptMessage(string $webhookMessage, string $unzerPlSecretKey): string
    {
        $encrypted = base64_decode($webhookMessage, true);
        if ($encrypted === false) {
            throw new WebhookDecrypterException(
                'Unable to decode webhook message. Message contains characters from outside the base64 alphabet.'
            );
        }
        if (base64_encode($encrypted) !== $webhookMessage) {
            throw new WebhookDecrypterException('Unable to decode webhook message. Message is not base64 encoded.');
        }

        $cipher = 'aes-256-gcm';
        $tagLength = 16;

        $ivLength = openssl_cipher_iv_length($cipher);
        if ($ivLength === false) {
            throw new WebhookDecrypterException('Unable to calculate iv length for cipher: ' . $cipher);
        }

        $minLength = 1 + $ivLength + $tagLength;
        if (strlen($encrypted) <= $minLength) {
            throw new WebhookDecrypterException('Unable to decode webhook message. Message to short.');
        }

        $iv = substr($encrypted, 1, $ivLength);
        $cipherText = substr($encrypted, 1 + $ivLength, -$tagLength);
        $tag = substr($encrypted, -$tagLength);
        $key = substr(hash('sha256', $unzerPlSecretKey, true), 0, 32);
        return (string) openssl_decrypt($cipherText, $cipher, $key, OPENSSL_RAW_DATA, $iv, $tag);
    }
}
