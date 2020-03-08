package com.northeast.dao;

import com.northeast.entites.Form;

import java.util.List;
import java.util.Optional;

public interface FormDao extends BaseDao<Form, Long> {
    Optional<Form> getByUserId(String userId);
    List<Form> getAll();
}
