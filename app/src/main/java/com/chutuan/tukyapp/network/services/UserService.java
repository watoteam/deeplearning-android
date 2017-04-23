package com.chutuan.tukyapp.network.services;

import com.chutuan.tukyapp.network.response.ResponseWrapper;
import com.chutuan.tukyapp.network.response.UserResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @FormUrlEncoded
    @POST("users")
    Call<ResponseWrapper<UserResponse>> createUser(@Field("first_name") String fName,
                                                   @Field("first_name") String lName,
                                                   @Field("first_name") String email,
                                                   @Field("first_name") String password);


}
