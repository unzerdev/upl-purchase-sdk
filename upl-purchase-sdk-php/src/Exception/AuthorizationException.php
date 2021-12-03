<?php

/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 */

declare(strict_types=1);

namespace Unzer\PayLater\Exception;

use Unzer\PayLater\Communication\ResponseHeaderCollection;
use Unzer\PayLater\Model\OperationResult;
use Throwable;

class AuthorizationException extends ApiResponseException
{
    /**
     * @param int $statusCode
     * @param string $responseBody
     * @param ResponseHeaderCollection $responseHeaders
     * @param string|null $errorId
     * @param string|null $errorMessage
     * @param OperationResult|null $operationResult
     * @param Throwable|null $previous
     */
    public function __construct(
        int $statusCode,
        string $responseBody,
        ResponseHeaderCollection $responseHeaders,
        ?string $errorId,
        ?string $errorMessage,
        ?OperationResult $operationResult,
        ?Throwable $previous = null
    ) {
        $message = $errorMessage !== null ?
            $errorMessage :
            'the Unzer Pay Later platform returned an authorization error response';

        parent::__construct(
            $message,
            $statusCode,
            $responseBody,
            $responseHeaders,
            $errorId,
            $operationResult,
            $previous
        );
    }
}
