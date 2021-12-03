<?php

/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 */

declare(strict_types=1);

namespace Unzer\PayLater\Test\Unit\Api;

use GuzzleHttp\Exception\RequestException;
use GuzzleHttp\Psr7\Request as GuzzleRequest;
use GuzzleHttp\Psr7\Response;
use Unzer\PayLater\Api\PurchaseLifecycleApi;
use Unzer\PayLater\Communication\RequestBuilder;
use Unzer\PayLater\Communication\RequestHeader;
use Unzer\PayLater\Communication\RequestHeaderCollection;
use Unzer\PayLater\Exception\ApiResponseException;
use Unzer\PayLater\Exception\BuilderException;
use Unzer\PayLater\Model\Amount;
use Unzer\PayLater\Model\CapturePurchaseRequest;
use Unzer\PayLater\Model\Currency;
use Unzer\PayLater\Model\InitializePurchaseRequest;
use Unzer\PayLater\Model\PurchaseOperationResponse;
use Unzer\PayLater\Model\RefundPurchaseRequest;
use Unzer\PayLater\Model\RefundReason;
use Unzer\PayLater\Model\ResponseWithAuthorization;

use function class_exists;

class PurchaseLifecycleApiTest extends AbstractApiTest
{
    public const TEST_NAME = 'PurchaseLifecycleApi';

    /**
     * @throws ApiResponseException
     * @throws BuilderException
     */
    public function testCapture(): void
    {
        $purchaseLifecycleApi = new PurchaseLifecycleApi(
            $this->createUnzerPayLaterCommunicator(
                200,
                [],
                '{"result": {"status": "OK"},"purchase": {"purchaseId": "CID-test0987654321"}}'
            )
        );

        /** @var PurchaseOperationResponse $result */
        $result = $purchaseLifecycleApi->capturePurchase(
            new CapturePurchaseRequest(new Amount(5000, new Currency(Currency::EUR))),
            'test-secret-key'
        );

        self::assertInstanceOf(PurchaseOperationResponse::class, $result);
    }

    /**
     * @param int $statusCode
     * @param string $exceptionClass
     * @dataProvider exceptionDataProvider
     * @throws BuilderException
     */
    // phpcs:ignore ObjectCalisthenics.Files.FunctionLength.ObjectCalisthenics\Sniffs\Files\FunctionLengthSniff
    public function testCaptureExceptions(
        int $statusCode,
        string $exceptionClass,
        string $message
    ): void {
        $purchaseLifecycleApi = new PurchaseLifecycleApi(
            $this->createUnzerPayLaterCommunicatorException(
                new RequestException(
                    'Error Communicating with Server',
                    new GuzzleRequest(
                        'POST',
                        '/purchase/capture',
                        (new RequestHeaderCollection(
                            new RequestHeader('unzer-pl-secret-key', 'test-secret-key')
                        )
                        )->getRequestHeadersAsArray(),
                        RequestBuilder::toJson(
                            new CapturePurchaseRequest(new Amount(5000, new Currency(Currency::EUR)))
                        )
                    ),
                    new Response($statusCode, [], $exceptionClass)
                )
            )
        );

        try {
            $purchaseLifecycleApi->capturePurchase(
                new CapturePurchaseRequest(new Amount(5000, new Currency(Currency::EUR))),
                'test-secret-key'
            );
        } catch (ApiResponseException $exception) {
            if (class_exists($exceptionClass)) {
                self::assertInstanceOf($exceptionClass, $exception);
            }
            self::assertSame($statusCode, $exception->getCode());
            self::assertSame($exceptionClass, $exception->getResponseBody());
            self::assertSame($message, $exception->getMessage());
        }
    }

    /**
     * @throws ApiResponseException
     * @throws BuilderException
     */
    public function testGetPurchase(): void
    {
        $purchaseLifecycleApi = new PurchaseLifecycleApi(
            $this->createUnzerPayLaterCommunicator(
                200,
                [],
                '{"result": {"status": "OK"},"purchase": {"purchaseId": "CID-test0987654321"}}'
            )
        );

        /** @var PurchaseOperationResponse $result */
        $result = $purchaseLifecycleApi->getPurchase('test12345', 'test-secret-key');

        self::assertInstanceOf(PurchaseOperationResponse::class, $result);
    }

