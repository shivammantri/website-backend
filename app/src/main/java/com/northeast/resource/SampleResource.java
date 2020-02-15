package com.northeast.resource;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.northeast.models.request.UserRequest;
import com.northeast.service.UserService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/website")
@Produces(MediaType.APPLICATION_JSON)
public class SampleResource {

    @Inject
    private UserService userService;

    @Path("/sample")
    @POST
    @Timed(absolute = true,name="com.northeast.resource.SampleResource.sample")
    public Response sample(UserRequest userRequest) {
        userService.create(userRequest);
        return Response.ok("this is test").build();
    }
}
