package com.rk.quarkus.mapper;

import com.rk.quarkus.entity.User;

public class UserMapper {

	public static com.rk.quarkus.dto.User toDTO(User entity) {
		com.rk.quarkus.dto.User dto = new com.rk.quarkus.dto.User();
		dto.setId(entity.id);
		dto.setName(entity.name);
		dto.setEmail(entity.email);
		dto.setPhone(entity.phone);
		return dto;
	}

	public static User toEntity(com.rk.quarkus.dto.User dto) {
		User entity = new User();
		entity.id = dto.getId(); // PanacheEntity has id
		entity.name = dto.getName();
		entity.email = dto.getEmail();
		entity.phone = dto.getPhone();
		return entity;
	}

	public static void updateEntity(User entity, com.rk.quarkus.dto.User dto) {
		entity.name = dto.getName();
		entity.email = dto.getEmail();
		entity.phone = dto.getPhone();
	}
}
