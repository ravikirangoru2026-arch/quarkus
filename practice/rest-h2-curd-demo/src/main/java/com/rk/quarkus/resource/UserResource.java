package com.rk.quarkus.resource;

import java.util.List;

import com.rk.quarkus.entity.User;

import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/v1/user")
public class UserResource {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional
	public User saveUser(User usr) {
		User.persist(usr);
		return usr;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<User> getAllUsers() {
		return User.findAll().list();
	}

	@Path("/paged")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<User> getAllUsersPaginated(@QueryParam("page") @DefaultValue("0") int page,
			@QueryParam("size") @DefaultValue("2") int size) {
		return User.findAll().page(Page.of(page, size)).list();
	}

	@Path("/sortedpaged")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<User> getAllUsersSortedPaged(@QueryParam("page") @DefaultValue("0") int page,
			@QueryParam("size") @DefaultValue("2") int size,
			@QueryParam("sortField") @DefaultValue("fname") String sortField
			) {
		return User.findAll(Sort.by(sortField)).page(Page.of(page, size)).list();
	}

	@Path("/byid/{id}")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public User getUserById(@PathParam("id") int id) {
		return User.findById(id);
	}

	@Path("/byfname/{fname}")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public User getUserFirstName(@PathParam("fname") String fname) {
		return User.find("fname", fname).firstResult();
	}

}
