package com.chutuan.tukyapp.ui.auth.login;

import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.widget.EditText;

import com.chutuan.tukyapp.R;
import com.chutuan.tukyapp.TuKyApp;
import com.chutuan.tukyapp.TuKyApp_;
import com.chutuan.tukyapp.network.response.ResponseWrapper;
import com.chutuan.tukyapp.network.response.UserResponse;
import com.chutuan.tukyapp.network.services.AuthService;
import com.chutuan.tukyapp.network.services.UserService;
import com.chutuan.tukyapp.ui.BaseFragment;
import com.chutuan.tukyapp.ui.main.MainActivity_;
import com.chutuan.tukyapp.utils.DialogUtils;
import com.chutuan.tukyapp.utils.validator.EmailValidator;
import com.chutuan.tukyapp.utils.validator.LengthValidator;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@EFragment(R.layout.login_fragment)
public class LoginFragment extends BaseFragment {
    @Inject
    AuthService authService;

    @ViewById(R.id.edtEmail)
    TextInputEditText edtEmail;

    @ViewById(R.id.edtPassword)
    TextInputEditText edtPassword;

    @AfterViews
    void afterView() {
        TuKyApp_.getInstance().getAppComponent().inject(this);
    }

    @Click(R.id.btnLogin)
    void onBtnLoginClicked() {
        if (!validate()) {
            return;
        }

        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        authService.login(email, password).enqueue(new Callback<ResponseWrapper<UserResponse>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<UserResponse>> call, Response<ResponseWrapper<UserResponse>> response) {
                if (response.isSuccessful()) {
                    ResponseWrapper<UserResponse> r = response.body();
                    if (r.isSuccessful()) {
                        MainActivity_.intent(LoginFragment.this).start();
                        getActivity().finish();
                    } else {
                        DialogUtils.showToast("Error when login");
                    }
                }else{
                    try {
                        DialogUtils.showToast("Error when login" + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<UserResponse>> call, Throwable t) {
                Log.e("A", "Fail");
            }
        });

    }

    private boolean validate() {
        LengthValidator pwdValidator = new LengthValidator("Mật khẩu phải lớn hơn 6 ký tự", 6, Integer.MAX_VALUE);
        EmailValidator emailValidator = new EmailValidator("Sai email cmnr");

        if (!pwdValidator.isValid(edtPassword)) {
            DialogUtils.showToast(pwdValidator.getErrorMessage());
            return false;
        }

        if (!emailValidator.isValid(edtEmail)) {
            DialogUtils.showToast(emailValidator.getErrorMessage());
            return false;
        }

        return true;
    }
}
