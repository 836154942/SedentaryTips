package com.spc.sedentary.tips.mvp.presenter;

import com.spc.sedentary.tips.base.BasePresenter;
import com.spc.sedentary.tips.base.TipsApplication;
import com.spc.sedentary.tips.database.service.RemarkService;
import com.spc.sedentary.tips.mvp.entity.RemarkEntity;
import com.spc.sedentary.tips.mvp.iview.CompleteRemarksAcView;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by spc on 2017/9/13.
 */

public class CompleteRemarksAcPresenter extends BasePresenter<CompleteRemarksAcView> {
    RemarkService mServices;

    @Inject
    public CompleteRemarksAcPresenter() {
        mServices = new RemarkService(TipsApplication.getInst());
    }


    public void cleanCompleteRemarks() {
//        mServices.
    }

    public void loadAllCompleteRemarks() {
        mvpView.showLoading();
        mCompositeSubscription.add(Observable.create(new Observable.OnSubscribe<List<RemarkEntity>>() {
            @Override
            public void call(Subscriber<? super List<RemarkEntity>> subscriber) {
                List<RemarkEntity> list = mServices.queryCompleteAll();
                subscriber.onNext(list);
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<RemarkEntity>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mvpView.hideLoading();
                    }

                    @Override
                    public void onNext(List<RemarkEntity> remarkEntities) {
                        mvpView.hideLoading();
                        mvpView.getAllCompleteData(remarkEntities);
                    }
                }));
    }
}
