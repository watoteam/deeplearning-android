package com.chutuan.tukyapp.ui.auth;

import com.chutuan.tukyapp.TuKyApp_;
import com.chutuan.tukyapp.model.Symptom;
import com.chutuan.tukyapp.network.response.ResponseWrapper;
import com.chutuan.tukyapp.network.response.SymptomResponse;
import com.chutuan.tukyapp.network.services.ApiService;
import com.chutuan.tukyapp.ui.BaseFragment;
import com.chutuan.tukyapp.ui.main.MainActivity_;
import com.chutuan.tukyapp.utils.DialogUtils;
import com.chutuan.tukyapp.utils.GsonUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Trieu Tuan on 4/26/2017.
 * Copyright (C) SFR Software.
 */
@EFragment
public class BaseLoginRegisterFragment extends BaseFragment {
    @Inject
    protected ApiService apiService;

    @AfterViews
    protected void baseLoginRegAfterView() {
        TuKyApp_.getInstance().getAppComponent().inject(this);
    }

    protected void getSymptoms() {
        DialogUtils.showProgressDialog(getContext(), "Đang lấy dữ liệu...");
        String accessToken = TuKyApp_.getInstance().getUserPref().accessToken().getOr(null);
        apiService.getSymptoms(accessToken).enqueue(new Callback<ResponseWrapper<SymptomResponse>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<SymptomResponse>> call, Response<ResponseWrapper<SymptomResponse>> response) {
                DialogUtils.dismissProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {
                        ArrayList<Symptom> list = response.body().getData().getSymptoms();
                        String json = GsonUtils.toJson(list);
                        TuKyApp_.getInstance().getDataPref().edit().symptoms().put(json).apply();

                        //Next UI
                        MainActivity_.intent(BaseLoginRegisterFragment.this).start();
                        getActivity().finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<SymptomResponse>> call, Throwable t) {
                DialogUtils.dismissProgressDialog();
            }
        });
    }
}
