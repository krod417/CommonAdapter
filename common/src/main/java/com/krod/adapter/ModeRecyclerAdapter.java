package com.krod.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * RecyclerView的适配器，用于该适配器的样式都需要加默认构造函数
 * Created by jian.wj on 15-11-25.
 */
public class ModeRecyclerAdapter extends RecyclerView.Adapter<RecyclerHolder> {
    private List<BaseViewHolder> list;
    private ViewManager viewManager;
    private Context context;

    public ModeRecyclerAdapter(Context context, ViewManager vm) {
        list = new ArrayList<>();
        this.viewManager = vm;
        this.context = context;
    }

    @Override
    public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = AdapterUtil.createItem(viewManager.typeMap.get(viewType), null);
        RecyclerHolder viewHolder = new RecyclerHolder(holder.onCreateView(context, parent));
        holder.afterViewCreated();
        viewHolder.setHolder(holder);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(final RecyclerHolder holder, final int position) {
        final BaseViewHolder iHolder = holder.getHolder();
        if (listener != null) {
            iHolder.getView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(iHolder.getView(), position, iHolder.getContent());
                }
            });
        }
        iHolder.bindData(position, list.get(position));
    }

    public interface IOnItemClickListener {
        <T> void onItemClick(View v, int position, T data);
    }

    private IOnItemClickListener listener;

    public void setOnItemClickListener(IOnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public int getItemViewType(int position) {
        Class typeClass = list.get(position).getClass();
        if (!viewManager.viewMap.containsKey(typeClass)) {
            throw new RuntimeException("The list does not contain the modelView:'" + typeClass.getName() + "'. Please check the ModelBuilder.");
        }
        return viewManager.viewMap.get(typeClass);
    }

    public void setList(List<? extends BaseViewHolder> list) {
        this.list.clear();
        if (list != null) {
            this.list.addAll(list);
        }
    }

    public <T> void setList(Class<? extends BaseViewHolder> c, List<T> list) {
        setList(AdapterUtil.setList(c, list));
    }

    public <T> void addList(Class<? extends BaseViewHolder> c, List<T> list) {
        this.list.addAll(AdapterUtil.setList(c, list));
    }

    public <T> void addItem(Class<? extends BaseViewHolder> c, T t) {
        this.list.add(AdapterUtil.createItem(c, t));
    }

    public List<? extends BaseViewHolder> getList() {
        return list;
    }

    public <T> T getContent(int position) {
        return (T) list.get(position).getContent();
    }

}
