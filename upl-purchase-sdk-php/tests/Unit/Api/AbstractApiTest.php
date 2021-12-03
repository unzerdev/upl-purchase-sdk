<?php

/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 */

declare(strict_types=1);

namespace Unzer\PayLater\Test\Unit\Api;

use GuzzleHttp\Client;
use GuzzleHttp\Exception\GuzzleException;
use GuzzleHttp\Handler\MockHandler;
use GuzzleHttp\HandlerStack;
use GuzzleHttp\Psr7\Response;
use Unzer\PayLater\Communication\ApiConnection;
use Unzer\PayLater\Communication\UnzerPayLaterCommunicator;
use Unzer\PayLater\Exception\ApiResponseException;
use Unzer\PayLater\Exception\AuthorizationException;
use Unzer\PayLater\Exception\ReferenceException;
use Unzer\PayLater\Exception\ServerErrorException;
use Unzer\PayLater\Exception\ValidationException;
use PHPUnit\Framework\TestCase;
use Psr\Log\Test\TestLogger;

abstract class AbstractApiTest extends TestCase
{
    /**
     * @param int $statusCode
     * @param array<string, string> $headers,
     * @param string $responseBody
     * @return UnzerPayLaterCommunicator
     */
    protected function createUnzerPayLaterCommunicator(
        int $statusCode = 200,
        array $headers = [],
        string $responseBody = ''
    ): UnzerPayLaterCommunicator {
        $mockHandler = new MockHandler([
            new Response($statusCode, $headers, $responseBody),
        ]);
        return $this->getUnzerPayLaterCommunicator($mockHandler);
    }

    /**
     * @param GuzzleException ...$exceptions
     * @return UnzerPayLaterCommunicator
     */
    protected function createUnzerPayLaterCommunicatorException(
        GuzzleException ...$exceptions
    ): UnzerPayLaterCommunicator {
        $mockHandler = new MockHandler($exceptions);
        return $this->getUnzerPayLaterCommunicator($mockHandler);
    }

    /**
     * @param MockHandler $mockHandler
     * @return UnzerPayLaterCommunicator
     */
    private function getUnzerPayLaterCommunicator(MockHandler $mockHandler): UnzerPayLaterCommunicator
    {
        return new UnzerPayLaterCommunicator(
            ApiConnection::createWithClient(
                new Client([
                    'handler' => HandlerStack::create($mockHandler),
                ]),
                new TestLogger(),
                false
            )
        );
    }

    /**
     * @return array<string, array>
     */
    // phpcs:ignore ObjectCalisthenics.Files.FunctionLength.ObjectCalisthenics\Sniffs\Files\FunctionLengthSniff
    public function exceptionDataProvider(): array
    {
        return [
            'ValidationException: 400' => [
                400,
                ValidationException::class,
                'the Unzer Pay Later platform returned an incorrect request error response',
            ],
            'AuthorizationException: 401' => [
                401,
                AuthorizationException::class,
                'the Unzer Pay Later platform returned an authorization error response',
            ],
            'AuthorizationException: 403' => [
                403,
                AuthorizationException::class,
                'the Unzer Pay Later platform returned an authorization error response',
            ],
            'ReferenceException: 404' => [
                404,
                ReferenceException::class,
                'the Unzer Pay Later platform returned a reference error response',
            ],
            'ReferenceException: 409' => [
                409,
                ReferenceException::class,
                'the Unzer Pay Later platform returned a reference error response',
            ],
            'ReferenceException: 410' => [
                410,
                ReferenceException::class,
                'the Unzer Pay Later platform returned a reference error response',
            ],
            'ServerErrorException: 500' => [
                500,
                ServerErrorException::class,
                'the Unzer Pay Later platform returned an error response',
            ],
            'ServerErrorException: 502' => [
                502,
                ServerErrorException::class,
                'the Unzer Pay Later platform returned an error response',
            ],
            'ServerErrorException: 503' => [
                503,
                ServerErrorException::class,
                'the Unzer Pay Later platform returned an error response',
            ],
            'ApiResponseException' => [
                200,
                ApiResponseException::class,
                '',
            ],
        ];
    }
}
