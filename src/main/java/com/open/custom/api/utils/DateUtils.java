package com.open.custom.api.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static final String DEFORT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static String getCurDateStr() {
        Date now = new Date();
        SimpleDateFormat sf = new SimpleDateFormat(DEFORT_DATE_FORMAT);
        return sf.format(now);
    }

    public static String getDateStr(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sf = new SimpleDateFormat(DEFORT_DATE_FORMAT);
        return sf.format(date);
    }

    public static String getDateStr(Date date, String patten) {
        if (date == null || patten == null) {
            return null;
        }
        SimpleDateFormat sf = new SimpleDateFormat(patten);
        return sf.format(date);
    }

    public static Date parseDate(String date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sf = new SimpleDateFormat(DEFORT_DATE_FORMAT);
        try {
            return sf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date parseDate(String date, String patten) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sf = new SimpleDateFormat(patten);
        try {
            return sf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
