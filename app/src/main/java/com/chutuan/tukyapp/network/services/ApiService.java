package com.chutuan.tukyapp.network.services;

import com.chutuan.tukyapp.model.Diagnose;
import com.chutuan.tukyapp.network.response.HistoryResponse;
import com.chutuan.tukyapp.network.response.ResponseWrapper;
import com.chutuan.tukyapp.network.response.SymptomResponse;
import com.chutuan.tukyapp.network.response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Trieu Tuan on 4/25/2017.
 * Copyright (C) SFR Software.
 */

public interface ApiService {
    @FormUrlEncoded
    @POST("users")
    Call<ResponseWrapper<UserResponse>> createUser(@Field("first_name") String firstname,
                                                   @Field("last_name") String lastname,
                                                   @Field("email") String email,
                                                   @Field("password") String password);

    @FormUrlEncoded
    @POST("auths/login")
    Call<ResponseWrapper<UserResponse>> login(@Field("email") String email, @Field("password") String password);

    @POST("auths/logout")
    Call<ResponseWrapper<Object>> logout(@Header("Authorization") String token);

    @GET("symptoms")
    Call<ResponseWrapper<SymptomResponse>> getSymptoms(@Header("Authorization") String token);

    @GET("diagnosis/histories")
    Call<ResponseWrapper<HistoryResponse>> getHistory(@Header("Authorization") String token);

    @FormUrlEncoded
    @POST("diagnosis")
    Call<ResponseWrapper<Diagnose>> diagnose(@Header("Authorization") String token, @Field("symptoms") String symptoms);
}
