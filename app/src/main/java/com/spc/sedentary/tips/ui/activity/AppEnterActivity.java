package com.spc.sedentary.tips.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.spc.sedentary.tips.R;
import com.spc.sedentary.tips.base.BaseActivity;

import java.util.concurrent.TimeUnit;

import rx.Observable;

/**
 * Created by spc on 2017/9/11.
 */

public class AppEnterActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        mCompositeSubscription.add(Observable.timer(5, TimeUnit.SECONDS)
                .subscribe(l -> {
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                }));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_enter;
    }
}
