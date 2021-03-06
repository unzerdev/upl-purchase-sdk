<?php

/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 */

declare(strict_types=1);

namespace Unzer\PayLater\Test\Unit\Webhook;

use Unzer\PayLater\Exception\WebhookDecrypterException;
use Unzer\PayLater\Model\MerchantReference;
use Unzer\PayLater\Model\OperationResult;
use Unzer\PayLater\Model\OperationStatus;
use Unzer\PayLater\Model\PurchaseInformation;
use Unzer\PayLater\Webhook\WebhookDecrypter;
use PHPUnit\Framework\TestCase;

use function json_decode;

class WebhookDecrypterTest extends TestCase
{
    /**
     * @param string $jsonData
     * @param string $secretKey
     * @param string $purchaseId
     * @param string $orderId
     * @dataProvider decryptDataProvider
     * @throws WebhookDecrypterException
     */
    public function testDecrypt(string $jsonData, string $secretKey, string $purchaseId, string $orderId): void
    {
        $json = json_decode($jsonData, true);

        $decrypter = new WebhookDecrypter();
        $response = $decrypter->decrypt($json['infoResponseMessage'], $secretKey);

        /** @var OperationResult $result */
        $result = $response->getResult();
        self::assertInstanceOf(OperationResult::class, $result);

        /** @var OperationStatus $operationStatus */
        $operationStatus = $result->getStatus();
        self::assertInstanceOf(OperationStatus::class, $operationStatus);
        self::assertSame(OperationStatus::OK, $operationStatus->getValue());

        /** @var PurchaseInformation $purchase */
        $purchase = $response->getPurchase();
        self::assertInstanceOf(PurchaseInformation::class, $purchase);
        self::assertSame($purchaseId, $purchase->getPurchaseId());

        /** @var MerchantReference $merchantReference */
        $merchantReference = $purchase->getMerchantReference();
        self::assertInstanceOf(MerchantReference::class, $merchantReference);
        self::assertSame($orderId, $merchantReference->getOrderId());
    }

    // phpcs:disable SlevomatCodingStandard.Files.LineLength.LineTooLong

    /**
     * @return array<string, array>
     */
    public function decryptDataProvider(): array
    {
        return [
            'process webhook' => [
                '{"infoResponseMessage":"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"}',
                'XWgtf4Hh0A1i1XenK5R4AJHKdMaVyX239XuUI8v2',
                'CID-ye5i87kjd2d8xzwcfyvn',
                'orderId-1594220305',
            ],
        ];
    }

    // phpcs:enable SlevomatCodingStandard.Files.LineLength.LineTooLong

    /**
     * @param string $jsonData
     * @param string $secretKey
     * @param string $message
     * @dataProvider decryptExceptionDataProvider
     */
    public function testDecryptException(string $jsonData, string $secretKey, string $message): void
    {
        $json = json_decode($jsonData, true);
        $decrypter = new WebhookDecrypter();
        try {
            $response = $decrypter->decrypt($json['infoResponseMessage'], $secretKey);
        } catch (WebhookDecrypterException $exception) {
            self::assertSame($message, $exception->getMessage());
        }
    }

    /**
     * @return array<string, array>
     */
    public function decryptExceptionDataProvider(): array
    {
        return [
            'Invalid message, no base64 message' => [
                '{"infoResponseMessage":"no base64 encoded message"}',
                'secret-key',
                'Unable to decode webhook message. Message is not base64 encoded.',
            ],
            'Invalid message, character from outside the base64 alphabet' => [
                '{"infoResponseMessage":"-dGVzdA=="}',
                'secret-key',
                'Unable to decode webhook message. Message contains characters from outside the base64 alphabet.',
            ],
            'Invalid message, message to short' => [
                '{"infoResponseMessage":"YQ=="}',
                'secret-key',
                'Unable to decode webhook message. Message to short.',
            ],
        ];
    }
}
