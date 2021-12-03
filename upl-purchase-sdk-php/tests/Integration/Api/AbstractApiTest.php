<?php

/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 */

declare(strict_types=1);

namespace Unzer\PayLater\Test\Integration\Api;

use Unzer\PayLater\Communication\ApiConnection;
use Unzer\PayLater\Communication\ConnectionConfiguration;
use Unzer\PayLater\Communication\UnzerPayLaterCommunicator;
use Unzer\PayLater\Test\Integration\AbstractTestCase;
use Psr\Log\Test\TestLogger;

abstract class AbstractApiTest extends AbstractTestCase
{
    /**
     * @return UnzerPayLaterCommunicator
     */
    protected function createUnzerPayLaterCommunicator(): UnzerPayLaterCommunicator
    {
        $configuration = new ConnectionConfiguration($this->baseUrl);
        $configuration->setLogger(new TestLogger());

        return new UnzerPayLaterCommunicator(
            ApiConnection::create($configuration)
        );
    }
}
