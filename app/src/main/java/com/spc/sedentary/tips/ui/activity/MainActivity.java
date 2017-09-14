package com.spc.sedentary.tips.ui.activity;

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
import android.widget.TextView;
import android.widget.Toast;

import com.spc.sedentary.tips.R;
import com.spc.sedentary.tips.base.BaseActivity;
import com.spc.sedentary.tips.services.AlarmServices;
import com.spc.sedentary.tips.utils.AlarmUtil;
import com.spc.sedentary.tips.utils.Constant;
import com.spc.sedentary.tips.utils.SpUtil;
import com.spc.sedentary.tips.utils.TLog;
import com.spc.sedentary.tips.utils.TimeUtils;
import com.spc.sedentary.tips.utils.ToastUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static int TIME_SELECT_MIN = 1;//时间选择器最小值
    public static int TIME_SELECT_DEFAULT = 1;//时间选择器默认
    public static int TIME_SELECT_MAX = 100;//时间选择器最大值
    public static int REQUEST_IGNORE_BATTERY_CODE = 0x999;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.mTvStatus)
    TextView mTvStatus;
    @BindView(R.id.mTvStudiedTime)
    TextView mTvStudiedTime;
    @BindView(R.id.mTvNextSleepTime)
    TextView mTvNextSleepTime;
    @BindView(R.id.mTvStudyStatusName)
    TextView mTvStudyStatusName;
    @BindView(R.id.mTvSleepStatusName)
    TextView mTvSleepStatusName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        TLog.e("MainActivityMainActivityMainActivity    onCreate");
    }

    @Override
    protected void onResume() {
        super.onResume();
        upDateView();
        mCompositeSubscription = new CompositeSubscription();
        mCompositeSubscription.add(Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(l -> upDateView()));
    }

    private void upDateView() {
        if (AlarmUtil.isServiceRunning(this, AlarmServices.class.getName())) {
            mTvStatus.setText("正在学习");
            long startTime = SpUtil.getPrefLong(Constant.SP_KEY_START_TIME, 0);
            long duringTime = SpUtil.getPrefLong(Constant.SP_KEY_TIME_DURING, 0);
            long endTime = startTime + duringTime;

            if (System.currentTimeMillis() - startTime < 0) {
                //是再休息中
                mTvStatus.setText("休息中");
                mTvStudiedTime.setText(TimeUtils.countdownTime(startTime, System.currentTimeMillis()));
                mTvStudyStatusName.setText("本次已经休息 ");
            } else {
                //是学习中
                mTvStatus.setText("正在学习中");
                mTvStudyStatusName.setText("本次已经学习 ");
                mTvStudiedTime.setText(TimeUtils.countdownTime(System.currentTimeMillis(), startTime));
            }

            if (endTime - System.currentTimeMillis() < 0) {
                mTvSleepStatusName.setText("稍等噢，马上就能休息~ ");
                mTvNextSleepTime.setText("(超时" + TimeUtils.countdownTime(System.currentTimeMillis(), endTime) + "）");
            } else {
                mTvSleepStatusName.setText("距离下次休息 ");
                mTvNextSleepTime.setText(TimeUtils.countdownTime(endTime, System.currentTimeMillis()));
            }

        } else {
            mTvStatus.setText("还没有开始学习");
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


    @OnClick(R.id.fab)
    public void bottomTips(View view) {
        Snackbar.make(view, R.string.main_bottom_tips, Snackbar.LENGTH_LONG)
                .setAction("TO  " + SpUtil.getPrefString(Constant.SP_KEY_DISPLAY_NAME,
                        getString(R.string.pref_default_display_name)), v -> Toast.makeText(this, "biu biu~", Toast.LENGTH_SHORT).show()).show();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        //设置抽屉效果
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
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
            case R.id.start_study:
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
            case R.id.end_study:
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
                            mTvStatus.setText("已经结束");
                            stopService(new Intent(MainActivity.this,AlarmServices.class));

                        }
                    });
                    overBuilder.create().show();
                }
                break;
            case R.id.nav_slideshow:
                ToastUtil.showToast("正在开发，敬请期待");
                break;
            case R.id.remark_list:
                startActivity(RemarkListActivity.buildIntent(this));
                break;
            case R.id.remark_done:
                startActivity(CompleteRemarksActivity.buildIntent(this));
                break;
            case R.id.nav_manage:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
            case R.id.nav_send:
                ToastUtil.showToast("这个超麻烦,多给我点时间~");
                startActivity(ShowTipsActivity.buildIntent(this));
                break;

            default:
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showStartDialog() {
        ViewGroup dialogView = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.dialog_start, null);
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
        upDateView();
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
