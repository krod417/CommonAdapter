/*
 * Copyright 2015 krod
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.krod.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 默认统一样式父类，包括RecyclerView，ListView，GridView,如果不传参数请加上默认构造函数，不能传null。
 * Created by jian.wj on 15-11-25.
 */

public abstract class BaseViewHolder<T> implements Serializable {

    protected Context context;
    protected Fragment fragment;
    /**
     * 索引位置
     */
    private int viewPosition;
    /**
     * item的View
     */
    protected View view;
    private long id;
    /**
     * The content you want to set for item.
     * 数据内容
     */
    protected T content;
    /**
     * Item check message.
     */
    private boolean isCheck;
//    /**
//     * Current status for item
//     */
//    private int status;
//    /**
//     * The type name of your item view. Default is the item view class name.
//     */
//    private String modelType;

    /**
     * Be used for cache.
     */
    private long timestamp;

    /**
     * Set true the item view will be bind only once.You could reset the timestamp for updating.
     */
    private boolean isSingleton;

    private Map<String, Object> attrs;
    /**
     * Tag for item.
     */
    private String tag = "";

    private BaseViewHolder<T> sourceHolder;


    public BaseViewHolder(T t) {
        super();
        this.content = t;
        this.tag = "";
        this.timestamp = System.currentTimeMillis();
    }

    public long getId() {
        return id;
    }

    public BaseViewHolder setId(long id) {
        this.id = id;
        return this;
    }

    public T getContent() {
        return content;
    }

    public BaseViewHolder setContent(T content) {
        this.content = content;
        return this;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public BaseViewHolder setCheck(boolean isCheck) {
        this.isCheck = isCheck;
        return this;
    }

    public void copy(BaseViewHolder<T> holder) {
        this.content = holder.content;
        this.fragment = holder.fragment;
        this.attrs = holder.attrs;
        this.isCheck = holder.isCheck;
        this.isSingleton = holder.isSingleton;
        this.tag = holder.tag;
        this.sourceHolder = holder;
    }

    public long getTimestamp() {
        return timestamp;
    }

    /**
     * You could set the timestamp from your net or db if you set singleton true for this item,
     * or ItemEntity would set the current time for its cache timestamp.
     * @param timestamp
     */
    public BaseViewHolder setTimestamp(long timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public boolean isSingleton() {
        return isSingleton;
    }

    /**
     * It would call bindView only once if set it true.
     * Just like a poster in your ListView.
     * @param isSingleton
     */
    public BaseViewHolder setSingleton(boolean isSingleton) {
        this.isSingleton = isSingleton;
        return this;
    }

    public String getTag() {
        return tag;
    }

    public BaseViewHolder setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public BaseViewHolder setFragment(Fragment fragment) {
        this.fragment = fragment;
        return this;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void bindData(int position, BaseViewHolder<T> model) {
        // Singleton depends on view's model saved last time.
        // If your item view does not extend from BaseViewHolder, you should check the cache timestamp if you need.
        if (!AdapterUtil.checkCache(this, model)) {
            setViewPosition(position);
            copy(model);
            bindView(model.content);
        }
    }

    public void setViewPosition(int viewPosition) {
        this.viewPosition = viewPosition;
    }

    public int getViewPosition() {
        return viewPosition;
    }

    public View onCreateView(Context context, ViewGroup root){
        this.context = context;
        this.view = createItemView(root);
        afterViewCreated();
        return this.view;
    }

    public View createItemView(ViewGroup root) {
       return LayoutInflater.from(context).inflate(itemViewId(), root, false);
    }

    public View findViewById(int resId) {
        if (view == null) {
            throw new RuntimeException("view no create");
        }
        return view.findViewById(resId);
    }

    public final View getView(){
        return view;
    }

    public Map<String, Object> getAttrs() {
        return this.attrs;
    }

    public BaseViewHolder setAttrs(Map<String, Object> attrs) {
        this.attrs = attrs;
        return this;
    }

    public BaseViewHolder addAttr(String key, Object value) {
        if(this.attrs == null) {
            this.attrs = new HashMap();
        }

        this.attrs.put(key, value);
        return this;
    }

    public <B> B getAttr(String key, Class<B> c) {
        return this.attrs == null?null:(B)this.attrs.get(key);
    }

    public boolean hasAttr(String key) {
        return this.attrs == null?false:this.attrs.get(key) != null;
    }

    public void removeAttr(String key) {
        if(this.attrs.get(key) != null) {
            this.attrs.remove(key);
        }

    }

    public <T extends View> T findById(int resId) {
        if (view == null) {
            throw new RuntimeException("view no create");
        }
        return (T) this.findViewById(resId);
    }

    public String getString(int resId) {
        if (context != null) {
            return context.getString(resId);
        }
        return "";
    }

    public BaseViewHolder<T> getSourceHolder() {
        return sourceHolder;
    }

    /**
     * 返回样式xml的Id
     *
     * @return
     */
    public abstract int itemViewId();

    /**
     * 关联控件
     */
    public abstract void afterViewCreated();

    public abstract void bindView(T t);
}
