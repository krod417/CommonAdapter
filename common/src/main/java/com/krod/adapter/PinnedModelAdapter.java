//package com.netease.framework.adapter;
//
//import android.content.Context;
//
//
///**
// * Created by hzzhangzhizhi on 2015/12/3.
// */
//public class PinnedModelAdapter  extends ModelAdapter implements PinnedSectionListView.PinnedSectionListAdapter{
//
//    public PinnedModelAdapter(Context context, ViewManager manager) {
//        super(context, manager);
//    }
//
//    @Override
//    public boolean isItemViewTypePinned(int viewType) {
//        return viewType >= 0 ? viewManager.pinnedMap.get(viewType) : false;
//    }
//}
