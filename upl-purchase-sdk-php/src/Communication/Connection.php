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
use Psr\Http\Message\ResponseInterface;

interface Connection
{
    /**
     * @param string $httpMethod
     * @param string $relativePath
     * @param RequestHeaderCollection $requestHeaders
     * @param Request|null $requestBody
     * @return ResponseInterface
     * @throws BuilderException
     * @throws ResponseException
     */
    public function execute(
        string $httpMethod,
        string $relativePath,
        RequestHeaderCollection $requestHeaders,
        ?Request $requestBody = null
    ): ResponseInterface;
}
