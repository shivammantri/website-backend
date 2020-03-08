package com.northeast.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.northeast.dao.FormDao;
import com.northeast.dao.SessionDao;
import com.northeast.dao.UserDao;
import com.northeast.entites.Form;
import com.northeast.entites.Session;
import com.northeast.entites.User;
import com.northeast.helper.Constants;
import com.northeast.helper.Eligibility;
import com.northeast.mapper.FormMapper;
import com.northeast.models.exceptions.UserException;
import com.northeast.models.request.FormRequest;
import com.northeast.models.response.FormResponse;

import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FormService {
    private final ObjectMapper objectMapper;
    private final SessionDao sessionDao;
    private final FormDao formDao;
    private final UserDao userDao;
    private final Eligibility eligibility;

    @Inject
    public FormService(ObjectMapper objectMapper, SessionDao sessionDao, FormDao formDao, UserDao userDao, Eligibility eligibility) {
        this.objectMapper = objectMapper;
        this.sessionDao = sessionDao;
        this.formDao = formDao;
        this.userDao = userDao;
        this.eligibility = eligibility;
    }

    @Transactional
    public void submit(FormRequest formRequest, String sessionId) {
        Optional<Session> session = sessionDao.getSessionBySessionId(sessionId);
        if(!session.isPresent()) {
            throw new UserException("No session exist for id :: " + sessionId, Response.Status.NOT_FOUND);
        }
        String userId = session.get().getUserId();
        String formData = "";
        try {
            formData = objectMapper.writeValueAsString(formRequest.getForm());
        } catch (JsonProcessingException e) {
            throw new UserException("Internal Server Error!", Response.Status.INTERNAL_SERVER_ERROR);
        }
        Optional<User> user = userDao.findById(userId);
        if(!user.isPresent()) {
            throw new UserException("User not found", Response.Status.NOT_FOUND);
        }
        if(!user.get().getFormEligible()) {
            throw new UserException("Form create not eligible", Response.Status.FORBIDDEN);
        }
        Form form = new Form();
        form.setUserId(userId);
        form.setData(formData);
        try {
            formDao.create(form);
            user.get().setFormEligible(Boolean.FALSE);
            userDao.update(user.get());
        } catch (Exception e) {
            throw new UserException("Internal Server Error!", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional
    public List<FormResponse> getAllForms(String username, String password) {
        if(!Constants.ADMIN_USERNAME.equals(username) || !Constants.ADMIN_PASSWORD.equals(password)) {
            throw new UserException("Unauthorized!", Response.Status.UNAUTHORIZED);
        }
        List<Form> forms = formDao.getAll();
        return forms.stream().map(FormMapper::transform).collect(Collectors.toList());
    }

    @Transactional
    public FormResponse getUserForm(String username, String password, String userId) {
        if(!Constants.ADMIN_USERNAME.equals(username) || !Constants.ADMIN_PASSWORD.equals(password)) {
            throw new UserException("Unauthorized!", Response.Status.UNAUTHORIZED);
        }
        Optional<Form> form = formDao.getByUserId(userId);
        if(!form.isPresent()) {
            throw new UserException("No form present for user :: " + userId, Response.Status.NOT_FOUND);
        }
        return FormMapper.transform(form.get());
    }

    public void stopEligibility(String username, String password) {
        if(!Constants.ADMIN_USERNAME.equals(username) || !Constants.ADMIN_PASSWORD.equals(password)) {
            throw new UserException("Unauthorized!", Response.Status.UNAUTHORIZED);
        }
        eligibility.setFormEligibility(Boolean.FALSE);
    }
}
