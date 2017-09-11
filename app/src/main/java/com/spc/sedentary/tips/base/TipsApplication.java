package com.spc.sedentary.tips.base;

import android.app.Application;

import com.spc.sedentary.tips.di.component.Appcomponent;
import com.spc.sedentary.tips.di.component.DaggerAppcomponent;
import com.spc.sedentary.tips.di.module.AppModule;

/**
 * Created by spc on 2017/9/9.
 */

public class TipsApplication extends Application {
    private static TipsApplication mInstance;
    private Appcomponent mAppcomponent;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initDagger();
    }

    public static TipsApplication getInst() {
        return mInstance;
    }

    private void initDagger() {
        mAppcomponent = DaggerAppcomponent.
                builder()
                .appModule(new AppModule(this))
                .build();
    }

    public Appcomponent getAppComponent() {
        return mAppcomponent;
    }

}
