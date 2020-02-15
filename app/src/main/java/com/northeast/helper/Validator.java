package com.northeast.helper;

import org.apache.commons.validator.routines.EmailValidator;

public class Validator {
    public static boolean validateEmail(String emailId) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(emailId);
    }

    public static boolean validateMobile(String mobile) {
        String pattern = "^[0-9]{10}$";
        return pattern.matches(mobile);
    }
}
