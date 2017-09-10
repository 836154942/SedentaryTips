package com.spc.sedentary.tips.services;

import android.app.job.JobParameters;
import android.app.job.JobService;

import com.spc.sedentary.tips.utils.TLog;

/**
 * Created by spc on 2017/9/10.
 */

public class CheckAlarmServices extends JobService {
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        TLog.e("onStartJob  onStartJob");
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return false;
    }
}
