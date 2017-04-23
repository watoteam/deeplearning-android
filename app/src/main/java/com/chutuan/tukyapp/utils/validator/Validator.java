package com.chutuan.tukyapp.utils.validator;


import android.widget.EditText;

public abstract class Validator {
    protected String errorMessage;

    public Validator(String msg) {
        errorMessage = msg;
    }

    public abstract boolean isValid(EditText et);

    public String getErrorMessage() {
        return errorMessage;
    }
}