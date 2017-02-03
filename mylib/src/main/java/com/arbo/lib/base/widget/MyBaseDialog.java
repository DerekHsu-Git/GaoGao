package com.arbo.lib.base.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.view.WindowManager.LayoutParams;


public class MyBaseDialog extends Dialog {

	protected Context context;
	protected int width = LayoutParams.MATCH_PARENT;
	protected int height = LayoutParams.WRAP_CONTENT;


	public MyBaseDialog(Context context, int width, int height) {
		this(context);
		this.width = width;
		this.height = height;
	}


	public MyBaseDialog(Context context) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.context = context;
	}

	public MyBaseDialog(Context context, int theme) {
		super(context, theme);
		this.context = context;
	}

	protected void init(int resId) {

		setContentView(resId);

		getWindow().setLayout(width,
				height);
	}


	protected <T extends View> T findView(int resId) {
		return (T) findViewById(resId);
	}


	protected void initViews() {


	}


}
