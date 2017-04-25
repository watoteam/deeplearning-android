package com.chutuan.tukyapp.network.response;

import com.chutuan.tukyapp.model.User;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Getter;


public class UserResponse {
    @Getter
    @SerializedName("user")
    private User user;

    @Getter
    @SerializedName("email")
    List<String> errors;
}
