package com.rk.quarkus.service;

import java.util.List;

import com.rk.quarkus.entity.User;
import com.rk.quarkus.exception.UserNotFoundException;
import com.rk.quarkus.mapper.UserMapper;
import com.rk.quarkus.repository.UserRepository;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithSessionOnDemand;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.quarkus.logging.Log;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class UserService {

	@Inject
	UserRepository repository;

	@WithSessionOnDemand
	public Uni<List<com.rk.quarkus.dto.User>> listAll() {
		Log.info("Get all users- started");
		return repository.listAll().onItem().transform(users -> users.stream().map(UserMapper::toDTO).toList());
	}

	@WithSessionOnDemand
	public Uni<com.rk.quarkus.dto.User> findById(Long id) {
		Log.info("Find user by id :"+ id);
		return repository.findById(id).onItem().ifNull().failWith(new UserNotFoundException("User not found")).onItem()
				.transform(UserMapper::toDTO);
	}

	@WithTransaction
	public Uni<com.rk.quarkus.dto.User> create(com.rk.quarkus.dto.User user) {
		User entity = UserMapper.toEntity(user);
		Log.info("Create user :"+entity);
		return repository.persist(entity).replaceWith(UserMapper.toDTO(entity));
	}

	@WithTransaction
	public Uni<com.rk.quarkus.dto.User> update(Long id, com.rk.quarkus.dto.User updated) {
		Log.info("Update user for id :"+id+" user details :"+updated);
		return repository.findById(id).onItem().ifNull().failWith(new NotFoundException()).onItem()
				.transform(entity -> {
					UserMapper.updateEntity(entity, updated);
					return UserMapper.toDTO(entity);
				});
	}

	@WithTransaction
	public Uni<Boolean> delete(Long id) {
		Log.info("Delete use by id :"+id);
		return repository.deleteById(id);
	}
}
