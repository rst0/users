package com.example.users.api;

import com.example.users.model.User;
import com.example.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/")
public class ApiEndpointMapper {
	@Autowired
	private UserService userService;

	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Response ping() {
		return Response.ok("Users Service is running").build();
	}

	@POST
	@Path("/users")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public User create(User user) {
		return userService.save(user);
	}

	@GET
	@Path("/users")
	@Produces(MediaType.APPLICATION_JSON)
	public Iterable<User> getAllUsers() {
		return userService.getAll();
	}

	@GET
	@Path("/users/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserById(@PathParam("id") long id) {
		return userService.getById(id);
//		return new User(123, "Ivan", "Ivanov");
	}

	@PUT
	@Path("/users/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") long id) {
		return Response.noContent().build();
	}

	@DELETE
	@Path("/users/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response delete(@PathParam("id") long id) {
		return Response.noContent().build();
	}
}
