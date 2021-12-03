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
use Unzer\PayLater\Model\AuthorizePurchaseRequest;
use Unzer\PayLater\Model\PurchaseOperationResponse;

class PurchaseAuthorizationApi extends BaseApi
{
    /**
     * Authorize a consumer to complete a transaction with our hosted solution. Can be started via SMS or URL.
     *
     * @param AuthorizePurchaseRequest $authorizePurchaseRequest Contains everything needed to start the Authorization Process.
     * @param string $authorization The access token received from the initialize request. Provide this for client-side requests in the Bearer format.
     * @return Response PurchaseAuthorization endpoints always return the same object with different state of the purchase and different fields populated. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null.
     * @throws ApiResponseException
     * @throws BuilderException
     */
    public function authorizePayLaterWithAuthorization(
        AuthorizePurchaseRequest $authorizePurchaseRequest,
        string $authorization
    ): Response {
        $uri = '/purchase/authorize/paylater';

        $requestHeaderCollection = new RequestHeaderCollection(
            new RequestHeader('Authorization', 'Bearer ' . $authorization)
        );

        try {
            return $this->communicator->getGenericResponse(
                HttpMethod::POST,
                $uri,
                $requestHeaderCollection,
                $authorizePurchaseRequest,
                PurchaseOperationResponse::class
            );
        } catch (ResponseException $exception) {
            throw $this->createApiResponseException($exception);
        }
    }

    /**
     * Authorize a consumer to complete a transaction with our hosted solution. Can be started via SMS or URL.
     *
     * @param AuthorizePurchaseRequest $authorizePurchaseRequest Contains everything needed to start the Authorization Process.
     * @param string $unzerPlSecretKey Secret key which can be requested from your account manager. Provide this for server-to-server communication.
     * @return Response PurchaseAuthorization endpoints always return the same object with different state of the purchase and different fields populated. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null. PurchaseLifecycle endpoints also return the same object when an error occurs. The purchase object however will be null.
     * @throws ApiResponseException
     * @throws BuilderException
     */
    public function authorizePayLater(
        AuthorizePurchaseRequest $authorizePurchaseRequest,
        string $unzerPlSecretKey
    ): Response {
        $uri = '/purchase/authorize/paylater';

        $requestHeaderCollection = new RequestHeaderCollection(
            new RequestHeader('unzer-pl-secret-key', $unzerPlSecretKey)
        );

        try {
            return $this->communicator->getGenericResponse(
                HttpMethod::POST,
                $uri,
                $requestHeaderCollection,
                $authorizePurchaseRequest,
                PurchaseOperationResponse::class
            );
        } catch (ResponseException $exception) {
            throw $this->createApiResponseException($exception);
        }
    }
}
