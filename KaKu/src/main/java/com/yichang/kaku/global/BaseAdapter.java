package com.yichang.kaku.global;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yichang.kaku.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaosu on 2015/11/16.
 */
public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {

    public BaseAdapter(List<T> datas) {
        //this.datas= datas;
        this.datas.addAll(datas);
    }

    private List<T> datas=new ArrayList<>();

    @Override
    public final int getCount() {
        return datas.size();
    }

    @Override
    public final T getItem(int position) {
        return datas.get(position);
    }

    @Override
    public final long getItemId(int position) {
        return position;
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(getLayoutId(), null);
            holder = new ViewHolder(convertView);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        getView(holder, getItem(position), position, parent.getContext());

        return convertView;
    }

    protected abstract void getView(ViewHolder holder, T item, int position, Context context);

    public static class ViewHolder {

        public ViewHolder(View rootView) {
            this.rootView = rootView;
            rootView.setTag(this);
        }

        public View getRootView() {
            return rootView;
        }

        public void setRootView(View rootView) {
            this.rootView = rootView;
        }

        private View rootView;

        public <T extends View> T getView(int resId) {
            SparseArray<View> childCache = (SparseArray<View>) rootView.getTag(R.id.childCache);

            if (null == childCache) {
                childCache = new SparseArray<>();
                rootView.setTag(R.id.childCache, childCache);
            }

            View view = childCache.get(resId);
            if (null == view) {
                view = rootView.findViewById(resId);
                childCache.put(resId, view);
            }

            if (null == view) {
                throw new RuntimeException("不存在这个id");
            }

            return (T) view;
        }
    }

    public void deleteItem(T item) {
        this.datas.remove(item);
        notifyDataSetChanged();
    }

    public void addItem(T item) {
        this.datas.add(item);
        notifyDataSetChanged();
    }

    public List<T> getDatas() {
        return datas;
    }

    public void notifyDataSetChanged(List<T> datas) {
       /* if (this.datas != datas) {

        }*/

        this.datas.clear();
        this.datas.addAll(datas);
        super.notifyDataSetChanged();
    }

    public abstract int getLayoutId();

}
