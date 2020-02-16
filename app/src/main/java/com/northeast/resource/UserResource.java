package com.northeast.resource;

import com.google.inject.Inject;
import com.northeast.models.exceptions.UserException;
import com.northeast.models.request.LoginRequest;
import com.northeast.models.request.UserRequest;
import com.northeast.models.response.LoginResponse;
import com.northeast.service.UserService;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
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

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(@Valid UserRequest userRequest) {
        userService.register(userRequest);
        return Response.ok("Successfully registered!").build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public LoginResponse login(@Valid LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    //reset
    //logout
}
