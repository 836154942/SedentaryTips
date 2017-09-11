package com.spc.sedentary.tips.di.module;


import com.spc.sedentary.tips.base.BaseMvpFragment;
import com.spc.sedentary.tips.di.scope.FragmentScope;

import dagger.Module;
import dagger.Provides;

/**
 * Created by spc on 2017/6/9.
 */

@Module
public class FragmentModule {
    private BaseMvpFragment fragment;

    public FragmentModule(BaseMvpFragment fragment) {
        this.fragment = fragment;
    }

    @Provides
    @FragmentScope
    public BaseMvpFragment provideBaseMvpFragment() {
        return fragment;
    }

}
