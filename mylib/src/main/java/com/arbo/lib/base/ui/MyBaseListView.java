package com.arbo.lib.base.ui;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class MyBaseListView extends ListView {

	public MyBaseListView(Context context) {
		super(context);
	}

	public MyBaseListView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyBaseListView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
				MeasureSpec.AT_MOST);
		super.onMeasure(widthMeasureSpec, expandSpec);
	}
}
