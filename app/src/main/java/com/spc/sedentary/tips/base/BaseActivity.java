package com.spc.sedentary.tips.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.spc.sedentary.tips.R;
import com.spc.sedentary.tips.ui.activity.MainActivity;
import com.spc.sedentary.tips.utils.TextCheckUtil;

import butterknife.ButterKnife;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by spc on 2017/9/9.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private ProgressDialog mProgressDialog;
    protected CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        if (!(this instanceof MainActivity))
            initActionBar();
    }

    private void initActionBar() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null)
            supportActionBar.setDisplayHomeAsUpEnabled(true);
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

    protected abstract int getLayoutId();

    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    public void showProgressDialog() {
        if (isFinishing()) {
            return;
        }
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setCanceledOnTouchOutside(false);
        }
        if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }


    protected void setCustomActionBar(String leftTitleString, String rightText, View.OnClickListener mRightListener) {
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        supportActionBar.setCustomView(R.layout.action_bar);
        supportActionBar.setDisplayShowCustomEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        ((TextView) supportActionBar.getCustomView().findViewById(R.id.mLeftTitle)).setText(leftTitleString);

        if (TextCheckUtil.isUseable(rightText)) {
            TextView mRightTV = (TextView) supportActionBar.getCustomView().findViewById(R.id.mTvRight);
            mRightTV.setText(rightText);
            mRightTV.setOnClickListener(mRightListener);
        }
    }

    @Override
    protected void onDestroy() {
        onUnsubscribe();
        super.onDestroy();
    }
}
