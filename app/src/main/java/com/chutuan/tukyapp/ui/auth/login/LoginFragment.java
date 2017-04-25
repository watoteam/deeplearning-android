package com.chutuan.tukyapp.ui.auth.login;

import android.widget.EditText;

import com.chutuan.tukyapp.R;
import com.chutuan.tukyapp.TuKyApp_;
import com.chutuan.tukyapp.model.Symptom;
import com.chutuan.tukyapp.model.User;
import com.chutuan.tukyapp.network.response.ResponseWrapper;
import com.chutuan.tukyapp.network.response.SymptomResponse;
import com.chutuan.tukyapp.network.response.UserResponse;
import com.chutuan.tukyapp.network.services.AuthService;
import com.chutuan.tukyapp.network.services.SymptomService;
import com.chutuan.tukyapp.ui.BaseFragment;
import com.chutuan.tukyapp.ui.main.MainActivity_;
import com.chutuan.tukyapp.utils.DialogUtils;
import com.chutuan.tukyapp.utils.GsonUtils;
import com.chutuan.tukyapp.utils.validator.EmailValidator;
import com.chutuan.tukyapp.utils.validator.LengthValidator;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@EFragment(R.layout.login_fragment)
public class LoginFragment extends BaseFragment {
    @Inject
    AuthService authService;

    @Inject
    SymptomService symptomService;

    @ViewById(R.id.edtEmail)
    EditText edtEmail;

    @ViewById(R.id.edtPassword)
    EditText edtPassword;

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
                    if (response.body().isSuccess()) {
                        User user = response.body().getData().getUser();
                        TuKyApp_.getInstance().getUserPref().edit()
                                .isLoggedIn().put(true)
                                .accessToken().put(user.getAccessToken())
                                .currentUser().put(GsonUtils.toJson(user))
                                .apply();
                        getSymptoms();
                    } else {
                        DialogUtils.showToast(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<UserResponse>> call, Throwable t) {

            }
        });

    }

    private void getSymptoms() {
        String accessToken = TuKyApp_.getInstance().getUserPref().accessToken().getOr(null);
        symptomService.getSymptoms(accessToken).enqueue(new Callback<ResponseWrapper<SymptomResponse>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<SymptomResponse>> call, Response<ResponseWrapper<SymptomResponse>> response) {
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {
                        ArrayList<Symptom> list = response.body().getData().getSymptoms();
                        String json = GsonUtils.toJson(list);
                        TuKyApp_.getInstance().getDataPref().edit().symptoms().put(json).apply();

                        //Next UI
                        MainActivity_.intent(LoginFragment.this).start();
                        getActivity().finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<SymptomResponse>> call, Throwable t) {

            }
        });
    }

    private boolean validate() {
        LengthValidator pwdValidator = new LengthValidator("Mật khẩu phải lớn hơn 6 ký tự", 6, Integer.MAX_VALUE);
        EmailValidator emailValidator = new EmailValidator("Email không đúng định dạng");

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
