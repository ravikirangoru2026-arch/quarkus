package com.rk.quarkus;

import java.util.SplittableRandom;

import com.rk.quarkus.dto.User;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HEAD;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.OPTIONS;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/v1")
public class GreetingResource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello";
    }
    
    @GET
    @Path("/p/{name}")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public String testPathParam(@PathParam("name") String name)
    {
    	return name;
    }
    
    
    @GET
    @Path("/q")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public String testQueryParam(@QueryParam(value ="name") String name)
    {
    	return name;
    }

    @POST
    @Path("/user")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public User saveUser(User usr)
    {
    	return usr;
    }
    
    @PUT
    @Path("/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public User fullUpdateUser(@PathParam("id") int id, User usr)
    {
    	usr.setId(id);
    	usr.setName(usr.getName().toUpperCase());
    	return usr;
    }
    
    @PATCH
    @Path("/user/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Transactional
    public User partialUpdate(@PathParam("id") int id, User usr)
    {
    	usr.setId(id);
    	usr.setName(usr.getName().toUpperCase());
    	return usr;
    }
    
    @DELETE
    @Path("/user/{id}")
    @Transactional
    public void deleteUser(@PathParam("id") int id)
    {
    	System.out.println("User is deleted "+id);
    }
    
    
    @HEAD
    @Path("/user/{id}")
    public Response checkUserExistence(@PathParam("id") int id)
    {
    	System.out.println("Returns headers only, no body (useful for existence checks).");
    	SplittableRandom ris= new SplittableRandom();
    	int randomNbr=ris.nextInt(0,2);
    	System.out.println("Generated Random number "+randomNbr);
    	return randomNbr==1?Response.ok().build():Response.status(404).build();
    }
    
    
    @OPTIONS
    public Response options()
    {
    	System.out.println("Used to describe allowed methods (CORS preflight).");
    	return Response.ok().header("Allow", "GET,POST,PUT,DELET").build();
    }
    
    @GET
    @Path("/user/h/{id}")
    public String showClientID(@HeaderParam("client-id") String clientID)
    {
    	System.out.println("Header param client-id: "+clientID);
    	return clientID;
    }
    
    
    
}
