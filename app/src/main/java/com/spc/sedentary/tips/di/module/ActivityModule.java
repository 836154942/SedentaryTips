package com.spc.sedentary.tips.di.module;


import com.spc.sedentary.tips.base.BaseMVPActivity;
import com.spc.sedentary.tips.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by spc on 2017/6/8.
 */
@Module
public class ActivityModule {
    BaseMVPActivity activity;

    public ActivityModule(BaseMVPActivity activity) {
        this.activity = activity;
    }

    @Provides
    @ActivityScope
    public BaseMVPActivity provideBindMobActivity() {
        return activity;
    }

}
