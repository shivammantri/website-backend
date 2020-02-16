package com.northeast.dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.northeast.entites.Session;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import javax.persistence.EntityManager;
import java.util.Optional;

public class SessionDaoImpl extends BaseDaoImpl<Session, Long> implements SessionDao {
    @Inject
    public SessionDaoImpl(Provider<EntityManager> entityManagerProvider) {
        super(entityManagerProvider);
    }

    @Override
    public Optional<Session> getSessionBySessionId(String sessionId) {
        Criteria criteria = getEntityManager().unwrap(org.hibernate.Session.class).createCriteria(getEntityClass());
        criteria.add(Restrictions.and(Restrictions.eq("sessionId", sessionId)));
        Session session = (Session) criteria.uniqueResult();
        return Optional.ofNullable(session);
    }
}
