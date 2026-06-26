package com.bank.caseapi.resource;

import com.bank.caseapi.dto.CreateCaseRequestDto;
import com.bank.caseapi.service.CaseService;

import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 * Public-facing endpoint. Accepts a simplified case-creation request and
 * forwards it to the external Pega case API, returning that API's response
 * (or a translated error) to the caller.
 */
@Path("/api/v1/cases")
public class CaseResource {

    @Inject
    CaseService caseService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Uni<Response> createCase(@Valid CreateCaseRequestDto dto) {
        return caseService.submitCase(dto)
                .onItem().transform(resp -> Response.status(Response.Status.CREATED).entity(resp).build());
    }
}
