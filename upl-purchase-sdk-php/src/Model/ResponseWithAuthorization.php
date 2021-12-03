<?php

/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 */

declare(strict_types=1);

namespace Unzer\PayLater\Model;

use Unzer\PayLater\Communication\Response;

class ResponseWithAuthorization implements Response
{
    /**
     * @var string
     */
    private $authorization;

    /**
     * @var Response
     */
    private $response;

    /**
     */
    public function __construct(string $authorization, Response $response)
    {
        $this->authorization = $authorization;
        $this->response = $response;
    }

    /**
     * @return string
     */
    public function getAuthorization(): string
    {
        return $this->authorization;
    }

    /**
     * @return Response
     */
    public function getResponse(): Response
    {
        return $this->response;
    }
}
