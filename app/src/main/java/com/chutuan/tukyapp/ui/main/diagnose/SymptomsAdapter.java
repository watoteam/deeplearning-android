package com.chutuan.tukyapp.ui.main.diagnose;

import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.chutuan.tukyapp.R;
import com.chutuan.tukyapp.TuKyApp_;
import com.chutuan.tukyapp.model.Symptom;
import com.chutuan.tukyapp.ui.MyRecyclerViewAdapter;
import com.chutuan.tukyapp.utils.GsonUtils;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * Created by Trieu Tuan on 4/25/2017.
 * Copyright (C) SFR Software.
 */

public class SymptomsAdapter extends MyRecyclerViewAdapter<Symptom, SymptomsAdapter.SymptomVH> {
    public SymptomsAdapter() {
        try {
            String json = TuKyApp_.getInstance().getDataPref().symptoms().getOr(null);
            Type type = new TypeToken<ArrayList<Symptom>>() {
            }.getType();
            ArrayList<Symptom> list = GsonUtils.fromJson(json, type);
            setList(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public SymptomsAdapter.SymptomVH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SymptomVH(parent, R.layout.symptom_item);
    }

    public class SymptomVH extends MyRecyclerViewAdapter.ViewHolder<Symptom> {
        public TextView tvLabel;
        public Spinner spChilds;

        public SymptomVH(ViewGroup parent, int resId) {
            super(parent, resId);
            tvLabel = (TextView) itemView.findViewById(R.id.tvLabel);
            spChilds = (Spinner) itemView.findViewById(R.id.spChilds);
        }

        @Override
        public void onBind(Symptom item, int pos) {
            tvLabel.setText(item.getContent());
            if (spChilds.getAdapter() == null) {
                ArrayAdapter<Symptom> spinnerAdapter = new ArrayAdapter<>(itemView.getContext(), android.R.layout.simple_list_item_1, item.getChilds());
                spChilds.setAdapter(spinnerAdapter);
            }
        }
    }
}
