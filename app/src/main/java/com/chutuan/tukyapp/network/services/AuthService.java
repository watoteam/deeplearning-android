package com.chutuan.tukyapp.network.services;


import com.chutuan.tukyapp.network.response.ResponseWrapper;
import com.chutuan.tukyapp.network.response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AuthService {
    @FormUrlEncoded
    @POST("auths/login")
    Call<ResponseWrapper<UserResponse>> login(@Field("email") String email,
                                              @Field("password") String password);
}
