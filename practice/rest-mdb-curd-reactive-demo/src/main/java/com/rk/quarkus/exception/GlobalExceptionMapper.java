package com.rk.quarkus.exception;

import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import com.rk.quarkus.dto.ExceptionResponse;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
public class GlobalExceptionMapper {

	@ServerExceptionMapper
	public RestResponse<ExceptionResponse> handleUserNotFoundException(UserNotFoundException e) {
		ExceptionResponse resp = new ExceptionResponse();
		resp.setStatusCode("U001");
		resp.setStatusMsg(e.getMessage());
		return RestResponse.status(Response.Status.NOT_FOUND, resp);

	}
}
