package com.northeast;

import com.google.inject.AbstractModule;
import com.northeast.dao.UserDao;
import com.northeast.dao.UserDaoImpl;

public class CoreModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(UserDao.class).to(UserDaoImpl.class);
    }
}
