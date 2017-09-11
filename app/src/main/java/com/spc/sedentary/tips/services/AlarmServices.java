package com.spc.sedentary.tips.services;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.spc.sedentary.tips.R;
import com.spc.sedentary.tips.activity.MainActivity;
import com.spc.sedentary.tips.activity.ShowTipsActivity;
import com.spc.sedentary.tips.utils.Constant;
import com.spc.sedentary.tips.utils.LogFileUtil;
import com.spc.sedentary.tips.utils.NetTest;
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
        TLog.e("AlarmServices ------<<<<<onCreate[[[[?.");
        Notification.Builder builder = new Notification.Builder(this);
        builder.setTicker("正在运行")
                .setContentText("好好学习")
                .setSmallIcon(R.mipmap.ic_launcher);
        Intent goIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, goIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        startForeground(111, builder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TLog.e("alarmServices ===== onStartCommand");
        long start = SpUtil.getPrefLong(Constant.SP_KEY_START_TIME, 0);
        long during = SpUtil.getPrefLong(Constant.SP_KEY_TIME_DURING, 0);
        long des = start + during;
        if (des < System.currentTimeMillis()) {
            TLog.e("时间到了   提醒!");
          startActivity(ShowTipsActivity.buildIntent(this));//到时间提醒
            LogFileUtil.appen(new Date().toLocaleString() + "       " + "假装进行了一次提醒!!!!! \r\n");
            //重新设置开始时间
            SpUtil.setSettingLong(Constant.SP_KEY_START_TIME, System.currentTimeMillis() + Constant.getSleep_milli_time());

        } else {
            TLog.e("在记录时间");
            LogFileUtil.appen(new Date().toLocaleString() + "\r\n");
            NetTest.sendMessageToServer(new Date().toLocaleString() +  "       "+android.os.Build.BRAND + "          \r\n");
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        TLog.e("onDestroyonDestroyonDestroy");
        super.onDestroy();
    }
}


