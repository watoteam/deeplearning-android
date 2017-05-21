package com.chutuan.tukyapp.ui.main.history;


import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.chutuan.tukyapp.R;
import com.chutuan.tukyapp.TuKyApp_;
import com.chutuan.tukyapp.model.Diagnose;
import com.chutuan.tukyapp.network.response.HistoryResponse;
import com.chutuan.tukyapp.network.response.ResponseWrapper;
import com.chutuan.tukyapp.network.services.ApiService;
import com.chutuan.tukyapp.ui.BaseFragment;
import com.chutuan.tukyapp.utils.DialogUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@EFragment(R.layout.history_fragment)
public class HistoryFragment extends BaseFragment {
    @ViewById(R.id.rvHistory)
    RecyclerView rvHistory;

    @ViewById(R.id.swRefresh)
    SwipeRefreshLayout swRefresh;

    @Inject
    ApiService apiService;

    private HistoryAdapter mAdapter;


    @AfterViews
    void afterViews() {
        TuKyApp_.getInstance().getAppComponent().inject(this);

        rvHistory.setLayoutManager(new LinearLayoutManager(getContext()));
        rvHistory.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rvHistory.setHasFixedSize(true);
        mAdapter = new HistoryAdapter();
        rvHistory.setAdapter(mAdapter);

        update();
        swRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                update();
            }
        });
    }

    private void update() {
        swRefresh.setRefreshing(true);

        String token = TuKyApp_.getInstance().getUserPref().accessToken().getOr(null);
        apiService.getHistory(token).enqueue(new Callback<ResponseWrapper<HistoryResponse>>() {
            @Override
            public void onResponse(Call<ResponseWrapper<HistoryResponse>> call, Response<ResponseWrapper<HistoryResponse>> response) {
                swRefresh.setRefreshing(false);
                if (response.isSuccessful()) {
                    if (response.body().isSuccess()) {
                        ArrayList<Diagnose> histories = response.body().getData().getHistories();
                        mAdapter.setList(histories);
                    }else{
                        DialogUtils.showToast(response.body().getMessage());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseWrapper<HistoryResponse>> call, Throwable t) {
                swRefresh.setRefreshing(false);
            }
        });
    }
}
