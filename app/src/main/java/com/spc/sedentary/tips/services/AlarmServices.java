package com.spc.sedentary.tips.services;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import com.spc.sedentary.tips.R;
import com.spc.sedentary.tips.utils.AlarmUtil;
import com.spc.sedentary.tips.utils.Constant;
import com.spc.sedentary.tips.utils.LogFileUtil;
import com.spc.sedentary.tips.utils.SpUtil;
import com.spc.sedentary.tips.utils.TLog;

import java.util.Date;

/**
 * Created by spc on 2017/9/10.
 */

public class AlarmServices extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        TLog.e("onCreate[[[[?.");
        Notification.Builder builder = new Notification.Builder(this);
        builder.setTicker("正在运行")
                .setContentText("好好学习")
                .setSmallIcon(R.mipmap.ic_launcher);
        startForeground(111, builder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TLog.e("alarmServices ===== onStartCommand");
        long start = SpUtil.getPrefLong(Constant.SP_KEY_START_TIME, 0);
        long during = SpUtil.getPrefLong(Constant.SP_KEY_TIME_DURING, 0);
        long des = start + during;
        if (des < System.currentTimeMillis()) {
            TLog.e("时间到了  结束闹钟 ~！！！！！！！！！！！");
            AlarmUtil.stopAlarmSericecs();
        } else {
            TLog.e("在记时");
            LogFileUtil.appen(new Date().toLocaleString() + "\r\n");
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        TLog.e("onDestroyonDestroyonDestroy");
        super.onDestroy();
    }
}
