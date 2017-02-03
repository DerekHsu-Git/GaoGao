package com.arbo.lib.util;


import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class DateUtil {
	private static final int SELECT_DAY = 0, SELECT_WEEK = 1, SELECT_MONTH = 2, SELECT_YEAR = 3;

	// String [] time_format = {"yyyy-MM-dd HH:mm:ss"};

	/**
	 * 获取当时间的时间戳
	 *
	 * @return
	 */
	public static int getNowUnixTime() {
		Date date = new Date();
		int currentTime = (int) ((date.getTime()) / 1000);

		return currentTime;
	}

	public static int getUnixTime(Date date) {
		return (int) (date.getTime() / 1000);
	}

	/**
	 * 将日期格式的字符串转为时间戳
	 *
	 * @param formatTime : 需要转换的时间字符串
	 * @param format     ： 字符串的格式
	 *
	 * @return
	 */
	public static int getUnixByFomateStr(String formatTime, String format) {
		int re_time = 0;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		Date d;
		try {
			d = sdf.parse(formatTime);
			re_time = (int) (d.getTime() / 1000);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return re_time;
	}

	/**
	 * 通过时间戳得到date
	 *
	 * @param time 时间戳
	 *
	 * @return
	 */
	public static Date getDateByUnixTime(int unixTime) {
		Date date = new Date();
		date.setTime(unixTime * 1000L);
		return date;
	}

	/**
	 * 把时间戳转换成字符串
	 *
	 * @param unixTimeTick : 需要转换的时间戳
	 * @param format       : 转换过来的时间格式
	 *
	 * @return
	 */
	public static String getFormatStrByUnix(int unixTimeTick, String format) {
		Date de = new Date(unixTimeTick * 1000L);
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		String str = simpleDateFormat.format(de);
		return str;
	}

	/**
	 * 字符串转换成统时间.
	 *
	 * @param formatStr 协议中的时间串.
	 * @param format    : 字符格式
	 *
	 * @return ： 时间
	 */
	public static Date getDateByFormatStr(String formatStr, String format) {
		Date dt = new Date();

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			dt = sdf.parse(formatStr);
		} catch (ParseException e) {

		}
		return dt;
	}

	/**
	 * 日期格式化为字符串
	 *
	 * @param date      系统时间
	 * @param formatStr 格式化字符串，如"yyyy-MM-dd HH:mm:ss"
	 *
	 * @return 格式化后的时间串.
	 */
	public static String getStringByDate(Date date, String formatStr) {

		DateFormat format = new SimpleDateFormat(formatStr);
		String str = format.format(date);
		return str;
	}

	private static SimpleDateFormat sdf;

	/**
	 * 得到现在时间
	 *
	 * @return
	 */
	public static Date getNow() {
		Date currentTime = new Date();
		return currentTime;
	}

	/**
	 * 获取当前时间的字符串
	 *
	 * @param fromatStr :字符串的格式
	 *
	 * @return
	 */
	public static String getNowStringTime(String fromatStr) {
		Date currentTime = new Date();
		sdf = new SimpleDateFormat(fromatStr);
		return sdf.format(currentTime);
	}

	/**
	 * 把一种格式的时间字符串转换成另一种格式的字符串
	 *
	 * @param dayString    需要转换的字符串
	 * @param beforeFormat 转换前的格式
	 * @param toFormat     转换后的格式
	 *
	 * @return
	 */
	public static String stringToString(String dayString, String beforeFormat,
										String toFormat) {
		sdf = new SimpleDateFormat(beforeFormat);
		Date date = null;
		try {
			date = sdf.parse(dayString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		sdf = new SimpleDateFormat(toFormat);
		dayString = sdf.format(date);
		return dayString;

	}

	/**
	 * 判断二个时间是否在同一个周
	 *
	 * @param date1 时间1
	 * @param date2 时间2
	 *
	 * @return
	 */
	public static boolean isSameWeekDates(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		int subYear = cal1.get(Calendar.YEAR) - cal2.get(Calendar.YEAR);
		if (0 == subYear) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
					.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (1 == subYear && Calendar.DECEMBER == cal2.get(Calendar.MONTH)) {

			// 如果12月的最后一周横跨来年第一周的话则最后一周即算做来年的第一周
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
					.get(Calendar.WEEK_OF_YEAR))
				return true;
		} else if (-1 == subYear && Calendar.DECEMBER == cal1.get(Calendar.MONTH)) {
			if (cal1.get(Calendar.WEEK_OF_YEAR) == cal2
					.get(Calendar.WEEK_OF_YEAR))
				return true;
		}
		return false;
	}


	public static boolean isSameYearDates(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		return (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR));
	}

	public static boolean isSameMonthDates(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		if (cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR)) {
			if (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 得到距离指定日期指定天数的日期
	 *
	 * @param cl       指定的日期
	 * @param isBefore 往前还是往后
	 * @param days     指定距离天数
	 *
	 * @return
	 */
	public static Calendar getCalendarByCalendar(Calendar cl, boolean isBefore,
												 int days) {
		// int type = 1;
		if (isBefore) {
			days = -1 * days;
		}

		// 使用roll方法进行向前回滚
		// cl.roll(Calendar.DATE, days);
		// 使用set方法直接进行设置

		int day = cl.get(Calendar.DATE);
		cl.set(Calendar.DATE, day + days);
		return cl;
	}

	/**
	 * 判断两个日期是否一样
	 *
	 * @param cl1 第一个
	 * @param cl2 第二个
	 *
	 * @return
	 */
	public static boolean getIsSampleDay(Calendar cl1, Calendar cl2) {

		Date date1 = cl1.getTime();
		Date date2 = cl2.getTime();

		String dateStr1 = getStringByDate(date1, "yyyyMMdd");
		String dateStr2 = getStringByDate(date2, "yyyyMMdd");
		if (dateStr1.equals(dateStr2)) {
			return true;
		} else {
			return false;
		}
	}


	/**
	 * 获取日期:格式为"yyyy-MM-dd"
	 *
	 * @param year
	 * @param month
	 * @param dayOfMonth
	 *
	 * @return
	 */
	public static String getFormatDate(int year, int month, int dayOfMonth) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(year);
		buffer.append("-");
		buffer.append(month > 9 ? month : ("0" + month));
		buffer.append("-");
		buffer.append(dayOfMonth > 9 ? dayOfMonth : ("0" + dayOfMonth));
		return buffer.toString();
	}


	/**
	 * 计算两个日期之间相差的天数
	 *
	 * @param smallDate 较小的时间
	 * @param bigDate   较大的时间
	 *
	 * @return 相差天数
	 *
	 * @throws ParseException
	 */
	public static int getDaysBetween(Date smallDate, Date bigDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		smallDate = sdf.parse(sdf.format(smallDate));
		bigDate = sdf.parse(sdf.format(bigDate));
		Calendar cal = Calendar.getInstance();
		cal.setTime(smallDate);
		long time1 = cal.getTimeInMillis();
		cal.setTime(bigDate);
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 字符串的日期格式的计算
	 */
	public static int getDaysBetween(String smallDate, String bigDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(sdf.parse(smallDate));
		long time1 = cal.getTimeInMillis();
		cal.setTime(sdf.parse(bigDate));
		long time2 = cal.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);

		return Integer.parseInt(String.valueOf(between_days));
	}

	/**
	 * 比较两个日期之间的大小
	 *
	 * @param d1
	 * @param d2
	 *
	 * @return 前者大于后者返回true 反之false
	 */
	public static int compareDate(Date d1, Date d2) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(d1);
		c2.setTime(d2);

		int result = c1.compareTo(c2);
		if (result == 0)
			return 0;
		else
			return result;
	}


	public static boolean isSameDayDates(Date selectDate, Date currentDate) {
		int sub = checkDateValid(selectDate, currentDate);
		return (sub == 0);
	}

	//比较两个日期的的差距
	private static int checkDateValid(Date selectDate, Date currentDate) {
		int sub = 0;
		try {
			sub = getDaysBetween(selectDate, currentDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return sub;
	}

	//日、周、月、年的前翻，后翻
	public static Date updateCurrentDate(Date date, boolean isPre, int type) {
		Calendar newDate = Calendar.getInstance();
		newDate.setTime(date);
		int tempVal;
		tempVal = isPre ? -1 : 1;
		switch (type) {
			case SELECT_DAY:
				newDate.add(Calendar.DAY_OF_YEAR, tempVal);
				break;
			case SELECT_WEEK:
				newDate.add(Calendar.WEEK_OF_YEAR, tempVal);
				break;
			case SELECT_MONTH:
				newDate.add(Calendar.MONTH, tempVal);
				break;
			case SELECT_YEAR:
				newDate.add(Calendar.YEAR, tempVal);
				break;
		}
		return newDate.getTime();
	}

	public static int getMinFromClock(int hour, int min) {
		return hour * 60 + min;
	}

	public static String getStrFromMin(int totalMin) {
		int hour = totalMin / 60;
		int min = totalMin % 60;
		String hourStr = hour < 10 ? "0" + hour : "" + hour;
		String minStr = min < 10 ? "0" + min : "" + min;
		return hourStr + ":" + minStr;
	}


	//将时间标号--> 10:30的形式
	public static String getTimeStr(int format, int time) {//0--47  format 1 --12小时  0--24小时
		String min,showH;
		int h;
		if (format == 0) {
			min = (time % 2 == 0) ? "00" : "30";
			h = time / 2;
			showH = (h<10)?("0"+h):(""+h);
			return showH + ":" + min;
		} else {
			if (time <= 24) {//早上
				min = (time % 2 == 0) ? "00" : "30";
				h = time / 2;
				showH = (h<10)?("0"+h):(""+h);
				return showH + ":" + min + " 上午";
			} else {
				time -= 24;
				min = (time % 2 == 0) ? "00" : "30";
				h = time / 2;
				showH = (h<10)?("0"+h):(""+h);
				return showH + ":" + min + " 下午";
			}
		}
	}

	//同上，仅用作计步当天详情的时间转换  （柱状图使用）
	public static String getTimeStrForStep(int format, int time) {//0--47  format 1 --12小时  0--24小时
		String min,showH;
		int h;
		if (format == 0) {
			min = (time % 2 == 0) ? "00" : "30";
			h = time / 2;
			showH = (h<10)?("0"+h):(""+h);
			return showH + ":" + min;
		} else {
			if (time < 24) {//早上
				min = (time % 2 == 0) ? "00" : "30";
				h = time / 2;
				showH = (h<10)?("0"+h):(""+h);
				if(time == 0){
					return "am "+showH + ":" + min ;
				}else {
					return showH + ":" + min;
				}
			} else {
				time -= 24;
				min = (time % 2 == 0) ? "00" : "30";
				h = time / 2;
				showH = (h<10)?("0"+h):(""+h);
				if(time != 0){
					return showH + ":" + min ;
				}else{
					return "pm "+showH + ":" + min;
				}
			}
		}
	}

//
//	public static String minToHHmm(int min){
//		int HH = 0,mm=0;
//		HH = min/60;
//		mm = min%60;
//		return HH+"时"+mm+"分";
//	}

}
