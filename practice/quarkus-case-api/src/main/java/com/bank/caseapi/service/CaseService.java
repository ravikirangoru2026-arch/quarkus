package com.bank.caseapi.service;

import com.bank.caseapi.client.PegaCaseClient;
import com.bank.caseapi.dto.CaseContent;
import com.bank.caseapi.dto.CaseRequest;
import com.bank.caseapi.dto.CaseResponse;
import com.bank.caseapi.dto.CreateCaseRequestDto;
import com.bank.caseapi.exception.ExternalApiException;
import com.bank.caseapi.util.Base64Util;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

/**
 * Orchestrates outbound case creation against the external Pega case API.
 * <p>
 * Package-private field visibility (no {@code private}) is intentional: it
 * lets {@code CaseServiceTest} inject mocks directly without firing up the
 * whole Quarkus container, keeping that test fast and CDI-free.
 */
@ApplicationScoped
public class CaseService {

    private static final Logger LOG = Logger.getLogger(CaseService.class);

    @Inject
    @RestClient
    PegaCaseClient pegaCaseClient;

    @Inject
    ObjectMapper objectMapper;

    @ConfigProperty(name = "pega.client.id")
    String clientId;

    @ConfigProperty(name = "pega.client.secret")
    String clientSecret;

    public Uni<CaseResponse> submitCase(CreateCaseRequestDto dto) {
        CaseRequest request = buildExternalRequest(dto);

        return pegaCaseClient.createCase(clientId, clientSecret, request)
                .onFailure().transform(this::mapToExternalApiException)
                .onFailure().invoke(t -> LOG.errorf(t, "Case creation failed for processId=%s, applicationId=%s",
                        dto.getProcessId(), dto.getApplicationId()))
                .onItem().invoke(resp -> LOG.infof("Case created: id=%s status=%s", resp.getId(), resp.getStatus()));
    }

    /** Package-private so the unit test can exercise mapping logic directly. */
    CaseRequest buildExternalRequest(CreateCaseRequestDto dto) {
        String encodedHitsTagList = Base64Util.encodeJsonToBase64(dto.getHitsTagList(), objectMapper);

        CaseContent content = new CaseContent();
        content.setHitsTagList(encodedHitsTagList);
        content.setDataSource(dto.getDataSource());
        content.setAlertType(dto.getAlertType());
        content.setTc(dto.getTc());
        content.setApplicationId(dto.getApplicationId());

        CaseRequest request = new CaseRequest();
        request.setCaseType(dto.getCaseType());
        request.setProcessId(dto.getProcessId());
        request.setParentCaseID(dto.getParentCaseId() == null ? "" : dto.getParentCaseId());
        request.setContent(content);
        return request;
    }

    private Throwable mapToExternalApiException(Throwable t) {
        if (t instanceof ExternalApiException) {
            return t;
        }
        if (t instanceof WebApplicationException wae) {
            int status = wae.getResponse() != null ? wae.getResponse().getStatus() : 502;
            return new ExternalApiException("Pega case API call failed with status " + status, status, wae);
        }
        return new ExternalApiException("Pega case API call failed: " + t.getMessage(), 502, t);
    }
}
