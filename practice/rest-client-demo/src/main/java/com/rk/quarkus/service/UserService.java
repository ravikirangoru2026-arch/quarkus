package com.rk.quarkus.service;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import com.rk.quarkus.dto.User;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import java.util.List;

@Path("/users")
@RegisterRestClient(configKey = "jsonplaceholder-api")
public interface UserService {
    @GET
    List<User> getUsers();
}
