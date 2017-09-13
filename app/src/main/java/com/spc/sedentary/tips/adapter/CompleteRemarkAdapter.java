package com.spc.sedentary.tips.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.spc.sedentary.tips.R;
import com.spc.sedentary.tips.mvp.entity.RemarkEntity;
import com.spc.sedentary.tips.view.SwipeItemLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by spc on 2017/9/13.
 */


public class CompleteRemarkAdapter extends RecyclerView.Adapter<CompleteRemarkAdapter.ViewHolder> {

    private ItemTouchListener mItemTouchListener;
    private List<RemarkEntity> mList;

    public interface ItemTouchListener {
        void onItemClick(int position);

        void onRecover(int position);

        void onDelete(int position);
    }

    public CompleteRemarkAdapter(List<RemarkEntity> data, ItemTouchListener itemTouchListener) {
        this.mList = data;
        this.mItemTouchListener = itemTouchListener;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_right_menu, parent, false);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mTvDate.setText(mList.get(position).getDate());
        holder.mColorView.setBackgroundColor(mList.get(position).getColor());
        holder.mTvContent.setText(mList.get(position).getContent());

        holder.mSwipeItemLayout.setSwipeEnable(true);
        if (mItemTouchListener != null) {
            holder.itemView.setOnClickListener(v -> mItemTouchListener.onItemClick(position));

            if (holder.mRightMenu != null) {
                holder.mllRecover.setOnClickListener(v -> {
                    mItemTouchListener.onRecover(position);
                    holder.mSwipeItemLayout.close();
                });
                holder.mllDelete.setOnClickListener(v -> {
                    mItemTouchListener.onDelete(position);
                    holder.mSwipeItemLayout.close();
                });

            }
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final View mRightMenu;
        private final SwipeItemLayout mSwipeItemLayout;
        @BindView(R.id.mColorView)
        View mColorView;
        @BindView(R.id.mTvContent)
        TextView mTvContent;
        @BindView(R.id.mTvDate)
        TextView mTvDate;
        @BindView(R.id.mllRecover)
        View mllRecover;
        @BindView(R.id.mllDelete)
        View mllDelete;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mSwipeItemLayout = (SwipeItemLayout) itemView.findViewById(R.id.swipe_layout);
            mRightMenu = itemView.findViewById(R.id.right_menu);
        }
    }
}


