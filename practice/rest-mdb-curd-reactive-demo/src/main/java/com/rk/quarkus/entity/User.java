package com.rk.quarkus.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.Column;

@Entity
@Table(name = "users")
public class User extends PanacheEntityBase {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto-increment
    public Long id;
	
    @Column(nullable = false)
    public String name;

    @Column(nullable = false, unique = true)
    public String email;

    public String phone;
}
