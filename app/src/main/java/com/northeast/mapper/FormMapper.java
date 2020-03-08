package com.northeast.mapper;

import com.northeast.entites.Form;
import com.northeast.models.response.FormResponse;

public class FormMapper {
    public static FormResponse transform(Form form) {
        FormResponse formResponse = new FormResponse();
        formResponse.setUserId(form.getUserId());
        formResponse.setFormData(form.getData());
        return formResponse;
    }
}
