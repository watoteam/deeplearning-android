package com.chutuan.tukyapp.ui.main.profile;

import android.text.TextUtils;
import android.widget.EditText;

import com.chutuan.tukyapp.R;
import com.chutuan.tukyapp.TuKyApp_;
import com.chutuan.tukyapp.model.User;
import com.chutuan.tukyapp.network.response.ResponseWrapper;
import com.chutuan.tukyapp.network.response.UserResponse;
import com.chutuan.tukyapp.network.services.ApiService;
import com.chutuan.tukyapp.ui.BaseActivity;
import com.chutuan.tukyapp.utils.DialogUtils;
import com.chutuan.tukyapp.utils.GsonUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Trieu Tuan on 5/17/2017.
 * Copyright (C) SFR Software.
 */

@EActivity(R.layout.change_pass_activity)
public class ChangePasswordActivity extends BaseActivity {
    @ViewById(R.id.edtPassword)
    EditText edtPassword;

    @ViewById(R.id.edtNewPassword)
    EditText edtNewPassword;

    @ViewById(R.id.edtNewPassConfirm)
    EditText edtNewPassConfirm;

    @Inject
    ApiService apiService;

    @AfterViews
    void afterViews() {
        setTitle(R.string.change_password);
        TuKyApp_.getInstance().getAppComponent().inject(this);
    }

    private boolean validate() {
        String password = edtPassword.getText().toString();
        String newPassword = edtNewPassword.getText().toString();
        String newPasswordConfirm = edtNewPassConfirm.getText().toString();

        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(newPasswordConfirm)) {
            DialogUtils.showToast("Vui lòng điền đầy đủ thông tin");
            return false;
        }

        if (!TextUtils.equals(newPassword, newPasswordConfirm)) {
            DialogUtils.showToast("Mật khẩu mới và xác nhận không giống nhau");
            return false;
        }

        return true;
    }

    @Click(R.id.btnChangePass)
    void onChangePassClicked() {
        if (!validate()) {
            return;
        }
        String password = edtPassword.getText().toString();
        String newPassword = edtNewPassword.getText().toString();
        String newPasswordConfirm = edtNewPassConfirm.getText().toString();

        String token = TuKyApp_.getInstance().getUserPref().accessToken().getOr(null);
        DialogUtils.showProgressDialog(this, "Vui lòng chờ...");
        apiService.updatePassword(token, password, newPassword, newPasswordConfirm).enqueue(new Callback<ResponseWrapper<UserResponse>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<UserResponse>> call, Response<ResponseWrapper<UserResponse>> response) {
                DialogUtils.dismissProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {
                        DialogUtils.showToast("Đổi mật khẩu thành công");
                        User user = response.body().getData().getUser();
                        TuKyApp_.getInstance().getUserPref().edit()
                                .isLoggedIn().put(true)
                                .accessToken().put(user.getAccessToken())
                                .currentUser().put(GsonUtils.toJson(user))
                                .apply();
                    } else {
                        DialogUtils.showMessageDialog(ChangePasswordActivity.this, response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<UserResponse>> call, Throwable t) {
                DialogUtils.dismissProgressDialog();
            }
        });

    }
}
