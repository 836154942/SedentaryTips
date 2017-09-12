package com.spc.sedentary.tips.utils;

import android.content.Context;
import android.os.Vibrator;

import com.spc.sedentary.tips.base.TipsApplication;

/**
 * Created by spc on 2017/9/10.
 */

public class VibratorUtil {
    public static void vibrate() {
        Vibrator vibrator = (Vibrator) TipsApplication.getInst().getSystemService(Context.VIBRATOR_SERVICE);
        //第一个参数：该数组中第一个元素是等待多长的时间才启动震动， //之后将会是开启和关闭震动的持续时间，单位为毫秒
        //第二个参数：重复震动时在pattern中的索引，如果设置为-1则表示不重复震动
        vibrator.vibrate(new long[]{0, 1500, 1500}, 0);
    }
    public static void stopVibrate(){
        Vibrator vibrator = (Vibrator) TipsApplication.getInst().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.cancel();
    }
}
