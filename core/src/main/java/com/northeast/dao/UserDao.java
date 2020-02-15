package com.northeast.dao;

import com.northeast.entites.User;

import java.io.Serializable;
import java.util.Optional;

public interface UserDao extends BaseDao<User, String> {
    Optional<User> findById(String userId);
}
