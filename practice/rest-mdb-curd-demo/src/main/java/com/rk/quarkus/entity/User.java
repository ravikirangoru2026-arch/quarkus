package com.rk.quarkus.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "users")
public class User extends PanacheEntity {

    @Column(nullable = false)
    public String name;

    @Column(nullable = false, unique = true)
    public String email;

    public String phone;
}
