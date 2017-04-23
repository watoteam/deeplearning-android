package com.chutuan.tukyapp.network.response;

import com.chutuan.tukyapp.model.User;
import com.google.gson.annotations.SerializedName;


public class UserResponse {
    @SerializedName("user")
    private User user;

    public User getUser() {
        return user;
    }
}
