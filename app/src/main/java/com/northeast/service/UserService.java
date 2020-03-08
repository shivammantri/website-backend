package com.northeast.service;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.northeast.bootstrap.WebsiteConfiguration;
import com.northeast.dao.SessionDao;
import com.northeast.dao.UserDao;
import com.northeast.entites.Session;
import com.northeast.entites.SessionData;
import com.northeast.entites.SessionStatus;
import com.northeast.entites.User;
import com.northeast.helper.IdGenerator;
import com.northeast.helper.Validator;
import com.northeast.mapper.UserMapper;
import com.northeast.models.exceptions.UserException;
import com.northeast.models.request.LoginRequest;
import com.northeast.models.request.UserRequest;
import com.northeast.models.response.LoginResponse;
import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.Response;
import java.util.Optional;

@Slf4j
public class UserService {
    private final UserDao userDao;
    private final UserMapper userMapper;
    private final String secret;
    private final SessionDao sessionDao;

    @Inject
    public UserService(UserDao userDao, UserMapper userMapper,
                       WebsiteConfiguration websiteConfiguration,
                       SessionDao sessionDao) {
        this.userDao = userDao;
        this.userMapper = userMapper;
        this.secret = websiteConfiguration.getSecret();
        this.sessionDao = sessionDao;
    }

    @Transactional
    public void register(UserRequest userRequest) {
        //validate emailId and mobile number
        if(!Validator.validateEmail(userRequest.getEmailId())) {
            throw new UserException("EmailId not valid.", Response.Status.BAD_REQUEST);
        }
        if(!Validator.validateMobile(userRequest.getMobile())) {
            throw new UserException("Mobile Number not valid.", Response.Status.BAD_REQUEST);
        }
        //Check if already exists
        Optional<User> userInDb = userDao.findByEmailOrMobile(userRequest.getEmailId(), userRequest.getMobile());
        if(userInDb.isPresent()) {
            throw new UserException("EmailId or Mobile number already exists.", Response.Status.CONFLICT);
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
    public LoginResponse login(LoginRequest loginRequest) {
        //validate emailId
        if(!Validator.validateEmail(loginRequest.getEmailId())) {
            throw new UserException("EmailId not valid.", Response.Status.BAD_REQUEST);
        }
        //Get user with email
        Optional<User> userInDb = userDao.findByEmail(loginRequest.getEmailId());
        if(!userInDb.isPresent()) {
            throw new UserException("User doesn't exist for email :: " + loginRequest.getEmailId(), Response.Status.NOT_FOUND);
        }
        User user = userInDb.get();
        //validate
        boolean authorized = validate(loginRequest.getPassword(), user.getPassword());
        if(!authorized) {
            throw new UserException("Password doesn't match.", Response.Status.UNAUTHORIZED);
        }
        String sessionId = IdGenerator.generateSessionId();
        Session session = getSession(user.getUserId());
        session.setSessionId(sessionId);
        try {
            sessionDao.create(session);
        }
        catch (Exception e) {
            throw new UserException("Internal Server Error! Please try again!", Response.Status.INTERNAL_SERVER_ERROR);
        }
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setSessionId(sessionId);
        return loginResponse;
    }

    @Transactional
    public String logout(String sessionId) {
        Optional<Session> session = sessionDao.getSessionBySessionId(sessionId);
        if(!session.isPresent()) {
            throw new UserException("No session exist for id :: " + sessionId, Response.Status.NOT_FOUND);
        }
        if(session.get().getSessionStatus().equals(SessionStatus.CLOSED)) {
            return "Session already closed";
        }
        session.get().setSessionStatus(SessionStatus.CLOSED);
        try {
            sessionDao.update(session.get());
        }
        catch (Exception e) {
            throw new UserException("Internal Serval Error!", Response.Status.INTERNAL_SERVER_ERROR);
        }
        return "Sucessfully logged out!";
    }

    private Session getSession(String userId) {
        Session session = new Session();
        session.setSessionStatus(SessionStatus.ACTIVE);
//        SessionData sessionData = new SessionData();
//        sessionData.setUserId(userId);
        session.setUserId(userId);
        return session;
    }

    private boolean validate(String enteredPassword, String passwordInDb) {
        String decryptedPassword = EncryptionService.decrypt(passwordInDb, secret);
        return enteredPassword.equals(decryptedPassword);
    }
}
