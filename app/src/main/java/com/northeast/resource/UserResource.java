package com.northeast.resource;

import com.google.inject.Inject;
import com.northeast.models.exceptions.UserException;
import com.northeast.models.request.LoginRequest;
import com.northeast.models.request.UserRequest;
import com.northeast.service.UserService;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource {
    private final UserService userService;

    @Inject
    public UserResource(UserService userService) {
        this.userService = userService;
    }

    @Path("/register")
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(UserRequest userRequest) {
        userService.register(userRequest);
        return Response.ok("Successfully registered!").build();
    }

    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest loginRequest) {
        boolean authorized = userService.login(loginRequest);
        if(!authorized) {
            throw new UserException("Password doesn't match.", Response.Status.UNAUTHORIZED);
        }
        return Response.ok("Logged in!").build();
    }

    //reset
}
