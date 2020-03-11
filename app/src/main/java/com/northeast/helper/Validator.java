package com.northeast.helper;

import org.apache.commons.validator.routines.EmailValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static boolean validateEmail(String emailId) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(emailId);
    }

    public static boolean validateMobile(String mobile) {
        Pattern pattern = Pattern.compile("(0/91)?[7-9][0-9]{9}");
        Matcher matcher = pattern.matcher(mobile);
        return (matcher.find() && matcher.group().equals(mobile));
    }
}
