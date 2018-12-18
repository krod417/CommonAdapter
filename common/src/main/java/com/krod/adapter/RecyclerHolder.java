package com.krod.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * RecyclerView的内部类
 * Created by jian.wj on 15-11-25.
 */
public class RecyclerHolder extends RecyclerView.ViewHolder {
    private BaseViewHolder holder;

    public RecyclerHolder(View itemView) {
        super(itemView);
    }

    public void setHolder(BaseViewHolder holder) {
        this.holder = holder;
    }

    public BaseViewHolder getHolder() {
        return holder;
    }
}
