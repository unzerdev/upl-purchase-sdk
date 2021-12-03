<?php

/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 */

declare(strict_types=1);

namespace Unzer\PayLater\Communication;

use Unzer\PayLater\Exception\BuilderException;
use Unzer\PayLater\Exception\ResponseException;
use Unzer\PayLater\Model\ResponseWithAuthorization;

interface Communicator
{
    /**
     * @param string $httpMethod
     * @param string $relativePath
     * @param RequestHeaderCollection $requestHeaderCollection ,
     * @param Request|null $requestBody
     * @return string
     * @throws BuilderException
     * @throws ResponseException
     */
    public function getStringResponse(
        string $httpMethod,
        string $relativePath,
        RequestHeaderCollection $requestHeaderCollection,
        ?Request $requestBody
    ): string;

    /**
     * @param string $httpMethod
     * @param string $relativePath
     * @param RequestHeaderCollection $requestHeaderCollection
     * @param Request|null $requestBody
     * @param string $responseClass
     * @return Response
     * @throws BuilderException
     * @throws ResponseException
     */
    public function getGenericResponse(
        string $httpMethod,
        string $relativePath,
        RequestHeaderCollection $requestHeaderCollection,
        ?Request $requestBody,
        string $responseClass
    ): Response;

    /**
     * @param string $httpMethod
     * @param string $relativePath
     * @param RequestHeaderCollection $requestHeaderCollection
     * @param Request|null $requestBody
     * @param string $responseClass
     * @return ResponseWithAuthorization
     * @throws BuilderException
     * @throws ResponseException
     */
    public function getResponseWithAuthorization(
        string $httpMethod,
        string $relativePath,
        RequestHeaderCollection $requestHeaderCollection,
        ?Request $requestBody,
        string $responseClass
    ): ResponseWithAuthorization;
}
