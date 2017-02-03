package com.arbo.lib.util;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;



public class AppUtil {


	/**
	 * 获取当前进程名称
	 *
	 * @param context
	 *
	 * @return
	 */
	public static String getCurrentProcessName(Context context) {
		int pid = android.os.Process.myPid();
		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
			if (appProcess.pid == pid) {
				return appProcess.processName;
			}
		}
		return null;
	}

	/*
	* 获取当前程序的版本号
	*/
	public static String getVersionCode(Context context) throws Exception {
		// 获取packagemanager的实例
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo = packageManager.getPackageInfo(
				context.getPackageName(), 0);
		return packInfo.versionName.toString();
	}


	/**
	 * 获取手机唯一识别码
	 *
	 * @param context
	 *
	 * @return
	 */
	public static String getMAC(Context context) {
		String MAC = "";
		try {
			TelephonyManager telephonyManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			MAC = telephonyManager.getDeviceId();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return MAC;
	}


//	public static String getDeviceInfo(Context context) {
//		try {
//			org.json.JSONObject json = new org.json.JSONObject();
//			TelephonyManager tm = (TelephonyManager) context
//					.getSystemService(Context.TELEPHONY_SERVICE);
//			String device_id = null;
//			if (checkPermission(context, Manifest.permission.READ_PHONE_STATE)) {
//				device_id = tm.getDeviceId();
//			}
//			String mac = null;
//			FileReader fstream = null;
//			try {
//				fstream = new FileReader("/sys/class/net/wlan0/address");
//			} catch (FileNotFoundException e) {
//				fstream = new FileReader("/sys/class/net/eth0/address");
//			}
//			BufferedReader in = null;
//			if (fstream != null) {
//				try {
//					in = new BufferedReader(fstream, 1024);
//					mac = in.readLine();
//				} catch (IOException e) {
//				} finally {
//					if (fstream != null) {
//						try {
//							fstream.close();
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//					}
//					if (in != null) {
//						try {
//							in.close();
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//					}
//				}
//			}
//			json.put("mac", mac);
//			if (TextUtils.isEmpty(device_id)) {
//				device_id = mac;
//			}
//			if (TextUtils.isEmpty(device_id)) {
//				device_id = android.provider.Settings.Secure.getString(context.getContentResolver(),
//						android.provider.Settings.Secure.ANDROID_ID);
//			}
//			json.put("device_id", device_id);
//			return json.toString();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}


//	public static boolean checkPermission(Context context, String permission) {
//		boolean result = false;
//		if (Build.VERSION.SDK_INT >= 23) {
//			try {
//				Class<?> clazz = Class.forName("android.content.Context");
//				Method method = clazz.getMethod("checkSelfPermission", String.class);
//				int rest = (Integer) method.invoke(context, permission);
//				if (rest == PackageManager.PERMISSION_GRANTED) {
//					result = true;
//				} else {
//					result = false;
//				}
//			} catch (Exception e) {
//				result = false;
//			}
//		} else {
//			PackageManager pm = context.getPackageManager();
//			if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
//				result = true;
//			}
//		}
//		return result;
//	}


	public static boolean checkUserPermission(Context context, String permission) {
		boolean result = false;
		if (Build.VERSION.SDK_INT >= 23) {
			try {
				int rest = ContextCompat.checkSelfPermission(context,
						permission);
				if (rest == PackageManager.PERMISSION_GRANTED) {
					result = true;
				} else {
					result = false;
				}
			} catch (Exception e) {
				result = false;
			}
		} else {
			result = true;
		}
		return result;
	}

}
