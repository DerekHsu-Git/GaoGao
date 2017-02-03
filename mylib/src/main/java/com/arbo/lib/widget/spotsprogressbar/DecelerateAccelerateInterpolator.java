package com.arbo.lib.widget.spotsprogressbar;

import android.animation.TimeInterpolator;



public class DecelerateAccelerateInterpolator implements TimeInterpolator {

	@Override
	public float getInterpolation(float input) {
		return (float) (Math.asin(2 * input - 1) / Math.PI + 0.5);
	}
}
