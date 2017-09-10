package com.spc.sedentary.tips.utils;

import android.widget.Toast;

import com.spc.sedentary.tips.base.TipsApplication;

/**
 * Created by spc on 2017/9/10.
 */

public class ToastUtil {
    public static Toast mToast;

    public static void showToast(String msg) {
        if (mToast == null) {
            mToast = Toast.makeText(TipsApplication.getInst(), "", Toast.LENGTH_SHORT);
        }
        mToast.setText(msg);
        mToast.show();
    }
    public static void showToast(int id) {
        if (mToast == null) {
            mToast = Toast.makeText(TipsApplication.getInst(), "", Toast.LENGTH_SHORT);
        }
        mToast.setText(id);
        mToast.show();
    }
}
