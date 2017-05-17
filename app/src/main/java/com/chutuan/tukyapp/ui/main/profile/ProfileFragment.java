package com.chutuan.tukyapp.ui.main.profile;

import android.widget.TextView;

import com.chutuan.tukyapp.R;
import com.chutuan.tukyapp.TuKyApp_;
import com.chutuan.tukyapp.model.User;
import com.chutuan.tukyapp.ui.BaseFragment;
import com.chutuan.tukyapp.utils.GsonUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Trieu Tuan on 5/17/2017.
 * Copyright (C) SFR Software.
 */

@EFragment(R.layout.profile_fragment)
public class ProfileFragment extends BaseFragment {
    @ViewById(R.id.tvFullname)
    TextView tvFullname;

    @ViewById(R.id.tvEmail)
    TextView tvEmail;

    @AfterViews
    void afterViews() {
        String userJson = TuKyApp_.getInstance().getUserPref().currentUser().getOr(null);
        if (userJson == null) {
            return;
        }
        User user = GsonUtils.fromJson(userJson, User.class);
        tvFullname.setText(user.getName());
        tvEmail.setText(user.getEmail());
    }

    @Click(R.id.llChangePassword)
    void onLayoutChangePasswordClicked() {
        ChangePasswordActivity_.intent(this).start();
    }
}
