package com.spc.sedentary.tips.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.spc.sedentary.tips.activity.MainActivity;
import com.spc.sedentary.tips.base.TipsApplication;
import com.spc.sedentary.tips.services.AlarmServices;

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
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), MainActivity.TIME_ALARM_POLL, pendingIntent);
    }

    //结束学习
    public static void stopAlarmSericecs() {
        PendingIntent pendingIntent = PendingIntent.getService(TipsApplication.getInst(), 0, new Intent(TipsApplication.getInst(), AlarmServices.class),
                PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager) TipsApplication.getInst().getSystemService(Service.ALARM_SERVICE);
        alarmManager.cancel(pendingIntent);
    }
}
