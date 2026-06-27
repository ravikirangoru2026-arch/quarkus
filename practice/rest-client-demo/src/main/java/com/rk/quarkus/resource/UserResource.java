package com.rk.quarkus.resource;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.rk.quarkus.dto.User;
import com.rk.quarkus.service.UserService;

import java.util.List;

@Path("/consume-users")
public class UserResource {

    @Inject
    @RestClient
    UserService userService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers() {
        return userService.getUsers();
    }
}
