package com.rk.quarkus.service;

import java.util.List;
import java.util.stream.Collectors;

import com.rk.quarkus.entity.User;
import com.rk.quarkus.mapper.UserMapper;
import com.rk.quarkus.repository.UserRepository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;

@ApplicationScoped
public class UserService {

	@Inject
	UserRepository repository;

	public List<com.rk.quarkus.dto.User> listAll() {
		return repository.listAll().stream().map(UserMapper::toDTO).collect(Collectors.toList());
	}

	public com.rk.quarkus.dto.User findById(@PathParam("id") Long id) {
		User entity = repository.findById(id);
		if (entity == null)
			throw new NotFoundException();
		return UserMapper.toDTO(entity);
	}

	@Transactional
	public com.rk.quarkus.dto.User create(com.rk.quarkus.dto.User user) {
		User entity = UserMapper.toEntity(user);
		repository.persist(entity);
		return UserMapper.toDTO(entity);
	}

	@Transactional
	public com.rk.quarkus.dto.User update(@PathParam("id") Long id, com.rk.quarkus.dto.User updated) {
		User entity = repository.findById(id);
		if (entity == null) {
			throw new NotFoundException();
		}
		UserMapper.updateEntity(entity, updated); // updated
		return UserMapper.toDTO(entity);
	}

	@Transactional
	public void delete(@PathParam("id") Long id) {
		repository.deleteById(id);
	}
}
