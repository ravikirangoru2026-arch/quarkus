package com.rk.quarkus.repository;

import com.rk.quarkus.entity.User;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
    // Custom queries can be added here
}
