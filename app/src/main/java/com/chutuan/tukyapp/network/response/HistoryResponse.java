package com.chutuan.tukyapp.network.response;

import com.chutuan.tukyapp.model.Diagnose;
import com.chutuan.tukyapp.model.Symptom;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.Getter;

/**
 * Created by Trieu Tuan on 4/26/2017.
 * Copyright (C) SFR Software.
 */

public class HistoryResponse {
    @Getter
    @SerializedName("histories")
    ArrayList<Diagnose> histories;
}
