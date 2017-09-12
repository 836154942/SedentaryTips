package com.spc.sedentary.tips.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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

public class RemarkAdapter extends RecyclerView.Adapter<RemarkAdapter.ViewHolder> {
    List<RemarkEntity> mList;
    Context mContext;


    public RemarkAdapter(Context mContext, List<RemarkEntity> mList) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_remark, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TLog.e("颜色是  "+mList.get(position).getColor());
        holder.mTvDate.setText(mList.get(position).getDate());
        holder.mColorView.setBackgroundColor(mList.get(position).getColor());
        holder.mTvContent.setText(mList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.mColorView)
        View mColorView;
        @BindView(R.id.mTvContent)
        TextView mTvContent;
        @BindView(R.id.mTvDate)
        TextView mTvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}