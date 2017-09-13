package com.spc.sedentary.tips.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.anno.spc.ActivityInject;
import com.spc.sedentary.tips.R;
import com.spc.sedentary.tips.adapter.RemarkAdapter;
import com.spc.sedentary.tips.base.BaseMVPActivity;
import com.spc.sedentary.tips.mvp.entity.RemarkEntity;
import com.spc.sedentary.tips.mvp.iview.RemarkListView;
import com.spc.sedentary.tips.mvp.presenter.RemarkListPresenter;
import com.spc.sedentary.tips.view.itemtouch.DefaultItemTouchHelpCallback;
import com.spc.sedentary.tips.view.itemtouch.DefaultItemTouchHelper;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by spc on 2017/9/11.
 */

@ActivityInject
public class RemarkListActivity extends BaseMVPActivity<RemarkListPresenter> implements RemarkListView, RemarkAdapter.OnItemClickListener {

    @BindView(R.id.mRecycleView)
    RecyclerView mRecycleView;

    private RemarkAdapter mAdapter;
    private List<RemarkEntity> mList;
    /**
     * 滑动拖拽的帮助类
     */
    private DefaultItemTouchHelper itemTouchHelper;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_remarklist;
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private void initView() {
        mAdapter = new RemarkAdapter(this, mList, this);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());

        // 把ItemTouchHelper和itemTouchHelper绑定
        itemTouchHelper = new DefaultItemTouchHelper(onItemTouchCallbackListener);
        itemTouchHelper.attachToRecyclerView(mRecycleView);
        mAdapter.setItemTouchHelper(itemTouchHelper);
        itemTouchHelper.setDragEnable(true);
        itemTouchHelper.setSwipeEnable(true);
    }

    public static Intent buildIntent(Context context) {
        return new Intent(context, RemarkListActivity.class);
    }

    @Override
    public void getDataSuccess(List<RemarkEntity> list) {
        dismissProgressDialog();
        mList = list;
        initView();
    }

    private DefaultItemTouchHelpCallback.OnItemTouchCallbackListener onItemTouchCallbackListener = new DefaultItemTouchHelpCallback.OnItemTouchCallbackListener() {
        @Override
        public void onSwiped(int adapterPosition) {
            if (mList != null) {
                mList.remove(adapterPosition);
                mAdapter.notifyItemRemoved(adapterPosition);
            }
        }

        @Override
        public boolean onMove(int srcPosition, int targetPosition) {
            if (mList != null) {
                // 更换数据源中的数据Item的位置
                Collections.swap(mList, srcPosition, targetPosition);

                // 更新UI中的Item的位置，主要是给用户看到交互效果
                mAdapter.notifyItemMoved(srcPosition, targetPosition);
                return true;
            }
            return false;
        }
    };


    @OnClick(R.id.mIvAdd)
    public void addRemark() {
        startActivity(RemarkAddActivity.buildIntent(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mvpPresenter.getData();
    }


    @Override
    public void onItemClick(int position) {
        startActivity(RemarkShowActivity.buildIntent(this, mList.get(position)));
    }
}
