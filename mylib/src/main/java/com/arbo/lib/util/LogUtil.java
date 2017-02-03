package com.arbo.lib.util;


import android.util.Log;


public class LogUtil {

	private static String TAG = "GetFit_Log";

	public static void e(String msg) {
		Log.e(TAG, getFormatText(msg));
	}

	public static void d(String msg) {
		Log.d(TAG, getFormatText(msg));
	}

	public static void i(String msg) {
		Log.i(TAG, getFormatText(msg));
	}

	private static String getFormatText(String msg) {
		return "{ " + msg + " }";
	}

}
