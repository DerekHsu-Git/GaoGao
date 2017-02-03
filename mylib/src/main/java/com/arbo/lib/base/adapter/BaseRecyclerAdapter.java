package com.arbo.lib.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


	protected List<T> dataList;
	protected Context context;
	protected LayoutInflater inflater;


	public BaseRecyclerAdapter(Context context, List<T> data) {
		this.context = context;
		this.dataList = data;
		inflater = LayoutInflater.from(context);
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	@Override
	public abstract RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);

	@Override
	public abstract void onBindViewHolder(RecyclerView.ViewHolder holder, int position);


	@Override
	public int getItemCount() {
		if (dataList == null) {
			return 0;
		}
		return dataList.size();
	}

	//2016.10.5日新增
	protected <T extends View> T findView(View rootView, int viewID) {
		return (T) rootView.findViewById(viewID);
	}


	//2016.12.06添加
	protected OnAdapterItemClickListener adapterItemClickListener;

	public interface OnAdapterItemClickListener {

		void onAdapterItemClick(View view, int position);
	}

	public void setAdapterItemClickListener(OnAdapterItemClickListener listener) {
		if (listener == null) {
			return;
		}
		adapterItemClickListener = listener;
	}
}
