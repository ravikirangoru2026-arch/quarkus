package com.rk.quarkus.service;

import java.util.List;

import com.rk.quarkus.entity.Person;
import com.rk.quarkus.repository.PersonRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.PathParam;

@ApplicationScoped
public class PersonService {

	@Inject
	private PersonRepository personRepository;

	@Transactional
	public Person savePerson(Person person) {
		return personRepository.savePerson(person);
	}

	public List<Person> getAllPersons() {

		return personRepository.getAllPersons();	
	}

	public Person getPersonById(@PathParam("id") int id) {
		return personRepository.getPersonById(id);
	}

	public Person getPersonFirstName(@PathParam("fname") String fname) {
		return personRepository.getPersonFirstName(fname);
	}

}
