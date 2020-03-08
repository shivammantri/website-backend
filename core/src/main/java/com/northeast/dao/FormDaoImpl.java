package com.northeast.dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.northeast.entites.Form;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public class FormDaoImpl extends BaseDaoImpl<Form, Long> implements FormDao {
    @Inject
    public FormDaoImpl(Provider<EntityManager> entityManagerProvider) {
        super(entityManagerProvider);
    }

    @Override
    public Optional<Form> getByUserId(String userId) {
        Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(getEntityClass());
        criteria.add(Restrictions.and(Restrictions.eq("userId", userId)));
        Form form = (Form) criteria.uniqueResult();
        return Optional.ofNullable(form);
    }

    @Override
    public List<Form> getAll() {
        Criteria criteria = getEntityManager().unwrap(Session.class).createCriteria(getEntityClass());
        List<Form> forms = criteria.list();
        return forms;
    }
}
