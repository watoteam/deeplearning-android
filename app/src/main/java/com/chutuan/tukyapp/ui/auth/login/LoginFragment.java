package com.chutuan.tukyapp.ui.auth.login;

import android.widget.EditText;

import com.chutuan.tukyapp.R;
import com.chutuan.tukyapp.TuKyApp_;
import com.chutuan.tukyapp.model.User;
import com.chutuan.tukyapp.network.response.ResponseWrapper;
import com.chutuan.tukyapp.network.response.UserResponse;
import com.chutuan.tukyapp.ui.auth.AuthActivity;
import com.chutuan.tukyapp.ui.auth.BaseLoginRegisterFragment;
import com.chutuan.tukyapp.utils.DialogUtils;
import com.chutuan.tukyapp.utils.GsonUtils;
import com.chutuan.tukyapp.utils.Utils;
import com.chutuan.tukyapp.utils.validator.EmailValidator;
import com.chutuan.tukyapp.utils.validator.EmptyValidator;
import com.chutuan.tukyapp.utils.validator.LengthValidator;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@EFragment(R.layout.login_fragment)
public class LoginFragment extends BaseLoginRegisterFragment {
    @ViewById(R.id.edtEmail)
    EditText edtEmail;

    @ViewById(R.id.edtPassword)
    EditText edtPassword;


    @Click(R.id.btnLogin)
    void onBtnLoginClicked() {
        if (!validate()) {
            return;
        }

        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        DialogUtils.showProgressDialog(getContext(), "Đang xác thực...");
        Utils.hideKeyboard(getActivity());
        apiService.login(email, password).enqueue(new Callback<ResponseWrapper<UserResponse>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<UserResponse>> call, Response<ResponseWrapper<UserResponse>> response) {
                DialogUtils.dismissProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {
                        User user = response.body().getData().getUser();
                        TuKyApp_.getInstance().getUserPref().edit()
                                .isLoggedIn().put(true)
                                .accessToken().put(user.getAccessToken())
                                .currentUser().put(GsonUtils.toJson(user))
                                .apply();
                        getSymptoms();
                    } else {
                        DialogUtils.showMessageDialog(getContext(), response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<UserResponse>> call, Throwable t) {
                DialogUtils.dismissProgressDialog();
            }
        });
    }

    @Click(R.id.tvSignUp)
    void onSignUpClicked() {
        ((AuthActivity) getActivity()).gotoRegister();
    }

    private boolean validate() {
        EmptyValidator emptyValidator = new EmptyValidator("Không được để trống");
        LengthValidator pwdValidator = new LengthValidator("Mật khẩu phải lớn hơn 6 ký tự", 6, Integer.MAX_VALUE);
        EmailValidator emailValidator = new EmailValidator("Email không đúng định dạng");

        if (!emptyValidator.isValid(edtEmail)) {
            DialogUtils.showToast(emptyValidator.getErrorMessage());
            return false;
        }

        if (!emptyValidator.isValid(edtPassword)) {
            DialogUtils.showToast(emptyValidator.getErrorMessage());
            return false;
        }

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
