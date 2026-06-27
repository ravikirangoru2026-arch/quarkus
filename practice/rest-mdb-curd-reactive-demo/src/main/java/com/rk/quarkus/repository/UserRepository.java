package com.rk.quarkus.repository;

import com.rk.quarkus.entity.User;

import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository implements PanacheRepository<User> {
    // Custom queries can be added here
}
