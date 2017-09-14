package com.spc.sedentary.tips.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.spc.sedentary.tips.R;
import com.spc.sedentary.tips.base.BaseActivity;

/**
 * Created by spc on 2017/9/14.
 */

public class AboutActivity extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static Intent buildIntent(Context context){
       Intent intent =new Intent(context,AboutActivity.class);
        return intent;
    }
}
