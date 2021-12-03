<?php

/**
 * Purchase API
 * @copyright Copyright (c) 2020 Unzer Pay Later
 * @license see LICENSE.TXT
 */

declare(strict_types=1);

namespace Unzer\PayLater\Test\Unit\Communication;

use GuzzleHttp\Psr7\Request;
use Unzer\PayLater\Communication\MessageAnonymizerFormatter;
use Unzer\PayLater\Communication\RequestBuilder;
use Unzer\PayLater\Exception\BuilderException;
use Unzer\PayLater\Model\Amount;
use Unzer\PayLater\Model\Consumer;
use Unzer\PayLater\Model\Currency;
use Unzer\PayLater\Model\InitializePurchaseRequest;
use Unzer\PayLater\Model\OperationResult;
use Unzer\PayLater\Model\Person;
use Unzer\PayLater\Model\PurchaseInformation;
use Unzer\PayLater\Model\PurchaseOperationResponse;
use PHPUnit\Framework\TestCase;

use function trim;

// phpcs:disable ObjectCalisthenics.Files.FunctionLength.ObjectCalisthenics\Sniffs\Files\FunctionLengthSniff
// phpcs:disable SlevomatCodingStandard.Files.LineLength.LineTooLong

class MessageAnonymizerFormatterTest extends TestCase
{
    /**
     * @param string $expected
     * @param string $requestBody
     * @param string $substitute
     * @dataProvider messageAnonymizerFormatterProvider
     */
    public function testMessageAnonymizerFormatter(
        string $expected,
        string $requestBody,
        string $substitute = '*****'
    ): void {
        $formatter = new MessageAnonymizerFormatter('{request}', $substitute);
        $request = new Request('GET', '/', [], $requestBody);
        $expected = "GET / HTTP/1.1\r\nHost: \r\n\r\n" . $expected;
        self::assertEquals($expected, trim($formatter->format($request)));
    }

    /**
     * @return array<string, array>
     * @throws BuilderException
     */
    public function messageAnonymizerFormatterProvider(): array
    {
        $request = new InitializePurchaseRequest(new Amount(25000, new Currency(Currency::EUR)));
        $request->setConsumer(new Consumer(new Person('Dhr', 'Unit', 'Test')));
        $request->setAdditionalInformation('Test Additional Information');

        $response = new PurchaseOperationResponse(
            new OperationResult('test1234567890'),
            new PurchaseInformation('test0987654321')
        );

        return [
            'no Anonymization' => [
                '{"test":"test"}',
                '{"test":"test"}',
            ],
            'consumer single field' => [
                '{"test":"test","consumer":"*****","request":"b"}',
                '{"test":"test","consumer":"test","request":"b"}',
            ],
            'consumer object field' => [
                '{"purchaseAmount":{"amount":25000,"currency":"EUR"},"consumer":{*****},"additionalInformation":"Test Additional Information"}',
                RequestBuilder::toJson($request),
            ],
            'purchase object field' => [
                '{"result":{"operationId":"test1234567890"},"purchase":{*****}}',
                RequestBuilder::toJson($response),
            ],
            'other substitute' => [
                '{"test":"test","consumer":"###","request":"b"}',
                '{"test":"test","consumer":"test","request":"b"}',
                '###',
            ],
        ];
    }

    /**
     * @param string $expected
     * @param array<string, string> $headers
     * @dataProvider messageAnonymizerFormatterHeaderProvider
     */
    public function testMessageAnonymizerFormatterHeaders(string $expected, array $headers): void
    {
        $formatter = new MessageAnonymizerFormatter('{req_headers}');
        $request = new Request('GET', '/', $headers);
        $expected = "GET / HTTP/1.1\r\n" . $expected;
        self::assertEquals($expected, trim($formatter->format($request)));
    }

    /**
     * @return array<string, array>
     */
    public function messageAnonymizerFormatterHeaderProvider(): array
    {
        return [
            'no Anonymization' => [
                "content-type: application/json\r\nAccept: application/json",
                [
                    'content-type' => 'application/json',
                    'Accept' => 'application/json',
                ],
            ],
            'with Anonymization' => [
                "content-type: application/json\r\naccess_token: *****\r\nAccept: application/json",
                [
                    'content-type' => 'application/json',
                    'access_token' => '1234567890',
                    'Accept' => 'application/json',
                ],
            ],
        ];
    }
}
