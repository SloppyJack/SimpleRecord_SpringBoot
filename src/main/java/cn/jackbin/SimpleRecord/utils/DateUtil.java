package cn.jackbin.SimpleRecord.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author: create by bin
 * @version: v1.0
 * @description: 日期工具类
 * @date: 2020/10/27 22:20
 **/
public class DateUtil {

    /**
     * 添加月份
     */
    public static Date addMonth(Date sourceDate, int month) {
        Calendar c = Calendar.getInstance();
        c.setTime(sourceDate);
        c.add(Calendar.MONTH, month);
        return c.getTime();
    }

    /**
     * 以周分割时间段
     *
     * @param startTime     开始时间戳(毫秒)
     * @param endTime       结束时间戳(毫秒)
     * @param beginDateList 开始段时间戳 和 结束段时间戳 一一对应
     * @param endDateList   结束段时间戳 和 开始段时间戳 一一对应
     */
    public static void getIntervalTimeByWeek(Long startTime, Long endTime, List<Long> beginDateList, List<Long> endDateList) {
        Date startDate = new Date(startTime);
        Date endDate = new Date(endTime);
        SimpleDateFormat sdw = new SimpleDateFormat("E");
        Calendar calendar = Calendar.getInstance();
        String begin = sdw.format(startDate);
        calendar.setTime(startDate);
        beginDateList.add(calendar.getTimeInMillis());
        if ("星期一".equals(begin)) {
            addTimeStamp(beginDateList, endDateList, startDate, endDate, sdw, calendar);
        } else {
            if ("星期日".equals(sdw.format(startDate))) {
                Calendar special = Calendar.getInstance();
                special.setTime(startDate);
                special.set(Calendar.HOUR_OF_DAY, 23);
                special.set(Calendar.MINUTE, 59);
                special.set(Calendar.SECOND, 59);
                endDateList.add(special.getTime().getTime());
            }
            addTimeStamp(beginDateList, endDateList, startDate, endDate, sdw, calendar);
        }
    }

    private static void addTimeStamp(List<Long> beginDateList, List<Long> endDateList, Date startDate, Date endDate, SimpleDateFormat sdw, Calendar calendar) {
        while (startDate.getTime() < endDate.getTime()) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            startDate = calendar.getTime();
            if ("星期一".equals(sdw.format(startDate))) {
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                beginDateList.add(calendar.getTimeInMillis());
            } else if ("星期日".equals(sdw.format(startDate)) || startDate.getTime() >= endDate.getTime()) {
                if (startDate.getTime() <= endDate.getTime()) {
                    calendar.set(Calendar.HOUR_OF_DAY, 23);
                    calendar.set(Calendar.MINUTE, 59);
                    calendar.set(Calendar.SECOND, 59);
                    endDateList.add(calendar.getTimeInMillis());
                } else {
                    endDateList.add(endDate.getTime());
                }
            }
        }
    }

    /**
     * 按照月份分割一段时间
     *
     * @param startTime     开始时间戳(毫秒)
     * @param endTime       结束时间戳(毫秒)
     * @param beginDateList 开始段时间戳 和 结束段时间戳 一一对应
     * @param endDateList   结束段时间戳 和 开始段时间戳 一一对应
     */
    public static void getIntervalTimeByMonth(Long startTime, Long endTime, List<Long> beginDateList, List<Long> endDateList) {
        Date startDate = new Date(startTime);
        Date endDate = new Date(endTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        beginDateList.add(calendar.getTimeInMillis());
        while (calendar.getTimeInMillis() < endDate.getTime()) {
            calendar.add(Calendar.MONTH, 1);
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.add(Calendar.DATE, -1);
            calendar.set(Calendar.HOUR_OF_DAY, 13);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            if(calendar.getTimeInMillis() < endDate.getTime()){
                endDateList.add(calendar.getTimeInMillis());
            } else {
                endDateList.add(endDate.getTime());
                break;
            }
            calendar.add(Calendar.DATE, 1);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            beginDateList.add(calendar.getTimeInMillis());
        }
    }

    public static void main(String[] args) throws ParseException {
        Date beginDate = DateFormat.getDateInstance().parse("2020/10/27");
        Date endDate = DateFormat.getDateInstance().parse("2020/12/01");
        List<Long> beginDateList = new ArrayList<Long>();
        // 4.结束时间段区间集合
        List<Long> endDateList = new ArrayList<Long>();
        DateUtil.getIntervalTimeByMonth(beginDate.getTime(), endDate.getTime(), beginDateList, endDateList);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < beginDateList.size(); i++) {
            Long beginStr = beginDateList.get(i);
            Long endStr = endDateList.get(i);
            String begin1 = sdf.format(new Date(beginStr));
            String end1 = sdf.format(new Date(endStr));
            System.out.println("第" + i + "段时间区间:" + begin1 + "-------" + end1);
        }
    }
}
