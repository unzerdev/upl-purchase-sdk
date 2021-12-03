package com.unzer.paylater.communication;

import java.net.URI;

import com.unzer.paylater.communication.standard.UnzerCommunicator;

/**
 * Builder for a {@link Communicator} object.
 */
public class CommunicatorBuilder {

    private URI apiEndpoint;
    private Connection connection;
    private Marshaller marshaller;

    /**
     * Sets the Unzer Pay Later platform API endpoint URI to use.
     */
    public CommunicatorBuilder withAPIEndpoint(URI apiEndpoint) {
        this.apiEndpoint = apiEndpoint;
        return this;
    }

    /**
     * Sets the {@link Connection} to use.
     */
    public CommunicatorBuilder withConnection(Connection connection) {
        this.connection = connection;
        return this;
    }

    /**
     * Sets the {@link Marshaller} to use.
     */
    public CommunicatorBuilder withMarshaller(Marshaller marshaller) {
        this.marshaller = marshaller;
        return this;
    }

    /**
     * Creates a fully initialized {@link Communicator} object.
     *
     * @throws IllegalArgumentException if not all required components are set
     */
    public Communicator build() {
        return new UnzerCommunicator(
                apiEndpoint,
                connection,
                marshaller
        );
    }
}
