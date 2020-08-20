package com.xk.android.global;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeOpe {
    static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 将时间转化为Date形式
     * @param time
     * @return
     */
    public static Date formatTime(String time){
        try {
            Date date = format.parse(time);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 检测是否到达指定时间
     * @param date
     * @return true表示到达指定时间，false表示未到达
     */
    public static boolean arriveTime(String date) {
        Date schedule = formatTime(date);
        Date current = new Date();
        if (current.after(schedule)) {
            return true;
        }
        return false;
    }

    /**
     * 获取时间差
     * @param fromTime
     * @param toTime
     * @return -1表示异常，否则返回时间差（微秒）
     */
    public static long getMicroSecondsGap(String fromTime, String toTime){
        try {
            long from = format.parse(fromTime).getTime();
            long to = format.parse(toTime).getTime();
            long micros = to - from;
            return micros;
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 获取当前年月日
     * @return 返回年月日字符串
     */
    public static String getCurDay(){
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String res = ft.format(date);
        return res;
    }

    /**
     * 获取当前时分秒
     * @return
     */
    public static String getCurTime(){
        SimpleDateFormat ft = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String res = ft.format(date);
        return res;
    }

    /**
     * 获取当前完整时间字符串
     * @return
     */
    public static String getCurDate(){
        Date date = new Date();
        String res = format.format(date);
        return res;
    }

    /**
     * 拼接日期时间
     * @param date 年月日
     * @param time 时分秒
     * @return 拼接后的日期字符串
     */
    public static String jointDateTime(String date, String time) {
        return date + " " + time;
    }

    /**
     * 判断当前字符串日期是否为周末
     * @param dateS
     * @return
     */
    public static boolean isWeekend(String dateS) {
        try {
            Date date = format.parse(dateS);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            if (calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
                    calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }
}
