<?php

/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 *
 * This class is based on the Unzer Pay Later OpenAPI specification, version 1.0.0.
 */

declare(strict_types=1);

namespace Unzer\PayLater\Api;

use Unzer\PayLater\Communication\HttpMethod;
use Unzer\PayLater\Communication\RequestHeader;
use Unzer\PayLater\Communication\RequestHeaderCollection;
use Unzer\PayLater\Communication\Response;
use Unzer\PayLater\Exception\ApiResponseException;
use Unzer\PayLater\Exception\BuilderException;
use Unzer\PayLater\Exception\ResponseException;
use Unzer\PayLater\Model\CapturePurchaseRequest;
use Unzer\PayLater\Model\InitializePurchaseRequest;
use Unzer\PayLater\Model\PurchaseOperationResponse;
use Unzer\PayLater\Model\RefundPurchaseRequest;
use Unzer\PayLater\Model\ResponseWithAuthorization;

class PurchaseLifecycleApi extends BaseApi
{
    /**
     * Confirm a capture(=shipping) of the purchased goods.
     *
     * @param CapturePurchaseRequest $capturePurchaseRequest Contains all data needed to process a capture(=shipping) of purchased goods.
     * @param string $unzerPlSecretKey Secret key which can be requested from your account manager. Only use this for server-to-server communication.
     * @return Response PurchaseLifecycle endpoints always return the same object with the latest state of the purchase and different fields populated. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null.
     * @throws ApiResponseException
     * @throws BuilderException
     */
    public function capturePurchase(
        CapturePurchaseRequest $capturePurchaseRequest,
        string $unzerPlSecretKey
    ): Response {
        $uri = '/purchase/capture';

        $requestHeaderCollection = new RequestHeaderCollection(
            new RequestHeader('unzer-pl-secret-key', $unzerPlSecretKey)
        );

        try {
            return $this->communicator->getGenericResponse(
                HttpMethod::POST,
                $uri,
                $requestHeaderCollection,
                $capturePurchaseRequest,
                PurchaseOperationResponse::class
            );
        } catch (ResponseException $exception) {
            throw $this->createApiResponseException($exception);
        }
    }

    /**
     * Query for a purchase for a given purchaseId.
     *
     * @param string $purchaseId PurchaseId received from initializePurchase or authorizePurchase response.
     * @param string $authorization The access token received from the initialize request. Provide this for client-side requests in the Bearer format.
     * @return Response PurchaseLifecycle endpoints always return the same object with the latest state of the purchase and different fields populated. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null.
     * @throws ApiResponseException
     * @throws BuilderException
     */
    public function getPurchaseWithAuthorization(
        string $purchaseId,
        string $authorization
    ): Response {
        $uri = '/purchase/info/{purchaseId}';
        $uri = $this->populateUri($uri, 'purchaseId', $purchaseId);

        $requestHeaderCollection = new RequestHeaderCollection(
            new RequestHeader('Authorization', 'Bearer ' . $authorization)
        );

        try {
            return $this->communicator->getGenericResponse(
                HttpMethod::GET,
                $uri,
                $requestHeaderCollection,
                null,
                PurchaseOperationResponse::class
            );
        } catch (ResponseException $exception) {
            throw $this->createApiResponseException($exception);
        }
    }

    /**
     * Query for a purchase for a given purchaseId.
     *
     * @param string $purchaseId PurchaseId received from initializePurchase or authorizePurchase response.
     * @param string $unzerPlSecretKey Secret key which can be requested from your account manager. Only use this for server-to-server communication.
     * @return Response PurchaseLifecycle endpoints always return the same object with the latest state of the purchase and different fields populated. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null.
     * @throws ApiResponseException
     * @throws BuilderException
     */
    public function getPurchase(
        string $purchaseId,
        string $unzerPlSecretKey
    ): Response {
        $uri = '/purchase/info/{purchaseId}';
        $uri = $this->populateUri($uri, 'purchaseId', $purchaseId);

        $requestHeaderCollection = new RequestHeaderCollection(
            new RequestHeader('unzer-pl-secret-key', $unzerPlSecretKey)
        );

        try {
            return $this->communicator->getGenericResponse(
                HttpMethod::GET,
                $uri,
                $requestHeaderCollection,
                null,
                PurchaseOperationResponse::class
            );
        } catch (ResponseException $exception) {
            throw $this->createApiResponseException($exception);
        }
    }

    /**
     * Initializes a purchase for a given amount and returns a response with all pre-configured (non-binding) payment options.
     *
     * @param InitializePurchaseRequest $initializePurchaseRequest Contains the data needed to initialize a purchase.
     * @param string $unzerPlSecretKey Secret key which can be requested from your account manager. Only use this for server-to-server communication.
     * @return ResponseWithAuthorization PurchaseLifecycle endpoints always return the same object with the latest state of the purchase and different fields populated.  In addition, the initialize operation returns a single-purchase authentication token in the response header <<access_token>>. This token has to be used by client-side callers. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null.
     * @throws ApiResponseException
     * @throws BuilderException
     */
    public function initializePurchase(
        InitializePurchaseRequest $initializePurchaseRequest,
        string $unzerPlSecretKey
    ): ResponseWithAuthorization {
        $uri = '/purchase/initialize';

        $requestHeaderCollection = new RequestHeaderCollection(
            new RequestHeader('unzer-pl-secret-key', $unzerPlSecretKey)
        );

        try {
            return $this->communicator->getResponseWithAuthorization(
                HttpMethod::POST,
                $uri,
                $requestHeaderCollection,
                $initializePurchaseRequest,
                PurchaseOperationResponse::class
            );
        } catch (ResponseException $exception) {
            throw $this->createApiResponseException($exception);
        }
    }

    /**
     * Refund part of or the full purchase amount in case consumer returned purchased goods.
     *
     * @param RefundPurchaseRequest $refundPurchaseRequest All data needed to process a refund of a purchase.
     * @param string $unzerPlSecretKey Secret key which can be requested from your account manager. Only use this for server-to-server communication.
     * @return Response PurchaseLifecycle endpoints always return the same object with the latest state of the purchase and different fields populated. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null.
     * @throws ApiResponseException
     * @throws BuilderException
     */
    public function refundPurchase(
        RefundPurchaseRequest $refundPurchaseRequest,
        string $unzerPlSecretKey
    ): Response {
        $uri = '/purchase/refund';

        $requestHeaderCollection = new RequestHeaderCollection(
            new RequestHeader('unzer-pl-secret-key', $unzerPlSecretKey)
        );

        try {
            return $this->communicator->getGenericResponse(
                HttpMethod::POST,
                $uri,
                $requestHeaderCollection,
                $refundPurchaseRequest,
                PurchaseOperationResponse::class
            );
        } catch (ResponseException $exception) {
            throw $this->createApiResponseException($exception);
        }
    }
}