    /**
     * @param int $statusCode
     * @param string $exceptionClass
     * @dataProvider exceptionDataProvider
     * @throws BuilderException
     */
    // phpcs:ignore ObjectCalisthenics.Files.FunctionLength.ObjectCalisthenics\Sniffs\Files\FunctionLengthSniff
    public function testGetPurchaseExceptions(
        int $statusCode,
        string $exceptionClass,
        string $message
    ): void {
        $purchaseLifecycleApi = new PurchaseLifecycleApi(
            $this->createUnzerPayLaterCommunicatorException(
                new RequestException(
                    'Error Communicating with Server',
                    new GuzzleRequest(
                        'GET',
                        '/purchase/info/test12345',
                        (new RequestHeaderCollection(
                            new RequestHeader('unzer-pl-secret-key', 'test-secret-key')
                        )
                        )->getRequestHeadersAsArray(),
                        null
                    ),
                    new Response($statusCode, [], $exceptionClass)
                )
            )
        );

        try {
            $purchaseLifecycleApi->getPurchase('test12345', 'test-authorization-string');
        } catch (ApiResponseException $exception) {
            if (class_exists($exceptionClass)) {
                self::assertInstanceOf($exceptionClass, $exception);
            }
            self::assertSame($statusCode, $exception->getCode());
            self::assertSame($exceptionClass, $exception->getResponseBody());
            self::assertSame($message, $exception->getMessage());
        }
    }

    /**
     * @throws ApiResponseException
     * @throws BuilderException
     */
    public function testGetPurchaseWithAuthorization(): void
    {
        $purchaseLifecycleApi = new PurchaseLifecycleApi(
            $this->createUnzerPayLaterCommunicator(
                200,
                [],
                '{"result": {"status": "OK"},"purchase": {"purchaseId": "CID-test0987654321"}}'
            )
        );

        /** @var PurchaseOperationResponse $result */
        $result = $purchaseLifecycleApi->getPurchaseWithAuthorization('test12345', 'test-authorization-string');

        self::assertInstanceOf(PurchaseOperationResponse::class, $result);
    }

    /**
     * @param int $statusCode
     * @param string $exceptionClass
     * @dataProvider exceptionDataProvider
     * @throws BuilderException
     */
    // phpcs:ignore ObjectCalisthenics.Files.FunctionLength.ObjectCalisthenics\Sniffs\Files\FunctionLengthSniff
    public function testGetPurchaseWithAuthorizationExceptions(
        int $statusCode,
        string $exceptionClass,
        string $message
    ): void {
        $purchaseLifecycleApi = new PurchaseLifecycleApi(
            $this->createUnzerPayLaterCommunicatorException(
                new RequestException(
                    'Error Communicating with Server',
                    new GuzzleRequest(
                        'GET',
                        '/purchase/info/test12345',
                        (new RequestHeaderCollection(
                            new RequestHeader('Authorization', 'Bearer test-authorization-key')
                        )
                        )->getRequestHeadersAsArray(),
                        null
                    ),
                    new Response($statusCode, [], $exceptionClass)
                )
            )
        );

        try {
            $purchaseLifecycleApi->getPurchaseWithAuthorization('test12345', 'test-authorization-string');
        } catch (ApiResponseException $exception) {
            if (class_exists($exceptionClass)) {
                self::assertInstanceOf($exceptionClass, $exception);
            }
            self::assertSame($statusCode, $exception->getCode());
            self::assertSame($exceptionClass, $exception->getResponseBody());
            self::assertSame($message, $exception->getMessage());
        }
    }

    /**
     * @throws ApiResponseException
     * @throws BuilderException
     */
    public function testInitializePurchase(): void
    {
        $purchaseLifecycleApi = new PurchaseLifecycleApi(
            $this->createUnzerPayLaterCommunicator(
                200,
                ['access_token' => 'd3d064a5-1fc5-4853-b2c8-683fe6481045'],
                '{"result": {"status": "OK"},"purchase": {"purchaseId": "CID-test0987654321"}}'
            )
        );

        $result = $purchaseLifecycleApi->initializePurchase(
            new InitializePurchaseRequest(
                new Amount(5000, new Currency(Currency::EUR))
            ),
            'test-secret-key'
        );

        self::assertInstanceOf(ResponseWithAuthorization::class, $result);
    }

