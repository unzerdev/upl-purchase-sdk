<?php

/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 */

declare(strict_types=1);

namespace Unzer\PayLater\Test\Unit\Communication;

use GuzzleHttp\Client;
use Unzer\PayLater\Communication\ApiConnection;
use Unzer\PayLater\Communication\ConnectionConfiguration;
use PHPUnit\Framework\TestCase;
use Psr\Log\Test\TestLogger;

class ApiConnectionTest extends TestCase
{
    public function testApiConnection(): void
    {
        $configuration = new ConnectionConfiguration('');
        $configuration->setLogger(new TestLogger());

        $api = ApiConnection::create($configuration);
        self::assertInstanceOf(ApiConnection::class, $api);

        // Enable debug mode to check if log message format changes
        $configuration->setDebug(true);

        $api = ApiConnection::create($configuration);
        self::assertInstanceOf(ApiConnection::class, $api);
    }

    public function testApiConnectionWithClient(): void
    {
        $api = ApiConnection::createWithClient(new Client([]), new TestLogger(), false);
        self::assertInstanceOf(ApiConnection::class, $api);
    }
}
