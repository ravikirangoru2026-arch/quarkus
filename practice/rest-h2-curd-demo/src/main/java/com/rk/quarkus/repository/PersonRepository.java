package com.rk.quarkus.repository;

import java.util.List;

import com.rk.quarkus.entity.Person;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.PathParam;

@ApplicationScoped
public class PersonRepository implements PanacheRepository<Person> {
	public Person savePerson(Person person) {
		Person.persist(person);
		return person;
	}

	public List<Person> getAllPersons() {	

		return Person.findAll().list();
	}

	public Person getPersonById(@PathParam("id") int id) {
		return Person.findById(id);
	}

	public Person getPersonFirstName(@PathParam("fname") String fname) {
		return Person.find("fname", fname).firstResult();
	}
}
