package com.spc.sedentary.tips.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;

import com.spc.sedentary.tips.R;
import com.spc.sedentary.tips.base.BaseActivity;
import com.spc.sedentary.tips.utils.VibratorUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by spc on 2017/9/10.
 */

public class ShowTipsActivity extends BaseActivity {
    @BindView(R.id.mButtonOK)
    Button mButtonOK;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_show_tips;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON);
        super.onCreate(savedInstanceState);
        VibratorUtil.vibrate();
    }

    @OnClick(R.id.mButtonOK)
    public void onViewClicked() {
        VibratorUtil.stopVibrate();
    }

    public static Intent buildIntent(Context context) {
        Intent intent = new Intent(context, ShowTipsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        return intent;
    }

    @Override
    protected void onDestroy() {
        VibratorUtil.stopVibrate();
        super.onDestroy();
    }
}
