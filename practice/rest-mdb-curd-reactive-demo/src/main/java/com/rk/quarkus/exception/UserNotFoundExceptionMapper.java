/*
package com.rk.quarkus.exception;

import com.rk.quarkus.dto.ExceptionResponse;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class UserNotFoundExceptionMapper implements ExceptionMapper<UserNotFoundException>{

	@Override
	public Response toResponse(UserNotFoundException exception) {
		ExceptionResponse resp=new ExceptionResponse();
		resp.setStatusCode("U001");
		resp.setStatusMsg(exception.getMessage());
		return Response.status(Response.Status.NOT_FOUND).entity(resp).build();
	}

}
*/
