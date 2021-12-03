package com.unzer.paylater.logging;

/**
 * A utility class to support logging.
 */
public final class LoggingUtil {

    private static final HeaderObfuscator HEADER_OBFUSCATOR = HeaderObfuscator
            .builder()
            .withFixedLength("access_token", 8)
            .withFixedLength("Authorization", 8)
            .withFixedLength("unzer-pl-secret-key", 8)
            .build();

    private LoggingUtil() { }

    /**
     * Obfuscates the value for the given header as necessary.
     */
    public static String obfuscateHeader(String name, String value) {
        return HEADER_OBFUSCATOR.obfuscateValue(name, value);
    }
}
