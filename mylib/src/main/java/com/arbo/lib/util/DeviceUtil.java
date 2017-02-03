package com.arbo.lib.util;


import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;

import java.io.File;

/**
 * @name 设备相关的工具类
 */
public class DeviceUtil {


	private static String TAG = "DeviceUtil";

	/**
	 * 获取系统的存储路径
	 *
	 * @return
	 */
	public static String getStoragePath() {
		String sdCardPath = "";

		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState())) {
			sdCardPath = Environment.getExternalStorageDirectory()
					.getAbsolutePath();
		} else {
			sdCardPath = Environment.getRootDirectory().getAbsolutePath();
		}
		return sdCardPath;
	}

	/**
	 * @param context
	 *
	 * @return
	 */
	public static boolean isNetworkConnected(Context context) {
		if (context != null) {
			ConnectivityManager mConnectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo mNetworkInfo = mConnectivityManager
					.getActiveNetworkInfo();
			if (mNetworkInfo != null) {
				return mNetworkInfo.isAvailable();
			}
		}
		return false;
	}

	/**
	 * 判断能否使用网络
	 *
	 * @param context
	 *
	 * @return
	 */
	public static boolean isHasNetWork(Context context) {
		ConnectivityManager conMan = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo.State mobile = conMan.getNetworkInfo(
				ConnectivityManager.TYPE_MOBILE).getState();
		NetworkInfo.State wifi = conMan.getNetworkInfo(
				ConnectivityManager.TYPE_WIFI).getState();

		if (mobile == NetworkInfo.State.CONNECTED
				|| wifi == NetworkInfo.State.CONNECTED) {
			return true;
		}
		return false;
	}

	/**
	 * 检测网络连接是否可用
	 *
	 * @param context
	 *
	 * @return
	 */
	@SuppressWarnings("null")
	public static boolean checkConnection(Context context) {

		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

		if (networkInfo != null || !networkInfo.isConnected()) {
			return false;
		}
		if (networkInfo.isRoaming()) {
			return true;
		}
		return true;
	}

	/**
	 * @param context
	 *
	 * @return
	 */
	public static boolean isWifi(Context context) {

		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();

		if (activeNetInfo != null
				&& activeNetInfo.getTypeName().endsWith("WIFI")) {
			return true;
		}
		return false;
	}

	/**
	 * gps是否可用
	 *
	 * @param context
	 *
	 * @return
	 */

	public static boolean isGpsEnable(Context context) {
		LocationManager locationManager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
	}

	/**
	 * 获取手机唯一识别码
	 *
	 * @param context
	 *
	 * @return
	 */
	public static String getIMEI(Context context) {
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

	/**
	 * 判断是否有sd卡
	 *
	 * @return
	 */
	public static boolean isHasSDCard() {
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);
	}

	/**
	 * 获取当前的手机号
	 *
	 * @return
	 */
	public static String getLocalNumber(Context mContext) {
		TelephonyManager tManager = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);
		String number = tManager.getLine1Number();
		return number;
	}


	/**
	 * 获取IMSI
	 *
	 * @param context
	 *
	 * @return
	 */
	public static String getIMSI(Context context) {
		String IMSI = "";
		try {
			TelephonyManager telephonyManager = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			IMSI = telephonyManager.getSubscriberId();
		} catch (Exception ex) {

		}
		return IMSI;
	}

	/**
	 * SDCARD是否存在
	 **/
	public static boolean externalMemoryAvailable() {
		return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
	}

	/**
	 * 获取手机内部剩余存储空间
	 *
	 * @return
	 */
	public static long getAvailableInternalMemorySize() {
		File path = Environment.getDataDirectory();
		StatFs stat = new StatFs(path.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();
		return availableBlocks * blockSize;
	}





	/**
	 * 统计指定目录下的子文件的大小（字节数），包括子孙目录下的所有文件
	 *
	 * @param baseDir
	 * @return
	 */
	public static long totalFileSize(File baseDir) {
		long size = 0;
		if ((baseDir != null) && (baseDir.isDirectory())) {
			File[] subs = baseDir.listFiles();
			int length = subs == null ? 0 : subs.length;
			for (int i = 0; i < length; i++) {
				File sub = subs[i];
				if (sub.isFile()) {
					size += sub.length();
				} else {
					size += totalFileSize(sub);
				}
			}
		}
		return size;
	}


	/**
	 * 刪除指定目录下的所有文件，包括子孙目录下的文件，并返回被删除的文件数量
	 *
	 * @param baseDir
	 * @return 被删除的文件数量
	 */
	public static int clearCacheFolder(File baseDir) {
		int count = 0;
		if ((baseDir != null) && (baseDir.isDirectory())) {
			File[] subs = baseDir.listFiles();
			int length = subs == null ? 0 : subs.length;
			for (int i = 0; i < length; i++) {
				File sub = subs[i];
				if (sub.isFile()) {
					if (sub.delete()) {
						count++;
					}
				} else {
					if ((!sub.getName().equals("smiley"))) {
						count += clearCacheFolder(sub);
					}
				}
			}
		}
		return count;
	}
}
