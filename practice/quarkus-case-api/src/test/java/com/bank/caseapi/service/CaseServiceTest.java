package com.bank.caseapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import com.bank.caseapi.client.PegaCaseClient;
import com.bank.caseapi.dto.CaseRequest;
import com.bank.caseapi.dto.CaseResponse;
import com.bank.caseapi.dto.CreateCaseRequestDto;
import com.bank.caseapi.exception.ExternalApiException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Pure unit test - no Quarkus container, no network calls. The REST client
 * is mocked directly, so this runs in milliseconds and is the right place
 * to pin down the request-building / Base64-encoding / error-mapping logic.
 */
class CaseServiceTest {

    private static final String CLIENT_ID = "client-id-1234567890";
    private static final String CLIENT_SECRET = "some-secret67JVMBM7KBVVV9DFD77V6FVFDVFDMB3234";

    private PegaCaseClient pegaCaseClient;
    private ObjectMapper objectMapper;
    private CaseService caseService;

    @BeforeEach
    void setUp() {
        pegaCaseClient = mock(PegaCaseClient.class);
        objectMapper = new ObjectMapper();

        caseService = new CaseService();
        caseService.pegaCaseClient = pegaCaseClient;
        caseService.objectMapper = objectMapper;
        caseService.clientId = CLIENT_ID;
        caseService.clientSecret = CLIENT_SECRET;
    }

    private CreateCaseRequestDto buildDto() throws Exception {
        JsonNode hitsTagList = objectMapper.readTree("{\"alerts\":[{\"id\":1,\"name\":\"Suspicious activity\"}]}");

        CreateCaseRequestDto dto = new CreateCaseRequestDto();
        dto.setCaseType("Some-Case-Type");
        dto.setProcessId("SomeProcessID");
        dto.setParentCaseId("");
        dto.setDataSource("SomeDataSource");
        dto.setAlertType("AlertT");
        dto.setTc("5");
        dto.setApplicationId("Some-id-12345");
        dto.setHitsTagList(hitsTagList);
        return dto;
    }

    @Test
    void shouldBuildExternalRequestWithBase64EncodedHitsTagList() throws Exception {
        CreateCaseRequestDto dto = buildDto();

        CaseRequest request = caseService.buildExternalRequest(dto);

        String expectedEncoded = Base64.getEncoder()
                .encodeToString(objectMapper.writeValueAsBytes(dto.getHitsTagList()));

        assertThat(request.getCaseType()).isEqualTo("Some-Case-Type");
        assertThat(request.getProcessId()).isEqualTo("SomeProcessID");
        assertThat(request.getParentCaseID()).isEqualTo("");
        assertThat(request.getContent().getHitsTagList()).isEqualTo(expectedEncoded);
        assertThat(request.getContent().getDataSource()).isEqualTo("SomeDataSource");
        assertThat(request.getContent().getAlertType()).isEqualTo("AlertT");
        assertThat(request.getContent().getTc()).isEqualTo("5");
        assertThat(request.getContent().getApplicationId()).isEqualTo("Some-id-12345");
    }

    @Test
    void encodedHitsTagListShouldDecodeBackToOriginalJson() throws Exception {
        CreateCaseRequestDto dto = buildDto();
        CaseRequest request = caseService.buildExternalRequest(dto);

        byte[] decoded = Base64.getDecoder().decode(request.getContent().getHitsTagList());
        JsonNode roundTripped = objectMapper.readTree(new String(decoded, StandardCharsets.UTF_8));

        assertThat(roundTripped).isEqualTo(dto.getHitsTagList());
    }

    @Test
    void shouldDefaultParentCaseIdToEmptyStringWhenNull() throws Exception {
        CreateCaseRequestDto dto = buildDto();
        dto.setParentCaseId(null);

        CaseRequest request = caseService.buildExternalRequest(dto);

        assertThat(request.getParentCaseID()).isEqualTo("");
    }

    @Test
    void shouldReturnCaseResponseOnSuccess() throws Exception {
        CreateCaseRequestDto dto = buildDto();
        CaseResponse mockResponse = new CaseResponse();
        mockResponse.setId("CASE-1001");
        mockResponse.setStatus("CREATED");

        when(pegaCaseClient.createCase(eq(CLIENT_ID), eq(CLIENT_SECRET), any(CaseRequest.class)))
                .thenReturn(Uni.createFrom().item(mockResponse));

        CaseResponse result = caseService.submitCase(dto).await().indefinitely();

        assertThat(result.getId()).isEqualTo("CASE-1001");
        assertThat(result.getStatus()).isEqualTo("CREATED");
    }

    @Test
    void shouldWrapUpstreamFailureAsExternalApiException() throws Exception {
        CreateCaseRequestDto dto = buildDto();

        WebApplicationException upstreamFailure =
                new WebApplicationException("Server error", Response.Status.INTERNAL_SERVER_ERROR);

        when(pegaCaseClient.createCase(any(), any(), any()))
                .thenReturn(Uni.createFrom().failure(upstreamFailure));

        assertThatThrownBy(() -> caseService.submitCase(dto).await().indefinitely())
                .isInstanceOf(ExternalApiException.class)
                .hasMessageContaining("500");
    }

    @Test
    void shouldWrapGenericFailureAsExternalApiException() throws Exception {
        CreateCaseRequestDto dto = buildDto();

        when(pegaCaseClient.createCase(any(), any(), any()))
                .thenReturn(Uni.createFrom().failure(new RuntimeException("connection reset")));

        assertThatThrownBy(() -> caseService.submitCase(dto).await().indefinitely())
                .isInstanceOf(ExternalApiException.class)
                .hasMessageContaining("connection reset");
    }
}
