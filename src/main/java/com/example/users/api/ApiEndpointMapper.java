package com.example.users.api;

import com.example.users.model.User;
import com.example.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Component
@Path("/users")
public class ApiEndpointMapper {
	@Autowired
	private UserService userService;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public User createUser(User user) {
		return userService.save(user);
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Iterable<User> getAllUsers() {
		return userService.retrieveAll();
	}

	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public User getUserById(@PathParam("id") long id) {
		return userService.retrieveById(id);
	}

	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public User updateUser(User user) {
		return userService.update(user);
	}

	@DELETE
	public Response deleteUser(User user) {
		Response.ResponseBuilder response;
		try {
			userService.delete(user);
			response = Response.ok();
		} catch (Exception e) {
			response = Response.notModified(e.getMessage());
		}
		return response.build();
	}
}
