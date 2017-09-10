package com.spc.sedentary.tips.services;

import android.app.Activity;
import android.app.Notification;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.spc.sedentary.tips.R;
import com.spc.sedentary.tips.utils.Constant;
import com.spc.sedentary.tips.utils.LogFileUtil;
import com.spc.sedentary.tips.utils.SpUtil;
import com.spc.sedentary.tips.utils.TLog;

import java.util.Date;

/**
 * Created by spc on 2017/9/9.
 */

public class TimeJobServices extends JobService {
    public static final int WHAT_TIME_DOING =0x201;
    public static final int WHAT_TIME_OVER =0x202;
    public static final int WHAT_TIPS =0x203;

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if (msg.what == WHAT_TIME_OVER) {
                TLog.e("over ");
                //提示并重新设置时间
                showRestTips(msg);
                jobFinished((JobParameters) msg.obj, false);
                JobScheduler tm = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
                tm.cancel(345);
            } else if (msg.what == WHAT_TIME_DOING) {
                LogFileUtil.appen(new Date().toLocaleString() + "\r\n");
                jobFinished((JobParameters) msg.obj, false);
            }else if (msg.what ==WHAT_TIPS){
//                jobFinished((JobParameters) msg.obj, false);
//                JobScheduler tm = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
//                tm.cancel(345);
            }
            return true;
        }
    });

    private void showRestTips(Message msg) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                TLog.e("!1111111111");
                try {
                    sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Message m = Message.obtain();
                m.obj = msg.obj;
                m.what=WHAT_TIPS;
                handler.sendMessage(m);
            }
        }.start();
    }


    @Override
    public boolean onStartJob(JobParameters params) {
        TLog.e("---->  onStartJob");
        checkTime(params);
        return true;
    }

    private void checkTime(JobParameters params) {
        Message m = Message.obtain();
        m.obj = params;
        long start = SpUtil.getPrefLong(Constant.SP_KEY_START_TIME, 0);
        long during = SpUtil.getPrefLong(Constant.SP_KEY_TIME_DURING, 0);
        long des = start + during;
        if (des < System.currentTimeMillis()) {
            m.what = WHAT_TIME_OVER;
            TLog.e("时间到了  ~！！！！！！！！！！！");
        } else {
            m.what = WHAT_TIME_DOING;
            TLog.e("在记时");
        }
        handler.sendMessageDelayed(m, 5);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TLog.e("---->  onStartCommand");
        Notification.Builder builder = new Notification.Builder(this);
        builder.setTicker("正在运行")
                .setContentText("好好学习")
                .setSmallIcon(R.mipmap.ic_launcher);
        startForeground(111, builder.build());
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        stopForeground(true);
        super.onDestroy();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        TLog.e("======>  onStopJob");
        return false;
    }


//    private void checkTime(Message msg) {
//        long start = SpUtil.getPrefLong(Constant.SP_KEY_START_TIME, 0);
//        long during = SpUtil.getPrefLong(Constant.SP_KEY_TIME_DURING, 0);
//        long des = start + during;
//        if (des < System.currentTimeMillis()) {
//            TLog.e("时间到了  ~！！！！！！！！！！！");
//            jobFinished((JobParameters) msg.obj, false);
//        } else {
//            TLog.e("在记时");
//            LogFileUtil.appen(new Date().toLocaleString() + "\r\n");
//            Message newMessage = msg.obtain();
//            newMessage.obj = (JobParameters) msg.obj;
//            newMessage.what = WHAT_TIME_DOING;
//            handler.sendMessageDelayed(newMessage, 3000);
//        }
//    }
}
