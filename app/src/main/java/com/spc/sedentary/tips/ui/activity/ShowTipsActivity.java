package com.spc.sedentary.tips.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.WindowManager;

import com.spc.sedentary.tips.R;
import com.spc.sedentary.tips.base.BaseActivity;
import com.spc.sedentary.tips.base.TipsApplication;
import com.spc.sedentary.tips.utils.Constant;
import com.spc.sedentary.tips.utils.SpUtil;
import com.spc.sedentary.tips.utils.TextCheckUtil;
import com.spc.sedentary.tips.utils.VibratorUtil;
import com.spc.sedentary.tips.view.PagerLayout;

import butterknife.BindView;

/**
 * Created by spc on 2017/9/10.
 */

public class ShowTipsActivity extends BaseActivity {

    @BindView(R.id.lock_view)
    PagerLayout myLinearLayout;
    Ringtone ringtone;

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
        initView();
        initNotification();
    }

    private void initNotification() {
        if (!SpUtil.getPrefBoolean(Constant.SP_KEY_IS_TIPS_SLEEP, true)) {
            return;
        }


        if (SpUtil.getPrefBoolean(Constant.SP_KEY_IS_VIBRATOR, true)) {
            VibratorUtil.vibrate();
        }
        if (TextCheckUtil.isUseable(SpUtil.getPrefString(Constant.SP_KEY_TIPS_VOICE, ""))) {
            playSound();
        }
    }

    private void initView() {
        myLinearLayout.setLayout(this, R.layout.slide_layout);
        myLinearLayout.setmListener(() -> {
            finish();
            ShowTipsActivity.this.overridePendingTransition(0, 0);
        });
    }


    public static Intent buildIntent(Context context) {
        Intent intent = new Intent(context, ShowTipsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        return intent;
    }

    private void playSound() {
        AudioManager audioManager = (AudioManager) TipsApplication.getInst().getSystemService(Context.AUDIO_SERVICE);
        try {
            // 判断是否处于静音模式
            if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT) {
                return;
            }
            Ringtone ringtone = RingtoneManager.getRingtone(TipsApplication.getInst(),
                    Uri.parse(SpUtil.getPrefString(Constant.SP_KEY_TIPS_VOICE, "")));
            if (ringtone == null)
                return;
            if (!ringtone.isPlaying()) {
                ringtone.play();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        VibratorUtil.stopVibrate();
        if (ringtone != null && ringtone.isPlaying()) {
            ringtone.stop();
        }
        super.onDestroy();
    }

}
