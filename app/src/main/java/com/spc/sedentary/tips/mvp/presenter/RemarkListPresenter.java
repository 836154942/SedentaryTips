package com.spc.sedentary.tips.mvp.presenter;

import com.spc.sedentary.tips.base.BasePresenter;
import com.spc.sedentary.tips.base.TipsApplication;
import com.spc.sedentary.tips.database.service.RemarkService;
import com.spc.sedentary.tips.mvp.entity.RemarkEntity;
import com.spc.sedentary.tips.mvp.iview.RemarkListView;
import com.spc.sedentary.tips.utils.Constant;
import com.spc.sedentary.tips.utils.TLog;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by spc on 2017/9/11.
 */

public class RemarkListPresenter extends BasePresenter<RemarkListView> {
    RemarkService mService;
    int position = 0;

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


    public void upDatePosition(List<RemarkEntity> list) {
        mvpView.showLoading();
        position = 0;
        mCompositeSubscription.add(Observable.from(list)
                .map(new Func1<RemarkEntity, Boolean>() {
                    @Override
                    public Boolean call(RemarkEntity remarkEntity) {
                        remarkEntity.setPosition(position);
                        position += 1;
                        return mService.update(remarkEntity);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {
                        mvpView.updatePositionSuccess();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        TLog.e("更新玩了一个");
                    }
                }));

    }
}
