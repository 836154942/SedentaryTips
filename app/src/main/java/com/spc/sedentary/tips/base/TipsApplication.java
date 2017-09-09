package com.spc.sedentary.tips.base;

import android.app.Application;

/**
 * Created by spc on 2017/9/9.
 */

public class TipsApplication extends Application {
    private static TipsApplication mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance =this;
    }

    public TipsApplication getInst() {
        return mInstance;
    }
}
