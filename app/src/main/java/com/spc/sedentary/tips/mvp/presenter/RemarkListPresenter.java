package com.spc.sedentary.tips.mvp.presenter;

import com.spc.sedentary.tips.base.BasePresenter;
import com.spc.sedentary.tips.base.TipsApplication;
import com.spc.sedentary.tips.database.service.RemarkService;
import com.spc.sedentary.tips.mvp.entity.RemarkEntity;
import com.spc.sedentary.tips.mvp.iview.RemarkListView;
import com.spc.sedentary.tips.utils.Constant;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by spc on 2017/9/11.
 */

public class RemarkListPresenter extends BasePresenter<RemarkListView> {
    RemarkService mService;

    @Inject
    public RemarkListPresenter() {
        mService = new RemarkService(TipsApplication.getInst());
    }

    public void getData() {
        mvpView.showLoading();
        mCompositeSubscription.add(Observable.create(new Observable.OnSubscribe<List<RemarkEntity>>() {
            @Override
            public void call(Subscriber<? super List<RemarkEntity>> subscriber) {
                List<RemarkEntity> list = mService.queryNormalAll();
                subscriber.onNext(list);
            }
        }).subscribeOn(Schedulers.io())
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
                        mvpView.getDataSuccess(remarkEntities);
                    }
                }));

    }


    public void updateComplete(RemarkEntity entity) {
        entity.setStatus(Constant.REMARK_STATUS_COMPLETE);
        mService.update(entity);
    }
}
