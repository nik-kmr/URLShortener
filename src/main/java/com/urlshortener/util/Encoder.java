package com.urlshortener.util;

import com.urlshortener.constants.Constants;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class Encoder {
    private final Map<Integer, Character> BINARY_TO_CHAR_MAP = buildBinaryToCharMap();
    public String toEncodedString(final Long number) {
        String binary = Long.toBinaryString(number);

        final int padding = (Constants.BINARY_ENCODED_LENGTH - binary.length() % Constants.BINARY_ENCODED_LENGTH) % Constants.BINARY_ENCODED_LENGTH;
        binary = "0".repeat(padding) + binary;

        StringBuilder encoded = new StringBuilder();
        for (int i = 0; i < binary.length(); i += Constants.BINARY_ENCODED_LENGTH) {
            final String chunk = binary.substring(i, i + Constants.BINARY_ENCODED_LENGTH);
            int value = Integer.parseInt(chunk, 2);
            final Character mappedChar = BINARY_TO_CHAR_MAP.get(value);
            if (mappedChar != null) {
                encoded.append(mappedChar);
            } else {
                throw new IllegalArgumentException("No mapping for binary chunk: " + chunk);
            }
        }

        return encoded.toString();
    }

    private Map<Integer, Character> buildBinaryToCharMap() {
        final Map<Integer, Character> map = new HashMap<>();
        int index = 0;
        // a to z → 0 to 25
        for (char c = 'a'; c <= 'z'; c++, index++) {
            map.put(index, c);
        }
        // 0 to 9 → 26 to 35
        for (char c = '0'; c <= '9'; c++, index++) {
            map.put(index, c);
        }
        // A to Z → 36 to 61
        for (char c = 'A'; c <= 'Z'; c++, index++) {
            map.put(index, c);
        }
        // $ → 62
        map.put(index++, '$');
        // # → 63
        map.put(index, '#');
        return map;
    }
}
