package com.chutuan.tukyapp.utils.validator;

import android.text.TextUtils;
import android.widget.EditText;

import com.chutuan.tukyapp.utils.validator.Validator;


public class EmptyValidator extends Validator {
    public EmptyValidator(String msg) {
        super(msg);
    }

    @Override
    public boolean isValid(EditText et) {
        return TextUtils.getTrimmedLength(et.getText()) > 0;
    }
}