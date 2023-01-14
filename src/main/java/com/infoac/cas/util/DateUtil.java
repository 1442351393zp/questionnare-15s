package com.infoac.cas.util;

import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;



/**
 * <p>
 * 日期工具类
 * </p>
 *
 * @author lewis
 * @version 1.0
 * @since 1.0
 */
public final class DateUtil {

//    private static final int timeBase = 10;
    
    private static final Map<String, ThreadLocal<SimpleDateFormat>> timestampFormatPool = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

    private static final Map<String, ThreadLocal<SimpleDateFormat>> dateFormatPool = new HashMap<String, ThreadLocal<SimpleDateFormat>>();

    private static final Object timestampFormatLock = new Object();

    private static final Object dateFormatLock = new Object();

    public static String dateFormatPattern = "yyyy-MM-dd";

    private static String dateFormatPattern_CN = "yyyy年MM月dd日";

    private static String timestampPattern = "yyyy-MM-dd HH:mm:ss";

    private static String gmttimetamPattern = "EE MMM dd HH:mm:ss zzz yyyy";//格林威治时间

    private static String simplifyDatePattern = "yyyyMMdd";

    private static String simplifyMonthPattern = "yyyy年MM月";

    private static String simplifyTimestampPattern = "yyyyMMddHHmmss";

    private static String timestampPatternCps = "yyyyMMddHHmmss";

    private static String timestampMonthPattern = "yyyy-MM";

    private static String timestampYearPattern = "yyyy";

    private final static SimpleDateFormat sdfDays = new SimpleDateFormat("yyyyMMdd");
    
    private final static SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final static SimpleDateFormat sdfTimeMilliSecond = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");

