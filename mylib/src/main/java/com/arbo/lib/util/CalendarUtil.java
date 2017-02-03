package com.arbo.lib.util;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;



public class CalendarUtil {

	/**
	 * 取得当前日期是多少周
	 *
	 * @param date
	 *
	 * @return
	 */
	public static int getWeekOfYear(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.MONDAY);
		c.setMinimalDaysInFirstWeek(7);
		c.setTime(date);
		return c.get(Calendar.WEEK_OF_YEAR);
	}

	/**
	 * 得到某一年周的总数
	 *
	 * @param year
	 *
	 * @return
	 */
	public static int getMaxWeekNumOfYear(int year) {
		Calendar c = new GregorianCalendar();
		c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
		return getWeekOfYear(c.getTime());
	}

	/**
	 * 得到某年某周的第一天
	 *
	 * @param year
	 * @param week
	 *
	 * @return
	 */
	public static Date getFirstDayOfWeek(int year, int week) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);
		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);
		return getFirstDayOfWeek(cal.getTime()).getTime();
	}

	/**
	 * 得到某年某周的最后一天
	 *
	 * @param year
	 * @param week
	 *
	 * @return
	 */
	public static Date getLastDayOfWeek(int year, int week) {
		Calendar c = new GregorianCalendar();
		c.set(Calendar.YEAR, year);
		c.set(Calendar.MONTH, Calendar.JANUARY);
		c.set(Calendar.DATE, 1);
		Calendar cal = (GregorianCalendar) c.clone();
		cal.add(Calendar.DATE, week * 7);
		return getLastDayOfWeek(cal.getTime()).getTime();
	}

	/**
	 * 取得指定日期所在周的第一天
	 *
	 * @param date
	 *
	 * @return
	 */
	public static Calendar getFirstDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.SUNDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return c;
	}

	/**
	 * 取得指定日期所在周的最后一天
	 *
	 * @param date
	 *
	 * @return
	 */
	public static Calendar getLastDayOfWeek(Date date) {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.SUNDAY);
		c.setTime(date);
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
		return c;
	}

	/**
	 * 取得当前日期所在周的第一天
	 *
	 * @param
	 *
	 * @return
	 */
	public static Calendar getFirstDayOfWeek() {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.SUNDAY);
		c.setTime(new Date());
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek()); // Monday
		return c;
	}

	/**
	 * 取得当前日期所在周的最后一天
	 *
	 * @param
	 *
	 * @return
	 */
	public static Date getLastDayOfWeek() {
		Calendar c = new GregorianCalendar();
		c.setFirstDayOfWeek(Calendar.SUNDAY);
		c.setTime(new Date());
		c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek() + 6); // Sunday
		return c.getTime();
	}

	public static Calendar getFirstDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal;
	}

	public static Calendar getLastDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal;
	}

	public static Calendar getFirstDayOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_YEAR, 1);
		return cal;
	}

	public static Calendar getLastDayOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.DAY_OF_YEAR, cal.getActualMaximum(Calendar.DAY_OF_YEAR));
		return cal;
	}

	public static int getDayOfMonth(Date date) {
		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date);
		return cal2.get(Calendar.DAY_OF_MONTH);
	}

	/**
	 * 获取日期所在周的第几天，1-->>7,以周日为一周的开始
	 *
	 * @param date
	 *
	 * @return
	 */
	public static int getDayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	public static String getWeekCHA(int weekDay) {
		String[] weekDayStr = new String[]{"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
		return weekDayStr[weekDay];
	}
}

