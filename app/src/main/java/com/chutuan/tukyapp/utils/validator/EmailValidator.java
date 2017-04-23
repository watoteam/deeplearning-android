package com.chutuan.tukyapp.utils.validator;

import com.chutuan.tukyapp.utils.validator.PatternValidator;

import java.util.regex.Pattern;

public class EmailValidator extends PatternValidator {
    public EmailValidator(String msg) {
        super(msg, Pattern.compile(REGEX_EMAIL, Pattern.CASE_INSENSITIVE));
    }
}