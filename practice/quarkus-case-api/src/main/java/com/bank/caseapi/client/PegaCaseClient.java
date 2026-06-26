package com.bank.caseapi.client;

import com.bank.caseapi.dto.CaseRequest;
import com.bank.caseapi.dto.CaseResponse;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

/**
 * Reactive (Mutiny) REST client for the external Pega case-creation API.
 * Base URL is resolved from {@code quarkus.rest-client.pega-case-api.url}.
 */
@RegisterRestClient(configKey = "pega-case-api")
@Path("/prweb/v1/cases")
public interface PegaCaseClient {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Uni<CaseResponse> createCase(
            @HeaderParam("client_id") String clientId,
            @HeaderParam("client_secret") String clientSecret,
            CaseRequest request);
}
