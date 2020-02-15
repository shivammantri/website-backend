package com.northeast.dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.northeast.entites.User;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.persistence.EntityManager;
import java.util.Optional;

public class UserDaoImpl extends BaseDaoImpl<User, String> implements UserDao {
    @Inject
    public UserDaoImpl(Provider<EntityManager> entityManagerProvider) {
        super(entityManagerProvider);
    }

    @Override
    public Optional<User> findById(String userId) {
        Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(getEntityClass());
        criteria.add(Restrictions.and(Restrictions.eq("userId", userId)));
        User user = (User) criteria.uniqueResult();
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByEmailOrMobile(String emailId, String mobile) {
        Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(getEntityClass());
        criteria.add(Restrictions.or(Restrictions.eq("emailId", emailId),
                Restrictions.eq("mobile", mobile)));
        User user = (User) criteria.uniqueResult();
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByEmail(String emailId) {
        Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(getEntityClass());
        criteria.add(Restrictions.and(Restrictions.eq("emailId", emailId)));
        User user = (User) criteria.uniqueResult();
        return Optional.ofNullable(user);
    }

}
