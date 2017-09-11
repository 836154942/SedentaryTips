package com.spc.sedentary.tips.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.spc.sedentary.tips.di.aptinject.InjectFragment;

import javax.inject.Inject;

/**
 * Created by spc on 2017/9/11.
 */

public  abstract class BaseMvpFragment<P extends BasePresenter> extends BaseFragment implements BaseMvpViewInterface {
    @Inject
    protected P mvpPresenter;

    protected View mRootView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        InjectFragment.inject(this);
        if (mvpPresenter != null)
            mvpPresenter.attachView(this);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void hideLoading() {
        if (getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).dismissProgressDialog();
    }

    @Override
    public void showLoading() {
        if (getActivity() instanceof BaseActivity)
            ((BaseActivity) getActivity()).showProgressDialog();
    }
}
