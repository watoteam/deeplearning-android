package com.chutuan.tukyapp.network.response;

import com.chutuan.tukyapp.model.Symptom;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.Getter;

/**
 * Created by Trieu Tuan on 4/25/2017.
 * Copyright (C) SFR Software.
 */

public class SymptomResponse {
    @Getter
    @SerializedName("symptoms")
    ArrayList<Symptom> symptoms;
}
