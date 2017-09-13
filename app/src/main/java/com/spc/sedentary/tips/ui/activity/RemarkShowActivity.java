package com.spc.sedentary.tips.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.spc.sedentary.tips.R;
import com.spc.sedentary.tips.base.BaseActivity;
import com.spc.sedentary.tips.mvp.entity.RemarkEntity;
import com.spc.sedentary.tips.utils.RxBus;

import butterknife.BindView;

/**
 * Created by spc on 2017/9/12.
 */

public class RemarkShowActivity extends BaseActivity {
    private static final String EXTRA_REMARK = "extra_remark";
    public static final int REQUEST_CODE_EDIT = 0x100;

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
        setCustomActionBar("查看备忘录", "编辑",
                v -> startActivityForResult(RemarkAddActivity.buildIntent(this, mRemark), REQUEST_CODE_EDIT));
        mRemark = (RemarkEntity) getIntent().getSerializableExtra(EXTRA_REMARK);
        updateView();
    }

    private void updateView() {
        mTvContent.setText(mRemark.getContent());
        mTvDate.setText(mRemark.getDate());
    }


    public static Intent buildIntent(Context c, RemarkEntity remark) {
        Intent intent = new Intent(c, RemarkShowActivity.class);
        intent.putExtra(EXTRA_REMARK, remark);
        return intent;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_EDIT && resultCode == RESULT_OK) {
            mRemark = (RemarkEntity) data.getSerializableExtra(RemarkAddActivity.EXTRA_REMARK);
            updateView();
            RxBus.getDefault().post(mRemark);
        }
    }
}
