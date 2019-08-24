package com.example.rezatanha.finalproject.Model.Alarm;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.rezatanha.finalproject.Controller.db.DateConverter;
import com.example.rezatanha.finalproject.Controller.db.ListConverter;
import com.example.rezatanha.finalproject.Controller.db.ListIntegerConverter;
import com.example.rezatanha.finalproject.Controller.db.ListMedicineConverter;
import com.example.rezatanha.finalproject.Model.Medicine.Medicine;

import java.io.Serializable;
import java.util.List;

import static java.lang.String.valueOf;

@Entity(tableName = "alarm")
@TypeConverters({DateConverter.class, ListConverter.class, ListMedicineConverter.class, ListIntegerConverter.class})
public class Alarm implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private String alarmSound;
    private List<Integer> dates;
    private int hour;
    private int minute;
    private int second;
    private List<Medicine> medicineList;
    private int unique;
    private int dayOfWeek;

    public Alarm() {
    }

    @NonNull
    public int getId() {
        return id;
    }

    public List<Integer> getDates() {
        return dates;
    }

    public void setDates(List<Integer> dates) {
        this.dates = dates;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }


    public List<Medicine> getMedicineList() {
        return medicineList;
    }

    public void setMedicineList(List<Medicine> medicineList) {
        this.medicineList = medicineList;
    }

    public String getAlarmSound() {
        return alarmSound;
    }

    public void setAlarmSound(String alarmSound) {
        this.alarmSound = alarmSound;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getUnique() {
        return unique;
    }

    public void setUnique(int unique) {
        this.unique = unique;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    @Ignore
    public String toPersianNumber(String text) {
        String[] persianNumbers = new String[]{"۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹"};
        if (text.length() == 0) {
            return "";
        }
        String out = "";
        int length = text.length();
        for (int i = 0; i < length; i++) {
            char c = text.charAt(i);
            if ('0' <= c && c <= '9') {
                int number = Integer.parseInt(valueOf(c));
                out += persianNumbers[number];
            } else if (c == '٫') {
                out += '،';
            } else {
                out += c;
            }
        }

        return out;
    }

    @Ignore
    public String toPersianDays(List<Integer> input) {
        String[] persianDays = new String[]{"", "یک شنبه", "دوشنبه", "سه شنبه", "چهارشنبه", "پنج شنبه", "جمعه", "شنبه"};
        String res = "";
        for (int i = 0; i < input.size(); i++) {
            res += persianDays[input.get(i)] + "\n";
        }
        return res;
    }

    @Ignore
    @Override
    public String toString() {


        String allMedicine = "";
        for (int i = 0; i < medicineList.size(); i++) {
            allMedicine += medicineList.get(i).getName() + "\n";
        }
        return "در ساعت: " + toPersianNumber(valueOf(hour)) + ":" + toPersianNumber(valueOf(minute)) + "شامل روزهای:\n " + toPersianDays(dates) + "شامل داروهای زیر:\n " + allMedicine;
    }
}
