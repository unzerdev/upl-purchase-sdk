<?php

/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 */

declare(strict_types=1);

namespace Unzer\PayLater\Test\Integration\Api;

use Unzer\PayLater\Api\LegalDocumentsApi;
use Unzer\PayLater\Api\PurchaseAuthorizationApi;
use Unzer\PayLater\Api\PurchaseLifecycleApi;
use Unzer\PayLater\Exception\ApiResponseException;
use Unzer\PayLater\Exception\BuilderException;
use Unzer\PayLater\Model\Amount;
use Unzer\PayLater\Model\AuthorizePurchaseRequest;
use Unzer\PayLater\Model\Consumer;
use Unzer\PayLater\Model\Currency;
use Unzer\PayLater\Model\InitializePurchaseRequest;
use Unzer\PayLater\Model\MethodType;
use Unzer\PayLater\Model\OperationResult;
use Unzer\PayLater\Model\OperationStatus;
use Unzer\PayLater\Model\Person;
use Unzer\PayLater\Model\PurchaseInformation;
use Unzer\PayLater\Model\PurchaseOperationResponse;
use Unzer\PayLater\Model\PurchaseState;
use Unzer\PayLater\Model\ResponseWithAuthorization;

// phpcs:disable ObjectCalisthenics.Files.FunctionLength.ObjectCalisthenics\Sniffs\Files\FunctionLengthSniff

class ApiTest extends AbstractApiTest
{
    /**
     * @return ResponseWithAuthorization
     * @throws ApiResponseException
     * @throws BuilderException
     */
    public function testInitializePurchase(): ResponseWithAuthorization
    {
        $purchaseLifecycleApi = new PurchaseLifecycleApi($this->createUnzerPayLaterCommunicator());
        $result = $purchaseLifecycleApi->initializePurchase(
            new InitializePurchaseRequest(
                new Amount(25000, new Currency(Currency::EUR)),
                new Consumer(
                    new Person('Dhr', 'Test', 'Demo')
                )
            ),
            $this->getSecretKey()
        );

        // Make sure the authorization code is a valid JWT (using regex)
        self::assertRegExp('/^[A-Za-z0-9-_=]+\.[A-Za-z0-9-_=]+\.?[A-Za-z0-9-_.+\/=]*$/', $result->getAuthorization());

        /** @var PurchaseOperationResponse $response */
        $response = $result->getResponse();

        /** @var OperationResult $operationResult */
        $operationResult = $response->getResult();
        self::assertInstanceOf(OperationResult::class, $operationResult);

        /** @var OperationStatus $operationStatus */
        $operationStatus = $operationResult->getStatus();
        self::assertInstanceOf(OperationStatus::class, $operationStatus);
        self::assertSame(OperationStatus::OK, $operationStatus->getValue());

        return $result;
    }

    /**
     * @param ResponseWithAuthorization $responseWithAuthorization
     * @depends testInitializePurchase
     * @throws BuilderException
     * @throws ApiResponseException
     */
    public function testGetPurchaseWithAuthorization(ResponseWithAuthorization $responseWithAuthorization): void
    {
        $purchaseLifecycleApi = new PurchaseLifecycleApi($this->createUnzerPayLaterCommunicator());

        /** @var PurchaseOperationResponse $response */
        $response = $responseWithAuthorization->getResponse();

        /** @var PurchaseInformation $purchaseInformation */
        $purchaseInformation = $response->getPurchase();
        self::assertInstanceOf(PurchaseInformation::class, $purchaseInformation);

        /** @var PurchaseOperationResponse $purchaseOperationResponse */
        $purchaseOperationResponse = $purchaseLifecycleApi->getPurchaseWithAuthorization(
            (string) $purchaseInformation->getPurchaseId(),
            $responseWithAuthorization->getAuthorization()
        );

        /** @var OperationResult $operationResult */
        $operationResult = $purchaseOperationResponse->getResult();
        self::assertInstanceOf(OperationResult::class, $operationResult);

        /** @var OperationStatus $operationStatus */
        $operationStatus = $operationResult->getStatus();
        self::assertInstanceOf(OperationStatus::class, $operationStatus);
        self::assertSame(OperationStatus::OK, $operationStatus->getValue());

        /** @var PurchaseInformation $purchaseInformation */
        $purchaseInformation = $purchaseOperationResponse->getPurchase();
        self::assertInstanceOf(PurchaseInformation::class, $purchaseInformation);

        /** @var PurchaseState $purchaseState */
        $purchaseState = $purchaseInformation->getState();
        self::assertInstanceOf(PurchaseState::class, $purchaseState);
        self::assertSame(PurchaseState::INITIALIZED, $purchaseState->getValue());
    }

