package com.rk.quarkus.resource;

import java.util.List;

import com.rk.quarkus.entity.Person;
import com.rk.quarkus.service.PersonService;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/v1/person")
public class PersonResource {

	@Inject
	private PersonService personService;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Person savePerson(Person person) {
		return personService.savePerson(person);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Person> getAllPersons() {
		return personService.getAllPersons();
	}

	@Path("/byid/{id}")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Person getPersonById(@PathParam("id") int id) {
		return personService.getPersonById(id);
	}

	@Path("/byfname/{fname}")
	@GET()
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Person getPersonFirstName(@PathParam("fname") String fname) {
		return personService.getPersonFirstName(fname);
	}

}
