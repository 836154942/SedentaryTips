package com.spc.sedentary.tips.mvp.presenter;

import com.spc.sedentary.tips.base.BasePresenter;
import com.spc.sedentary.tips.mvp.iview.RemarkListView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by spc on 2017/9/11.
 */

public class RemarkListPresenter extends BasePresenter<RemarkListView> {
    @Inject
    public RemarkListPresenter() {
    }

    public void getData() {
        mCompositeSubscription.add(Observable.timer(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(l -> mvpView.getDataSuccess(null)));
    }
}
