package com.unzer.paylater;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Assert;
import org.junit.Test;

import com.unzer.paylater.communication.Communicator;
import com.unzer.paylater.communication.CommunicatorConfiguration;
import com.unzer.paylater.communication.Connection;
import com.unzer.paylater.communication.standard.JsonMarshaller;
import com.unzer.paylater.communication.standard.UnzerCommunicator;
import com.unzer.paylater.communication.standard.UnzerConnection;
import com.unzer.paylater.communication.standard.UnzerConnectionTest;

public class FactoryTest {

    public static final URI PROPERTIES_URI;

    static {
        try {
            PROPERTIES_URI = FactoryTest.class.getResource("configuration.properties").toURI();
        } catch (URISyntaxException e) {
            InstantiationError error = new InstantiationError(e.getMessage());
            error.initCause(e);
            throw error;
        }
    }

    @Test
    public void testCreateConfiguration() {
        CommunicatorConfiguration configuration = Factory.createConfiguration(PROPERTIES_URI);
        Assert.assertEquals(URI.create("https://test.com"), configuration.getApiEndpoint());
        Assert.assertEquals(-1, configuration.getConnectTimeout());
        Assert.assertEquals(-1, configuration.getReadTimeout());
        Assert.assertEquals(100, configuration.getMaxConnections());
    }

    @Test
    public void testCreateCommunicator() {
        Communicator communicator = Factory.createCommunicator(PROPERTIES_URI);

        Assert.assertSame(UnzerCommunicator.class, communicator.getClass());
        Assert.assertSame(JsonMarshaller.INSTANCE, communicator.getMarshaller());

        Connection connection = communicator.getConnection();
        Assert.assertTrue(connection instanceof UnzerConnection);
        UnzerConnectionTest.assertConnection((UnzerConnection) connection, -1, -1, 100);
    }
}
