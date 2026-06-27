package com.rk.quarkus.resource;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

import com.rk.quarkus.service.UserService;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

	@Inject
	UserService userService;

	@GET
	public List<com.rk.quarkus.dto.User> listAll() {
		return userService.listAll();
	}

	@GET
	@Path("/{id}")
	public com.rk.quarkus.dto.User findById(@PathParam("id") Long id) {
		return userService.findById(id);
	}

	@POST
	public com.rk.quarkus.dto.User create(com.rk.quarkus.dto.User user) {
		return userService.create(user);
	}

	@PUT
	@Path("/{id}")
	public com.rk.quarkus.dto.User update(@PathParam("id") Long id, com.rk.quarkus.dto.User updated) {
		return userService.update(id, updated);
	}

	@DELETE
	@Path("/{id}")
	public void delete(@PathParam("id") Long id) {
		userService.delete(id);
	}
}
