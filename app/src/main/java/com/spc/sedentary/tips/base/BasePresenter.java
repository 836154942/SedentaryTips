package com.spc.sedentary.tips.base;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by spc on 2017/9/11.
 */

public class BasePresenter <V extends BaseMvpViewInterface>  {
    protected V mvpView;
    protected CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    public void attachView(V mvpView) {
        this.mvpView = mvpView;
    }


    public void detachView() {
        this.mvpView = null;
        onUnsubscribe();
    }


    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }
}

