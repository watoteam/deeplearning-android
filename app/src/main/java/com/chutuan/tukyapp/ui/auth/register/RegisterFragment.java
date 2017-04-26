package com.chutuan.tukyapp.ui.auth.register;

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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EFragment(R.layout.register_fragment)
public class RegisterFragment extends BaseLoginRegisterFragment {
    @ViewById(R.id.edtFirstname)
    EditText edtFirstname;

    @ViewById(R.id.edtLastname)
    EditText edtLastname;

    @ViewById(R.id.edtEmail)
    EditText edtEmail;

    @ViewById(R.id.edtPassword)
    EditText edtPassword;

    @Click(R.id.btnSignUp)
    void onSignUpClicked() {
        if (!validate()) {
            return;
        }
        String firstName = edtFirstname.getText().toString();
        String lastName = edtLastname.getText().toString();
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();

        doRegister(firstName, lastName, email, password);
    }

    @Click(R.id.tvLogin)
    void onTvLoginClicked() {
        ((AuthActivity) getActivity()).gotoLogin();
    }

    private void doRegister(String fName, String lName, String email, String password) {
        DialogUtils.showProgressDialog(getContext(), "Đang đăng ký...");
        Utils.hideKeyboard(getActivity());
        apiService.createUser(fName, lName, email, password).enqueue(new Callback<ResponseWrapper<UserResponse>>() {
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
                        List<String> errs = response.body().getData().getErrors();
                        StringBuilder sb = new StringBuilder();
                        if (errs != null) {
                            for (String s : errs) {
                                sb.append(s).append("\n");
                            }
                        }
                        DialogUtils.showMessageDialog(getContext(), sb.toString());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<UserResponse>> call, Throwable t) {
                DialogUtils.dismissProgressDialog();
            }
        });
    }

    private boolean validate() {
        EmptyValidator emptyValidator = new EmptyValidator("Không được để trống");
        LengthValidator lengthValidator = new LengthValidator("Mật khẩu phải lớn hơn 6 ký tự", 6, Integer.MAX_VALUE);
        EmailValidator emailValidator = new EmailValidator("Email không đúng định dạng");

        if (!emptyValidator.isValid(edtFirstname)) {
            edtFirstname.setError(emptyValidator.getErrorMessage());
            return false;
        }

        if (!emptyValidator.isValid(edtLastname)) {
            edtFirstname.setError(emptyValidator.getErrorMessage());
            return false;
        }

        if (!emptyValidator.isValid(edtEmail)) {
            edtFirstname.setError(emptyValidator.getErrorMessage());
            return false;
        }

        if (!emptyValidator.isValid(edtPassword)) {
            edtFirstname.setError(emptyValidator.getErrorMessage());
            return false;
        }

        if (!lengthValidator.isValid(edtPassword)) {
            edtPassword.setError(lengthValidator.getErrorMessage());
            return false;
        }

        if (!emailValidator.isValid(edtEmail)) {
            edtEmail.setError(emailValidator.getErrorMessage());
            return false;
        }
        return true;
    }
}
