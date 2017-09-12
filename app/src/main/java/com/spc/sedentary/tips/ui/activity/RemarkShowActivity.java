package com.spc.sedentary.tips.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.spc.sedentary.tips.R;
import com.spc.sedentary.tips.base.BaseActivity;
import com.spc.sedentary.tips.mvp.entity.RemarkEntity;
import com.spc.sedentary.tips.utils.Constant;
import com.spc.sedentary.tips.utils.TextCheckUtil;

import butterknife.BindView;

/**
 * Created by spc on 2017/9/12.
 */

public class RemarkShowActivity extends BaseActivity {
    private static final String EXTRA_REMARK = "extra_remark";
    @BindView(R.id.mTvContent)
    TextView mTvContent;
    @BindView(R.id.mTvDate)
    TextView mTvDate;
    RemarkEntity mRemark;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_remark_show;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRemark = (RemarkEntity) getIntent().getSerializableExtra(EXTRA_REMARK);
        mTvContent.setText(mRemark.getContent());
        mTvDate.setText(mRemark.getDate());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent buildIntent(Context c, RemarkEntity remark) {
        Intent intent = new Intent(c, RemarkShowActivity.class);
        intent.putExtra(EXTRA_REMARK, remark);
        return intent;
    }
}
