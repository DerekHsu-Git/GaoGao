package com.arbo.lib.base.adapter;


import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;


public abstract class MyBaseAdapter<T> extends BaseAdapter {

	protected Context context;
	protected List<T> dataList;
	protected LayoutInflater inflater;
	protected Resources resources;

	public MyBaseAdapter(Context context, List<T> dataList) {

		this.context = context;
		this.dataList = dataList;
		inflater = LayoutInflater.from(context);
		resources = context.getResources();
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	@Override
	public int getCount() {
		if (dataList == null)
			return 0;

		return dataList.size();
	}

	@Override
	public T getItem(int i) {
		if (dataList == null)
			return null;

		return dataList.get(i);
	}

	@Override
	public long getItemId(int i) {
		return i;
	}

	@Override
	public abstract View getView(int position, View view, ViewGroup viewGroup);

	//2016.10.5日新增
	protected <T extends View> T findView(View rootView, int viewID) {
		return (T) rootView.findViewById(viewID);
	}

}
