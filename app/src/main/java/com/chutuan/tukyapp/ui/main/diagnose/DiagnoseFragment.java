package com.chutuan.tukyapp.ui.main.diagnose;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.chutuan.tukyapp.R;
import com.chutuan.tukyapp.model.Symptom;
import com.chutuan.tukyapp.ui.BaseFragment;
import com.chutuan.tukyapp.utils.GsonUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

@EFragment(R.layout.diagnose_fragment)
public class DiagnoseFragment extends BaseFragment {
    @ViewById(R.id.rvSymptoms)
    RecyclerView rvSymptoms;

    @AfterViews
    void afterViews() {
        rvSymptoms.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSymptoms.setHasFixedSize(true);
        SymptomsAdapter adapter = new SymptomsAdapter();
        rvSymptoms.setAdapter(adapter);


    }

    @Click(R.id.btnProcess)
    void onProcessClicked() {
        ArrayList<Symptom> selecteds = new ArrayList<>();

        for (int i = 0; i < rvSymptoms.getAdapter().getItemCount(); i++) {
            SymptomsAdapter.SymptomVH vh = (SymptomsAdapter.SymptomVH) rvSymptoms.findViewHolderForAdapterPosition(i);
            if (vh != null) {
                Symptom s = (Symptom) vh.spChilds.getSelectedItem();
                selecteds.add(s);
            }
        }

        Log.e("A", GsonUtils.toJson(selecteds));
    }
}
