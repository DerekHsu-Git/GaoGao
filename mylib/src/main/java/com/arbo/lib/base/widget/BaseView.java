package com.arbo.lib.base.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;


public abstract class BaseView extends View {

	protected int viewWidth = 0;
	protected int viewHeight = 0;
	protected Context mContext;

	public BaseView(Context context) {
		this(context, null);
	}

	public BaseView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BaseView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.mContext = context;
		initAttributeSet(attrs);
		init();

	}

	protected void initAttributeSet(AttributeSet attrs) {


	}

	/**
	 * 初始化一下变量
	 */
	protected abstract void init();


	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		viewWidth = MeasureSpec.getSize(widthMeasureSpec);
		viewHeight = MeasureSpec.getSize(heightMeasureSpec);


		reCalculateSize();

		setMeasuredDimension(viewWidth, viewHeight);
	}


	protected void reCalculateSize() {

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);


		drawCustomView(canvas);
	}

	/**
	 * 绘制自己的视图
	 *
	 * @param canvas
	 */
	protected abstract void drawCustomView(Canvas canvas);


	protected int dip2px(float dipValue) {
		final float scale = mContext.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}
}
