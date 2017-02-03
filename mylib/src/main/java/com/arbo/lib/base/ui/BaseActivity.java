package com.arbo.lib.base.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;




public abstract class BaseActivity extends AppCompatActivity {


	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(initLayoutID());
		initViews();
		initViewListener();
		process(savedInstanceState);
	}


	@Override
	protected void onResume() {
		super.onResume();

	}

	/**
	 * 返回主布局id
	 */
	protected abstract int initLayoutID();

	/**
	 * 初始化页面控件
	 */
	protected void initViews() {
	}

	/**
	 * 页面控件点击事件处理
	 */
	protected void initViewListener() {
	}

	/**
	 * 逻辑处理
	 */
	protected void process(Bundle savedInstanceState) {
	}

	protected <T extends View> T findView(int resId) {
		return (T) findViewById(resId);
	}


	/**
	 * 透明状态栏，在setContentView之后调用
	 */
	protected void transparentBar(int color) {
		//StatusBarUtil.setCustomStatueView(this, color);

	}
}
