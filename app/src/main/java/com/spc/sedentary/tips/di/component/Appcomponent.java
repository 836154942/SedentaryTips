package com.spc.sedentary.tips.di.component;


import com.spc.sedentary.tips.di.module.AppModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by spc on 2017/6/8.
 * application的 component 相当于base
 */

@Singleton
@Component(modules = AppModule.class)
public interface Appcomponent {

}
