package com.chutuan.tukyapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

public class Symptom {
    @Getter
    @SerializedName("content")
    private String content;

    @Getter
    @SerializedName("value")
    private int value;

    public int selectedChildPos = 0;

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

    @Override
    public boolean equals(Object obj) {
        if(obj == null) return false;
        if(!(obj instanceof Symptom))return false;
        Symptom other = (Symptom)obj;
        return other.value == this.value && other.content == this.content;
    }

    @Override
    public int hashCode() {
        return value;
    }
}
