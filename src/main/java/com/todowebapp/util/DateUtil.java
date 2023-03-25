package com.todowebapp.util;

import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Slf4j
public class DateUtil {

    public static final String DEFAULT_DATE_FORMAT = "yyyyMMddHHmmss";
    public static final String DEFAULT_DATE_IN_FORMAT = "yyyyMMdd";
    public static final String DEFAULT_DATE_OUT_FORMAT = "yyyyMMdd";

    public static Date stringToDate(String s, String inFormat) throws ParseException {
        if (s == null){
            throw new ParseException("date string is null", 0);
        }

        if (inFormat == null) {
            throw new ParseException("format string is null", 0);
        }


        SimpleDateFormat formatter = new SimpleDateFormat(inFormat);

        Date date = null;

        try {
            date = formatter.parse(s);
        } catch (ParseException e) {
            log.error(" wrong format or date :\"" + s + "\" with format \"" + inFormat + "\"", 0);
        }

        if (!formatter.format(date).equals(s))
            throw new ParseException( "Out of bound date:\"" + s + "\" with format \"" + inFormat + "\"", 0);

        return date;
    }

    public static Date stringToDate(String s) throws ParseException {
        return stringToDate(s, DEFAULT_DATE_IN_FORMAT);
    }

    /**
     * java.util.Date 타입을  java.lang.String 타입으로 변환한다
     *
     * @param date
     * @return
     */

    public static String dateToString(Date date) {
        return dateToString(date, DEFAULT_DATE_IN_FORMAT);
    }

    /**
     * java.util.Date 타입을  java.lang.String 타입으로 변환한다
     *
     * @param date
     * @return
     */

    public static String dateToString(Date date, String inFormat) {
        SimpleDateFormat formatter = new SimpleDateFormat(inFormat);
        return formatter.format(date);
    }

    public static int getBetweenDate(String fromDate, String toDate) throws ParseException {// 지정된 두 일자간의 차일수
        long betWeenTime = Math.abs(stringToDate(fromDate).getTime() - stringToDate(toDate).getTime());
        return (int) (betWeenTime / (1000 * 60 * 60 * 24));
    }

}
