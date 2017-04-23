package com.chutuan.tukyapp.network.response;

import com.google.gson.annotations.SerializedName;


public class ResponseWrapper<T> {
    @SerializedName("success")
    boolean success;

    @SerializedName("status")
    int status;

    @SerializedName("message")
    boolean message;

    @SerializedName("data")
    T data;

    public T getData() {
        return data;
    }

    public boolean isSuccessful() {
        return success;
    }
}
