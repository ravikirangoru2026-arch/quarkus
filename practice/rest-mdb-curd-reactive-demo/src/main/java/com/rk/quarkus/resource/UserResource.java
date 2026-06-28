	package com.rk.quarkus.resource;

import java.util.List;

import com.rk.quarkus.service.UserService;

import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

	@Inject
	UserService userService;

	@GET
	@WithSession
	public Uni<List<com.rk.quarkus.dto.User>> listAll() {
		return userService.listAll();
	}

	@GET
	@Path("/{id}")
	@WithSession
	public Uni<com.rk.quarkus.dto.User> findById(@PathParam("id") Long id) {
		return userService.findById(id);
	}

	@POST
	@WithTransaction
	public Uni<com.rk.quarkus.dto.User> create(com.rk.quarkus.dto.User user) {
		return userService.create(user);
	}

	@PUT
	@Path("/{id}")
	@WithTransaction
	public Uni<com.rk.quarkus.dto.User> update(@PathParam("id") Long id, com.rk.quarkus.dto.User updated) {
		return userService.update(id, updated);
	}

	@DELETE
	@Path("/{id}")
	@WithTransaction
	public Uni<Boolean> delete(@PathParam("id") Long id) {
		return userService.delete(id);
	}
}
