package com.spc.sedentary.tips.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spc.sedentary.tips.R;
import com.spc.sedentary.tips.mvp.entity.RemarkEntity;
import com.spc.sedentary.tips.utils.TLog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by spc on 2017/9/12.
 */

public class RemarkAdapter extends RecyclerView.Adapter<RemarkAdapter.ViewHolder> implements View.OnClickListener {
    private List<RemarkEntity> mList;
    private Context mContext;
    private OnItemClickListener mListener;
    /**
     * Item拖拽滑动帮助
     */
    private ItemTouchHelper itemTouchHelper;

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.mRootView) {
            mListener.onItemClick((Integer) view.getTag());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setItemTouchHelper(ItemTouchHelper itemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper;
    }

    public RemarkAdapter(Context mContext, List<RemarkEntity> mList, OnItemClickListener listener) {
        this.mList = mList;
        this.mContext = mContext;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_remark, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TLog.e("onBindViewHolderonBind----> " + position);
        holder.mTvDate.setText(mList.get(position).getDate());
        holder.mColorView.setBackgroundColor(mList.get(position).getColor());
        holder.mTvContent.setText(mList.get(position).getContent());
        holder.mRootView.setOnClickListener(this);
        holder.mRootView.setTag(position);
        holder.mTouchView.setVisibility(View.VISIBLE);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnTouchListener {

        @BindView(R.id.mColorView)
        View mColorView;
        @BindView(R.id.mTvContent)
        TextView mTvContent;
        @BindView(R.id.mTvDate)
        TextView mTvDate;
        @BindView(R.id.mRootView)
        View mRootView;
        @BindView(R.id.iv_touch)
        View mTouchView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mTouchView.setOnTouchListener(this);
        }


        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (view == mTouchView)
                itemTouchHelper.startDrag(this);
            return false;
        }
    }
}

