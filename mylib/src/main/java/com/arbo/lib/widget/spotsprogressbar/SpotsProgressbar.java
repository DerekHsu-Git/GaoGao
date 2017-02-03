package com.arbo.lib.widget.spotsprogressbar;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.arbo.lib.R;


public class SpotsProgressbar extends FrameLayout {

	private final String TAG = "SpotsProgressbar";

	/**
	 * the default number of Spot
	 */
	private static final int DEFAULT_COUNT = 5;
	/**
	 * the default delay between two Spot
	 */
	private static final int DEFAULT_DELAY = 150;
	/**
	 * the default animator duration
	 */
	private static final int DEFAULT_DURATION = 1500;

	/**
	 * the number of Spot
	 */
	private int mSpotsCount;
	/**
	 * the spot size
	 */
	private int mSpotsSize;
	/**
	 * the spot shape resource id
	 */
	private int mSpotsResId;
	/**
	 * the spot animator duration
	 */
	private int mDuration;
	/**
	 * the animator delay between two spot
	 */
	private int mDelay;

	/**
	 * the SpotsProgressbar view width
	 */
	private int mWidth;

	/**
	 * SpotAnimatedView that SpotsProgressbar contain
	 */
	private SpotAnimatedView[] mSpotViews;

	/**
	 * Animator that SpotAnimatedView combine
	 */
	private Animator[] mAnimators;
	private AnimatorSet mAnimatorSet;

	private AnimatorListenerAdapter animatorListenerAdapter = new AnimatorListenerAdapter() {
		@Override
		public void onAnimationEnd(Animator animation) {
			startAnimate();
		}
	};

	public SpotsProgressbar(Context context) {
		this(context, null);
	}

	public SpotsProgressbar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SpotsProgressbar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(attrs, defStyleAttr, 0);
	}

	@TargetApi(Build.VERSION_CODES.LOLLIPOP)
	public SpotsProgressbar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
		super(context, attrs, defStyleAttr, defStyleRes);
		init(attrs, defStyleAttr, defStyleRes);
	}

	// init the style attr
	private void init(AttributeSet attrs, int defStyleAttr, int defStyleRes) {

		TypedArray a = getContext().getTheme().obtainStyledAttributes(
				attrs,
				R.styleable.SpotsProgressbar,
				defStyleAttr, defStyleRes);

		mSpotsCount = a.getInt(R.styleable.SpotsProgressbar_sp_spot_count, DEFAULT_COUNT);
		mSpotsSize = a.getDimensionPixelSize(R.styleable.SpotsProgressbar_sp_spot_size, -1);
		if(mSpotsSize == -1) {
			mSpotsSize = getContext().getResources().getDimensionPixelSize(R.dimen.sport_progressbar_sport_size);
		}
		mSpotsResId = a.getResourceId(R.styleable.SpotsProgressbar_sp_spot_shape, 0);
		mDuration = a.getInt(R.styleable.SpotsProgressbar_sp_duration, DEFAULT_DURATION);
		mDelay = a.getInt(R.styleable.SpotsProgressbar_sp_delay, DEFAULT_DELAY);
		a.recycle();

	}

	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);

		if(changed) {
			// what i want is that when the layout is done then initProgress
			int width = getWidth();
			if(width!= 0 && width != mWidth) {
				mWidth = width;
				initProgress();
			}
		}

	}

	//
	private void initProgress() {
		if(mSpotViews == null) {
			mSpotViews = new SpotAnimatedView[mSpotsCount];
		}
		int progressWidth = mWidth;

		for (int i = 0; i < mSpotsCount; i++) {
			SpotAnimatedView v = new SpotAnimatedView(getContext());
			if(mSpotsResId != 0) { // set spot shape
				v.setBackgroundResource(mSpotsResId);
			} else {
				v.setBackgroundResource(R.drawable.spot);
			}
			v.setTarget(progressWidth);
			v.setXFactor(-1f);
			this.addView(v, mSpotsSize, mSpotsSize); // add view
			mSpotViews[i] = v;
		}

		startAnimate();
	}

	// start animate or restart if when animatorListenerAdapter onAnimationEnd() is called.
	private void startAnimate() {
		if (mAnimators == null) {
			mAnimators = createAnimations();
		}
		mAnimatorSet = new AnimatorSet();
		mAnimatorSet.playTogether(mAnimators);
		mAnimatorSet.addListener(animatorListenerAdapter);
		mAnimatorSet.start();
	}

	private Animator[] createAnimations() {
		Animator[] animators = new Animator[mSpotsCount];
		for (int i = 0; i < mSpotsCount; i++) {
			Animator move = ObjectAnimator.ofFloat(mSpotViews[i], "xFactor", 0.0f, 1.0f);
			move.setDuration(mDuration);
			move.setInterpolator(new DecelerateAccelerateInterpolator());
			move.setStartDelay(mDelay * i);
			animators[i] = move;
		}
		return animators;
	}

	@Override
	protected void onVisibilityChanged(View changedView, int visibility) {
		super.onVisibilityChanged(changedView, visibility);
		if(isEnabled()) {
			Log.d(TAG, "isEnabled");
			if(visibility == View.VISIBLE) {
				if(mAnimatorSet != null) {
					mAnimatorSet.end();
					mAnimatorSet.cancel();
				}
				if(mSpotViews != null) {
					startAnimate();
				}
			} else {
				if(mAnimatorSet != null) {
					mAnimatorSet.end();
					mAnimatorSet.cancel();
				}
			}
		}

	}

}
