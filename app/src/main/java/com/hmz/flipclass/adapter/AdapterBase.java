package com.hmz.flipclass.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/1/16.
 */
public class AdapterBase<T> extends BaseAdapter {

    private List<T> mList = new LinkedList<T>();
    protected Context mContext;
    protected LayoutInflater mInflater;

    public AdapterBase(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    public List<T> getList() {
        return mList;
    }

    public void appendToList(List<T> list) {
        if (list == null) {
            return;
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addToList(List<T> list) {
        if (list == null) {
            return;
        }
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void appendToList(T t) {
        if (t == null) {
            return;
        }
        if (mList == null) {
            mList = new ArrayList<T>();
        }
        mList.add(t);
        notifyDataSetChanged();
    }

    public void appendToTopList(List<T> list) {
        if (list == null) {
            return;
        }
        mList.addAll(0, list);
        notifyDataSetChanged();
    }

    public void appendToTopList(T t) {
        if (t == null) {
            return;
        }
        mList.add(0, t);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        mList.remove(position);
    }

    public void updateItem(int position, T t) {
        mList.set(position, t);
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public T getItem(int position) {
        if (position > mList.size() - 1) {
            return null;
        }
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (position == getCount() - 1) {
            onReachBottom();
        }
        return getExView(position, convertView, parent);
    }

    protected View getExView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    protected void onReachBottom() {
    }

}