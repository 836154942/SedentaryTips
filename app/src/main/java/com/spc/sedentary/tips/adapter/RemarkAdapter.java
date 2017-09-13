package com.spc.sedentary.tips.adapter;

import android.content.Context;
import android.graphics.Color;
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
import com.spc.sedentary.tips.utils.TextCheckUtil;
import com.spc.sedentary.tips.utils.TimeUtils;

import java.util.Date;
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
    private boolean mIsShowOrder;

    public boolean ismIsShowOrder() {
        return mIsShowOrder;
    }

    public void setmIsShowOrder(boolean mIsShowOrder) {
        this.mIsShowOrder = mIsShowOrder;
    }

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
        holder.mColorView.setBackgroundColor(mList.get(position).getColor());
        holder.mTvContent.setText(mList.get(position).getContent());
        holder.mRootView.setOnClickListener(this);
        holder.mRootView.setTag(position);
        if (mIsShowOrder)
            holder.mTouchView.setVisibility(View.VISIBLE);
        else
            holder.mTouchView.setVisibility(View.GONE);


        if (TextCheckUtil.isUseable(mList.get(position).getDate())) {
            StringBuilder builder = new StringBuilder("距离 ");
            builder.append(mList.get(position).getDate());
            Date nowDate = new Date();
            Date targetDate = TimeUtils.rmarkStingTODate(mList.get(position).getDate());
            if (targetDate != null) {
                if (nowDate.before(targetDate)) {
                    builder.append(" 还有 ");
                    holder.mTvDate.setTextColor(mContext.getResources()
                            .getColor(R.color.textColorPrimary));
                    builder.append(TimeUtils.getDays(targetDate.getTime(), nowDate.getTime()));
                    builder.append(" 天");
                } else {
                    builder.append(" ………… 已经超时");
                    builder.append(TimeUtils.getDays(nowDate.getTime(), targetDate.getTime()));
                    builder.append(" 天");
                    holder.mTvDate.setTextColor(Color.RED);
                }
            }
            holder.mTvDate.setText(builder.toString());
        } else {
            holder.mTvDate.setText("");
        }
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

