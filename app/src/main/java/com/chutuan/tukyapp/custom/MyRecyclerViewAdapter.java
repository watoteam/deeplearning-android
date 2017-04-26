package com.chutuan.tukyapp.custom;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trieu Tuan on 3/26/2017.
 * Copyright (C) SFR Software.
 */

public abstract class MyRecyclerViewAdapter<T, VH extends MyRecyclerViewAdapter.ViewHolder<T>> extends RecyclerView.Adapter<VH> implements Filterable {
    private List<T> mObjects = new ArrayList<>();
    private List<T> mOriginalValues;
    private Filter mFilter;
    private static final Object mLock = new Object();

    public MyRecyclerViewAdapter(List<T> mObjects) {
        this.mObjects = mObjects;
    }

    public MyRecyclerViewAdapter() {
    }

    public synchronized void setList(List<T> list) {
        if (list == null) return;
        mObjects = list;
        mOriginalValues = list;
        notifyDataSetChanged();
    }

    public void addItem(T item) {
        mObjects.add(item);
        notifyDataSetChanged();
    }

    public void removeItem(T item) {
        mObjects.remove(item);
        notifyDataSetChanged();
    }

    public void replaceItem(T item) {
        int index = mObjects.indexOf(item);
        if (index != -1) {
            mObjects.remove(index);
            mObjects.add(index, item);
        }
        notifyDataSetChanged();
    }

    public void removeAll() {
        mObjects.clear();
        notifyDataSetChanged();
    }

    public synchronized List<T> getList() {
        return mObjects;
    }

    public T getItem(int position) {
        return mObjects.get(position);
    }

    @Override
    public int getItemCount() {
        return mObjects.size();
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        T item = mObjects.get(position);
        holder.performBind(item, position);
    }

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ArrayFilter();
        }
        return mFilter;
    }

    private class ArrayFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence prefix) {
            FilterResults results = new FilterResults();
            if (mOriginalValues == null || mOriginalValues.isEmpty()) {
                synchronized (mLock) {
                    mOriginalValues = new ArrayList<>(mObjects);
                }
            }
            if (prefix == null || prefix.length() == 0) {
                List<T> list;
                synchronized (mLock) {
                    list = new ArrayList<>(mOriginalValues);
                }
                results.values = list;
                results.count = list.size();
            } else {
                String prefixString = prefix.toString().toLowerCase();
                List<T> values;
                synchronized (mLock) {
                    values = new ArrayList<>(mOriginalValues);
                }

                final List<T> newValues = new ArrayList<>();

                for (T value : values) {
                    String valueText = value.toString().toLowerCase();
                    if (valueText.startsWith(prefixString)) {
                        newValues.add(value);
                    } else {
                        final String[] words = valueText.split(" ");
                        for (String word : words) {
                            if (word.startsWith(prefixString)) {
                                newValues.add(value);
                                break;
                            }
                        }
                    }
                }
                results.values = newValues;
                results.count = newValues.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            //noinspection unchecked
            mObjects = (List<T>) results.values;
            notifyDataSetChanged();
        }
    }

    public static abstract class ViewHolder<T> extends RecyclerView.ViewHolder {
        private T item;

        public ViewHolder(ViewGroup parent, int resId) {
            super(LayoutInflater.from(parent.getContext()).inflate(resId, parent, false));
        }

        final void performBind(T item, int position) {
            this.item = item;
            onBind(item, position);
        }

        public abstract void onBind(T item, int pos);

        protected T getItem() {
            return item;
        }

    }
}