package com.spc.sedentary.tips.utils;

import android.content.Context;
import android.media.AudioManager;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import com.spc.sedentary.tips.base.TipsApplication;

import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * Created by spc on 2017/9/12.
 */

public class RingUtil {
    public static void viberateAndPlayTone() {
        AudioManager audioManager = (AudioManager) TipsApplication.getInst().getSystemService(Context.AUDIO_SERVICE);

        try {
            // 判断是否处于静音模式
            if (audioManager.getRingerMode() == AudioManager.RINGER_MODE_SILENT) {
                TLog.e("静音模式~~~~");
                return;
            }
            if (TextCheckUtil.isUseable(SpUtil.getPrefString(Constant.SP_KEY_TIPS_VOICE, ""))) {
                Ringtone ringtone = RingtoneManager.getRingtone(TipsApplication.getInst(),
                        Uri.parse(SpUtil.getPrefString(Constant.SP_KEY_TIPS_VOICE, "")));

                if (ringtone ==null)
                    return;

                if (!ringtone.isPlaying()) {
                    String vendor = Build.MANUFACTURER;

                    ringtone.play();
                    // for samsung S3, we meet a bug that the phone will
                    // continue ringtone without stop
                    // so add below special handler to stop it after 3s if
                    // needed
                    if (vendor != null && vendor.toLowerCase().contains("samsung")) {
                        Observable.timer(3, TimeUnit.SECONDS)
                                .subscribe(l -> {
                                    if (ringtone.isPlaying()) {
                                        ringtone.stop();
                                    }
                                });
                    }
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void stopSound(){

    }
}
