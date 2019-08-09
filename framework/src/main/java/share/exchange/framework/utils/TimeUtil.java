package share.exchange.framework.utils;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/**
 *
 * @ClassName:      TimeUtil
 * @Description:    日期时间操作工具包
 * @Author:         ZL
 * @CreateDate:     2019/08/02 16:35
 */
@SuppressLint("SimpleDateFormat")
public class TimeUtil {

    private final static int SECONDS = 1;
    private final static int MINUTES = 60 * SECONDS;
    private final static int HOURS = 60 * MINUTES;
    private final static int DAYS = 24 * HOURS;
    private final static int WEEKS = 7 * DAYS;
    private final static int MONTHS = 4 * WEEKS;
    private final static int YEARS = 12 * MONTHS;

    private TimeUtil() {
    }

    /**
     * 获取时间戳
     *
     * @param dateformat
     * @return
     */
    public static String getNowTime(String dateformat) {// yyyy/MM/dd HH:mm:ss
        Date d = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat(dateformat);// 可以方便地修改日期格式
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        String hehe = dateFormat.format(d);
        return hehe;
    }

    /**
     * 返回pattern格式的时间字符串
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String getDateFormat(Date date, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        df.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        return df.format(date);
    }

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return dateFormater.parse(sdate);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 将字符串转位日期类型
     *
     * @param sdate   要转换的日期
     * @param pattern 日期的标准格式模板
     * @return
     */
    public static Date toDate(String sdate, String pattern) {
        try {
            SimpleDateFormat dateFormater = new SimpleDateFormat(pattern);
            return dateFormater.parse(sdate);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date toDate2(String sdate) {
        try {
            SimpleDateFormat dateFormater2 = new SimpleDateFormat("yyyy-MM-dd");
            return dateFormater2.parse(sdate);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date toDate3(String sdate) {
        try {
            SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return dateFormater.parse(sdate);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date toDate5(String sdate) {
        try {
            SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy年MM月dd日HH点");
            return dateFormater.parse(sdate);
        } catch (Exception e) {
            return null;
        }
    }


    public static Date toDate6(String sdate) {
        try {
            SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy年M月");
            return dateFormater.parse(sdate);
        } catch (Exception e) {
            return null;
        }
    }

    public static Date toDate7(String sdate, String format) {
        try {
            SimpleDateFormat dateFormater = new SimpleDateFormat(format);
            return dateFormater.parse(sdate);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 根据pattern格式构建时间
     *
     * @param dateStrig
     * @param pattern
     * @return
     */
    public static Date getDate(String dateStrig, String pattern) {
        DateFormat df = new SimpleDateFormat(pattern);
        try {
            return df.parse(dateStrig);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * long 类型日期转string
     *
     * @param time
     * @param dateFormat
     * @return
     */
    public static String longDate2Str(long time, String dateFormat) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        Date dt = new Date(time);
        // 得到精确到秒的表示
        String sDateTime = sdf.format(dt);
        return sDateTime;
    }

    /**
     * @param date
     * @return
     * @description 以友好的时间显示
     */
    public static String format(Date date) {
        long delta = getNowUnixTimeInMillis() - date.getTime();
        if (delta < 1L * 60000L) {
            long seconds = toSeconds(delta);
            return (seconds <= 0 ? 1 : seconds) + "秒前";
        }
        if (delta < 45L * 60000L) {
            long minutes = toMinutes(delta);
            return (minutes <= 0 ? 1 : minutes) + "分钟前";
        }
        if (delta < 24L * 3600000L) {
            long hours = toHours(delta);
            return (hours <= 0 ? 1 : hours) + "小时前";
        }
        if (delta < 48L * 3600000L) {
            return "昨天";
        }
        if (delta < 30L * 86400000L) {
            long days = toDays(delta);
            return (days <= 0 ? 1 : days) + "天前";
        }
        if (delta < 12L * 4L * 604800000L) {
            long months = toMonths(delta);
            return (months <= 0 ? 1 : months) + "月前";
        } else {
            long years = toYears(delta);
            return (years <= 0 ? 1 : years) + "年前";
        }
    }

    private static long toSeconds(long date) {
        return date / 1000L;
    }

    private static long toMinutes(long date) {
        return toSeconds(date) / 60L;
    }

    private static long toHours(long date) {
        return toMinutes(date) / 60L;
    }

    private static long toDays(long date) {
        return toHours(date) / 24L;
    }

    private static long toMonths(long date) {
        return toDays(date) / 30L;
    }

    private static long toYears(long date) {
        return toMonths(date) / 365L;
    }

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public static String friendlyTime(String sdate) {
        Date time = null;
        if (sdate != null && sdate.contains(":")) {
            time = toDate(sdate);
        } else {
            time = toDate2(sdate);
        }
        String ftime = format(time);
        return ftime;
    }

    /**
     * 格式化时间
     *
     * @param ms
     * @return
     */
    public static String formatTime(long ms) {
        int ss = 1000;
        int mi = ss * 60;
        int hh = mi * 60;
        int dd = hh * 24;

        long day = ms / dd;
        long hour = (ms - day * dd) / hh;
        long minute = (ms - day * dd - hour * hh) / mi;
        long second = (ms - day * dd - hour * hh - minute * mi) / ss;
        long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;

        // String strDay = day < 10 ? "0" + day : "" + day;
        // String strHour = hour < 10 ? "0" + hour : "" + hour;
        String strMinute = minute < 10 ? "0" + minute : "" + minute;
        String strSecond = second < 10 ? "0" + second : "" + second;
        String strMilliSecond = milliSecond < 10 ? "0" + milliSecond : "" + milliSecond;
        strMilliSecond = milliSecond < 100 ? "0" + strMilliSecond : "" + strMilliSecond;
        return strMinute + ":" + strSecond;
    }

    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            SimpleDateFormat dateFormater2 = new SimpleDateFormat("yyyy-MM-dd");
            String nowDate = dateFormater2.format(today);
            String timeDate = dateFormater2.format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * @param oldTime 较小的时间
     * @param newTime 较大的时间 (如果为空   默认当前时间 ,表示和当前时间相比)
     * @return -1 ：同一天.    0：昨天 .   1 ：至少是前天.
     * @throws ParseException 转换异常
     */
    public static int isYeaterday(Date oldTime, Date newTime) {
        if (newTime == null) {
            newTime = new Date();
        }
        //将下面的 理解成  yyyy-MM-dd 00：00：00 更好理解点
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String todayStr = format.format(newTime);
        Date today = null;
        try {
            today = format.parse(todayStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //昨天 86400000=24*60*60*1000 一天
        if ((today.getTime() - oldTime.getTime()) > 0 && (today.getTime() - oldTime.getTime()) <= 86400000) {
            return 0;
        } else if ((today.getTime() - oldTime.getTime()) <= 0) { //至少是今天
            return -1;
        } else { //至少是前天
            return 1;
        }
    }

    /**
     * 比较两个日期时间的大小
     *
     * @param startDate
     * @param endDate
     * @return 【an int < 0 if this Date is less than the specified Date, 0 if
     * they are equal, and an int > 0 if this Date is greater.】
     * startDate 日期小于 endDate 返回-1, 日期等于 endDate 返回0,  startDate > endDate 返回 1
     */
    public static int compareDate(Date startDate, Date endDate) {
        return startDate.compareTo(endDate);
    }

    /**
     * 比较两个日期时间的大小 到秒为止
     *
     * @param startDateStr
     * @param endDateStr
     * @return 【<0表示startDateStr小于endDateStr，
     * 0表相等，大于0表示startDateStr大于endDateStr】
     */
    public static int compareDate(String startDateStr, String endDateStr) {
        Date startDate = toDate(startDateStr);
        Date endDate = toDate(endDateStr);
        return compareDate(startDate, endDate);
    }

    /**
     * 比较两个日期时间的大小
     *
     * @param startDateStr
     * @param endDateStr
     * @return 【<0表示startDateStr小于endDateStr，
     * 0表相等，大于0表示startDateStr大于endDateStr】
     */
    public static int compareDate(String startDateStr, String endDateStr, String pattern) {
        Date startDate = toDate(startDateStr, pattern);
        Date endDate = toDate(endDateStr, pattern);
        return compareDate(startDate, endDate);
    }

    /**
     * 比较两个日期时间的大小 到分为止
     *
     * @param startDateStr
     * @param endDateStr
     * @return 【<0表示startDateStr小于endDateStr，
     * 0表相等，大于0表示startDateStr大于endDateStr】
     */
    public static int compareDate2(String startDateStr, String endDateStr) {
        Date startDate = toDate3(startDateStr);
        Date endDate = toDate3(endDateStr);
        return compareDate(startDate, endDate);
    }

    /**
     * 比较两个日期时间的大小
     *
     * @param startDateStr
     * @param endDateStr
     * @return 【<0表示startDateStr小于endDateStr，
     * 0表相等，大于0表示startDateStr大于endDateStr】
     */
    public static int compareDate3(String startDateStr, String endDateStr) {
        Date startDate = toDate2(startDateStr);
        Date endDate = toDate2(endDateStr);
        return compareDate(startDate, endDate);
    }

    /**
     * @return
     * @description 获取当前时间的Unix 时间戳
     */
    public static long getNowUnixTimeInMillis() {
        long currentMillis = Calendar.getInstance().getTimeInMillis() / 1000;
        return currentMillis;
    }

    /**
     * 返回日期时间的时间戳  毫秒
     *
     * @param dateStr 格式yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static long getTimeInMillis(String dateStr) {
        if (!StringUtil.isEmpty(dateStr)) {
            Date date = toDate(dateStr);
            return date.getTime();
        }
        return 0;
    }

    /**
     * 返回日期时间的时间戳 秒
     *
     * @param dateStr 格式yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static long getTimeInsecond(String dateStr) {
        if (!StringUtil.isEmpty(dateStr)) {
            Date date = toDate(dateStr);
            return date.getTime() / 1000;
        }
        return 0;
    }

    public static String getFormatTime(long millionTime) {
        if (millionTime > 0) {
            SimpleDateFormat format = new SimpleDateFormat("mm:ss");
            format.setTimeZone(TimeZone.getTimeZone("GMT"));
            Calendar mCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
            mCalendar.setTimeInMillis(millionTime);
            int hours = mCalendar.get(Calendar.HOUR);
            if (hours > 0) {
                format.applyPattern("HH:mm:ss");
            }
            return format.format(mCalendar.getTime());
        }
        return "00:00";
    }

    /**
     * 时间转成星期
     *
     * @param time
     * @return
     */
    public static String getWeek(String time) {
        int weekDays = 7;
        String[] weekArray = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        int dayIndex = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(time);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
            if (dayIndex < 1 || dayIndex > weekDays) {
                return null;
            }
            return weekArray[dayIndex - 1];
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return weekArray[dayIndex - 1];
    }

    /**
     * 时间转成星期
     *
     * @param time
     * @return
     * @date 2016年6月23日
     */
    public static String[] getWeekArray(String time) {
        String[] result = new String[2];
        int weekDays = 7;
        String[] weekArray = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        String[] weekArrayEng = {"SUN.", "MON.", "TUE.", "WEN.", "THU.", "FRI.", "STA."};
        int dayIndex = 0;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = sdf.parse(time);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
            if (dayIndex < 1 || dayIndex > weekDays) {
                result[0] = "";
                result[1] = "";
                return result;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        result[0] = weekArray[dayIndex - 1];
        result[1] = weekArrayEng[dayIndex - 1];
        return result;
    }

    /**
     * @param date
     * @return
     * @modifier
     * @modifier_date
     */
    public static String getWeekByDate(Date date) {
        int weekDays = 7;
        String[] weekArray = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        int dayIndex = 0;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        dayIndex = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayIndex < 1 || dayIndex > weekDays) {
            return null;
        }
        return weekArray[dayIndex - 1];
    }

    /**
     * 判断两个时间是否在同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isInOneDay(Date date1, Date date2) {
        String str1 = getDateFormat(date1, "yyyy-MM-dd");
        String str2 = getDateFormat(date2, "yyyy-MM-dd");
        return str1.equals(str2);
    }

    /**
     * 获取当前月份
     *
     * @return
     * @description
     * @modifier_date
     */
    public static String getCurrentMonth() {
        String month = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        month = calendar.get(Calendar.MONTH) + 1 + "";
        return month;
    }

    /**
     * @param dateStr      要判断的时间
     * @param startDateStr 时间范围的开始时间
     * @param endDateStr   时间范围的结束时间
     * @return
     * @description 判断某个时间是否在一个时间范围内
     */
    public static boolean isInDayRange(String dateStr, String startDateStr, String endDateStr) {
        boolean result = false;
        try {
            Date startDate = toDate(startDateStr);
            Date endDate = toDate(endDateStr);
            Date date = toDate(dateStr);
            result = date.after(startDate) && date.before(endDate);
        } catch (Exception e) {
        }
        return result;
    }

    /**
     * @param dateStr (显示格式为：秒、分、时、天前或者为yy.MM.dd(如15.12.15)<目前主要用于学趣任务列表时间显示>)
     * @return
     */
    public static String toDate4(String dateStr) {
        Date time = null;
        if (dateStr != null && dateStr.contains(":")) {
            time = toDate(dateStr);
        } else {
            time = toDate2(dateStr);
        }
        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();
        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) Math.abs((ct - lt));
        if (days <= 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour <= 0) {
                ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
            } else {
                ftime = hour + "小时前";
            }
        } else if (days > 0 && days <= 3) {
            ftime = days + "天前";
        } else if (days > 3) {
            ftime = getDateFormat(time, "yyyy年MM月dd日");
        }
        return ftime;
    }

    /**
     * @param date
     * @return
     * @description 获取当前时间所在年的周数
     */
    public static int getWeekOfYear(Date date) {
        Calendar c = new GregorianCalendar();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.setMinimalDaysInFirstWeek(7);
        c.setTime(date);
        return c.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * @param year
     * @return
     * @description 获取当前时间所在年的最大周数
     */
    public static int getMaxWeekNumOfYear(int year) {
        Calendar c = new GregorianCalendar();
        c.set(year, Calendar.DECEMBER, 31, 23, 59, 59);
        return getWeekOfYear(c.getTime());
    }

    /**
     * 获取当前年
     *
     * @return
     */
    public static int getYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取当前月
     *
     * @return
     */
    public static int getMonth() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取当前月的日期
     *
     * @return
     */
    public static int getDay() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取当前月第一天（如2020-12-1）
     *
     * @return
     */
    public static String getCurrentEarly() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        return year + "-" + month + "-" + 1;
    }

    /**
     * 当前周开始日期（如2020-12-1）
     *
     * @return
     */
    public static String getCurrentWeek() {
        Calendar currentDate = new GregorianCalendar();
        currentDate.setFirstDayOfWeek(Calendar.MONDAY);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return getDateFormat(currentDate.getTime(), "yyyy-MM-dd");
    }

    /**
     * 取得指定天数后的时间
     *
     * @param date      基准时间
     * @param dayAmount 指定天数，允许为负数
     * @return 指定天数后的时间
     */
    public static Date addDay(Date date, int dayAmount) {
        if (date == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, dayAmount);
        return calendar.getTime();
    }

    /**
     * 取得指定月数后的时间
     *
     * @param date        基准时间
     * @param monthAmount 指定月数，允许为负数
     * @return 指定月数后的时间
     */
    public static Date addMonth(Date date, int monthAmount) {
        if (date == null) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, monthAmount);
        return calendar.getTime();
    }

    /**
     * yyyy-MM-dd格式字符串转换成时间戳
     *
     * @param user_time
     * @return
     */
    public static String getTime(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return re_time;
    }

    /**
     * 获取当前月第一天（如2020-12-01）
     *
     * @return
     */
    public static String getCurrentEarly2() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        return year + "-" + month + "-" + "01";
    }

    /**
     * 获取两个时间段的时间差
     *
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 毫秒
     */
    public static long getIntervalDate(Date startDate, Date endDate) {
        if (null == startDate || null == endDate) {
            return -1;
        }
        return endDate.getTime() - startDate.getTime();
    }

    /**
     * 获取月的字符串表示
     *
     * @param month
     * @return
     */
    public static String getMonthCN(int month) {
        String result = "";
        switch (month) {
            case 1:
                result = "一月";
                break;
            case 2:
                result = "二月";
                break;
            case 3:
                result = "三月";
                break;
            case 4:
                result = "四月";
                break;
            case 5:
                result = "五月";
                break;
            case 6:
                result = "六月";
                break;
            case 7:
                result = "七月";
                break;
            case 8:
                result = "八月";
                break;
            case 9:
                result = "九月";
                break;
            case 10:
                result = "十月";
                break;
            case 11:
                result = "十一月";
                break;
            case 12:
                result = "十二月";
                break;
            default:
                break;
        }
        return result;
    }

    /**
     * 根据当前几点转成上下午
     *
     * @param hour
     * @return
     */
    public static String getAmOrPm(int hour) {
        if (0 <= hour && hour <= 12)//上午
        {
            return "上午";
        } else {
            return "下午";
        }
    }

    /**
     * @param formatTime 时间转换 -转成年月日 2016-08-17 23 转成2016年08月17日23点
     * @param isNeedHour 是否需要小时 016年08月17日
     * @return
     */
    public static String timeFormat(String formatTime, boolean isNeedHour) {
        String time;
        if (isNeedHour) {
            time = formatTime.replaceFirst("-", "年").replace("-", "月").replace(" ", "日") + "点";
        } else {
            time = formatTime.replaceFirst("-", "年").replace("-", "月").replace(" ", "日");
        }
        return time;
    }

    /**
     * 获取当前时间
     *
     * @param timeType 类似"yyyy-MM" yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getStringDate(String timeType) {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat(timeType);
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * Unix时间戳转时间格式
     *
     * @param timeStampString unix
     * @param formats         时间格式
     * @return
     */
    public static String timeStamp2Date(String timeStampString, String formats) {
        if (TextUtils.isEmpty(formats)) {
            formats = "yyyy-MM-dd HH:mm:ss";
        }
        Long timestamp = Long.parseLong(timeStampString) * 1000;
        String date = new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
        return date;
    }

    /**
     * 功能描述：返回月
     *
     * @param date
     *            Date 日期
     * @return 返回月份
     */
    public static int getMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MONTH) + 1;
    }

    /**
     * 功能描述：返回日期
     *
     * @param date
     *            Date 日期
     * @return 返回日份
     */
    public static int getDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

}