package com.unzer.paylater.logging;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LoggingUtilTest {

    @Test
    public void testObfuscateHeader() {
        testObfuscateHeaderWithMatch("Authorization", "Basic 2Ku0lQgzbklEOvYxwKn9YW1l", "********");
        testObfuscateHeaderWithMatch("authorization", "Basic 2Ku0lQgzbklEOvYxwKn9YW1l", "********");
        testObfuscateHeaderWithMatch("AUTHORIZATION", "Basic 2Ku0lQgzbklEOvYxwKn9YW1l", "********");

        testObfuscateHeaderWithMatch("unzer-pl-secret-key", "foobar", "********");
        testObfuscateHeaderWithMatch("unzer-pl-secret-key", "foobar", "********");
        testObfuscateHeaderWithMatch("unzer-pl-secret-key", "foobar", "********");

        testObfuscateHeaderWithNoMatch("Content-Type", "application/json");
        testObfuscateHeaderWithNoMatch("content-type", "application/json");
        testObfuscateHeaderWithNoMatch("CONTENT-TYPE", "application/json");
    }

    private void testObfuscateHeaderWithMatch(String name, String originalValue, String expectedObfuscatedValue) {
        String obfuscatedValue = LoggingUtil.obfuscateHeader(name, originalValue);

        assertEquals(expectedObfuscatedValue, obfuscatedValue);
    }

    private void testObfuscateHeaderWithNoMatch(String name, String originalValue) {
        String obfuscatedValue = LoggingUtil.obfuscateHeader(name, originalValue);

        assertEquals(originalValue, obfuscatedValue);
    }
}
