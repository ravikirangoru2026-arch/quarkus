package com.rk.quarkus.resource;

import java.util.List;

import com.rk.quarkus.entity.Employee;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/v1/emp")
public class EmployeeResource {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Transactional
	public Employee saveEmployee(Employee emp) {
		Employee.persist(emp);
		return emp;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public List<Employee> getAllEmployee() {
		return Employee.findAll().list();
	}

	}
