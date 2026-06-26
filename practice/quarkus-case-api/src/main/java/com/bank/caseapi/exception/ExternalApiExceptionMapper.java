package com.bank.caseapi.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ExternalApiExceptionMapper implements ExceptionMapper<ExternalApiException> {

    @Override
    public Response toResponse(ExternalApiException exception) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("error", "EXTERNAL_API_ERROR");
        body.put("message", exception.getMessage());
        body.put("upstreamStatus", exception.getStatusCode());

        return Response.status(Response.Status.BAD_GATEWAY)
                .entity(body)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
