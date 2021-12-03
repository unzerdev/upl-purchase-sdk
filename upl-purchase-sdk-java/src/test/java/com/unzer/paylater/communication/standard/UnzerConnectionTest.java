package com.unzer.paylater.communication.standard;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.http.HttpHost;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.conn.DefaultHttpClientConnectionOperator;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.junit.Assert;
import org.junit.Test;

import com.unzer.paylater.communication.CommunicatorConfiguration;
import com.unzer.paylater.util.ReflectionUtil;

public class UnzerConnectionTest {

    private static final int CONNECT_TIMEOUT = 10000;
    private static final int READ_TIMEOUT = 20000;
    private static final int MAX_CONNECTIONS = 100;

    public static void assertConnection(UnzerConnection connection, int connectTimeout, int readTimeout, int maxConnections) {
        assertRequestConfig(connection, connectTimeout, readTimeout);
        assertMaxConnections(connection, maxConnections);
    }

    private static void assertRequestConfig(UnzerConnection connection, int connectTimeout, int readTimeout) {

        RequestConfig requestConfig = connection.requestConfig;
        Assert.assertEquals(connectTimeout, requestConfig.getConnectTimeout());
        Assert.assertEquals(readTimeout, requestConfig.getSocketTimeout());
    }

    @SuppressWarnings("resource")
    private static void assertMaxConnections(UnzerConnection connection, int maxConnections) {
        CloseableHttpClient httpClient = ReflectionUtil.getField(connection, "httpClient", CloseableHttpClient.class);
        PoolingHttpClientConnectionManager connectionManager = ReflectionUtil.getField(httpClient, "connManager", PoolingHttpClientConnectionManager.class);
        Assert.assertEquals(maxConnections, connectionManager.getDefaultMaxPerRoute());
        Assert.assertTrue(maxConnections <= connectionManager.getMaxTotal());

        HttpHost target = new HttpHost("test.com", -1, "https");
        HttpRoute route = new HttpRoute(target);
        Assert.assertEquals(maxConnections, connectionManager.getMaxPerRoute(route));
    }

    @SuppressWarnings("resource")
    private static void assertHttpsProtocols(UnzerConnection connection, Set<String> httpsProtocols) {
        CloseableHttpClient httpClient = ReflectionUtil.getField(connection, "httpClient", CloseableHttpClient.class);
        PoolingHttpClientConnectionManager connectionManager = ReflectionUtil.getField(httpClient, "connManager", PoolingHttpClientConnectionManager.class);
        DefaultHttpClientConnectionOperator connectionOperator = ReflectionUtil.getField(connectionManager, "connectionOperator", DefaultHttpClientConnectionOperator.class);
        Registry<?> socketFactoryRegistry = ReflectionUtil.getField(connectionOperator, "socketFactoryRegistry", Registry.class);
        SSLConnectionSocketFactory sslConnectionSocketFactory = (SSLConnectionSocketFactory) socketFactoryRegistry.lookup("https");
        String[] supportedProtocols = ReflectionUtil.getField(sslConnectionSocketFactory, "supportedProtocols", String[].class);

        Assert.assertNotNull(supportedProtocols);
        Assert.assertEquals(httpsProtocols, new HashSet<>(Arrays.asList(supportedProtocols)));
    }

    @Test
    @SuppressWarnings("resource")
    public void testConstruct() {

        UnzerConnection connection = new UnzerConnection(CONNECT_TIMEOUT, READ_TIMEOUT);
        assertRequestConfig(connection, CONNECT_TIMEOUT, READ_TIMEOUT);
        assertMaxConnections(connection, CommunicatorConfiguration.DEFAULT_MAX_CONNECTIONS);
        assertHttpsProtocols(connection, CommunicatorConfiguration.DEFAULT_HTTPS_PROTOCOLS);
    }

    @Test
    @SuppressWarnings("resource")
    public void testConstructWithMaxConnections() {
        UnzerConnection connection = new UnzerConnection(CONNECT_TIMEOUT, READ_TIMEOUT, MAX_CONNECTIONS);
        assertRequestConfig(connection, CONNECT_TIMEOUT, READ_TIMEOUT);
        assertMaxConnections(connection, MAX_CONNECTIONS);
        assertHttpsProtocols(connection, CommunicatorConfiguration.DEFAULT_HTTPS_PROTOCOLS);
    }

    @Test
    @SuppressWarnings("resource")
    public void testConstructWithHttpsProtocols() {
        Set<String> httpsProtocols = new HashSet<>(Arrays.asList("TLSv1.2", "TLSv1.3"));

        UnzerConnection connection = new UnzerConnection(CONNECT_TIMEOUT, READ_TIMEOUT, MAX_CONNECTIONS, httpsProtocols);
        assertRequestConfig(connection, CONNECT_TIMEOUT, READ_TIMEOUT);
        assertMaxConnections(connection, MAX_CONNECTIONS);
        assertHttpsProtocols(connection, httpsProtocols);
    }

    @Test
    @SuppressWarnings("resource")
    public void testConstructWithZeroHttpsProtocols() {
        Set<String> httpsProtocols = Collections.emptySet();

        UnzerConnection connection = new UnzerConnection(CONNECT_TIMEOUT, READ_TIMEOUT, MAX_CONNECTIONS, httpsProtocols);
        assertRequestConfig(connection, CONNECT_TIMEOUT, READ_TIMEOUT);
        assertMaxConnections(connection, MAX_CONNECTIONS);
        assertHttpsProtocols(connection, CommunicatorConfiguration.DEFAULT_HTTPS_PROTOCOLS);
    }

    @Test
    @SuppressWarnings("resource")
    public void testConstructWithNullHttpsProtocols() {
        Set<String> httpsProtocols = null;

        UnzerConnection connection = new UnzerConnection(CONNECT_TIMEOUT, READ_TIMEOUT, MAX_CONNECTIONS, httpsProtocols);
        assertRequestConfig(connection, CONNECT_TIMEOUT, READ_TIMEOUT);
        assertMaxConnections(connection, MAX_CONNECTIONS);
        assertHttpsProtocols(connection, CommunicatorConfiguration.DEFAULT_HTTPS_PROTOCOLS);
    }
}
