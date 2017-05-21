package com.chutuan.tukyapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Trieu Tuan on 4/26/2017.
 * Copyright (C) SFR Software.
 */

public class Diagnose {
    @Getter
    @SerializedName("symptoms")
    ArrayList<String> symptoms;

    @Getter
    @SerializedName("message")
    String message;

    @Getter
    @Setter
    @SerializedName("per_cent")
    Float percent;

    @Getter
    @SerializedName("advice")
    String advice;

    @Getter
    @SerializedName("created_at")
    String createdAt;
}
