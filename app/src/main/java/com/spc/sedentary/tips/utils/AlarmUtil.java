package com.spc.sedentary.tips.utils;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;

import com.spc.sedentary.tips.base.TipsApplication;
import com.spc.sedentary.tips.services.AlarmServices;

import java.util.List;

/**
 * Created by spc on 2017/9/10.
 */

public class AlarmUtil {

    //开始学习
    public static void startAlarmServices() {
        AlarmManager alarmManager = (AlarmManager) TipsApplication.getInst().getSystemService(Service.ALARM_SERVICE);
        Intent intent = new Intent();
        intent.setClass(TipsApplication.getInst(), AlarmServices.class);
        PendingIntent pendingIntent = PendingIntent.
                getService(TipsApplication.getInst(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), Constant.get_time_interval(), pendingIntent);
        SpUtil.setPrefBoolean(Constant.SP_KEY_IS_STARTED,true);
    }

    //结束学习
    public static void stopAlarmSericecs() {
        SpUtil.setPrefBoolean(Constant.SP_KEY_IS_STARTED,false);
        PendingIntent pendingIntent = PendingIntent.getService(TipsApplication.getInst(), 0, new Intent(TipsApplication.getInst(), AlarmServices.class),
                PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) TipsApplication.getInst().getSystemService(Service.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }


    //判断服务正在运行.  正在学习?
    public static boolean isServiceRunning(Context mContext, String className) {
        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager)
                mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager.getRunningServices(200);

        if (!(serviceList.size()>0)) {
            return false;
        }

        for (int i=0; i<serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }
}
