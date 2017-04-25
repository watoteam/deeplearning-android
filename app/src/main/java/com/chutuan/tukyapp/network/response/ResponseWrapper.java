package com.chutuan.tukyapp.network.response;

import com.google.gson.annotations.SerializedName;

import lombok.Getter;

public class ResponseWrapper<T> {
    @Getter
    @SerializedName("success")
    private boolean success;

    @Getter
    @SerializedName("status")
    private int status;

    @Getter
    @SerializedName("message")
    private String message;

    @Getter
    @SerializedName("data")
    private T data;

}
