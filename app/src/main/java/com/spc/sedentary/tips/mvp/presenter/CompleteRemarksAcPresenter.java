package com.spc.sedentary.tips.mvp.presenter;

import com.spc.sedentary.tips.base.BasePresenter;
import com.spc.sedentary.tips.base.TipsApplication;
import com.spc.sedentary.tips.database.service.RemarkService;
import com.spc.sedentary.tips.mvp.entity.RemarkEntity;
import com.spc.sedentary.tips.mvp.iview.CompleteRemarksAcView;
import com.spc.sedentary.tips.utils.Constant;

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

        mCompositeSubscription.add(Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                subscriber.onNext(mServices.deleteAllComplete());
            }
        })
                .subscribeOn(Schedulers.immediate())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o ->mvpView.deleteAllComplete(o)));

    }


    public boolean delete(RemarkEntity remarkEntity) {
        return mServices.delete(remarkEntity);
    }

    public boolean recover(RemarkEntity remarkEntity) {
        remarkEntity.setStatus(Constant.REMARK_STATUS_NORMAL);
        return mServices.update(remarkEntity);
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
