package com.arbo.lib.widget.spotsprogressbar;

import android.content.Context;
import android.view.View;



public class SpotAnimatedView extends View {

	private int target;

	public SpotAnimatedView(Context context) {
		super(context);
	}

	public float getXFactor() {
		return getX() / target;
	}

	public void setXFactor(float xFactor) {
		setX(target * xFactor);
	}

	public void setTarget(int target) {
		this.target = target;
	}

	public int getTarget() {
		return target;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		setMeasuredDimension(measure(widthMeasureSpec, true), measure(heightMeasureSpec, false));
	}

	private int measure(int measureSpec, boolean isWidth) {
		int result;
		int mode = MeasureSpec.getMode(measureSpec);
		int size = MeasureSpec.getSize(measureSpec);
		int padding = isWidth ? getPaddingLeft() + getPaddingRight() : getPaddingTop() + getPaddingBottom();
		if (mode == MeasureSpec.EXACTLY) {
			result = size;
		} else {
			result = isWidth ? getSuggestedMinimumWidth() : getSuggestedMinimumHeight();
			result += padding;
			if (mode == MeasureSpec.AT_MOST) {
				if (isWidth) {
					result = Math.max(result, size);
				} else {
					result = Math.min(result, size);
				}
			}
		}
		return result;
	}

}