    /**
     * @param ResponseWithAuthorization $responseWithAuthorization
     * @depends testInitializePurchase
     * @throws BuilderException
     * @throws ApiResponseException
     */
    public function testGetPurchase(ResponseWithAuthorization $responseWithAuthorization): void
    {
        $purchaseLifecycleApi = new PurchaseLifecycleApi($this->createUnzerPayLaterCommunicator());

        /** @var PurchaseOperationResponse $response */
        $response = $responseWithAuthorization->getResponse();

        /** @var PurchaseInformation $purchaseInformation */
        $purchaseInformation = $response->getPurchase();
        self::assertInstanceOf(PurchaseInformation::class, $purchaseInformation);

        /** @var PurchaseOperationResponse $purchaseOperationResponse */
        $purchaseOperationResponse = $purchaseLifecycleApi->getPurchase(
            (string) $purchaseInformation->getPurchaseId(),
            $this->getSecretKey()
        );

        /** @var OperationResult $operationResult */
        $operationResult = $purchaseOperationResponse->getResult();
        self::assertInstanceOf(OperationResult::class, $operationResult);

        /** @var OperationStatus $operationStatus */
        $operationStatus = $operationResult->getStatus();
        self::assertInstanceOf(OperationStatus::class, $operationStatus);
        self::assertSame(OperationStatus::OK, $operationStatus->getValue());

        /** @var PurchaseInformation $purchaseInformation */
        $purchaseInformation = $purchaseOperationResponse->getPurchase();
        self::assertInstanceOf(PurchaseInformation::class, $purchaseInformation);

        /** @var PurchaseState $purchaseState */
        $purchaseState = $purchaseInformation->getState();
        self::assertInstanceOf(PurchaseState::class, $purchaseState);
        self::assertSame(PurchaseState::INITIALIZED, $purchaseState->getValue());
    }

    /**
     * @param ResponseWithAuthorization $responseWithAuthorization
     * @depends testInitializePurchase
     * @throws ApiResponseException
     * @throws BuilderException
     */
    public function testAuthorizePaylater(ResponseWithAuthorization $responseWithAuthorization): void
    {
        $purchaseAuthorizationApi = new PurchaseAuthorizationApi($this->createUnzerPayLaterCommunicator());

        /** @var PurchaseOperationResponse $response */
        $response = $responseWithAuthorization->getResponse();

        /** @var PurchaseInformation $purchaseInformation */
        $purchaseInformation = $response->getPurchase();
        self::assertInstanceOf(PurchaseInformation::class, $purchaseInformation);

        /** @var PurchaseOperationResponse $purchaseOperationResponse */
        $purchaseOperationResponse = $purchaseAuthorizationApi->authorizePayLater(
            new AuthorizePurchaseRequest(
                (string) $purchaseInformation->getPurchaseId(),
                new MethodType(MethodType::URL)
            ),
            $this->getSecretKey()
        );

        /** @var OperationResult $operationResult */
        $operationResult = $purchaseOperationResponse->getResult();
        self::assertInstanceOf(OperationResult::class, $operationResult);

        /** @var OperationStatus $operationStatus */
        $operationStatus = $operationResult->getStatus();
        self::assertInstanceOf(OperationStatus::class, $operationStatus);
        self::assertSame(OperationStatus::OK, $operationStatus->getValue());

        /** @var PurchaseInformation $purchaseInformation */
        $purchaseInformation = $purchaseOperationResponse->getPurchase();
        self::assertInstanceOf(PurchaseInformation::class, $purchaseInformation);

        /** @var PurchaseState $purchaseState */
        $purchaseState = $purchaseInformation->getState();
        self::assertInstanceOf(PurchaseState::class, $purchaseState);
        self::assertSame(PurchaseState::INITIALIZED, $purchaseState->getValue());

        /** @var array<string, string> $metaData */
        $metaData = $purchaseInformation->getMetaData();
        self::assertIsArray($metaData);
        self::assertArrayHasKey('INSTORE_SELFSERVICE_AUTH_URL', $metaData);
    }

