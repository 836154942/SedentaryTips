package com.spc.sedentary.tips.services;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.spc.sedentary.tips.utils.TLog;

/**
 * Created by spc on 2017/9/9.
 */

public class TimeJobServices extends JobService {

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Toast.makeText(TimeJobServices.this, "MyJobService", Toast.LENGTH_SHORT).show();
//            JobParameters param = (JobParameters) msg.obj;
//            handler.sendEmptyMessageDelayed(0x11, 1500);
            TLog.e("---->  handleMessage");
            return true;
        }
    });

    @Override
    public boolean onStartJob(JobParameters params) {
        TLog.e("---->  onStartJob");
//        NetTest.sendMessageToServer("这事测试asdadasfasdfasdfas");
        Message m = Message.obtain();
        m.obj = params;
        handler.sendMessage(m);
        return true;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        TLog.e("---->  onStartCommand");
        return START_STICKY;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        TLog.e("======>  onStopJob");
        return false;
    }
}
