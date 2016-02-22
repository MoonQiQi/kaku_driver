package com.yichang.kaku.tools;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {

	public static final int onDayMillis = 86400000;
    public static final int oneDaySecond = 86400;
    /**
     * 日期格式"yyyy-MM-dd"
     */
    public static DateFormat DateFormatter1 = new SimpleDateFormat(
            "yyyy-MM-dd", Locale.CHINA);
    /**
     * 日期格式"yyyy年MM月dd日"
     */
    public static DateFormat DateFormatter2 = new SimpleDateFormat(
            "yyyy年MM月dd日", Locale.CHINA);
    /**
     * 时间格式"HH:mm:ss"
     */
    public static DateFormat TimeFormatter1 = new SimpleDateFormat("HH:mm:ss",
            Locale.CHINA);
    /**
     * 时间格式"HH:mm"
     */
    public static DateFormat TimeFormatter2 = new SimpleDateFormat("HH:mm",
            Locale.CHINA);

    public static String millis2String(long milliseconds, String dateFormat) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliseconds);
        Date date = calendar.getTime();
        return date2String(date, dateFormat);
    }

    public static String second2String(long second, String dateFormat) {
        return millis2String(second * 1000, dateFormat);
    }

    public static String date2String(Date date, String dateFormat) {
        DateFormat formatter = new SimpleDateFormat(dateFormat, Locale.CHINA);
        return formatter.format(date);
    }

    /**
     * 毫秒秒单位时间戳转成时间字符串,格式"yyyy/mm/dd 周W"
     *
     * @param milliseconds 毫秒
     * @return
     */
    public static String millis2StringWithWeek(long milliseconds) {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        c.setTimeInMillis(milliseconds);
        int year, month, day, week;
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        week = c.get(Calendar.DAY_OF_WEEK);
        StringBuffer buffer = new StringBuffer();
        buffer.append(year);
        buffer.append("/");
        buffer.append(month);
        buffer.append("/");
        buffer.append(day);
        buffer.append(" ");
        buffer.append("周");
        buffer.append(getWeek(week));
        return buffer.toString();
    }

    /**
     * 秒单位时间戳转成时间字符串,格式"yyyy/mm/dd 周W"
     *
     * @param seconds 秒
     * @return
     */
    public static String second2StringWithWeek(long seconds) {
        return millis2StringWithWeek(seconds * 1000);
    }

    /**
     * 毫秒秒单位时间戳转成时间字符串,格式"yyyy-mm-dd 星期几 HH:mm"
     *
     * @param milliseconds 毫秒
     * @return
     */
    public static String calender2DatetimeWithWeek(long milliseconds) {
        Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        c.setTimeInMillis(milliseconds);
        int year, month, day, week, hour, min;
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        week = c.get(Calendar.DAY_OF_WEEK);
        hour = c.get(Calendar.HOUR_OF_DAY);
        min = c.get(Calendar.MINUTE);
        StringBuffer buffer = new StringBuffer();
        buffer.append(year);
        buffer.append("-");
        buffer.append(month);
        buffer.append("-");
        buffer.append(day);
        buffer.append(" ");
        buffer.append("星期");
        buffer.append(getWeek(week));
        buffer.append(" ");
        buffer.append(hour);
        buffer.append(":");
        buffer.append(min);
        return buffer.toString();
    }

    public static int getMonth(int month) {
        return month+1;
    }
    
    public static String getWeek(int week) {
        switch (week) {
            case Calendar.MONDAY:
                return "一";
            case Calendar.TUESDAY:
                return "二";
            case Calendar.WEDNESDAY:
                return "三";
            case Calendar.THURSDAY:
                return "四";
            case Calendar.FRIDAY:
                return "五";
            case Calendar.SATURDAY:
                return "六";
            case Calendar.SUNDAY:
                return "日";
            default:
                return "一";
        }
    }

    /**
     * 使用参数Format将字符串转为Date
     */
    public static long parse(String strDate)
            throws ParseException {
        final String pattern = "yyyy-MM-dd";
        long date = new SimpleDateFormat(pattern).parse(strDate)
                .getTime();
        return date;
    }
    
    /**
     * 获取当月起始时间戳
     * @return
     */
    public static long getCurMonthStart(){
    	Calendar c = Calendar.getInstance();
    	c.set(Calendar.DATE, 1);
//    	c.set(Calendar.HOUR_OF_DAY, 0);
//    	c.set(Calendar.MINUTE, 0);
//    	c.set(Calendar.SECOND, 0);
    	long data = c.getTimeInMillis();
    	return data;
    }
    
    /**
     * 获取当月结束时间戳
     * @return
     */
    public static long getCurMonthEnd(){
    	Calendar c = Calendar.getInstance();
    	//先取下月第一天
    	int month = c.get(Calendar.MONTH)+1;
    	if(month == 13){
    		month = 1;
    		int year = c.get(Calendar.YEAR)+1;
    		c.set(Calendar.YEAR, year);
    	}
    	c.set(Calendar.MONTH, month);
    	c.set(Calendar.DATE, 1);
//    	c.set(Calendar.HOUR_OF_DAY, 0);
//    	c.set(Calendar.MINUTE, 0);
//    	c.set(Calendar.SECOND, 0);
    	//取到毫秒后减1天
    	long data = c.getTimeInMillis() - onDayMillis;
    	return data;
    }
    
    /**
     * 获取指定日期时间戳
     * @return
     */
    public static long getMillis(int year, int month, int day){
    	Calendar c = Calendar.getInstance();
    	c.set(Calendar.YEAR, year);
    	c.set(Calendar.MONTH, month);
    	c.set(Calendar.DATE, day);
//    	c.set(Calendar.HOUR_OF_DAY, 0);
//    	c.set(Calendar.MINUTE, 0);
//    	c.set(Calendar.SECOND, 0);
    	long data = c.getTimeInMillis();
    	return data;
    }
    
    /**
     * 获取指定月份起始时间戳
     * @param year
     * @param month
     * @return
     */
    public static long getMonthStartMillis(int year, int month){
    	return getMillis(year, month, 1);
    }
    
    /**
     * 获取指定月份结束时间戳
     * @param year
     * @param month
     * @return
     */
    public static long getMonthEndMillis(int year, int month){
    	++month;
    	if(month == 13){
    		month = 1;
    		++year;
    	}
    	return getMillis(year, month, 1) - onDayMillis;
    }
    
    public static String dateFormat() {
        String str = null;
        str = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        return str;
    };

    public static String dateFormat(long date) {
        Date d = new Date(date * 1000);
        String str = null;
        str = new SimpleDateFormat("MM月dd日").format(d);
        return str;
    };

    public static boolean isDateExpire() {
        Date date = new Date();
        int hour = date.getHours();
        if (hour > 14) {
            return true;
        }
        return false;
    }
}
