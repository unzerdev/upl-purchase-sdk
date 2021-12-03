package com.unzer.paylater.logging;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

abstract class Obfuscator {

    private final Map<String, ValueObfuscator> obfuscators;

    Obfuscator(Map<String, ValueObfuscator> obfuscators, boolean caseInsensitive) {
        this.obfuscators = copy(obfuscators, caseInsensitive);
    }

    private Map<String, ValueObfuscator> copy(Map<String, ValueObfuscator> obfuscators, boolean caseInsensitive) {
        Map<String, ValueObfuscator> copy = caseInsensitive
                ? new TreeMap<>(String.CASE_INSENSITIVE_ORDER)
                : new LinkedHashMap<>();
        copy.putAll(obfuscators);
        return Collections.unmodifiableMap(copy);
    }

    String obfuscateValue(String key, String value) {
        ValueObfuscator obfuscator = obfuscators.get(key);
        return obfuscator != null ? obfuscator.obfuscateValue(value) : value;
    }

    abstract static class Builder {
        private final Map<String, ValueObfuscator> obfuscators = new LinkedHashMap<>();

        Builder() {}

        Builder withAll(String key) {
            obfuscators.put(key, ValueObfuscator.ALL);
            return this;
        }

        Builder withFixedLength(String key, int fixedLength) {
            obfuscators.put(key, ValueObfuscator.fixedLength(fixedLength));
            return this;
        }

        Builder withKeepStartCount(String key, int count) {
            obfuscators.put(key, ValueObfuscator.keepStartCount(count));
            return this;
        }

        Builder withKeepEndCount(String key, int count) {
            obfuscators.put(key, ValueObfuscator.keepEndCount(count));
            return this;
        }

        Map<String, ValueObfuscator> getObfuscators() {
            return obfuscators;
        }

        abstract Obfuscator build();
    }
}
