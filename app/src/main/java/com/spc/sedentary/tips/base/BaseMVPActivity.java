package com.spc.sedentary.tips.base;

import android.os.Bundle;

import com.spc.sedentary.tips.di.aptinject.InjectActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by spc on 2017/9/11.
 */

public abstract class BaseMVPActivity<P extends BasePresenter> extends BaseActivity implements BaseMvpViewInterface {
    @Inject
    protected P mvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        InjectActivity.inject(this);
        mvpPresenter.attachView(this);

    }

    protected abstract int getLayoutId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }

    @Override
    public void showLoading() {
        showProgressDialog();
    }

    @Override
    public void hideLoading() {
        dismissProgressDialog();
    }


}

