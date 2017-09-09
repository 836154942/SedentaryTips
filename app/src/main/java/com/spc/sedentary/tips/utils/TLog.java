package com.spc.sedentary.tips.utils;

import android.util.Log;

/**
 * Created by spc on 2017/9/9.
 */

public class TLog {
    public final static String TAG = "sedentaryLog";

    public static void e(String string) {
        Log.e(TAG, string);
    }

    public static void d(String string) {
        Log.d(TAG, string);
    }
}
