package com.spc.sedentary.tips.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.anno.spc.ActivityInject;
import com.spc.sedentary.tips.R;
import com.spc.sedentary.tips.adapter.CompleteRemarkAdapter;
import com.spc.sedentary.tips.base.BaseMVPActivity;
import com.spc.sedentary.tips.mvp.entity.RemarkEntity;
import com.spc.sedentary.tips.mvp.iview.CompleteRemarksAcView;
import com.spc.sedentary.tips.mvp.presenter.CompleteRemarksAcPresenter;
import com.spc.sedentary.tips.utils.Constant;
import com.spc.sedentary.tips.utils.RxBus;
import com.spc.sedentary.tips.utils.SpUtil;
import com.spc.sedentary.tips.view.SwipeMenuRecyclerView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by spc on 2017/9/13.
 */
@ActivityInject
public class CompleteRemarksActivity extends BaseMVPActivity<CompleteRemarksAcPresenter> implements CompleteRemarksAcView {
    @BindView(R.id.mRecycleView)
    SwipeMenuRecyclerView mRecycleView;
    @BindView(R.id.mIvAdd)
    ImageView mIvAdd;
    private CompleteRemarkAdapter mAdapter;
    private List<RemarkEntity> mList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_remarklist;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        loadData();
    }

    private void loadData() {
        mvpPresenter.loadAllCompleteRemarks();
        initRxBusReceive();
    }

    private void initView() {
        mIvAdd.setVisibility(View.GONE);
        setCustomActionBar("已完成", "清空", v -> showClearDialog());
    }

    private void showClearDialog() {
        AlertDialog.Builder build = new AlertDialog.Builder(this);
        build.setTitle("亲爱的" + SpUtil.getPrefString(Constant.SP_KEY_DISPLAY_NAME,
                getResources().getString(R.string.pref_default_display_name)))
                .setMessage("确定要清空已完成吗？……无法恢复噢")
                .setNegativeButton("算了", null)
                .setPositiveButton("清空", (dialog, which) -> mvpPresenter.cleanCompleteRemarks())
                .show();
    }

    public static Intent buildIntent(Context context) {
        Intent intent = new Intent(context, CompleteRemarksActivity.class);
        return intent;
    }


    @Override
    public void getAllCompleteData(List<RemarkEntity> remarkEntities) {
        mList = remarkEntities;
        mAdapter = new CompleteRemarkAdapter(mList, mItemTouchListener);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecycleView.addItemDecoration(dividerItemDecoration);


    }

    @Override
    public void deleteAllComplete(Boolean res) {
        if (res) {
            mList.clear();
            mAdapter.notifyDataSetChanged();
            Snackbar.make(mRecycleView, "已清空", Snackbar.LENGTH_LONG)
                    .setAction("", null).show();
        } else {
            Snackbar.make(mRecycleView, "操作失败~", Snackbar.LENGTH_LONG)
                    .setAction("", null).show();
        }
    }


    CompleteRemarkAdapter.ItemTouchListener mItemTouchListener = new CompleteRemarkAdapter.ItemTouchListener() {
        @Override
        public void onItemClick(int position) {
            startActivity(RemarkShowActivity.buildIntent(CompleteRemarksActivity.this, mList.get(position)));
        }

        @Override
        public void onRecover(int position) {
            mvpPresenter.recover(mList.get(position));
            mList.remove(position);
            mAdapter.notifyItemRemoved(position);
            Snackbar.make(mRecycleView, "已恢复为待处理~", Snackbar.LENGTH_SHORT)
                    .setAction("", null).show();
            notifyDataWithDelay();
        }

        @Override
        public void onDelete(int position) {
            mvpPresenter.delete(mList.get(position));
            mList.remove(position);
            mAdapter.notifyItemRemoved(position);
            Snackbar.make(mRecycleView, "已删除~", Snackbar.LENGTH_SHORT)
                    .setAction("", null).show();
            notifyDataWithDelay();
        }
    };

    private void notifyDataWithDelay() {
        mCompositeSubscription.add(Observable.timer(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(l -> mAdapter.notifyDataSetChanged()));
    }

    private void initRxBusReceive() {
        mCompositeSubscription.add(RxBus.getDefault()
                .toObservable(RemarkEntity.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(entity -> {
                    if (mList != null && mList.size() > 0) {

                        for (int i = 0; i < mList.size(); i++) {
                            if (mList.get(i).getId() == entity.getId()) {
                                mList.get(i).setColor(entity.getColor());
                                mList.get(i).setContent(entity.getContent());
                                mList.get(i).setDate(entity.getDate());
                                mAdapter.notifyItemChanged(i);
                                break;
                            }
                        }
                    }
                }));
    }


}
