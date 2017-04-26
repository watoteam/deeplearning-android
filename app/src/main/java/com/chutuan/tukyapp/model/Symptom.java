package com.chutuan.tukyapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.Getter;

public class Symptom {
    @Getter
    @SerializedName("content")
    private String content;

    @Getter
    @SerializedName("value")
    private int value;

    @Getter
    @SerializedName("attributes")
    ArrayList<Symptom> childs;

    public Symptom(String content, int value) {
        this.content = content;
        this.value = value;
    }

    @Override
    public String toString() {
        return content;
    }
}
