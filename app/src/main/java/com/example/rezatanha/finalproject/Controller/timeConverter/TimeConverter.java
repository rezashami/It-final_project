package com.example.rezatanha.finalproject.Controller.timeConverter;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

import saman.zamani.persiandate.PersianDate;

import static java.lang.String.valueOf;

public class TimeConverter {
    public static Date getGEOTime(Integer year, Integer month, Integer day) {
        if (year == 0 || month == 0 || day == 0) {
            return new Date();
        }
        PersianDate pDate = new PersianDate().setShDay(day).setShYear(year).setShMonth(month).setHour(0).setMinute(0).setSecond(0);
        return pDate.toDate();

    }

    public static String getPersianDashedTime(Date d) {
        if (d == null)
            return "ثبت نشده";
        Log.e("TimeConverter", d.toString());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(d);
        PersianDate persianDate = new PersianDate();
        int[] intDate = persianDate.toJalali(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        String month = intDate[1] < 10 ? "0" + intDate[1] : String.valueOf(intDate[1]);
        String day = intDate[2] < 10 ? "0" + intDate[2] : String.valueOf(intDate[2]);
        return toPersianNumber(intDate[0] + " - " + month + " - " + day);
    }

    @NonNull
    public static String toPersianNumber(String text) {
        String[] persianNumbers = new String[]{"۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹"};
        if (text.length() == 0) {
            return "";
        }
        StringBuilder out = new StringBuilder();
        int length = text.length();
        for (int i = 0; i < length; i++) {
            char c = text.charAt(i);
            if ('0' <= c && c <= '9') {
                int number = Integer.parseInt(valueOf(c));
                out.append(persianNumbers[number]);
            } else {
                out.append(c);
            }
        }

        return out.toString();
    }

    public static String getDayString(Date input, int number) {
        String result = "";
        if (input == null)
            return result;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(input);
        PersianDate persianDate = new PersianDate();
        int[] intDate = persianDate.toJalali(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));
        if (number == 2) {
            result = String.valueOf(intDate[0]);
        } else if (number == 1) {
            result = intDate[1] < 10 ? "0" + intDate[1] : String.valueOf(intDate[1]);
        } else if (number == 0) {
            result = intDate[2] < 10 ? "0" + intDate[2] : String.valueOf(intDate[2]);
        }
        return result;
    }
}
