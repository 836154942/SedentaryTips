package com.spc.sedentary.tips.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.anno.spc.ActivityInject;
import com.spc.sedentary.tips.R;
import com.spc.sedentary.tips.adapter.RemarkAdapter;
import com.spc.sedentary.tips.base.BaseMVPActivity;
import com.spc.sedentary.tips.mvp.entity.RemarkEntity;
import com.spc.sedentary.tips.mvp.iview.RemarkListView;
import com.spc.sedentary.tips.mvp.presenter.RemarkListPresenter;
import com.spc.sedentary.tips.utils.RxBus;
import com.spc.sedentary.tips.view.itemtouch.DefaultItemTouchHelpCallback;
import com.spc.sedentary.tips.view.itemtouch.DefaultItemTouchHelper;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;


/**
 * Created by spc on 2017/9/11.
 */

@ActivityInject
public class RemarkListActivity extends BaseMVPActivity<RemarkListPresenter> implements RemarkListView, RemarkAdapter.OnItemClickListener {
    public static final int REQUEST_ADD_REMARK = 0x102;

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
        mvpPresenter.getData();
        initRxBusReceive();
    }


    private void initView() {
        mAdapter = new RemarkAdapter(this, mList, this);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setAdapter(mAdapter);
        mRecycleView.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        mRecycleView.addItemDecoration(dividerItemDecoration);


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
        mList = list;
        initView();
    }

    private DefaultItemTouchHelpCallback.OnItemTouchCallbackListener onItemTouchCallbackListener = new DefaultItemTouchHelpCallback.OnItemTouchCallbackListener() {
        @Override
        public void onSwiped(int adapterPosition) {
            if (mList != null) {
                mvpPresenter.updateComplete(mList.get(adapterPosition));
                mList.remove(adapterPosition);
                mAdapter.notifyItemRemoved(adapterPosition);
                Snackbar.make(mRecycleView, "真棒！又完成一项~", Snackbar.LENGTH_SHORT)
                        .setAction("", null).show();
                notifyDataWithDelay();
            }
        }

        @Override
        public boolean onMove(int srcPosition, int targetPosition) {
            if (mList != null) {
                // 更换数据源中的数据Item的位置
                Collections.swap(mList, srcPosition, targetPosition);
                mAdapter.notifyItemMoved(srcPosition, targetPosition);
                mAdapter.notifyItemRangeChanged(srcPosition, targetPosition);
                //重拍数据的编号
                return true;
            }
            return false;
        }
    };


    @OnClick(R.id.mIvAdd)
    public void addRemark() {
        startActivityForResult(RemarkAddActivity.buildIntent(this), REQUEST_ADD_REMARK);
    }


    @Override
    public void onItemClick(int position) {
        startActivity(RemarkShowActivity.buildIntent(this, mList.get(position)));
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD_REMARK && resultCode == RESULT_OK) {
            mList.add((RemarkEntity) data.getSerializableExtra(RemarkAddActivity.EXTRA_REMARK));
            mAdapter.notifyItemInserted(mList.size());
        }
    }

    private void notifyDataWithDelay() {
        mCompositeSubscription.add(Observable.timer(300, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(l -> mAdapter.notifyDataSetChanged()));
    }


    private void initRxBusReceive() {
        mCompositeSubscription.add(RxBus.getDefault()
                .toObservable(RemarkEntity.class)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(entity -> {
                    if (mList != null && mList.size() > 0) {
                        //比对id

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