package com.chutuan.tukyapp.ui.main.diagnose;


import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.chutuan.tukyapp.R;
import com.chutuan.tukyapp.TuKyApp;
import com.chutuan.tukyapp.TuKyApp_;
import com.chutuan.tukyapp.model.Diagnose;
import com.chutuan.tukyapp.model.Symptom;
import com.chutuan.tukyapp.network.response.HistoryResponse;
import com.chutuan.tukyapp.network.response.ResponseWrapper;
import com.chutuan.tukyapp.network.services.ApiService;
import com.chutuan.tukyapp.ui.BaseFragment;
import com.chutuan.tukyapp.utils.DialogUtils;
import com.chutuan.tukyapp.utils.GsonUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EFragment(R.layout.diagnose_fragment)
public class DiagnoseFragment extends BaseFragment {
    @ViewById(R.id.rvSymptoms)
    RecyclerView rvSymptoms;

    @Inject
    ApiService apiService;

    @AfterViews
    void afterViews() {
        TuKyApp_.getInstance().getAppComponent().inject(this);
        rvSymptoms.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSymptoms.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rvSymptoms.setHasFixedSize(true);
        SymptomsAdapter adapter = new SymptomsAdapter();
        rvSymptoms.setAdapter(adapter);
    }

    @Click(R.id.btnProcess)
    void onProcessClicked() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < rvSymptoms.getAdapter().getItemCount(); i++) {
            SymptomsAdapter.SymptomVH vh = (SymptomsAdapter.SymptomVH) rvSymptoms.findViewHolderForAdapterPosition(i);
            if (vh != null) {
                Symptom s = (Symptom) vh.spChilds.getSelectedItem();
                sb.append(s.getValue());
                if (i != rvSymptoms.getAdapter().getItemCount() - 1) {
                    sb.append(",");
                }
            }
        }

        String token = TuKyApp_.getInstance().getUserPref().accessToken().getOr(null);
        DialogUtils.showProgressDialog(getContext(), "Đang chẩn đoán...");
        apiService.diagnose(token, sb.toString()).enqueue(new Callback<ResponseWrapper<Diagnose>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<Diagnose>> call, Response<ResponseWrapper<Diagnose>> response) {
                DialogUtils.dismissProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {
                        Diagnose diagnose = response.body().getData();
                        if (diagnose != null) {
                            DialogUtils.showMessageDialog(getContext(), "Kết quả", diagnose.getMessage());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<Diagnose>> call, Throwable t) {
                DialogUtils.dismissProgressDialog();
            }
        });

    }
}
