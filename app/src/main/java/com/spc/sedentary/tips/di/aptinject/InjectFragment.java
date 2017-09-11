package com.spc.sedentary.tips.di.aptinject;

import android.support.v4.app.Fragment;
import android.support.v4.util.ArrayMap;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * Created by JokAr on 16/8/6.
 */
public class InjectFragment {
    private static final ArrayMap<String, Object> injectMap = new ArrayMap<>();

    public static void inject(Fragment fragment) {
        String className = fragment.getClass().getName();
        try {
            Object inject = injectMap.get(className);

            if (inject == null) {
                Class<?> aClass = Class.forName(className + "$$InjectFragment");
                inject =  aClass.newInstance();
                injectMap.put(className, inject);
            }
            Method m1 = inject.getClass().getDeclaredMethod("inject",fragment.getClass());
            m1.invoke(inject,fragment);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