    private static SimpleDateFormat getTimestampMonthPatternFormat() {
        ThreadLocal<SimpleDateFormat> tl = dateFormatPool
                .get(timestampMonthPattern);
        if (null == tl) {
            synchronized (dateFormatLock) {
                tl = dateFormatPool.get(timestampMonthPattern);
                if (null == tl) {
                    tl = new ThreadLocal<SimpleDateFormat>() {
                        protected synchronized SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(timestampMonthPattern);
                        }
                    };
                    dateFormatPool.put(timestampMonthPattern, tl);
                }
            }
        }
        return tl.get();
    }
    private static SimpleDateFormat getDateFormatCN() {
        ThreadLocal<SimpleDateFormat> tl = dateFormatPool
                .get(dateFormatPattern_CN);
        if (null == tl) {
            synchronized (dateFormatLock) {
                tl = dateFormatPool.get(dateFormatPattern_CN);
                if (null == tl) {
                    tl = new ThreadLocal<SimpleDateFormat>() {
                        protected synchronized SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(dateFormatPattern_CN);
                        }
                    };
                    dateFormatPool.put(dateFormatPattern_CN, tl);
                }
            }
        }
        return tl.get();
    }

    private static SimpleDateFormat getDateFormatGMT() {
        ThreadLocal<SimpleDateFormat> tl = dateFormatPool
                .get(gmttimetamPattern);
        if (null == tl) {
            synchronized (dateFormatLock) {
                tl = dateFormatPool.get(gmttimetamPattern);
                if (null == tl) {
                    tl = new ThreadLocal<SimpleDateFormat>() {
                        protected synchronized SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(gmttimetamPattern,java.util.Locale.ENGLISH);
                        }
                    };
                    dateFormatPool.put(gmttimetamPattern, tl);
                }
            }
        }
        return tl.get();
    }

    private static SimpleDateFormat getDateFormat() {
        ThreadLocal<SimpleDateFormat> tl = dateFormatPool
                .get(dateFormatPattern);
        if (null == tl) {
            synchronized (dateFormatLock) {
                tl = dateFormatPool.get(dateFormatPattern);
                if (null == tl) {
                    tl = new ThreadLocal<SimpleDateFormat>() {
                        protected synchronized SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(dateFormatPattern);
                        }
                    };
                    dateFormatPool.put(dateFormatPattern, tl);
                }
            }
        }
        return tl.get();
    }

    public static SimpleDateFormat getTimestampFormat() {
        ThreadLocal<SimpleDateFormat> tl = timestampFormatPool
                .get(timestampPattern);
        if (null == tl) {
            synchronized (timestampFormatLock) {
                tl = timestampFormatPool.get(timestampPattern);
                if (null == tl) {
                    tl = new ThreadLocal<SimpleDateFormat>() {
                        protected synchronized SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(timestampPattern);
                        }
                    };
                    timestampFormatPool.put(timestampPattern, tl);
                }
            }
        }
        return tl.get();
    }

    private static SimpleDateFormat getSimplifyTimestampFormat() {
        ThreadLocal<SimpleDateFormat> tl = timestampFormatPool
                .get(simplifyTimestampPattern);
        if (null == tl) {
            synchronized (timestampFormatLock) {
                tl = timestampFormatPool.get(simplifyTimestampPattern);
                if (null == tl) {
                    tl = new ThreadLocal<SimpleDateFormat>() {
                        protected synchronized SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(
                                    simplifyTimestampPattern);
                        }
                    };
                    timestampFormatPool.put(simplifyTimestampPattern, tl);
                }
            }
        }
        return tl.get();
    }

    private static SimpleDateFormat getSimplifyMonthFormat() {
        ThreadLocal<SimpleDateFormat> tl = timestampFormatPool
                .get(simplifyMonthPattern);
        if (null == tl) {
            synchronized (timestampFormatLock) {
                tl = timestampFormatPool.get(simplifyMonthPattern);
                if (null == tl) {
                    tl = new ThreadLocal<SimpleDateFormat>() {
                        protected synchronized SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(simplifyMonthPattern);
                        }
                    };
                    timestampFormatPool.put(simplifyMonthPattern, tl);
                }
            }
        }
        return tl.get();
    }


    private static SimpleDateFormat getSimplifyDateFormat() {
        ThreadLocal<SimpleDateFormat> tl = timestampFormatPool
                .get(simplifyDatePattern);
        if (null == tl) {
            synchronized (timestampFormatLock) {
                tl = timestampFormatPool.get(simplifyDatePattern);
                if (null == tl) {
                    tl = new ThreadLocal<SimpleDateFormat>() {
                        protected synchronized SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(simplifyDatePattern);
                        }
                    };
                    timestampFormatPool.put(simplifyDatePattern, tl);
                }
            }
        }
        return tl.get();
    }

    public static SimpleDateFormat getTimestampFormatCps() {
        ThreadLocal<SimpleDateFormat> tl = timestampFormatPool
                .get(timestampPatternCps);
        if (null == tl) {
            synchronized (timestampFormatLock) {
                tl = timestampFormatPool.get(timestampPatternCps);
                if (null == tl) {
                    tl = new ThreadLocal<SimpleDateFormat>() {
                        protected synchronized SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(timestampPatternCps);
                        }
                    };
                    timestampFormatPool.put(timestampPatternCps, tl);
                }
            }
        }
        return tl.get();
    }

    private static SimpleDateFormat getDateYearFormat() {
        ThreadLocal<SimpleDateFormat> tl = dateFormatPool
                .get(timestampYearPattern);
        if (null == tl) {
            synchronized (dateFormatLock) {
                tl = dateFormatPool.get(timestampYearPattern);
                if (null == tl) {
                    tl = new ThreadLocal<SimpleDateFormat>() {
                        protected synchronized SimpleDateFormat initialValue() {
                            return new SimpleDateFormat(timestampYearPattern);
                        }
                    };
                    dateFormatPool.put(timestampYearPattern, tl);
                }
            }
        }
        return tl.get();
    }

    /**
     * 格式化成时间戳格式
     *
     * @param date 要格式化的日期对象
     * @return yyyy
     */
    public static String timestampYearFormat(Date date) {
        if (date == null) {
            return "";
        }
        return getDateYearFormat().format(date);
    }

    /**
     * 格式化成时间戳格式
     *
     * @param date 要格式化的日期对象
     * @return "年年年年-月月-日日 时时:分分:秒秒"格式的日期字符串
     */
    public static String timestampFormat(Date date) {
        if (date == null) {
            return "";
        }
        return getTimestampFormat().format(date);
    }

    /**
     * 格式化成时间戳格式
     *
     * @param date 要格式化的日期对象
     * @return "年年年年-月月"格式的日期字符串
     */
    public static String timestampMonthPatternFormat(Date date) {
        if (date == null) {
            return "";
        }
        return getTimestampMonthPatternFormat().format(date);
    }

    /**
     * 格式化成时间戳格式
     *
     * @param date 要格式化的日期对象
     * @return "年年年年月月日日时时分分秒秒"格式的日期字符串
     */
    public static String simplifyTimestampFormat(Date date) {
        if (date == null) {
            return "";
        }
        return getSimplifyTimestampFormat().format(date);
    }

    /**
     * 格式化成时间戳格式
     *
     * @param date 要格式化的日期对象
     * @return "YYYY年MM月
     */
    public static String simplifyMonthFormat(Date date) {
        if (date == null) {
            return "";
        }
        return getSimplifyMonthFormat().format(date);
    }

    /**
     * 格式化成日期格式
     *
     * @param date 要格式化的日期对象
     * @return "年年年年月月日日"格式的日期字符串
     */
    public static String simplifyDateFormat(Date date) {
        if (date == null) {
            return "";
        }
        return getSimplifyDateFormat().format(date);
    }

    /**
     * 格式化成时间戳格式
     *
     * @param date 要格式化的日期对象
     * @return "年年年年-月月-日日 时时:分分:秒秒"格式的日期字符串
     * @author warship
     * @created 2013年11月10日 上午12:27:01
     */
    public static String timestampFormatCps(Date date) {
        if (date == null) {
            return "";
        }
        return getTimestampFormatCps().format(date);
    }

    /**
     * 格式化成Unix时间戳格式
     *
     * @param date
     * @return
     */
    public static long unixTimestampFormat(Date date) {
        String unixDate = String.valueOf(date.getTime()).substring(0, 10);
        return Long.parseLong(unixDate);
    }

    /**
     * 获取今天剩余的秒数
     *
     * @return
     */
    public static long getSurplusSecondOfCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        ;

        return (calendar.getTimeInMillis() - System.currentTimeMillis()) / 1000;
    }

    /**
     * 格式化成时间戳格式
     *
     * @param datetime 要格式化的日期
     * @return "年年年年-月月-日日 时时:分分:秒秒"格式的日期字符串
     */
    public static String timestampFormat(long datetime) {
        return getTimestampFormat().format(new Date(datetime));
    }

    /**
     * 将"年年年年-月月-日日 时时:分分:秒秒"格式的日期字符串转换成Long型日期
     *
     * @param timestampStr 年年年年-月月-日日 时时:分分:秒秒"格式的日期字符串
     * @return Long型日期
     */
    public static long formatTimestampToLong(String timestampStr) {
        try {
            return getTimestampFormat().parse(timestampStr).getTime();
        } catch (ParseException e) {
            return 0L;
        }
    }

    /**
     * 将"年年年年-月月-日日 时时:分分:秒秒"格式的日期字符串转换成日期
     *
     * @param timestampStr 年年年年-月月-日日 时时:分分:秒秒"格式的日期字符串
     * @return 日期
     */
    public static Date formatTimestampToDate(String timestampStr) {
        try {
            return getTimestampFormat().parse(timestampStr);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 将"年年年年-月月-日日"格式的日期字符串转换成日期
     * <p>
     * 年年年年-月月-日日"格式的日期字符串
     *
     * @return 日期
     */
    public static Date formatDateToDate(String dateStr) {
        if (StringUtil.isEmpty(dateStr)) {
            return null;
        }
        try {
            return getDateFormat().parse(dateStr);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 格式化成日期格式
     *
     * @param date 要格式化的日期
     * @return "年年年年-月月-日日"格式的日期字符串
     */
    public static String dateFormat(Date date) {
        if (date == null) {
            return "";
        }
        return getDateFormat().format(date);
    }

    /**
     * 格式化成日期格式
     *
     * @param date 要格式化的日期
     * @return "××××年××月××日"格式的日期字符串
     */
    public static String dateFormatCN(Date date) {
        if (date == null) {
            return "";
        }
        return getDateFormatCN().format(date);
    }

    /**
     * 格式化成日期格式
     *
     * @param gmtString 要格式化的日期
     * @return gmt格式的日期字符串
     */
    public static Date parseGMTStringToDate(String gmtString) {
        if (StringUtil.isEmpty(gmtString)) {
            return null;
        }
        try {
            return getDateFormatGMT().parse(gmtString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 格式化成日期格式
     *
     * @param datetime 要格式化的日期
     * @return "年年年年-月月-日日"格式的日期字符串
     */
    public static String dateFormat(long datetime) {
        return getDateFormat().format(new Date(datetime));
    }

    /**
     * 将"年年年年-月月-日日"格式的日期字符串转换成Long型日期
     *
     * @param dateStr "年年年年-月月-日日"格式的日期字符串
     * @return Long型日期
     */
    public static long formatDateToLong(String dateStr) {
        try {
            return getDateFormat().parse(dateStr).getTime();
        } catch (ParseException e) {
            return 0L;
        }
    }

    /**
     * 得到本月的第一天
     *
     * @return 以"年年年年-月月-日日"格式返回当前月第一天的日期
     */
    public static String getFirstDayOfCurrentMonth(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return getDateFormat().format(calendar.getTime());
    }

    /**
     * 获取本月第一个的开始时间
     *
     * @return
     */
    public static Date getFirstDayAndTimeOfCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        return calendar.getTime();
    }

    /**
     * 得到本月的最后一天
     *
     * @return 以"年年年年-月月-日日"格式返回当前月最后一天的日期
     * @author warship
     * @created 2013年11月10日 上午12:33:42
     */
    public static String getLastDayOfCurrentMonth(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        calendar.set(Calendar.DAY_OF_MONTH,
                calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return getDateFormat().format(calendar.getTime());
    }
    /**
     * 获取当前天开始时间
     *
     * @return
     */
    public static Date getStartTimeOfCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取当前天的结束时间
     *
     * @return
     */
    public static Date getEndTimeOfCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 获取指定日期的前一天开始时间
     *
     * @param date
     * @return 2017年5月18日
     * @leikun
     */
    public static Date getStartTimeOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取指定日期的后一天开始时间
     *
     * @param date
     * @return 2018年7月18日
     * @leikun
     */
    public static Date getStartTimeCurrentOfDay(Date date, int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, offset);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取指定日期的开始时间:前一天的15:30
     *
     * @param date
     * @return 2017年6月1日
     * @leikun
     */
    public static Date getFifteenThirtyBeforeOneDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取指定日期的开始时间:2017-06-20 00:00:00
     *
     * @return 2017年6月1日
     * @leikun
     */
    public static Date getSpectTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2017, 05, 21);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        ;
        return calendar.getTime();
    }

    /**
     * 获取指定日期当天的时间08:30
     *
     * @param date
     * @return 2017年6月1日
     * @leikun
     */
    public static Date getEightThirtyOfGivenDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取指定日期当天的时间15:30
     *
     * @param date
     * @return 2017年6月1日
     * @leikun
     */
    public static Date getFifteenThirtyOfGivenDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取指定日期的前一天结束时间
     *
     * @param date
     * @return 2017年5月18日
     * @leikun
     */
    public static Date getEndTimeOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }
    /**
     * 计算出两个时间相差分钟数
     * @return 2017年5月18日
     * @leikun
     */
    public static int getLeadTime(Date startTime,Date endTime) {
        long minute=(endTime.getTime()-startTime.getTime())/(1000*60);//转化minute
        return new Long(minute).intValue();
    }

    /**
     * 获取指定日期的前一天结束时间
     *
     * @param date
     * @return 2017年5月18日
     * @leikun
     */
    public static Date getEndTimeOfDayWithOffset(Date date, int offset) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, offset);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    public static boolean isInTime(String time, String time_limit_start,
                                   String time_limit_over) {
        try {
            String nowDate = new SimpleDateFormat(dateFormatPattern).format(new Date());

            return isOutOfDate(nowDate + " " + time, nowDate + " "
                    + time_limit_start, nowDate + " " + time_limit_over);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isOutOfDate(String time, String start_time,
                                      String over_time) throws ParseException {
        long timeLong = new SimpleDateFormat(timestampPattern).parse(time).getTime();
        long ckStartTimeLong = new SimpleDateFormat(timestampPattern).parse(start_time)
                .getTime();
        long ckOverTimeLong = new SimpleDateFormat(timestampPattern).parse(over_time)
                .getTime();
        if (timeLong > ckStartTimeLong && timeLong < ckOverTimeLong) {
            return true;
        }
        return false;
    }

    /**
     * 指定时间是否在指定的星期内
     *
     * @param date
     * @param weeks
     * @return
     */
    public static boolean isInWeek(Date date, int... weeks) {
        if (null == date) {
            return false;
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int currentWeek = calendar.get(Calendar.DAY_OF_WEEK);
        for (int week : weeks) {
            if (week == currentWeek) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取int格式的当天的月份，比如当前日期是2017年3月27日，得到3
     *
     * @return 2017年3月27日
     */
    public static int getCurrentMonth() {

        Calendar now = Calendar.getInstance();
        return now.get(Calendar.MONTH) + 1;
    }
    /**
     * 获取int格式的当天的月份，比如某月是2017年3月27日，得到3
     *
     * @return 2017年3月27日
     */
    public static int getCurrentMonth(Date date) {

        Calendar now = Calendar.getInstance();
        now.setTime(date);
        return now.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取int格式的当天的年份，比如当前日期是2017年3月27日，得到2017
     *
     * @return 2017年3月27日
     */
    public static int getCurrentYear() {

        Calendar now = Calendar.getInstance();
        return now.get(Calendar.YEAR);
    }
    /**
     * 获取int格式的当天的年份，比如某日期是2017年3月27日，得到2017
     *
     * @return 2017年3月27日
     */
    public static int getCurrentYear(Date currentDate) {
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        return c.get(Calendar.YEAR);
    }

    /**
     * 获取int格式的当天的日子，比如当前日期是2017年3月27日，得到27
     *
     * @return 2017年3月27日
     */
    public static int getDayOfMonth() {

        Calendar now = Calendar.getInstance();
        return now.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取int格式的当时的小时
     *
     * @return 2017年6月1日
     * @leikun
     */
    public static int getCurrentHourOfDay() {
        Calendar now = Calendar.getInstance();
        return now.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 获取当月的 天数
     *
     * @return 2017年3月27日
     */
    public static int getCurrentMonthDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DATE, 1);
        calendar.roll(Calendar.DATE, -1);

        return calendar.get(Calendar.DATE);
    }

    /**
     * 获取偏移时间字符串
     *
     * @param offset
     * @return
     */
    public static String getOffsetDay(int offset) {
        Calendar calendar2 = Calendar.getInstance();
        SimpleDateFormat sdf2 = new SimpleDateFormat(timestampPattern);
        calendar2.add(Calendar.DATE, offset);
        return sdf2.format(calendar2.getTime());
    }

    /**
     * 获取偏移时间字符串
     *
     * @param offset
     * @return
     */
    public static Date getOffsetMonth(Date time, int offset) {
        if(time == null){
            time = new Date();
        }
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(time);
        calendar2.add(Calendar.MONTH, offset);
        return calendar2.getTime();
    }

    public static String getCurrentDateStr() {
        SimpleDateFormat sdf2 = new SimpleDateFormat(timestampPattern);
        return sdf2.format(new Date());
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static Integer daysBetween(Date smdate, Date bdate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            smdate = sdf.parse(sdf.format(smdate));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        try {
            bdate = sdf.parse(sdf.format(bdate));
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 获取指定日期往后offset天的时间
     *
     * @param date
     * @param offset
     * @return
     */
    public static Date getOffsetDate(Date date, int offset) {
        if (date == null) {
            date = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.get(Calendar.DATE) + offset);

        return calendar.getTime();
    }

    public static String date2TimeStamp(String date_str, String format) {
        try {
            if (StringUtil.isEmpty(date_str) || StringUtil.isEmpty(format)) {
                return "";
            }
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
    /**
     * 获取过去第几天的日期
     *
     * @param past
     * @return
     */
    public static String getPastDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }
    /**
     * 第几季
     * @param date
     */
    public static int getQuarterByDate(Date date){

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int m = calendar.get(Calendar.MONTH) + 1;
        if(m>=1 && m<=3){
            return 1;
        }else if(m>=4 && m<=6){
            return 2;
        }else if(m>=7 && m<=9) {
            return 3;
        }else{
            return 4;
        }
    }
    /**
     * 获取某月的第一天
     *	@param year
     *	@param month
     */
    public static String getFisrtDayOfMonth(int year,int month)
    {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);
        //获取某月最小天数
        int firstDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
        //设置日历中月份的最小天数
        cal.set(Calendar.DAY_OF_MONTH, firstDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String firstDayOfMonth = sdf.format(cal.getTime());

        return firstDayOfMonth;
    }
    /**
     * 获取某月的最后一天
     *	@param month
     */
    public static String getLastDayOfMonth(int year,int month)
    {
        Calendar cal = Calendar.getInstance();
        //设置年份
        cal.set(Calendar.YEAR,year);
        //设置月份
        cal.set(Calendar.MONTH, month-1);

        //获取某月最大天数
        int lastDay = 31;
        if(month==4||month==6||month==9||month==11){
            lastDay = 30;
        }else if(month==2){
            if(year % 4 == 0){
                lastDay = 29;
            }else{
                lastDay = 28;
            }
        }else{

        }
        //设置日历中月份的最大天数
        cal.set(Calendar.DAY_OF_MONTH, lastDay);
        //格式化日期
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String lastDayOfMonth = sdf.format(cal.getTime());

        return lastDayOfMonth;
    }


    /**
     * 获取某季度的最后一天
     *	@param num
     */
    public static String getCurrQuarterLastDay(Date date ,int num) {
        //String[] s = new String[2];
        String str = "";
        // 设置本年的季
        Calendar quarterCalendar = null;
        quarterCalendar = Calendar.getInstance();
        quarterCalendar.setTime(date);
        switch (num) {
            case 1: // 本年到现在经过了一个季度，在加上前4个季度
                str = DateUtil.formatDate(quarterCalendar.getTime(), "yyyy-MM-dd");
                str = str.substring(0, str.length() - 5) + "03-31";
                break;
            case 2: // 本年到现在经过了二个季度，在加上前三个季度
                str = DateUtil.formatDate(quarterCalendar.getTime(), "yyyy-MM-dd");
                str = str.substring(0, str.length() - 5) + "06-30";
                break;
            case 3:// 本年到现在经过了三个季度，在加上前二个季度
                str = DateUtil.formatDate(quarterCalendar.getTime(), "yyyy-MM-dd");
                str = str.substring(0, str.length() - 5) + "09-30";
                break;
            case 4:// 本年到现在经过了四个季度，在加上前一个季度
                str = DateUtil.formatDate(quarterCalendar.getTime(), "yyyy-MM-dd");
                str = str.substring(0, str.length() - 5) + "12-31";
                break;
        }
        return str;
    }
    /**
     * 获取某季度的第一天
     *	@param num
     */
    public static String getCurrQuarterFirstDay(Date date ,int num) {
        //String[] s = new String[2];
        String str = "";
        // 设置本年的季
        Calendar quarterCalendar = null;
        quarterCalendar = Calendar.getInstance();
        quarterCalendar.setTime(date);
        switch (num) {
            case 1: // 本年到现在经过了一个季度，在加上前4个季度
                str = DateUtil.formatDate(quarterCalendar.getTime(), "yyyy-MM-dd");
                str = str.substring(0, str.length() - 5) + "01-01";
                break;
            case 2: // 本年到现在经过了二个季度，在加上前三个季度
                str = DateUtil.formatDate(quarterCalendar.getTime(), "yyyy-MM-dd");
                str = str.substring(0, str.length() - 5) + "04-01";
                break;
            case 3:// 本年到现在经过了三个季度，在加上前二个季度
                str = DateUtil.formatDate(quarterCalendar.getTime(), "yyyy-MM-dd");
                str = str.substring(0, str.length() - 5) + "07-01";
                break;
            case 4:// 本年到现在经过了四个季度，在加上前一个季度
                str = DateUtil.formatDate(quarterCalendar.getTime(), "yyyy-MM-dd");
                str = str.substring(0, str.length() - 5) + "10-01";
                break;
        }
        return str;
    }
    /**
     * 用途：以指定的格式格式化日期字符串
     * @param pattern 字符串的格式
     * @param currentDate 被格式化日期
     * @return String 已格式化的日期字符串
     * @throws NullPointerException 如果参数为空
     */
    public static String formatDate(Date currentDate, String pattern){
        if(currentDate == null || "".equals(pattern) || pattern == null){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(currentDate);
    }

    /**
     * 某年的第一天
     * @param currentDate
     * @return
     */
    public static String getYearFirstDay(Date currentDate){
        if(currentDate == null ){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        Date y = c.getTime();
        String year = sdf.format(y);
        year = year.substring(0, year.length() - 5) + "01-01";
        return year;
    }
    /**
     * 某年的最后一天
     * @param currentDate
     * @return
     */
    public static String getYearLastDay(Date currentDate){
        if(currentDate == null ){
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        Date y = c.getTime();
        String year = sdf.format(y);
        year = year.substring(0, year.length() - 5) + "12-30";
        return year;
    }

    /**
     * 某一月的天数
     * @param date
     * @return
     */
    public static int getDaysOfMonth(Date date){
        if(date == null ){
            return 0;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
    }
    
    /**
	 * 获取YYYY-MM-DD HH:mm:ss格式
	 * @return
	 */
	public static String getTime() {
		return sdfTime.format(new Date());
	}

    /**
     * 获取YYYY-MM-DD HH:mm:ss SSS格式
     * @return
     */
    public static String getMilliSecondTime() {
        return sdfTimeMilliSecond.format(new Date());
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     * @return
     */
    public static String getBef3Time(int hour) {
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.HOUR_OF_DAY,instance.get(Calendar.HOUR_OF_DAY)+hour);
        return sdfTime.format(instance.getTime());
    }





	/**
	 * 获取YYYYMMDD格式
	 * @return
	 */
	public static String getDays(){
		return sdfDays.format(new Date());
	}
	
	public static String get32UUID() {
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}
	
	/**
	 * 生成一个年月日时分秒毫秒+随机的六位数的id
	 * (共23位随机数)
	 * @author wangqingjie
	 * @return
	 */
	public static String getCommentId(){
		String tsdid = new SimpleDateFormat("yyyyMMddHHmmssSSS").format( new Date());
		int am = (int) ((Math.random()*9+1)*100000);//取得随机6位数
		
		String id = tsdid + String.valueOf(am);
		return id;
	}
	/**
	 * 和当前时间比较大小
	 * @author wangqingjie
	 * @return
	 */
	public static int compDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        str = str+":00";
        int flag =0;
        try {
            Date parse = format.parse(str);
            Date date = new Date();
            flag =  parse.compareTo(date);
        }catch (Exception e){
            //以防万一
            Long time = formatDateToDate(str).getTime();
            Date date = new Date();
            Long time2 = date.getTime();
            if(time > time2) {
                return 1;
            }else {
                return -1;
            }
        }
        return flag;
		/*Long time = formatDateToDate(str).getTime();
		Date date = new Date();
		Long time2 = date.getTime();
		if(time > time2) {
			return 1;
		}else {
			return -1;
		}*/
	}



	/**
	 * 过去2年集合
	 * @author
	 * @return
	 */
	public static List<Integer> getYearList(){
		List<Integer> list = new ArrayList<>();
		int year = getCurrentYear();
		if(year != 2019) {
			list.add(year-1);
		}
		list.add(year);
		return list;
	}

	/**
	* @Description: 获取2019年之后到指定时间之前的年份
	* @Param:
	* @Return:
	* @Exception:
	*/
	public static List<String> get2019ToNowYearList(Date date){
        String yyyy = timestampYearFormat(date);
        List<String> list = new ArrayList<>();
        if(StringUtils.isNotBlank(yyyy)){
            Integer currentYear = Integer.parseInt(yyyy);
//        int year = getCurrentYear();
            while(currentYear >= 2022){
                list.add(currentYear.toString());
                currentYear --;
            }
        }

        return list;
    }

//    public static void main(String args[]) throws ParseException{
//        String a= getPastDate(5);
//        a = a.replaceAll("-","");
//        System.out.println(a);
//        String before7Day = DateUtil.getPastDate(7);
//        String b= getLastDayOfCurrentMonth(DateUtil.formatDateToDate(before7Day));
//        System.out.println(b);
        //过去一年
//        String b = getLastDayOfMonth(2018,3);
//        int a = compDate("2019-12-07 15:55");
//        System.out.println("过去一年："+b);
//        System.out.println("过去一年："+a);
//        String a = getFisrtDayOfMonth(2018,2);
//        System.out.println(a);
//        System.out.println(getOffsetDate(formatDateToDate("2018-07-26"), -1));

//        List<String> toNowYearList = get2019ToNowYearList(new Date());
//        System.out.println(DateUtil.getSSSTime());
//    }
}