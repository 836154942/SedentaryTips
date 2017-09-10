package com.spc.sedentary.tips.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.spc.sedentary.tips.R;
import com.spc.sedentary.tips.base.BaseActivity;
import com.spc.sedentary.tips.services.AlarmServices;
import com.spc.sedentary.tips.services.TimeJobServices;
import com.spc.sedentary.tips.utils.AlarmUtil;
import com.spc.sedentary.tips.utils.Constant;
import com.spc.sedentary.tips.utils.LogFileUtil;
import com.spc.sedentary.tips.utils.SpUtil;
import com.spc.sedentary.tips.utils.TLog;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final int TIME_ALARM_POLL = 1 * 60 * 1000; //alarmservice1分钟轮询一次
    public static final int TIME_JOB_POLL = 1 * 60 * 1000; //JobScheduler1分钟检查一次闹钟

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.mNumberPicker)
    NumberPicker mNumberPicker;

    public static final int REQUEST_IGNORE_BATTERY_CODE = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @OnClick(R.id.fab)
    public void bottomTips(View view) {
        Snackbar.make(view, "久坐记得起来活动下噢~", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        //设置抽屉效果
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        //设置选择器
        mNumberPicker.setMinValue(30);
        mNumberPicker.setMaxValue(60);
        mNumberPicker.setValue(30);

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_camera:
                break;
            case R.id.nav_gallery:
                break;
            case R.id.nav_slideshow:
                break;
            case R.id.nav_manage:
                break;
            case R.id.nav_share:
                break;
            case R.id.nav_send:
                break;
            default:
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public static boolean isIgnoringBatteryOptimizations(Activity activity) {
        String packageName = activity.getPackageName();
        PowerManager pm = (PowerManager) activity
                .getSystemService(Context.POWER_SERVICE);
        if (pm.isIgnoringBatteryOptimizations(packageName)) {
            return true;
        } else {
            isIgnoreBatteryOption(activity);
            return false;
        }
    }

    @OnClick(R.id.mBtnSet)
    public void onViewClicked(View view) {
        if (!isIgnoringBatteryOptimizations(this)) {
            return;
        }
        //当前时间存储sp，在jobservices里面比对时间戳
        SpUtil.setSettingLong(Constant.SP_KEY_START_TIME, System.currentTimeMillis());
        SpUtil.setSettingLong(Constant.SP_KEY_TIME_DURING, 110000);
        TLog.e("开始时间");
        AlarmUtil.startAlarmServices();
//        startService(new Intent(this, TimeJobServices.class));
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
//            JobInfo jobInfo = new JobInfo.Builder(345, new ComponentName(getPackageName(), TimeJobServices.class.getName()))
//                    .setRequiredNetworkType(JobInfo.NETWORK_TYPE_NONE)
//                    .setPeriodic(2000)
//                    .build();
//            int res;
//            if ((res = jobScheduler.schedule(jobInfo)) <= 0) {
//            } else {
//            }
//
//        }
    }


    public static void isIgnoreBatteryOption(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                Intent intent = new Intent();
                String packageName = activity.getPackageName();
                PowerManager pm = (PowerManager) activity.getSystemService(Context.POWER_SERVICE);
                if (!pm.isIgnoringBatteryOptimizations(packageName)) {
//                    intent.setAction(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS);
                    intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                    intent.setData(Uri.parse("package:" + packageName));
                    activity.startActivityForResult(intent, REQUEST_IGNORE_BATTERY_CODE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IGNORE_BATTERY_CODE) {

            }
        } else if (resultCode == RESULT_CANCELED) {
            if (requestCode == REQUEST_IGNORE_BATTERY_CODE) {
                Toast.makeText(this, "请开启忽略电池优化", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
