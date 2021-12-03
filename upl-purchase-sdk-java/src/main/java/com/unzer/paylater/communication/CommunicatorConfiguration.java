package com.unzer.paylater.communication;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.http.util.Asserts;

/**
 * Configuration for the communicator.
 */
public class CommunicatorConfiguration {

    public static final int DEFAULT_CONNECT_TIMEOUT = 10000;
    public static final int DEFAULT_READ_TIMEOUT = 10000;
    public static final int DEFAULT_MAX_CONNECTIONS = 10;
    public static final Set<String> DEFAULT_HTTPS_PROTOCOLS = Collections.unmodifiableSet(new LinkedHashSet<>(Collections.singletonList("TLSv1.2")));

    private static final String UNZER_PAYLATER_API_ENDPOINT_HOST = "unzer.paylater.api.endpoint.host";
    private static final Pattern COMMA_SEPARATOR_PATTERN = Pattern.compile("\\s*,\\s*");

    private URI apiEndpoint;
    private int connectTimeout = DEFAULT_CONNECT_TIMEOUT;
    private int readTimeout = DEFAULT_READ_TIMEOUT;
    private int maxConnections = DEFAULT_MAX_CONNECTIONS;
    private Set<String> httpsProtocols = new LinkedHashSet<>(DEFAULT_HTTPS_PROTOCOLS);

    public CommunicatorConfiguration() {}

    /**
     *
     * @param properties a {@link Properties} object containing the following properties (optional unless specified otherwise):<br/>
     * <strong>- unzer.paylater.api.endpoint.host - required</strong><br/>
     * - unzer.paylater.api.connectTimeout<br/>
     * - unzer.paylater.api.endpoint.scheme<br/>
     * - unzer.paylater.api.endpoint.port<br/>
     * - unzer.paylater.api.https.protocols<br/>
     * - unzer.paylater.api.maxConnections<br/>
     * - unzer.paylater.api.readTimeout<br/>
     */
    public CommunicatorConfiguration(Properties properties) {
        if (properties != null) {
            apiEndpoint = getApiEndpoint(properties);
            connectTimeout = getProperty(properties, "unzer.paylater.api.connectTimeout", DEFAULT_CONNECT_TIMEOUT);
            readTimeout = getProperty(properties, "unzer.paylater.api.readTimeout", DEFAULT_READ_TIMEOUT);
            maxConnections = getProperty(properties, "unzer.paylater.api.maxConnections", DEFAULT_MAX_CONNECTIONS);

            String httpsProtocolString = properties.getProperty("unzer.paylater.api.https.protocols");
            if (httpsProtocolString != null) {
                httpsProtocols.clear();
                httpsProtocols.addAll(Arrays.asList(COMMA_SEPARATOR_PATTERN.split(httpsProtocolString.trim())));
            }
        }
    }

    private int getProperty(Properties properties, String key, int defaultValue) {
        String propertyValue = properties.getProperty(key);
        return propertyValue != null && !propertyValue.trim().isEmpty()
                ? Integer.parseInt(propertyValue)
                : defaultValue;
    }

    private URI getApiEndpoint(Properties properties) {
        String scheme = properties.getProperty("unzer.paylater.api.endpoint.scheme", "https");
        String host = properties.getProperty(UNZER_PAYLATER_API_ENDPOINT_HOST);
        String port = properties.getProperty("unzer.paylater.api.endpoint.port", "-1");

        if (host == null) {
            throw new IllegalStateException("Could not find properties '" + UNZER_PAYLATER_API_ENDPOINT_HOST + "' in the given properties.");
        }
        try {
            return new URI(scheme, null, host, Integer.parseInt(port), null, null, null);
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Unable to construct API endpoint URI", e);
        }
    }

    /**
     * Returns the Unzer Pay Later platform API endpoint URI.
     */
    public URI getApiEndpoint() {
        return apiEndpoint;
    }

    public void setApiEndpoint(URI apiEndpoint) {
        if (apiEndpoint != null) {
            if (apiEndpoint.getPath() != null && !apiEndpoint.getPath().isEmpty()) {
                throw new IllegalArgumentException("apiEndpoint should not contain a path");
            }
            if (apiEndpoint.getUserInfo() != null || apiEndpoint.getQuery() != null || apiEndpoint.getFragment() != null) {
                throw new IllegalArgumentException("apiEndpoint should not contain user info, query or fragment");
            }
        }
        this.apiEndpoint = apiEndpoint;
    }

    public CommunicatorConfiguration withApiEndpoint(URI apiEndpoint) {
        setApiEndpoint(apiEndpoint);
        return this;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public CommunicatorConfiguration withConnectTimeout(int connectTimeout) {
        setConnectTimeout(connectTimeout);
        return this;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public CommunicatorConfiguration withReadTimeout(int readTimeout) {
        setReadTimeout(readTimeout);
        return this;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public CommunicatorConfiguration withMaxConnections(int maxConnections) {
        setMaxConnections(maxConnections);
        return this;
    }

    public Set<String> getHttpsProtocols() {
        if (httpsProtocols == null) {
            httpsProtocols = new LinkedHashSet<>();
        }
        return httpsProtocols;
    }

    public void setHttpsProtocols(Set<String> httpsProtocols) {
        Asserts.notNull(httpsProtocols, "httpsProtocols");
        this.httpsProtocols = httpsProtocols;
    }

    public CommunicatorConfiguration withHttpsProtocols(Collection<String> httpsProtocols) {
        getHttpsProtocols().clear();
        getHttpsProtocols().addAll(httpsProtocols);
        return this;
    }

    public CommunicatorConfiguration withHttpsProtocols(String... httpsProtocols) {
        return withHttpsProtocols(Arrays.asList(httpsProtocols));
    }
}
