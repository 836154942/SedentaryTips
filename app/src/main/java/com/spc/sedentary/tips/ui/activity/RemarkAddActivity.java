package com.spc.sedentary.tips.ui.activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.spc.sedentary.tips.R;
import com.spc.sedentary.tips.base.BaseActivity;
import com.spc.sedentary.tips.database.service.RemarkService;
import com.spc.sedentary.tips.mvp.entity.RemarkEntity;
import com.spc.sedentary.tips.utils.Constant;
import com.spc.sedentary.tips.utils.TextCheckUtil;
import com.spc.sedentary.tips.utils.ToastUtil;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by spc on 2017/9/12.
 */

public class RemarkAddActivity extends BaseActivity {
    public static final String EXTRA_REMARK = "extra_remark";
    @BindView(R.id.mEdText)
    EditText mEdText;
    @BindView(R.id.mTvDate)
    TextView mTvDate;
    @BindView(R.id.mllDate)
    LinearLayout mllDate;
    @BindView(R.id.mIvSelect1)
    ImageView mIvSelect1;

    @BindView(R.id.mIvSelect2)
    ImageView mIvSelect2;
    @BindView(R.id.mIvSelect3)
    ImageView mIvSelect3;
    @BindView(R.id.mIvSelect4)
    ImageView mIvSelect4;
    @BindView(R.id.mIvSelect5)
    ImageView mIvSelect5;
    RemarkEntity mRemark;
    private DatePickerDialog startDialog;
    private boolean isEdit;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_remark_add;
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initView();
    }

    private void initData() {
        mRemark = (RemarkEntity) getIntent().getSerializableExtra(EXTRA_REMARK);
        isEdit = (mRemark != null);
        if (mRemark == null) {
            mRemark = new RemarkEntity();
            mRemark.setColor(getResources().getColor(R.color.remark_color_1));
        }
    }

    private void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);//显示返回按钮。
        }

        Calendar calendar = Calendar.getInstance();
        // 获取当前对应的年、月、日的信息
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        // 初始化DatePickerDialog(开始时间)
        startDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                setTitle(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                mTvDate.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
            }
        }, year, month, day);

        if (isEdit) {
            setTitle("修改忘信息");
            mEdText.setText(mRemark.getContent());
            mTvDate.setText(mRemark.getDate());
            initSelectedColor();
        } else
            setTitle("添加备忘信息");
    }

    //设置已经选择的颜色
    private void initSelectedColor() {
        if (mRemark.getColor() == getResources().getColor(R.color.remark_color_1)) {
            setColorSelect(R.id.mColor1);
        } else if (mRemark.getColor() == getResources().getColor(R.color.remark_color_2)) {
            setColorSelect(R.id.mColor2);
        } else if (mRemark.getColor() == getResources().getColor(R.color.remark_color_3)) {
            setColorSelect(R.id.mColor3);
        } else if (mRemark.getColor() == getResources().getColor(R.color.remark_color_4)) {
            setColorSelect(R.id.mColor4);
        } else if (mRemark.getColor() == getResources().getColor(R.color.remark_color_5)) {
            setColorSelect(R.id.mColor5);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            if ((!isEdit && TextCheckUtil.isUseable(mEdText.getText().toString()))
                    || (isEdit && !mEdText.getText().toString().equals(mRemark.getContent()))) {
                showSaveDialog();
            } else {
                finish();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSaveDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("温习提示")
                .setMessage("备忘录尚未保存，是否真的退出？")
                .setNegativeButton("取消", null)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                }).create().show();
    }

    public static Intent buildIntent(Context context) {
        Intent intent = new Intent(context, RemarkAddActivity.class);
        return intent;
    }

    public static Intent buildIntent(Context context, RemarkEntity mRemark) {
        Intent intent = buildIntent(context);
        intent.putExtra(EXTRA_REMARK, mRemark);
        return intent;
    }

    @OnClick({R.id.mColor1, R.id.mColor2, R.id.mColor3, R.id.mColor4, R.id.mColor5,
            R.id.mllDate, R.id.mBtnOK})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mColor1:
            case R.id.mColor2:
            case R.id.mColor3:
            case R.id.mColor4:
            case R.id.mColor5:
                setColorSelect(view.getId());
                break;
            case R.id.mllDate:
                startDialog.show();
                break;
            case R.id.mBtnOK:
                if (!TextCheckUtil.isUseable(mEdText.getText().toString())) {
                    ToastUtil.showToast("您还什么都没写呢~");
                    return;
                }
                mRemark.setContent(mEdText.getText().toString().trim());
                mRemark.setDate(mTvDate.getText().toString());
                mRemark.setStatus(Constant.REMARK_STATUS_NORMAL);
                RemarkService remarkService = new RemarkService(this);
                if (isEdit) {
                    if (remarkService.update(mRemark)) {
                        finish();
                    } else {
                        Snackbar.make(view, "修改失败", Snackbar.LENGTH_SHORT).show();
                    }
                } else {
                    if (remarkService.insert(mRemark)) {
                        finish();
                    } else {
                        Snackbar.make(view, "保存失败", Snackbar.LENGTH_SHORT).show();
                    }
                }
                break;
            default:
                break;
        }
    }

    public void setColorSelect(int id) {
        mIvSelect1.setVisibility(View.GONE);
        mIvSelect2.setVisibility(View.GONE);
        mIvSelect3.setVisibility(View.GONE);
        mIvSelect4.setVisibility(View.GONE);
        mIvSelect5.setVisibility(View.GONE);
        switch (id) {
            case R.id.mColor1:
                mIvSelect1.setVisibility(View.VISIBLE);
                mRemark.setColor(getResources().getColor(R.color.remark_color_1));
                break;
            case R.id.mColor2:
                mIvSelect2.setVisibility(View.VISIBLE);
                mRemark.setColor(getResources().getColor(R.color.remark_color_2));
                break;
            case R.id.mColor3:
                mIvSelect3.setVisibility(View.VISIBLE);
                mRemark.setColor(getResources().getColor(R.color.remark_color_3));
                break;
            case R.id.mColor4:
                mIvSelect4.setVisibility(View.VISIBLE);
                mRemark.setColor(getResources().getColor(R.color.remark_color_4));
                break;
            case R.id.mColor5:
                mIvSelect5.setVisibility(View.VISIBLE);
                mRemark.setColor(getResources().getColor(R.color.remark_color_5));
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((!isEdit && TextCheckUtil.isUseable(mEdText.getText().toString()))
                    || (isEdit && !mEdText.getText().toString().equals(mRemark.getContent()))) {
                showSaveDialog();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
