package com.northeast.resource;

import com.google.inject.Inject;
import com.northeast.models.exceptions.UserException;
import com.northeast.models.request.LoginRequest;
import com.northeast.models.request.UserRequest;
import com.northeast.models.response.LoginResponse;
import com.northeast.service.UserService;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
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
    public Response login(@Valid LoginRequest loginRequest) {
        LoginResponse loginResponse = userService.login(loginRequest);
        return Response.ok("Successfully logged in!").cookie(
                new NewCookie("NEYSessionId", loginResponse.getSessionId())).build();
    }

    @POST
    @Path("/logout/{sessionId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@PathParam("sessionId") String sessionId) {
        String message = userService.logout(sessionId);
        return Response.ok(message).build();
    }

    //reset
}
