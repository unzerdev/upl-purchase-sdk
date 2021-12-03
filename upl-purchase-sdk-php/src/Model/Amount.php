<?php

/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 *
 * This class is based on the Unzer Pay Later OpenAPI specification, version 1.0.0.
 */

declare(strict_types=1);

namespace Unzer\PayLater\Model;

// phpcs:disable ObjectCalisthenics.Metrics.MethodPerClassLimit
// phpcs:disable ObjectCalisthenics.Metrics.PropertyPerClassLimit

class Amount
{
    /**
     * @var int
     */
    private $amount;

    /**
     * @var Currency
     */
    private $currency;

    /**
     * @param int $amount
     * @param Currency $currency
     */
    public function __construct(
        int $amount,
        Currency $currency
    ) {
        $this->amount = $amount;
        $this->currency = $currency;
    }

    /**
     * Amount in cents.
     * @return int
     */
    public function getAmount(): int
    {
        return $this->amount;
    }

    /**
     * @return Currency
     */
    public function getCurrency(): Currency
    {
        return $this->currency;
    }
}
