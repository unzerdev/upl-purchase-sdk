package com.unzer.paylater.communication;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

public class CommunicatorConfigurationTest {

    private static final String TEST_ENDPOINT_HOST = "test.com/";
    private static final String TEST_ENDPOINT = "https://" + TEST_ENDPOINT_HOST;
    private static final int DEFAULT_CONNECT_TIMEOUT = CommunicatorConfiguration.DEFAULT_CONNECT_TIMEOUT;
    private static final int DEFAULT_READ_TIMEOUT = CommunicatorConfiguration.DEFAULT_READ_TIMEOUT;
    private static final int DEFAULT_MAX_CONNECTIONS = CommunicatorConfiguration.DEFAULT_MAX_CONNECTIONS;
    private static final Set<String> DEFAULT_HTTPS_PROTOCOLS = CommunicatorConfiguration.DEFAULT_HTTPS_PROTOCOLS;

    @Test
    public void testConstructFromProperties() {

        Properties properties = new Properties();
        properties.setProperty("unzer.paylater.api.endpoint.host", TEST_ENDPOINT_HOST);

        assertConfiguration(properties,
                TEST_ENDPOINT,
                DEFAULT_CONNECT_TIMEOUT,
                DEFAULT_READ_TIMEOUT,
                DEFAULT_MAX_CONNECTIONS,
                DEFAULT_HTTPS_PROTOCOLS);
    }

    @Test
    public void testConstructFromPropertiesWithTimeouts() {

        Properties properties = new Properties();
        properties.setProperty("unzer.paylater.api.endpoint.host", TEST_ENDPOINT_HOST);
        properties.setProperty("unzer.paylater.api.connectTimeout", "7");
        properties.setProperty("unzer.paylater.api.readTimeout", "5");

        assertConfiguration(properties,
                TEST_ENDPOINT,
                7,
                5,
                DEFAULT_MAX_CONNECTIONS,
                DEFAULT_HTTPS_PROTOCOLS);
    }

    @Test
    public void testConstructFromPropertiesWithMaxConnections() {

        Properties properties = new Properties();
        properties.setProperty("unzer.paylater.api.endpoint.host", TEST_ENDPOINT_HOST);
        properties.setProperty("unzer.paylater.api.maxConnections", "100");

        assertConfiguration(properties,
                TEST_ENDPOINT,
                DEFAULT_CONNECT_TIMEOUT,
                DEFAULT_READ_TIMEOUT,
                100,
                DEFAULT_HTTPS_PROTOCOLS);
    }

    @Test
    public void testConstructFromPropertiesWithHostAndScheme() {

        Properties properties = new Properties();
        properties.setProperty("unzer.paylater.api.endpoint.host", TEST_ENDPOINT_HOST);
        properties.setProperty("unzer.paylater.api.endpoint.scheme", "http");

        assertConfiguration(properties,
                "http://" + TEST_ENDPOINT_HOST,
                DEFAULT_CONNECT_TIMEOUT,
                DEFAULT_READ_TIMEOUT,
                DEFAULT_MAX_CONNECTIONS,
                DEFAULT_HTTPS_PROTOCOLS);
    }

    @Test
    public void testConstructFromPropertiesWithHostAndPort() {

        Properties properties = new Properties();
        properties.setProperty("unzer.paylater.api.endpoint.host", TEST_ENDPOINT_HOST);
        properties.setProperty("unzer.paylater.api.endpoint.port", "8443");

        assertConfiguration(properties,
                TEST_ENDPOINT + ":8443",
                DEFAULT_CONNECT_TIMEOUT,
                DEFAULT_READ_TIMEOUT,
                DEFAULT_MAX_CONNECTIONS,
                DEFAULT_HTTPS_PROTOCOLS);
    }

    @Test
    public void testConstructFromPropertiesWithHostSchemeAndPort() {

        Properties properties = new Properties();
        properties.setProperty("unzer.paylater.api.endpoint.host", TEST_ENDPOINT_HOST);
        properties.setProperty("unzer.paylater.api.endpoint.scheme", "http");
        properties.setProperty("unzer.paylater.api.endpoint.port", "8080");

        assertConfiguration(properties,
                "http://" + TEST_ENDPOINT_HOST + ":8080",
                DEFAULT_CONNECT_TIMEOUT,
                DEFAULT_READ_TIMEOUT,
                DEFAULT_MAX_CONNECTIONS,
                DEFAULT_HTTPS_PROTOCOLS);
    }

    @Test
    public void testConstructFromPropertiesWithIPv6Host() {

        Properties properties = new Properties();
        properties.setProperty("unzer.paylater.api.endpoint.host", "::1");

        assertConfiguration(properties,
                "https://[::1]",
                DEFAULT_CONNECT_TIMEOUT,
                DEFAULT_READ_TIMEOUT,
                DEFAULT_MAX_CONNECTIONS,
                DEFAULT_HTTPS_PROTOCOLS);
    }

    @Test
    public void testConstructFromPropertiesWithHttpsProtocols() {

        Properties properties = new Properties();
        properties.setProperty("unzer.paylater.api.endpoint.host", TEST_ENDPOINT_HOST);
        properties.setProperty("unzer.paylater.api.https.protocols", "TLSv1, TLSv1.1, TLSv1.2");

        assertConfiguration(properties,
                TEST_ENDPOINT,
                DEFAULT_CONNECT_TIMEOUT,
                DEFAULT_READ_TIMEOUT,
                DEFAULT_MAX_CONNECTIONS,
                new HashSet<>(Arrays.asList("TLSv1", "TLSv1.1", "TLSv1.2")));
    }

    private void assertConfiguration(Properties properties, String apiEndpoint, int connectTimeout, int readTimeout, int saxConnections, Set<String> httpsProtocols) {
        CommunicatorConfiguration configuration = new CommunicatorConfiguration(properties);
        Assert.assertEquals(URI.create(apiEndpoint), configuration.getApiEndpoint());
        Assert.assertEquals(connectTimeout, configuration.getConnectTimeout());
        Assert.assertEquals(readTimeout, configuration.getReadTimeout());
        Assert.assertEquals(saxConnections, configuration.getMaxConnections());
        Assert.assertEquals(httpsProtocols, configuration.getHttpsProtocols());
    }
}
