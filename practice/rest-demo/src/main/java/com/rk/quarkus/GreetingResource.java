package com.rk.quarkus;

import com.rk.quarkus.dto.User;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST API..";
    }
    //http://localhost:8080/hello
    
    @GET
    @Path("/p/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public String testPathParam(@PathParam(value ="name") String name)
    {
    	return name;
    }
    //http://localhost:8080/hello/p/ram
    
    
    @GET
    @Path("/q")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public String testQueryParam(@QueryParam(value ="name") String name)
    {
    	return name;
    }
    //http://localhost:8080/hello/q?name=rajram

    @POST
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public User saveUser(User usr)
    {
    	return usr;
    }
    //POST call use Bruno/postman: http://localhost:8080/hello/user
    /* Body --> 
     {
        "id" : 20,
        "name" : "ravi"
    }
    */
    
}
