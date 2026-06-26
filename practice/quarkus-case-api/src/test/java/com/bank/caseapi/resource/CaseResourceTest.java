package com.bank.caseapi.resource;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.matchingJsonPath;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

@QuarkusTest
@QuarkusTestResource(WireMockPegaResource.class)
class CaseResourceTest {

    private static final String VALID_REQUEST_BODY = """
            {
              "caseType": "Some-Case-Type",
              "processId": "SomeProcessID",
              "parentCaseId": "",
              "dataSource": "SomeDataSource",
              "alertType": "AlertT",
              "tc": "5",
              "applicationId": "Some-id-12345",
              "hitsTagList": { "alerts": [ { "id": 1, "name": "Suspicious activity" } ] }
            }
            """;

    @AfterEach
    void resetWireMock() {
        WireMockPegaResource.getWireMockServer().resetAll();
    }

    @Test
    void shouldCreateCaseSuccessfully() {
        WireMockPegaResource.getWireMockServer().stubFor(
                post(urlEqualTo("/prweb/v1/cases"))
                        .withHeader("client_id", equalTo("client-id-1234567890"))
                        .withHeader("client_secret", equalTo("some-secret67JVMBM7KBVVV9DFD77V6FVFDVFDMB3234"))
                        .withRequestBody(matchingJsonPath("$.content.HitsTagList"))
                        .withRequestBody(matchingJsonPath("$.caseType"))
                        .willReturn(aResponse()
                                .withStatus(201)
                                .withHeader("Content-Type", "application/json")
                                .withBody("{\"id\":\"CASE-1001\",\"status\":\"CREATED\"}")));

        given()
                .contentType("application/json")
                .body(VALID_REQUEST_BODY)
                .when()
                .post("/api/v1/cases")
                .then()
                .statusCode(201)
                .body("id", is("CASE-1001"))
                .body("status", is("CREATED"));
    }

    @Test
    void shouldReturnBadGatewayWhenExternalApiFails() {
        WireMockPegaResource.getWireMockServer().stubFor(
                post(urlEqualTo("/prweb/v1/cases"))
                        .willReturn(aResponse().withStatus(500).withBody("Internal Pega error")));

        given()
                .contentType("application/json")
                .body(VALID_REQUEST_BODY)
                .when()
                .post("/api/v1/cases")
                .then()
                .statusCode(502)
                .body("error", is("EXTERNAL_API_ERROR"));
    }

    @Test
    void shouldReturnBadRequestWhenMandatoryFieldMissing() {
        String invalidBody = """
                {
                  "processId": "SomeProcessID",
                  "dataSource": "SomeDataSource",
                  "alertType": "AlertT",
                  "tc": "5",
                  "applicationId": "Some-id-12345",
                  "hitsTagList": { "alerts": [] }
                }
                """;

        given()
                .contentType("application/json")
                .body(invalidBody)
                .when()
                .post("/api/v1/cases")
                .then()
                .statusCode(400)
                .body("error", is("VALIDATION_ERROR"));
    }
}
