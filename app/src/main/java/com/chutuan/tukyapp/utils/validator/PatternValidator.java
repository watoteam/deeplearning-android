package com.chutuan.tukyapp.utils.validator;

import android.widget.EditText;

import com.chutuan.tukyapp.utils.validator.Validator;

import java.util.regex.Pattern;


public class PatternValidator extends Validator {
    private Pattern mPattern;

    protected static final String REGEX_EMAIL
            = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

    protected static final String REGEX_PHONE =
            "(\\+[0-9]+[\\- \\.]*)?(\\([0-9]+\\)[\\- \\.]*)?([0-9][0-9\\- \\.][0-9\\- \\.]+[0-9])";

    public PatternValidator(String msg, Pattern pattern) {
        super(msg);
        if (pattern == null) {
            throw new IllegalArgumentException("pattern must not be null");
        }
        mPattern = pattern;
    }

    @Override
    public boolean isValid(EditText et) {
        return mPattern.matcher(et.getText()).matches();
    }
}