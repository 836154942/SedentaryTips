package com.spc.sedentary.tips.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.spc.sedentary.tips.R;
import com.spc.sedentary.tips.base.BaseActivity;
import com.spc.sedentary.tips.services.AlarmServices;
import com.spc.sedentary.tips.utils.AlarmUtil;
import com.spc.sedentary.tips.utils.Constant;
import com.spc.sedentary.tips.utils.SpUtil;
import com.spc.sedentary.tips.utils.TLog;
import com.spc.sedentary.tips.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final int TIME_ALARM_POLL = 1 * 60 * 1000; //alarmservice1分钟轮询一次
    public static final int TIME_SELECT_MIN = 2;
    public static final int TIME_SELECT_DEFAULT = 5;
    public static final int TIME_SELECT_MAX = 100;
    public static final int REQUEST_IGNORE_BATTERY_CODE = 99;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        TLog.e("MainActivityMainActivityMainActivity    onCreate");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @OnClick(R.id.fab)
    public void bottomTips(View view) {
        Snackbar.make(view, R.string.main_bottom_tips, Snackbar.LENGTH_LONG)
                .setAction(R.string.main_bottom_to, v -> Toast.makeText(this, "biu biu~", Toast.LENGTH_SHORT).show()).show();
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
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }

            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_camera:
                if (!isIgnoringBatteryOptimizations(this)) {
                    isIgnoreBatteryOption(this);
                    break;
                }

                if (!AlarmUtil.isServiceRunning(this, AlarmServices.class.getName())) {
                    SpUtil.setPrefBoolean(Constant.SP_KEY_IS_STARTED, false);
                    TLog.e("假装运行`~~~~");
                }

                if (SpUtil.getPrefBoolean(Constant.SP_KEY_IS_STARTED, false)) {

                    AlertDialog.Builder overrideBuilder = new AlertDialog.Builder(this);
                    overrideBuilder.setTitle(R.string.over_dialog_title);
                    overrideBuilder.setMessage(R.string.start_override_dialog_message);
                    overrideBuilder.setNegativeButton(R.string.start_override_cancel, null);
                    overrideBuilder.setPositiveButton(R.string.start_override_sure,
                            (dialog, which) -> showStartDialog());
                    overrideBuilder.create().show();
                } else {
                    showStartDialog();
                }
                break;
            case R.id.nav_gallery:
                if (!SpUtil.getPrefBoolean(Constant.SP_KEY_IS_STARTED, false)) {
                    ToastUtil.showToast(R.string.not_start_tips);
                } else {
                    AlertDialog.Builder overBuilder = new AlertDialog.Builder(this);
                    overBuilder.setTitle(R.string.over_dialog_title);
                    overBuilder.setMessage(R.string.over_dialog_message);
                    overBuilder.setNegativeButton(R.string.over_dialog_cancel, null);
                    overBuilder.setPositiveButton(R.string.over_dialog_sure, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AlarmUtil.stopAlarmSericecs();
                            ToastUtil.showToast(R.string.end_tips);
                        }
                    });
                    overBuilder.create().show();
                }
                break;
            case R.id.nav_slideshow:
            case R.id.nav_manage:
                ToastUtil.showToast("正在开发，敬请期待");
                break;
            case R.id.nav_send:
                ToastUtil.showToast("这个很麻烦,多给我点时间~");
                break;
//            case R.id.nav_share:
//                break;
//
            default:
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showStartDialog() {
        ViewGroup dialogView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.content_main, null);
        NumberPicker mNumberPicker = (NumberPicker) dialogView.findViewById(R.id.mNumberPicker);
        mNumberPicker.setMinValue(TIME_SELECT_MIN);
        mNumberPicker.setMaxValue(TIME_SELECT_MAX);
        mNumberPicker.setValue(TIME_SELECT_DEFAULT);
        AlertDialog.Builder startBuild = new AlertDialog.Builder(this);
        startBuild.setTitle(R.string.start_dialog_title);
        startBuild.setView(dialogView);
        startBuild.setNegativeButton(R.string.start_dialog_cancel, null);
        startBuild.setPositiveButton(R.string.start_dialog_sure, (dialog, which) -> {
            TLog.e("选择的时间是  ！" + mNumberPicker.getValue());

            setStatusStart(mNumberPicker.getValue() * 60 * 1000);
        });
        startBuild.show();
    }


    public static boolean isIgnoringBatteryOptimizations(Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        String packageName = activity.getPackageName();
        PowerManager pm = (PowerManager) activity
                .getSystemService(Context.POWER_SERVICE);
        if (pm.isIgnoringBatteryOptimizations(packageName)) {
            return true;
        } else {
            return false;
        }
    }


    //开始提醒
    public void setStatusStart(int intervalMilli) {
        TLog.e("开始时间");
        startService(new Intent(this, AlarmServices.class));
        SpUtil.setSettingLong(Constant.SP_KEY_START_TIME, System.currentTimeMillis());
        SpUtil.setSettingLong(Constant.SP_KEY_TIME_DURING, intervalMilli);
        AlarmUtil.startAlarmServices();
        ToastUtil.showToast(R.string.start_tips);
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
