package com.northeast.resource;

import com.codahale.metrics.annotation.Timed;
import com.google.inject.Inject;
import com.northeast.dao.UserDao;
import com.northeast.entites.User;
import com.northeast.helper.IdGenerator;
import com.northeast.mapper.UserMapper;
import com.northeast.models.request.UserRequest;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/website")
@Produces(MediaType.APPLICATION_JSON)
public class SampleResource {
    private final UserMapper userMapper;
    private final UserDao userDao;

    @Inject
    public SampleResource(UserMapper userMapper, UserDao userDao) {
        this.userMapper = userMapper;
        this.userDao = userDao;
    }

    @Path("/sample")
    @POST
    @Timed(absolute = true,name="com.northeast.resource.SampleResource.sample")
    public Response sample(UserRequest userRequest) {
        User user = userMapper.mapRequestToEntity(userRequest);
        user.setUserId(IdGenerator.generateUserId());
        userDao.create(user);
        return Response.ok("this is test").build();
    }
}
