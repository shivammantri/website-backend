package com.northeast.service;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.northeast.bootstrap.WebsiteConfiguration;
import com.northeast.dao.UserDao;
import com.northeast.entites.User;
import com.northeast.helper.IdGenerator;
import com.northeast.mapper.UserMapper;
import com.northeast.models.exceptions.UserException;
import com.northeast.models.request.LoginRequest;
import com.northeast.models.request.UserRequest;

import javax.ws.rs.core.Response;
import java.util.Optional;

public class UserService {
    private final UserDao userDao;
    private final UserMapper userMapper;
    private final String secret;

    @Inject
    public UserService(UserDao userDao, UserMapper userMapper, WebsiteConfiguration websiteConfiguration) {
        this.userDao = userDao;
        this.userMapper = userMapper;
        this.secret = websiteConfiguration.getSecret();
    }

    @Transactional
    public void register(UserRequest userRequest) {
        //Check if already exists
        Optional<User> userInDb = userDao.findByEmailOrMobile(userRequest.getEmailId(), userRequest.getMobile());
        if(userInDb.isPresent()) {
            throw new UserException("EmailId or Mobile already exists.", Response.Status.CONFLICT);
        }
        //Map to new user
        User user = userMapper.mapRequestToEntity(userRequest);
        user.setUserId(IdGenerator.generateUserId());
        user.setPassword(EncryptionService.encrypt(userRequest.getPassword(), secret));
        //Persist
        try {
            userDao.create(user);
        }
        catch (Exception e) {
            throw new UserException("Error in registration.", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public boolean login(LoginRequest loginRequest) {
        //Get user with email
        Optional<User> userInDb = userDao.findByEmail(loginRequest.getEmailId());
        if(!userInDb.isPresent()) {
            throw new UserException("User doesn't exist for email :: " + loginRequest.getEmailId(), Response.Status.NOT_FOUND);
        }
        User user = userInDb.get();
        //validate
        return validate(loginRequest.getPassword(), user.getPassword());
    }

    private boolean validate(String enteredPassword, String passwordInDb) {
        String decryptedPassword = EncryptionService.decrypt(passwordInDb, secret);
        return enteredPassword.equals(decryptedPassword);
    }
}
