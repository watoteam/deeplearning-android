package com.chutuan.tukyapp.model;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

public class User {
    @SerializedName("id")
    private int id;

    @Getter
    @SerializedName("first_name")
    private String firstName;

    @Getter
    @SerializedName("last_name")
    private String lastName;

    @Getter
    @SerializedName("email")
    private String email;

    @Getter
    @SerializedName("access_token")
    private String accessToken;

    @Getter
    @SerializedName("avatar")
    private String avatar;

    public String getName() {
        return firstName + " " + lastName;
    }
}
