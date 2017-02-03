package com.arbo.lib.base.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public abstract class BaseFragment extends Fragment {


	protected View contentView;

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		if (contentView == null) {
			contentView = createView(inflater, container, savedInstanceState);

			initLayoutID();
			initViews();
			initViewListener();
			process(savedInstanceState);
		}


		return contentView;
	}


	protected View createView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


		return inflater.inflate(initLayoutID(), null);
	}


	protected abstract int initLayoutID();

	protected void initViews() {
	}


	protected void initViewListener() {
	}


	/**
	 * 逻辑处理
	 */
	protected void process(Bundle savedInstanceState) {
	}


	protected <T extends View> T findView(int resId) {

		if (contentView == null) {
			return null;
		}
		return (T) contentView.findViewById(resId);
	}


}
