package com.northeast.dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.northeast.entites.User;

import javax.persistence.EntityManager;
import java.util.Optional;

public class UserDaoImpl extends BaseDaoImpl<User, String> implements UserDao {
    @Inject
    public UserDaoImpl(Provider<EntityManager> entityManagerProvider) {
        super(entityManagerProvider);
    }

    @Override
    public Optional<User> findById(String userId) {
        return Optional.empty();
    }
}
