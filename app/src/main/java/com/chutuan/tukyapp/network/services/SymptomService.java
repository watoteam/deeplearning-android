package com.chutuan.tukyapp.network.services;

import com.chutuan.tukyapp.TuKyApp_;
import com.chutuan.tukyapp.network.response.ResponseWrapper;
import com.chutuan.tukyapp.network.response.SymptomResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by Trieu Tuan on 4/25/2017.
 * Copyright (C) SFR Software.
 */

public interface SymptomService {
    @GET("symptoms")
    Call<ResponseWrapper<SymptomResponse>> getSymptoms(@Header("Authorization") String access_token);

}
