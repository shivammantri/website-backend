package com.northeast.mapper;

import com.northeast.entites.User;
import com.northeast.models.request.UserRequest;

public class UserMapper {
    public User mapRequestToEntity(UserRequest userRequest) {
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmailId(userRequest.getEmailId());
        user.setMobile(userRequest.getMobile());
        user.setSourceOfInfo(userRequest.getSourceOfInfo());
        user.setFormEligible(true);
        return user;
    }
}
