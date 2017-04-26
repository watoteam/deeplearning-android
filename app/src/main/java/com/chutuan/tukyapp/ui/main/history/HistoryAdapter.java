package com.chutuan.tukyapp.ui.main.history;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.chutuan.tukyapp.R;
import com.chutuan.tukyapp.model.Diagnose;
import com.chutuan.tukyapp.custom.MyRecyclerViewAdapter;
import com.chutuan.tukyapp.utils.Utils;
import com.chutuan.tukyapp.custom.MyListView;

/**
 * Created by Trieu Tuan on 4/26/2017.
 * Copyright (C) SFR Software.
 */

public class HistoryAdapter extends MyRecyclerViewAdapter<Diagnose, HistoryAdapter.VH> {
    @Override
    public HistoryAdapter.VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return new VH(parent, R.layout.history_item);
    }

    public class VH extends MyRecyclerViewAdapter.ViewHolder<Diagnose> {
        private TextView tvDate;
        private MyListView lvSymptoms;
        private TextView tvMessage;
        private TextView tvAdvice;
        private ArrayAdapter<String> symptomAdapter;
        private TextView btnToggle;

        public VH(ViewGroup parent, int resId) {
            super(parent, resId);
            tvDate = (TextView) itemView.findViewById(R.id.tvDate);
            lvSymptoms = (MyListView) itemView.findViewById(R.id.lvSymptoms);
            tvMessage = (TextView) itemView.findViewById(R.id.tvMessage);
            tvAdvice = (TextView) itemView.findViewById(R.id.tvAdvice);
            btnToggle = (TextView) itemView.findViewById(R.id.btnToggle);

            btnToggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (lvSymptoms.getVisibility() == View.VISIBLE) {
                        lvSymptoms.setVisibility(View.GONE);
                        return;
                    }
                    lvSymptoms.setVisibility(View.VISIBLE);
                }
            });
        }

        @Override
        public void onBind(Diagnose item, int pos) {
            tvDate.setText(Utils.formatTimestamp(item.getCreatedAt()));
            tvMessage.setText(item.getMessage());
            tvAdvice.setText(item.getAdvice());

            //TextColor
            if (item.getPercent() < 50) {
                tvMessage.setTextColor(Color.parseColor("#009C6E"));
            } else if (item.getPercent() >= 50 && item.getPercent() < 100) {
                tvMessage.setTextColor(Color.parseColor("#FFA022"));
            } else if (item.getPercent() >= 100) {
                tvMessage.setTextColor(Color.parseColor("#F04242"));
            }

            if (symptomAdapter == null) {
                symptomAdapter = new ArrayAdapter<>(itemView.getContext(), android.R.layout.simple_list_item_1, item.getSymptoms());
                lvSymptoms.setAdapter(symptomAdapter);
            }
        }
    }
}