    /**
     * @param int $statusCode
     * @param string $exceptionClass
     * @dataProvider exceptionDataProvider
     * @throws BuilderException
     */
    // phpcs:ignore ObjectCalisthenics.Files.FunctionLength.ObjectCalisthenics\Sniffs\Files\FunctionLengthSniff
    public function testInitializePurchaseExceptions(
        int $statusCode,
        string $exceptionClass,
        string $message
    ): void {
        $purchaseLifecycleApi = new PurchaseLifecycleApi(
            $this->createUnzerPayLaterCommunicatorException(
                new RequestException(
                    'Error Communicating with Server',
                    new GuzzleRequest(
                        'POST',
                        '/purchase/initialize',
                        (new RequestHeaderCollection(
                            new RequestHeader('unzer-pl-secret-key', 'test-secret-key')
                        )
                        )->getRequestHeadersAsArray(),
                        RequestBuilder::toJson(new InitializePurchaseRequest(
                            new Amount(5000, new Currency(Currency::EUR))
                        ))
                    ),
                    new Response($statusCode, [], $exceptionClass)
                )
            )
        );

        try {
            $purchaseLifecycleApi->initializePurchase(
                new InitializePurchaseRequest(
                    new Amount(5000, new Currency(Currency::EUR))
                ),
                'test-secret-key'
            );
        } catch (ApiResponseException $exception) {
            if (class_exists($exceptionClass)) {
                self::assertInstanceOf($exceptionClass, $exception);
            }
            self::assertSame($statusCode, $exception->getCode());
            self::assertSame($exceptionClass, $exception->getResponseBody());
            self::assertSame($message, $exception->getMessage());
        }
    }

    /**
     * @throws ApiResponseException
     * @throws BuilderException
     */
    public function testRefund(): void
    {
        $purchaseLifecycleApi = new PurchaseLifecycleApi(
            $this->createUnzerPayLaterCommunicator(
                200,
                [],
                '{"result": {"status": "OK"},"purchase": {"purchaseId": "CID-test0987654321"}}'
            )
        );

        /** @var PurchaseOperationResponse $result */
        $result = $purchaseLifecycleApi->refundPurchase(
            new RefundPurchaseRequest(
                'test12345',
                new Amount(5000, new Currency(Currency::EUR)),
                new RefundReason(RefundReason::MERCHANT_CAN_NOT_DELIVER_GOODS)
            ),
            'test-secret-key'
        );

        self::assertInstanceOf(PurchaseOperationResponse::class, $result);
    }

    /**
     * @param int $statusCode
     * @param string $exceptionClass
     * @dataProvider exceptionDataProvider
     * @throws BuilderException
     */
    // phpcs:ignore ObjectCalisthenics.Files.FunctionLength.ObjectCalisthenics\Sniffs\Files\FunctionLengthSniff
    public function testRefundExceptions(
        int $statusCode,
        string $exceptionClass,
        string $message
    ): void {
        $purchaseLifecycleApi = new PurchaseLifecycleApi(
            $this->createUnzerPayLaterCommunicatorException(
                new RequestException(
                    'Error Communicating with Server',
                    new GuzzleRequest(
                        'POST',
                        '/purchase/refund',
                        (new RequestHeaderCollection(
                            new RequestHeader('unzer-pl-secret-key', 'test-secret-key')
                        )
                        )->getRequestHeadersAsArray(),
                        RequestBuilder::toJson(new RefundPurchaseRequest(
                            'test12345',
                            new Amount(5000, new Currency(Currency::EUR)),
                            new RefundReason(RefundReason::MERCHANT_CAN_NOT_DELIVER_GOODS)
                        ))
                    ),
                    new Response($statusCode, [], $exceptionClass)
                )
            )
        );

        try {
            $purchaseLifecycleApi->refundPurchase(
                new RefundPurchaseRequest(
                    'test12345',
                    new Amount(5000, new Currency(Currency::EUR)),
                    new RefundReason(RefundReason::MERCHANT_CAN_NOT_DELIVER_GOODS)
                ),
                'test-secret-key'
            );
        } catch (ApiResponseException $exception) {
            if (class_exists($exceptionClass)) {
                self::assertInstanceOf($exceptionClass, $exception);
            }
            self::assertSame($statusCode, $exception->getCode());
            self::assertSame($exceptionClass, $exception->getResponseBody());
            self::assertSame($message, $exception->getMessage());
        }
    }
}
