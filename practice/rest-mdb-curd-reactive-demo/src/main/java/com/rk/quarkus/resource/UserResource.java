	package com.rk.quarkus.resource;

import java.util.List;

import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import com.rk.quarkus.service.UserService;

import io.quarkus.logging.Log;
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
@Tag(name = "User API", description = "Operations on users")
public class UserResource {

	@Inject
	UserService userService;

	@GET
	@Operation(summary = "List all users", description = "Returns all users in the system")
	public Uni<List<com.rk.quarkus.dto.User>> listAll() {
		Log.info("Get all users details");
		return userService.listAll();
	}

	@GET
	@Path("/{id}")
	@Operation(summary = "Fetch user", description = "Fetch user by id")
	public Uni<com.rk.quarkus.dto.User> findById(@PathParam("id") Long id) {
		return userService.findById(id);
	}

	@POST
	@Operation(summary = "Create user", description = "Creates a new user")
	public Uni<com.rk.quarkus.dto.User> create(com.rk.quarkus.dto.User user) {
		return userService.create(user);
	}

	@PUT
	@Path("/{id}")
	@Operation(summary = "Update user", description = "Update user")
	public Uni<com.rk.quarkus.dto.User> update(@PathParam("id") Long id, com.rk.quarkus.dto.User updated) {
		return userService.update(id, updated);
	}

	@DELETE
	@Path("/{id}")
	@Operation(summary = "Delete user", description = "Delete user")
	public Uni<Boolean> delete(@PathParam("id") Long id) {
		return userService.delete(id);
	}
}
