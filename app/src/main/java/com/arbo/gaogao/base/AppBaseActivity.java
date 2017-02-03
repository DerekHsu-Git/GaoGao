package com.arbo.gaogao.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.arbo.lib.base.ui.BaseActivity;


public class AppBaseActivity extends BaseActivity {

	protected Context context;
	protected ImageView backImageView;
	protected TextView titleTextView;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		//getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		//在这里调用

//		StatusBarUtil.enableTranslucentStatusbar(this, R.color.colorPrimaryDark);
		super.onCreate(savedInstanceState);
	//	StatusBarUtil.setCustomStatueView(this, R.color.transparent);
	}

	@Override
	protected int initLayoutID() {
		return 0;
	}

	protected void initTitleView() {
		/*backImageView = findView(R.id.header_left_btn);
		titleTextView = findView(R.id.header_text);
		backImageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackImageClick();
			}
		});*/
	}


	protected void setTitleTextView(int resId) {
		titleTextView.setText(resId);
	}


	protected void onBackImageClick() {

	}

//	@Override
//	public void onWindowFocusChanged(boolean hasFocus) {
//		super.onWindowFocusChanged(hasFocus);
//		if (hasFocus && Build.VERSION.SDK_INT >= 19) {
//			View decorView = getWindow().getDecorView();
//			decorView.setSystemUiVisibility(
//					View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//							| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//							| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//							| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
//							| View.SYSTEM_UI_FLAG_FULLSCREEN
//							| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
//		}
//
//	}
}
