package com.bank.caseapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Maps to the "content" object of the external Pega case-creation payload.
 * Field names are PascalCase on the wire, so they are pinned explicitly
 * via @JsonProperty rather than relying on a naming strategy.
 */
public class CaseContent {

    @JsonProperty("HitsTagList")
    private String hitsTagList;

    @JsonProperty("DataSource")
    private String dataSource;

    @JsonProperty("AlertType")
    private String alertType;

    @JsonProperty("TC")
    private String tc;

    @JsonProperty("ApplicationId")
    private String applicationId;

    public CaseContent() {
    }

    public String getHitsTagList() {
        return hitsTagList;
    }

    public void setHitsTagList(String hitsTagList) {
        this.hitsTagList = hitsTagList;
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
}
