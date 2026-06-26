package com.bank.caseapi.dto;

/**
 * Outbound request body for POST http://some-endpoint/prweb/v1/cases
 */
public class CaseRequest {

    private String caseType;
    private String processId;
    private String parentCaseID;
    private CaseContent content;

    public CaseRequest() {
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

    public String getParentCaseID() {
        return parentCaseID;
    }

    public void setParentCaseID(String parentCaseID) {
        this.parentCaseID = parentCaseID;
    }

    public CaseContent getContent() {
        return content;
    }

    public void setContent(CaseContent content) {
        this.content = content;
    }
}
