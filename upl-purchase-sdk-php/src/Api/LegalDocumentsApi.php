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
use Unzer\PayLater\Exception\ApiResponseException;
use Unzer\PayLater\Exception\BuilderException;
use Unzer\PayLater\Exception\ResponseException;

class LegalDocumentsApi extends BaseApi
{
    /**
     * Generates a terms-and-conditions document in html format.
     *
     * @param string $purchaseId The purchaseId received from the initialize request that started the verification process.
     * @param string $authorization The access token received from the initialize request. Provide this for client-side requests in the Bearer format.
     * @return string Terms and conditions in HTML format.
     * @throws ApiResponseException
     * @throws BuilderException
     */
    public function getTermsAndConditionsWithAuthorization(
        string $purchaseId,
        string $authorization
    ): string {
        $uri = '/purchase/legaldocuments/termsandconditions/{purchaseId}';
        $uri = $this->populateUri($uri, 'purchaseId', $purchaseId);

        $requestHeaderCollection = new RequestHeaderCollection(
            new RequestHeader('Authorization', 'Bearer ' . $authorization)
        );

        try {
            return $this->communicator->getStringResponse(
                HttpMethod::GET,
                $uri,
                $requestHeaderCollection,
                null
            );
        } catch (ResponseException $exception) {
            throw $this->createApiResponseException($exception);
        }
    }

    /**
     * Generates a terms-and-conditions document in html format.
     *
     * @param string $purchaseId The purchaseId received from the initialize request that started the verification process.
     * @param string $unzerPlSecretKey Secret key which can be requested from your account manager. Provide this for server-to-server communication.
     * @return string Terms and conditions in HTML format.
     * @throws ApiResponseException
     * @throws BuilderException
     */
    public function getTermsAndConditions(
        string $purchaseId,
        string $unzerPlSecretKey
    ): string {
        $uri = '/purchase/legaldocuments/termsandconditions/{purchaseId}';
        $uri = $this->populateUri($uri, 'purchaseId', $purchaseId);

        $requestHeaderCollection = new RequestHeaderCollection(
            new RequestHeader('unzer-pl-secret-key', $unzerPlSecretKey)
        );

        try {
            return $this->communicator->getStringResponse(
                HttpMethod::GET,
                $uri,
                $requestHeaderCollection,
                null
            );
        } catch (ResponseException $exception) {
            throw $this->createApiResponseException($exception);
        }
    }
}
