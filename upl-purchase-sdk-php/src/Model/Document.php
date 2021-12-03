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

class Document
{
    /**
     * @var string|null
     */
    private $name;

    /**
     * @var string|null
     */
    private $url;

    /**
     * @param string|null $name
     * @param string|null $url
     */
    public function __construct(
        string $name = null,
        string $url = null
    ) {
        $this->name = $name;
        $this->url = $url;
    }

    /**
     * @return string|null
     */
    public function getName(): ?string
    {
        return $this->name;
    }

    /**
     * @param string $name
     * @return Document
     */
    public function setName(string $name): Document
    {
        $this->name = $name;
        return $this;
    }

    /**
     * @return string|null
     */
    public function getUrl(): ?string
    {
        return $this->url;
    }

    /**
     * @param string $url
     * @return Document
     */
    public function setUrl(string $url): Document
    {
        $this->url = $url;
        return $this;
    }
}
