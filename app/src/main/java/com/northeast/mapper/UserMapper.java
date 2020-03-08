package com.northeast.mapper;

import com.google.inject.Inject;
import com.northeast.entites.User;
import com.northeast.helper.Eligibility;
import com.northeast.models.request.UserRequest;

public class UserMapper {
    private final Eligibility eligibility;

    @Inject
    public UserMapper(Eligibility eligibility) {
        this.eligibility = eligibility;
    }

    public User mapRequestToEntity(UserRequest userRequest) {
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmailId(userRequest.getEmailId());
        user.setMobile(userRequest.getMobile());
        user.setSourceOfInfo(userRequest.getSourceOfInfo());
        user.setFormEligible(eligibility.getFormEligibility());
        return user;
    }
}
