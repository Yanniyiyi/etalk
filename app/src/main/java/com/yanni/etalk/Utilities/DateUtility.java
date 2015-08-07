package com.yanni.etalk.Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by macbookretina on 5/07/15.
 */
public class DateUtility {


    private static SimpleDateFormat serverMonthFormat = new SimpleDateFormat("yyyy-MM");
    private static SimpleDateFormat serverDateFormat = new SimpleDateFormat("yyy-MM-dd");
    private static SimpleDateFormat compareDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private static Calendar monthCalendar;

    private static Calendar todaysDate = Calendar.getInstance();
    private static final int Year = todaysDate.get(Calendar.YEAR);
    private static final int Month = todaysDate.get(Calendar.MONTH);
    private static final int DayOfMonth = todaysDate.get(Calendar.DAY_OF_MONTH);
    private static Calendar dateCalendar;
    private static Date currentMonth;
    private static Date currentDate;
    private static String MON = "周一";
    private static String TUE = "周二";
    private static String WED = "周三";
    private static String THU = "周四";
    private static String FRI = "周五";
    private static String SAT = "周六";
    private static String SUN = "周日";


    public static String getCurrentMonth() {
        monthCalendar = Calendar.getInstance();
        currentMonth = monthCalendar.getTime();
        return serverMonthFormat.format(currentMonth);
    }

    public static String getCurrentDate() {
        dateCalendar = Calendar.getInstance();
        currentDate = dateCalendar.getTime();
        return serverDateFormat.format(currentDate);
    }

    public static String getLastMonth() {
        monthCalendar.add(Calendar.MONTH, -1);
        return serverMonthFormat.format(monthCalendar.getTime());
    }

    public static String getNextMonth() {
        monthCalendar.add(Calendar.MONTH, 1);
        return serverMonthFormat.format(monthCalendar.getTime());
    }

    public static String getLastDay() {
        dateCalendar.add(Calendar.DATE, -1);
        return serverDateFormat.format(dateCalendar.getTime());
    }

    public static String getLastDay(String date) {
        strToCalendar(date);
        dateCalendar.add(Calendar.DATE, -1);
        System.out.println(serverDateFormat.format(dateCalendar.getTime()));
        return serverDateFormat.format(dateCalendar.getTime());
    }

    public static String getNextDay() {
        dateCalendar.add(Calendar.DATE, 1);
        return serverDateFormat.format(dateCalendar.getTime());
    }

    public static String getNextDay(String date) {
        strToCalendar(date);
        dateCalendar.add(Calendar.DATE, 1);
        return serverDateFormat.format(dateCalendar.getTime());
    }

    public static boolean lessThanCurrentDate() {
        System.out.println(serverDateFormat.format(currentDate) + "current");
        System.out.println(serverDateFormat.format(dateCalendar.getTime()) + "display");
        if (serverDateFormat.format(currentDate).equals(serverDateFormat.format(dateCalendar.getTime()))) {
            return false;
        }
        return dateCalendar.getTime().before(currentDate);
    }

    public static boolean lessThanCurrentMonth() {
        return !currentMonth.before(monthCalendar.getTime());
    }

    /**
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String calculateWeekDay(String strDate) {


        // 定义日期格式
        Date date = null;
        try {
            date = serverDateFormat.parse(strDate);// 将字符串转换为日期
        } catch (ParseException e) {
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        String day = "";
        switch (dayForWeek) {
            case 0:
                day = SUN;
                break;
            case 1:
                day = MON;
                break;
            case 2:
                day = TUE;
                break;
            case 3:
                day = WED;
                break;
            case 4:
                day = THU;
                break;
            case 5:
                day = FRI;
                break;
            case 6:
                day = SAT;
                break;
        }
        return strDate + " " + day;
    }

    public static String bookClassFormat(String strDate) {


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");// 定义日期格式
        Date date = null;
        try {
            date = format.parse(strDate);// 将字符串转换为日期
        } catch (ParseException e) {
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        String day = "";
        switch (dayForWeek) {
            case 0:
                day = SUN;
                break;
            case 1:
                day = MON;
                break;
            case 2:
                day = TUE;
                break;
            case 3:
                day = WED;
                break;
            case 4:
                day = THU;
                break;
            case 5:
                day = FRI;
                break;
            case 6:
                day = SAT;
                break;
        }
        return c.get(Calendar.YEAR) + "年"
                + (c.get(Calendar.MONTH) + 1) + "月" +
                c.get(Calendar.DATE) + "日" +
                " " + day;
    }

    public static String bookClassDialogDate(String strDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");// 定义日期格式
        Date date = null;// 将字符串转换为日期
        try {
            date = format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR) + "年"
                + (c.get(Calendar.MONTH) + 1) + "月" +
                c.get(Calendar.DATE) + "日";
    }

    public static String getTimePeriod(String time) {
        if (Integer.parseInt(time.substring(0, 1)) == 0) {
            return "上午" + time;
        } else if (Integer.parseInt(time.substring(0, 2)) < 12) {
            return "上午" + time;
        } else {
            return "下午" + time;
        }
    }

    public static boolean greaterThanCourseTime(String date, String time) {
        //compareDateFormat.setTimeZone();
        StringBuffer courseTime = new StringBuffer(date);
        courseTime.append(" ");
        courseTime.append(time.substring(0, 5));
        boolean result = true;
        try {
            Date convertedCourseTime = compareDateFormat.parse(courseTime.toString());
            Calendar currentTime = Calendar.getInstance();
            currentTime.add(Calendar.HOUR, 1);
            if (currentTime.getTime().after(convertedCourseTime)) {
                System.out.println("true");
                result = true;
            } else {
                System.out.println("false");
                result = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Date strToDate(String strDate) {
        Date date = null;
        try {
            date = serverDateFormat.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static void strToCalendar(String strDate) {

        try {
            dateCalendar.setTime(serverDateFormat.parse(strDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
