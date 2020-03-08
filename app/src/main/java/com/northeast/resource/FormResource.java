package com.northeast.resource;

import com.google.inject.Inject;
import com.northeast.models.request.FormRequest;
import com.northeast.models.response.FormResponse;
import com.northeast.service.FormService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/form")
@Produces(MediaType.APPLICATION_JSON)
public class FormResource {
    private final FormService formService;

    @Inject
    public FormResource(FormService formService) {
        this.formService = formService;
    }

    @POST
    @Path("/submit")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response submitForm(FormRequest formRequest, @QueryParam("NEYSessionId") String sessionId) {
        formService.submit(formRequest, sessionId);
        return Response.ok("Successfully submitted!").build();
    }

    @POST
    @Path("/all")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public List<FormResponse> getAllForms(@QueryParam("username") String username, @QueryParam("password")String password) {
        return formService.getAllForms(username, password);
    }

    @POST
    @Path("/user/{userId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public FormResponse getUserForm(@QueryParam("username")String username, @QueryParam("password")String password,
                                    @PathParam("userId") String userId) {
        return formService.getUserForm(username, password, userId);
    }
}