    /**
     * @param ResponseWithAuthorization $responseWithAuthorization
     * @depends testInitializePurchase
     * @throws ApiResponseException
     * @throws BuilderException
     */
    public function testAuthorizePaylaterWithAuthorization(ResponseWithAuthorization $responseWithAuthorization): void
    {
        $purchaseAuthorizationApi = new PurchaseAuthorizationApi($this->createUnzerPayLaterCommunicator());

        /** @var PurchaseOperationResponse $response */
        $response = $responseWithAuthorization->getResponse();

        /** @var PurchaseInformation $purchaseInformation */
        $purchaseInformation = $response->getPurchase();
        self::assertInstanceOf(PurchaseInformation::class, $purchaseInformation);

        /** @var PurchaseOperationResponse $purchaseOperationResponse */
        $purchaseOperationResponse = $purchaseAuthorizationApi->authorizePayLaterWithAuthorization(
            new AuthorizePurchaseRequest(
                (string) $purchaseInformation->getPurchaseId(),
                new MethodType(MethodType::URL)
            ),
            $responseWithAuthorization->getAuthorization()
        );

        /** @var OperationResult $operationResult */
        $operationResult = $purchaseOperationResponse->getResult();
        self::assertInstanceOf(OperationResult::class, $operationResult);

        /** @var OperationStatus $operationStatus */
        $operationStatus = $operationResult->getStatus();
        self::assertInstanceOf(OperationStatus::class, $operationStatus);
        self::assertSame(OperationStatus::OK, $operationStatus->getValue());

        /** @var PurchaseInformation $purchaseInformation */
        $purchaseInformation = $purchaseOperationResponse->getPurchase();
        self::assertInstanceOf(PurchaseInformation::class, $purchaseInformation);

        /** @var PurchaseState $purchaseState */
        $purchaseState = $purchaseInformation->getState();
        self::assertInstanceOf(PurchaseState::class, $purchaseState);
        self::assertSame(PurchaseState::INITIALIZED, $purchaseState->getValue());

        /** @var array<string, string> $metaData */
        $metaData = $purchaseInformation->getMetaData();
        self::assertIsArray($metaData);
        self::assertArrayHasKey('INSTORE_SELFSERVICE_AUTH_URL', $metaData);
    }

    /**
     * @param ResponseWithAuthorization $responseWithAuthorization
     * @depends testInitializePurchase
     * @throws BuilderException
     * @throws ApiResponseException
     */
    public function testTermsAndConditions(ResponseWithAuthorization $responseWithAuthorization): void
    {
        $legalDocumentsApi = new LegalDocumentsApi($this->createUnzerPayLaterCommunicator());

        /** @var PurchaseOperationResponse $response */
        $response = $responseWithAuthorization->getResponse();

        /** @var PurchaseInformation $purchaseInformation */
        $purchaseInformation = $response->getPurchase();
        self::assertInstanceOf(PurchaseInformation::class, $purchaseInformation);

        self::assertNotEmpty($legalDocumentsApi->getTermsAndConditions(
            (string) $purchaseInformation->getPurchaseId(),
            $this->getSecretKey()
        ));
    }

    /**
     * @param ResponseWithAuthorization $responseWithAuthorization
     * @depends testInitializePurchase
     * @throws BuilderException
     * @throws ApiResponseException
     */
    public function testTermsAndConditionsWithAuthorization(ResponseWithAuthorization $responseWithAuthorization): void
    {
        $legalDocumentsApi = new LegalDocumentsApi($this->createUnzerPayLaterCommunicator());

        /** @var PurchaseOperationResponse $response */
        $response = $responseWithAuthorization->getResponse();

        /** @var PurchaseInformation $purchaseInformation */
        $purchaseInformation = $response->getPurchase();
        self::assertInstanceOf(PurchaseInformation::class, $purchaseInformation);

        self::assertNotEmpty($legalDocumentsApi->getTermsAndConditionsWithAuthorization(
            (string) $purchaseInformation->getPurchaseId(),
            $responseWithAuthorization->getAuthorization()
        ));
    }
}
