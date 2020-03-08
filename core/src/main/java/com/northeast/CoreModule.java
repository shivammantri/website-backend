package com.northeast;

import com.google.inject.AbstractModule;
import com.northeast.dao.*;
import com.northeast.entites.Session;

public class CoreModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(UserDao.class).to(UserDaoImpl.class);
        bind(SessionDao.class).to(SessionDaoImpl.class);
        bind(FormDao.class).to(FormDaoImpl.class);
    }
}
