package com.syiti.vbp.util.support;

import org.apache.commons.lang.StringUtils;
import org.apache.http.util.TextUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class DateUtil {

    public static String getNow() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return new SimpleDateFormat("yyyy-MM-dd").format(cal.getTime());
    }

    public static String getStart() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
    }

    public static String getEnd() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
    }

    /**
     * 获取昨天零点时间：yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getYestoryStart() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);          //TODO 明天时间：cal.add(Calendar.DATE,1);后天：后天就是把1改成2 ，以此类推。
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
    }

    /**
     * 获取昨天结束时间：yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String getYestoryEnd() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
    }

    /**
     * Java将Unix时间戳转换成指定格式日期字符串
     *
     * @param timestampString 时间戳 如："1473048265";
     * @param formats         要格式化的格式 默认："yyyy-MM-dd HH:mm:ss";
     * @return 返回结果 如："2016-09-05 16:06:42";
     */
    public static String TimeStamp2Date(String timestampString, String formats) {
        if (StringUtils.isBlank(timestampString)) {
            return null;
        }
        if (TextUtils.isEmpty(formats))
            formats = "yyyy-MM-dd HH:mm:ss";
        Long timestamp = null;
        if (timestampString.length() == 13) {
            timestamp = Long.parseLong(timestampString);
        } else {
            timestamp = Long.parseLong(timestampString) * 1000;
        }

        return new SimpleDateFormat(formats, Locale.CHINA).format(new Date(timestamp));
    }

    /**
     * 获取指定格式的当前时间
     *
     * @param formats
     * @return String
     */
    public static String getCurrentFormatDate(String formats) {
        if (TextUtils.isEmpty(formats))
            formats = "yyyy-MM-dd HH:mm:ss";
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(formats);
        return dateFormat.format(date);
    }

    /**
     * 获取指定格式的当前时间--返回Timestamp类型
     *
     * @param formats
     * @return Timestamp
     */
    public static Timestamp getCurrentTimeStampFormat(String formats) {
        if (TextUtils.isEmpty(formats))
            formats = "yyyy-MM-dd HH:mm:ss";
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(formats);
        return Timestamp.valueOf(dateFormat.format(date));
    }

    /**
     * 获取两个时间的时间差，返回差距多少秒
     *
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     * @throws ParseException
     */
    public static int getTimeDiffSecond(Timestamp startTime, Timestamp endTime) throws ParseException {

        long diff = startTime.getTime() - endTime.getTime();
        return (int) (diff / (1000));
    }

    /**
     * 获取某日期往前多少天的日期
     *
     * @param nowDate
     * @param beforeNum
     * @return
     * @CreateTime 2016-1-13
     */
    public static Date getBeforeDate(Date nowDate, Integer beforeNum) {
        Calendar calendar = Calendar.getInstance(); // 得到日历
        calendar.setTime(nowDate);// 把当前时间赋给日历
        calendar.add(Calendar.DAY_OF_MONTH, -beforeNum); // 设置为前beforeNum天
        return calendar.getTime(); // 得到前beforeNum天的时间
    }


    /**
     * 获取某日期往前/后多少月份的日期
     *
     * @param nowDate
     * @param num
     * @return
     * @CreateTime 2016-1-13
     */
    public static Date getDateMonth(Date nowDate, Integer num) {
        Calendar calendar = Calendar.getInstance(); // 得到日历
        calendar.setTime(nowDate);// 把当前时间赋给日历
        calendar.add(Calendar.MONTH, num); // 设置为前/后beforeNum月份
        return calendar.getTime();
    }

    /**
     * 比较两个日期是否相同
     *
     * @param d1
     * @param d2
     * @return
     */
    public static boolean isEqualDate(Date d1, Date d2) {
        LocalDate localDate1 = ZonedDateTime.ofInstant(d1.toInstant(), ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = ZonedDateTime.ofInstant(d2.toInstant(), ZoneId.systemDefault()).toLocalDate();
        return localDate1.isEqual(localDate2);
    }

    /**
     * 返回需要的日期形式
     * </p>
     * 如：style=“yyyy年MM月dd日 HH时mm分ss秒SSS毫秒”。 返回“xxxx年xx月xx日xx时xx分xx秒xxx毫秒”
     * </p>
     *
     * @param date
     * @param style
     * @return
     */
    public static String getNeededDateStyle(Date date, String style) {
        if (date == null) {
            date = new Date();
        }
        if (StringUtils.isEmpty(style)) {
            style = "yyyy年MM月dd日";
        }

        SimpleDateFormat sdf = new SimpleDateFormat(style);
        return sdf.format(date);
    }

    /**
     * 获取过去12个月的月份
     *
     * @return
     */
    public static String[] getLast12Months() {

        String[] last12Months = new String[12];

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1); //要先+1,才能把本月的算进去</span>
        for (int i = 0; i < 12; i++) {
            cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) - 1); //逐次往前推1个月
            last12Months[11 - i] = cal.get(Calendar.YEAR) + String.format("%02d", (cal.get(Calendar.MONTH) + 1));
        }
        return last12Months;
    }

    /**
     * 更改时间格式
     *
     * @param time
     * @param format
     * @return
     */
    public static String formatTime(Timestamp time, String format) {
        if (TextUtils.isEmpty(format))
            format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(time);
    }


//	/**
//	 * 转Timestamp
//	 * @param date
//	 * @return
//	 */
//	public static Timestamp getTimeStampDate(String date) {
//		// TODO: 2018/6/24 0024 2018/05/06 多零会报错
//		if(StringUtils.isBlank(date)){
//			return null;
//		}
//		date  = date + " 00:00:00";
//		return Timestamp.valueOf(date);
//	}

    /**
     * 将时间格式字符串转换为时间 yyyy-MM-dd
     *
     * @param strDate
     * @return
     */
    public static Date strToDateFormat(String strDate) {
        Date date = null;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = formatter.parse(strDate);
        } catch (Exception e) {
            return null;
        }
        return date;
    }

    /**
     * 功能：判断输入年份是否为闰年
     *
     * @param year
     * @return 是：true  否：false
     * @author LIXIN
     */
    public static boolean leapYear(int year) {
        boolean leap;
        if (year % 4 == 0) {
            if (year % 100 == 0) {
                if (year % 400 == 0) leap = true;
                else leap = false;
            } else leap = true;
        } else leap = false;
        return leap;
    }

    /**
     * 获取当月最后一天
     *
     * @param month
     * @param year
     * @return
     */
    public static String getMoneyEndDay(String month, String year) {
        if (month.equals("01") || month.equals("03") || month.equals("06") || month.equals("07") || month.equals("08") || month.equals("10") || month.equals("12")) {
            return "31";
        }
        if (month.equals("04") || month.equals("06") || month.equals("09") || month.equals("11")) {
            return "30";
        }
        if (month.equals("2")) {
            if (leapYear(Integer.parseInt(year))) {
                return "29";
            } else {
                return "28";
            }
        }
        return null;
    }

}
