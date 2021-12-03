package com.unzer.paylater.communication.standard;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.unzer.paylater.communication.Marshaller;
import com.unzer.paylater.exception.MarshallerSyntaxException;

/**
 * {@link Marshaller} implementation based on {@link Gson}.
 */
public class JsonMarshaller implements Marshaller {

    public static final JsonMarshaller INSTANCE = new JsonMarshaller();
    // Gson instances are thread-safe, so reuse one single instance
    private static final Gson GSON;
    private static final Charset CHARSET = StandardCharsets.UTF_8;

    static {
        // Gson by default does not serialize certain classes the way it should for the Unzer Pay Later API.
        GSON = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class,
                        (JsonDeserializer<LocalDate>) (json, type, jsonDeserializationContext) -> LocalDate.parse(json.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(LocalDate.class,
                        (JsonSerializer<LocalDate>) (source, type, jsonDeserializationContext) -> new JsonPrimitive(source.format(DateTimeFormatter.ISO_DATE)))
                .registerTypeAdapter(OffsetDateTime.class,
                        (JsonDeserializer<OffsetDateTime>) (json, type, jsonDeserializationContext) -> OffsetDateTime.parse(json.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(OffsetDateTime.class,
                        (JsonSerializer<OffsetDateTime>) (source, type, jsonDeserializationContext) -> new JsonPrimitive(source.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME)))
                .create();
    }

    protected JsonMarshaller() { }

    @Override
    public String marshal(Object requestObject) {
        return GSON.toJson(requestObject);
    }

    @Override
    public <T> T unmarshal(InputStream jsonStream, Class<T> type) {
        try {
            return GSON.fromJson(new InputStreamReader(jsonStream, CHARSET), type);
        } catch (JsonSyntaxException e) {
            throw new MarshallerSyntaxException(e);
        }
    }

    @Override
    public <T> T unmarshal(String json, Class<T> type) {
        try {
            return GSON.fromJson(json, type);
        } catch (JsonSyntaxException e) {
            throw new MarshallerSyntaxException(e);
        }
    }
}
