package com.arbo.lib.util;


import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.lang.reflect.Field;



public class DisplayUtil {


	/**
	 * 获得屏幕宽度
	 *
	 * @param context
	 *
	 * @return
	 */
	public static int getScreenWidth(Context context) {

		DisplayMetrics outMetrics = getMetrics(context);
		return outMetrics.widthPixels;
	}

	/**
	 * 获得屏幕宽度
	 *
	 * @param context
	 *
	 * @return
	 */
	public static int getScreenHeight(Context context) {
		DisplayMetrics outMetrics = getMetrics(context);
		return outMetrics.heightPixels;
	}


	private static DisplayMetrics getMetrics(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		return outMetrics;
	}


	/**
	 * 获取手机状态栏高度
	 * @param context
	 * @return
	 */
	public static int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, statusBarHeight = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			statusBarHeight = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return statusBarHeight;
	}

}
