package com.arbo.lib.util;


import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * com.fdkj.libs.utils
 */

public class StatusBarUtil {

	/**
	 * @param activity
	 * @param color
	 */
	public static void setCustomStatueView(Activity activity, int color) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			//使状态栏透明
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

			// 如果想要给状态栏加点颜色，例如加点透明的阴影，就需要加上下面的三行代码
			// 这是生成一个状态栏大小的矩形，给这个矩形添加颜色，添加 statusView 到布局中
			View statusView = createStatusView(activity, color);
			ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
			decorView.addView(statusView);


			// 设置根布局的参数
			ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content)).getChildAt(0);
			rootView.setFitsSystemWindows(false);
			rootView.setClipToPadding(true);
		}
	}


	/**
	 * 这个生成一个状态栏大小的矩形，给这个矩形，添加 statusView 到布局中
	 *
	 * @param color
	 *
	 * @return
	 */
	private static View createStatusView(Activity activity, int color) {
		// 获得状态栏高度
		int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
		int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);

		// 绘制一个和状态栏一样高的矩形
		View statusView = new View(activity);
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
				statusBarHeight);
		statusView.setLayoutParams(params);
		statusView.setBackgroundColor(color);
		statusView.getBackground().setAlpha(0);
		return statusView;
	}


	/**
	 * 启用 透明状态栏
	 *
	 * @param activity
	 */
	public static void enableTranslucentStatusbar(Activity activity, int color) {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

			LogUtil.e("Build.VERSION_CODES.KITKAT");
			Window window = activity.getWindow();
			// Translucent status bar
			window.setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			// Translucent navigation bar
			window.setFlags(
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
//
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//
//			LogUtil.e("Build.VERSION_CODES.LOLLIPOP");
//			Window window = activity.getWindow();
//			window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
//					| WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//			window.getDecorView().setSystemUiVisibility(/*View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//					| */View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//					| View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//			window.setStatusBarColor(color);
//			window.setNavigationBarColor(color);
//		}

	}


}
