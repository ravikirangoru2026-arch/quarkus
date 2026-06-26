package com.bank.caseapi.dto;

import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Inbound contract for our own exposed endpoint (POST /api/v1/cases).
 * <p>
 * Callers send the "hitsTagList" payload as a plain JSON object/array; this
 * service is responsible for Base64-encoding it before forwarding to the
 * upstream Pega case API, so callers never need to know about that
 * implementation detail.
 */
public class CreateCaseRequestDto {

    @NotBlank(message = "caseType is required")
    private String caseType;

    @NotBlank(message = "processId is required")
    private String processId;

    /** Optional - defaults to "" when creating a brand-new (non-child) case. */
    private String parentCaseId;

    @NotBlank(message = "dataSource is required")
    private String dataSource;

    @NotBlank(message = "alertType is required")
    private String alertType;

    @NotBlank(message = "tc is required")
    private String tc;

    @NotBlank(message = "applicationId is required")
    private String applicationId;

    @NotNull(message = "hitsTagList is required")
    private JsonNode hitsTagList;

    public CreateCaseRequestDto() {
    }

    public String getCaseType() {
        return caseType;
    }

    public void setCaseType(String caseType) {
        this.caseType = caseType;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getParentCaseId() {
        return parentCaseId;
    }

    public void setParentCaseId(String parentCaseId) {
        this.parentCaseId = parentCaseId;
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getTc() {
        return tc;
    }

    public void setTc(String tc) {
        this.tc = tc;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public JsonNode getHitsTagList() {
        return hitsTagList;
    }

    public void setHitsTagList(JsonNode hitsTagList) {
        this.hitsTagList = hitsTagList;
    }
}
