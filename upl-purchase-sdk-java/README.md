# Unzer Pay Later Java SDK

The Unzer Pay Later Java SDK lets you communicate with the [Unzer Pay Later platform](https://unzer-pl.stoplight.io/docs/upl-purchase-api/) and provides the following features:

* Handles authentication in all requests towards the API 
* Wrapper around all API calls and responses to make building a request and interpreting responses as easy as possible
* Takes care of marshalling and unmarshalling request and responses
* Processes errors from the API and transforms them in specific exceptions
* Handles decrypting of incoming webhook messages

## Documentation

The project contains multiple integration tests that shows how to use the SDK.

For more examples and detailed information on how to use the SDK, you can go to the [Unzer Pay Later Developer Hub](https://unzer-pl.stoplight.io/docs/upl-purchase-api/).

## Installation

When using [Maven](http://maven.apache.org/), include the SDK as a Maven dependency:

    <dependency>
      <groupId>com.unzer.paylater</groupId>
      <artifactId>unzer-sdk-java</artifactId>
      <version>x.y.z</version>
    </dependency>

If Maven is not a possibility, download the latest version of the SDK from GitHub. Retrieve the `unzer-sdk-java-x.y.z-bin.zip` file from the [releases](https://github.com/unzerdev/upl-purchase-sdk/releases) page, where `x.y.z` is the version number.
Add the JAR files inside the `lib` folder to your project, except for `unzer-sdk-java-x.y.z-sources.jar`

### Requirements
The Unzer Pay Later Java SDK requires Java version 8 or higher.

## Project structure

The project contains three components:

* `/src/main/java/` which contains all source code of the SDK
* `/src/test/java/` which contains all unit tests
* `/src/it/java/` which contains all the integration tests

## Building the project

[Maven](http://maven.apache.org/) is needed to build the project. To build the SDK, the following command must be executed in the directory that contains the `pom.xml` file:

    mvn -clean package

This command will run all unit tests and once succesful generate the following files in the `target` directory:
* `unzer-sdk-java-x.y.z-bin.zip`, contains all the JAR files for standalone deployments
* `unzer-sdk-java-x.y.z.jar`, contains the compiled class files
* `unzer-sdk-java-x.y.z-sources.jar`, contains the source code
* `unzer-sdk-java-x.y.z-src.zip`, contains all files of the project
