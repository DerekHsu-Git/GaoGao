package com.arbo.lib.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.Map;


public class NavigateUtil {

	private static String PARCELABLE_EXTRA_KEY = "parce_model";

	public static <T> T getParcelableExtra(Activity activity) {
		Parcelable parcelable = activity.getIntent().getParcelableExtra(NavigateUtil.PARCELABLE_EXTRA_KEY);
		activity = null;
		return (T) parcelable;
	}


	protected static void overlay(Context context, Class<? extends Activity> targetClazz) {
		Intent intent = new Intent(context, targetClazz);
		context.startActivity(intent);
		context = null;
	}

	/**
	 * @param context
	 * @param targetClazz
	 */
	protected static void forward(Context context, Class<? extends Activity> targetClazz) {
		Intent intent = new Intent(context, targetClazz);
		context.startActivity(intent);
		if (isActivity(context)) {
			return;
		}
		((Activity) context).finish();
		context = null;
	}

	/**
	 * 带有参数的跳转
	 *
	 * @param context
	 * @param targetClazz
	 * @param params
	 */
	protected static void overlay(Context context, Class<? extends Activity> targetClazz, Map<String, String> params) {
		Intent intent = new Intent(context, targetClazz);
		setIntentInfo(intent, params);
		context.startActivity(intent);
		context = null;
	}

	/**
	 * 带有参数的跳转
	 *
	 * @param context
	 * @param targetClazz
	 * @param params
	 */
	protected static void forward(Context context, Class<? extends Activity> targetClazz, Map<String, String> params) {
		Intent intent = new Intent(context, targetClazz);
		setIntentInfo(intent, params);
		context.startActivity(intent);
		if (isActivity(context)) return;
		((Activity) context).finish();
		context = null;
	}

	//********************************************************************************************//
	//********************************************************************************************//
	//********************************************************************************************//


	private static void overlay(Context context, Class<? extends Activity> targetClazz, int flags,
								Parcelable parcelable) {
		Intent intent = new Intent(context, targetClazz);
		setFlags(intent, flags);
		putParcelableExtra(intent, parcelable);
		context.startActivity(intent);
		context = null;
	}

	private static void overlay(Context context, Class<? extends Activity> targetClazz,
								Parcelable parcelable) {
		Intent intent = new Intent(context, targetClazz);
		putParcelableExtra(intent, parcelable);
		context.startActivity(intent);
		context = null;
	}

	private static void overlay(Context context, Class<? extends Activity> targetClazz,
								Serializable serializable) {
		Intent intent = new Intent(context, targetClazz);
		putSerializableExtra(intent, serializable);
		context.startActivity(intent);
		context = null;
	}

	private static void forward(Context context, Class<? extends Activity> targetClazz,
								int flags, Parcelable parcelable) {
		Intent intent = new Intent(context, targetClazz);
		setFlags(intent, flags);
		intent.putExtra(PARCELABLE_EXTRA_KEY, parcelable);
		context.startActivity(intent);
		if (isActivity(context)) return;
		((Activity) context).finish();
		context = null;
	}

	protected static void forward(Context context, Class<? extends Activity> targetClazz,
								  Parcelable parcelable) {
		Intent intent = new Intent(context, targetClazz);
		putParcelableExtra(intent, parcelable);
		context.startActivity(intent);
		if (isActivity(context)) return;
		((Activity) context).finish();
		context = null;
	}

	private static void forward(Context context, Class<? extends Activity> targetClazz,
								Serializable serializable) {
		Intent intent = new Intent(context, targetClazz);
		putSerializableExtra(intent, serializable);
		context.startActivity(intent);
		if (isActivity(context)) return;
		((Activity) context).finish();
		context = null;
	}


	private static void startForResult(Context context, Class<? extends Activity> targetClazz,
									   int flags) {
		Intent intent = new Intent(context, targetClazz);
		if (isActivity(context)) return;
		((Activity) context).startActivityForResult(intent, flags);
		context = null;
	}

	private static void startForResult(Context context, Class<? extends Activity> targetClazz,
									   int flags, Parcelable parcelable) {
		Intent intent = new Intent(context, targetClazz);
		if (isActivity(context)) return;
		putParcelableExtra(intent, parcelable);
		((Activity) context).startActivityForResult(intent, flags);
		context = null;
	}

	private static void setResult(Context context, Class<? extends Activity> targetClazz,
								  int flags, Parcelable parcelable) {
		Intent intent = new Intent(context, targetClazz);
		setFlags(intent, flags);
		putParcelableExtra(intent, parcelable);
		if (isActivity(context)) return;
		((Activity) context).setResult(flags, intent);
		((Activity) context).finish();
	}

	/**
	 * @param context
	 *
	 * @return
	 */
	private static boolean isActivity(Context context) {

		if (!(context instanceof Activity)) {
			return true;
		} else {
			return false;
		}
	}

	private static void setFlags(Intent intent, int flags) {
		if (flags < 0) return;
		intent.setFlags(flags);
	}

	private static void putParcelableExtra(Intent intent, Parcelable parcelable) {
		if (parcelable == null) return;
		intent.putExtra(PARCELABLE_EXTRA_KEY, parcelable);
	}

	private static void putSerializableExtra(Intent intent, Serializable serializable) {
		if (serializable == null) return;
		intent.putExtra(PARCELABLE_EXTRA_KEY, serializable);
	}

	private static void setIntentInfo(Intent intent, Map<String, String> paramsMap) {
		//解析封装参数
		if (null != paramsMap && paramsMap.size() > 0) {
			for (String key : paramsMap.keySet()) {
				intent.putExtra(key, paramsMap.get(key));
			}
		}
	}
}
