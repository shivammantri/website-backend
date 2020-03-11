package com.northeast.resource;

import com.google.inject.Inject;
import com.northeast.models.request.LoginRequest;
import com.northeast.models.request.UserRequest;
import com.northeast.models.response.LoginResponse;
import com.northeast.service.UserService;
import org.apache.commons.lang3.tuple.Pair;

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
        return Response.ok("Successfully registered!").header("access-control-allow-origin", "*").build();
    }

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@Valid LoginRequest loginRequest) {
        Pair<LoginResponse, String> loginResponse = userService.login(loginRequest);
        return Response.ok(loginResponse.getLeft()).cookie(
                new NewCookie("NEYSessionId", loginResponse.getRight())).build();
    }

    @POST
    @Path("/logout")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@QueryParam("NEYSessionId") String sessionId) {
        String message = userService.logout(sessionId);
        return Response.ok(message).build();
    }

    //reset
}
