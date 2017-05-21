package com.chutuan.tukyapp.ui.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chutuan.tukyapp.R;
import com.chutuan.tukyapp.model.Diagnose;
import com.chutuan.tukyapp.utils.GsonUtils;
import com.github.lzyzsd.circleprogress.CircleProgress;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by alext on 21/05/2017.
 */

public class ResultDialog extends AlertDialog {

    private ResultDialog(Context context, Diagnose result) {
        super(context);

        View v = LayoutInflater.from(context).inflate(R.layout.result_dialog, (ViewGroup) null);
        setView(v);

        CircleProgress pvResult = (CircleProgress) v.findViewById(R.id.pvResult);
        TextView tvAdvice = (TextView) v.findViewById(R.id.tvAdvice);
        TextView tvResult = (TextView) v.findViewById(R.id.tvResult);

        if (result.getPercent() < 50) {
            tvResult.setTextColor(Color.parseColor("#009C6E"));
            pvResult.setFinishedColor(Color.parseColor("#009C6E"));
        } else if (result.getPercent() >= 50 && result.getPercent() < 100) {
            tvResult.setTextColor(Color.parseColor("#FFA022"));
            pvResult.setFinishedColor(Color.parseColor("#FFA022"));
        } else if (result.getPercent() >= 100) {
            tvResult.setTextColor(Color.parseColor("#F04242"));
            pvResult.setFinishedColor(Color.parseColor("#F04242"));
        }

        pvResult.setProgress(result.getPercent().intValue());
        tvAdvice.setText(result.getAdvice());
        tvResult.setText(result.getMessage());

        setButton(BUTTON_POSITIVE, "OK", new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        setTitle("Kết quả chẩn đoán");
    }

    public static void show(Context context, Diagnose result){
        ResultDialog resultDialog = new ResultDialog(context, result);
        resultDialog.show();
    }
}
