package com.bank.caseapi.util;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Encodes the (potentially large) HitsTagList JSON payload into the Base64
 * string format expected by the upstream Pega case API.
 */
public final class Base64Util {

    private Base64Util() {
        // utility class
    }

    public static String encodeJsonToBase64(JsonNode node, ObjectMapper mapper) {
        if (node == null) {
            throw new IllegalArgumentException("hitsTagList JSON must not be null");
        }
        try {
            byte[] jsonBytes = mapper.writeValueAsBytes(node);
            return Base64.getEncoder().encodeToString(jsonBytes);
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to serialize/encode hitsTagList JSON", e);
        }
    }

    public static String decodeBase64ToString(String base64) {
        byte[] decoded = Base64.getDecoder().decode(base64);
        return new String(decoded, StandardCharsets.UTF_8);
    }
}
