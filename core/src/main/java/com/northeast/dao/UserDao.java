package com.northeast.dao;

import com.northeast.entites.User;

import java.util.Optional;

public interface UserDao extends BaseDao<User, String> {
    Optional<User> findById(String userId);
    Optional<User> findByEmailOrMobile(String emailId, String mobile);
    Optional<User> findByEmail(String emailId);
}
