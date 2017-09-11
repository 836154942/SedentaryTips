package com.process;


import com.squareup.javapoet.ClassName;

/**
 * Created by spc on 2017/6/8.
 */
public class TypeUtil {
    /**
     * ?????
     */
    public static final String MAIN_PROJECT_PACKAGE_NAME = "com.spc.sedentary.tips";
    /**
     * activity ????
     */
    public static final String MAIN_ACTIVITY_PATH = MAIN_PROJECT_PACKAGE_NAME + ".ui.activity";
    public static final String MAIN_FRAGMENT_PATH = MAIN_PROJECT_PACKAGE_NAME + ".ui.fragment";

    /**
     * app???component ???? ????  ??
     */
    public static final String APP_Component_Name = "appcomponent";
    public static final String APP_Component_Name_Capital = "Appcomponent";
    public static final String APP_COMPONENT_PATH = MAIN_PROJECT_PACKAGE_NAME + ".di.component." + APP_Component_Name_Capital;
    /**
     * activity?Module ???? ????
     */
    public static final String APP_ActivityModule_Name = "activityModule";
    public static final String APP_ActivityModule_Name_Capital = "ActivityModule";
    public static final String ACTIVITY_MODULE_PATH = MAIN_PROJECT_PACKAGE_NAME + ".di.module." + APP_ActivityModule_Name_Capital;

    /**
     * fragment module
     */
    public static final String APP_FragmentModule_Name = "fragmentModule";
    public static final String APP_FragmentModule_Name_Capital = "FragmentModule";
    public static final String FRAGMENT_MODULE_PATH = MAIN_PROJECT_PACKAGE_NAME + ".di.module." + APP_FragmentModule_Name_Capital;


    /**
     * ??application????App???  Component???
     */
    public static final String APPCOMPONENT_PROVIDE_PATH = MAIN_PROJECT_PACKAGE_NAME + ".base.TipsApplication.getInst().getAppComponent()";

    /**
     * cope ???
     */
    public static final ClassName ACTIVITY_SCOPE_CLASSNAME = ClassName.get(MAIN_PROJECT_PACKAGE_NAME + ".di.scope", "ActivityScope");
    public static final ClassName FRAGMENT_SCOPE_CLASSNAME = ClassName.get(MAIN_PROJECT_PACKAGE_NAME + ".di.scope", "FragmentScope");

    /**
     * ???????
     */
    public static final String METHOD_NAME = "inject";

    public static final String ANNOTATION_PATH ="com.anno.spc.ActivityInject";
    public static final String ANNOTATION_FRAGMENT_PATH = "com.anno.spc.FragmentInject";
//    public static final ClassName INJET_NAME = ClassName.get("com.example.injectlib", "Inject");
}
