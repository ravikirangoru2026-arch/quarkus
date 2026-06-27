package com.rk.quarkus.service;

import java.util.List;

import com.rk.quarkus.entity.User;
import com.rk.quarkus.mapper.UserMapper;
import com.rk.quarkus.repository.UserRepository;

import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.quarkus.hibernate.reactive.panache.common.WithSessionOnDemand;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;

@ApplicationScoped
public class UserService {

	@Inject
	UserRepository repository;

	public Uni<List<com.rk.quarkus.dto.User>> listAll() {
		return repository.listAll().onItem().transform(users -> users.stream().map(UserMapper::toDTO).toList());
	}

	public Uni<com.rk.quarkus.dto.User> findById(Long id) {
		return repository.findById(id).onItem().ifNull().failWith(new NotFoundException()).onItem()
				.transform(UserMapper::toDTO);
	}

	public Uni<com.rk.quarkus.dto.User> create(com.rk.quarkus.dto.User user) {
		User entity = UserMapper.toEntity(user);
		return repository.persist(entity).replaceWith(UserMapper.toDTO(entity));
	}

	public Uni<com.rk.quarkus.dto.User> update(Long id, com.rk.quarkus.dto.User updated) {
		return repository.findById(id).onItem().ifNull().failWith(new NotFoundException()).onItem()
				.transform(entity -> {
					UserMapper.updateEntity(entity, updated);
					return UserMapper.toDTO(entity);
				});
	}

	public Uni<Boolean> delete(Long id) {
		return repository.deleteById(id);
	}
}
