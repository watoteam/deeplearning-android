package com.chutuan.tukyapp.ui.main.diagnose;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.chutuan.tukyapp.R;
import com.chutuan.tukyapp.TuKyApp_;
import com.chutuan.tukyapp.model.Diagnose;
import com.chutuan.tukyapp.model.Symptom;
import com.chutuan.tukyapp.network.response.ResponseWrapper;
import com.chutuan.tukyapp.network.response.SymptomResponse;
import com.chutuan.tukyapp.network.services.ApiService;
import com.chutuan.tukyapp.ui.BaseFragment;
import com.chutuan.tukyapp.ui.dialog.ResultDialog;
import com.chutuan.tukyapp.utils.DialogUtils;
import com.chutuan.tukyapp.utils.GsonUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

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

    private SymptomsAdapter symptomsAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.diagnose_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_refresh) {
            getSymptoms();
        }
        return super.onOptionsItemSelected(item);
    }

    private void getSymptoms() {
        DialogUtils.showProgressDialog(getContext(), "Đang lấy dữ liệu...");
        String accessToken = TuKyApp_.getInstance().getUserPref().accessToken().getOr(null);
        apiService.getSymptoms(accessToken).enqueue(new Callback<ResponseWrapper<SymptomResponse>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<SymptomResponse>> call, Response<ResponseWrapper<SymptomResponse>> response) {
                DialogUtils.dismissProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {
                        ArrayList<Symptom> list = response.body().getData().getSymptoms();
                        String json = GsonUtils.toJson(list);
                        TuKyApp_.getInstance().getDataPref().edit().symptoms().put(json).apply();

                        symptomsAdapter.refreshDataSet();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<SymptomResponse>> call, Throwable t) {
                DialogUtils.dismissProgressDialog();
            }
        });
    }

    @AfterViews
    void afterViews() {
        TuKyApp_.getInstance().getAppComponent().inject(this);
        rvSymptoms.setLayoutManager(new LinearLayoutManager(getContext()));
        rvSymptoms.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rvSymptoms.setHasFixedSize(true);

        symptomsAdapter = new SymptomsAdapter();
        rvSymptoms.setAdapter(symptomsAdapter);
        symptomsAdapter.refreshDataSet();
    }

    @Click(R.id.btnProcess)
    void onProcessClicked() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < symptomsAdapter.getItemCount(); i++) {
            try {
                Symptom symptom = symptomsAdapter.getItem(i);
                int selectedChildPos = symptom.selectedChildPos;
                sb.append(symptom.getChilds().get(selectedChildPos).getValue());
                sb.append(",");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String symptoms = sb.toString();
        int lastIndexOfComma = symptoms.lastIndexOf(",");
        if(lastIndexOfComma != -1){
            symptoms = symptoms.substring(0, lastIndexOfComma);
        }

        Log.e("A", symptoms);

        String token = TuKyApp_.getInstance().getUserPref().accessToken().getOr(null);
        DialogUtils.showProgressDialog(getContext(), "Đang chẩn đoán...");
        apiService.diagnose(token, symptoms).enqueue(new Callback<ResponseWrapper<Diagnose>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<Diagnose>> call, Response<ResponseWrapper<Diagnose>> response) {
                DialogUtils.dismissProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {
                        Diagnose diagnose = response.body().getData();
                        if (diagnose != null) {
                            ResultDialog.show(getContext(), diagnose);
                        }
                    } else {
                        DialogUtils.showToast(response.body().getMessage());
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
