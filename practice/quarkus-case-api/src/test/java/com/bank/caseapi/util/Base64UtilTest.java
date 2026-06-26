package com.bank.caseapi.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

class Base64UtilTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldEncodeJsonNodeToBase64AndRoundTrip() throws Exception {
        JsonNode node = objectMapper.readTree("{\"key\":\"value\",\"nested\":{\"a\":1}}");

        String encoded = Base64Util.encodeJsonToBase64(node, objectMapper);
        String decodedJson = Base64Util.decodeBase64ToString(encoded);
        JsonNode decodedNode = objectMapper.readTree(decodedJson);

        assertThat(decodedNode).isEqualTo(node);
    }

    @Test
    void shouldRejectNullNode() {
        assertThatThrownBy(() -> Base64Util.encodeJsonToBase64(null, objectMapper))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("must not be null");
    }
}
