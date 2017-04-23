package com.chutuan.tukyapp.utils.validator;

import android.text.TextUtils;
import android.widget.EditText;


public class LengthValidator extends Validator {
    private int mMin;
    private int mMax;

    public LengthValidator(String msg, int min, int max) {
        super(msg);
        mMin = min;
        mMax = max;
    }

    @Override
    public boolean isValid(EditText et) {
        int length = TextUtils.getTrimmedLength(et.getText());
        return length < mMax && length >= mMin;
    }
}
