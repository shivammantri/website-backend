package com.northeast.models.request;

import lombok.Data;

import java.util.Map;

@Data
public class FormRequest {
    private Map<String, String> form;
}
