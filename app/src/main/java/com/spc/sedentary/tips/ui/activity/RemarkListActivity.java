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

    @Override
    protected int getLayoutId() {
        return R.layout.activity_remarklist;
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private void initData() {
        mvpPresenter.getData();
    }

    private void initView() {
        mAdapter = new RemarkAdapter(this, mList, this);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
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
