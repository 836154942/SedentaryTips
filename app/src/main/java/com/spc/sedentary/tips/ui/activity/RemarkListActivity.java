package com.spc.sedentary.tips.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.anno.spc.ActivityInject;
import com.spc.sedentary.tips.R;
import com.spc.sedentary.tips.base.BaseMVPActivity;
import com.spc.sedentary.tips.mvp.iview.RemarkListView;
import com.spc.sedentary.tips.mvp.presenter.RemarkListPresenter;
import com.spc.sedentary.tips.utils.ToastUtil;

import java.util.List;


/**
 * Created by spc on 2017/9/11.
 */

@ActivityInject
public class RemarkListActivity extends BaseMVPActivity<RemarkListPresenter> implements RemarkListView {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_remarklist;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showLoading();
        mvpPresenter.getData();
    }

    public static Intent buildIntent(Context context) {
        return new Intent(context, RemarkListActivity.class);
    }

    @Override
    public void getDataSuccess(List<String> list) {
        dismissProgressDialog();
        ToastUtil.showToast("mvp 模板");

    }
}
