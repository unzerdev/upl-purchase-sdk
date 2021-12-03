# Unzer Pay Later Client SDK

The Unzer Pay Later Client SDK lets you communicate with the [Unzer Pay Later platform](https://unzer-pl.stoplight.io/docs/upl-purchase-api/) and provides the following features:

* Handles authentication in all requests towards the API 
* Wrapper around all API calls and responses to make building a request and interpreting responses as easy as possible
* Takes care of marshalling and unmarshalling request and responses
* Processes errors from the API and transforms them in specific exceptions
* Handles decrypting of incoming webhook messages

## Documentation

The project contains multiple unit and integration tests that shows how to use the SDK.

For more examples and detailed information on how to use the SDK, you can go to the [Unzer Pay Later Developer Hub](https://unzer-pl.stoplight.io/docs/upl-purchase-api/).

## Installation

The Unzer Pay Later Client SDK can be installed using [NPM](https://www.npmjs.com/) by including the following to the package.json file:

```json
"unzer-paylater-sdk-client-javascript": "^1.0.0"
```

### Requirements
The Unzer Pay Later client-javascript.js SDK requires: TODO: browser  versions?

## Project structure

The project contains three components:

* `/src/unzer-paylater/` which contains all source code of the SDK
* `/test/` which contains all unit and integration tests
