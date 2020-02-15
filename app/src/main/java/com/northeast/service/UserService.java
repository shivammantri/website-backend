package com.northeast.service;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.northeast.dao.UserDao;
import com.northeast.entites.User;
import com.northeast.models.request.UserRequest;

public class UserService {
    private final UserDao userDao;

    @Inject
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    @Transactional
    public void create(UserRequest userRequest) {
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmailId(userRequest.getEmailId());
        user.setMobile(userRequest.getMobile());
        user.setSourceOfInfo(userRequest.getSourceOfInfo());
        user.setPassword(userRequest.getPassword());
        user.setUserId("User1");
        userDao.create(user);
    }
}
