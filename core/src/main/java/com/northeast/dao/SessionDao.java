package com.northeast.dao;

import com.northeast.entites.Session;

import java.util.Optional;

public interface SessionDao extends BaseDao<Session, Long>{
    Optional<Session> getSessionBySessionId(String sessionId);
}
