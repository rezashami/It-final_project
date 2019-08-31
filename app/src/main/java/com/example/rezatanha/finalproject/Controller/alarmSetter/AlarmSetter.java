package com.example.rezatanha.finalproject.Controller.alarmSetter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import com.example.rezatanha.finalproject.Controller.alarmController.AlarmReceiver;
import com.example.rezatanha.finalproject.Controller.parcelableUtil.ParcelableUtil;
import com.example.rezatanha.finalproject.Model.Alarm.Alarm;

import java.util.Calendar;
import java.util.List;

public class AlarmSetter {
    private final Context context;

    public AlarmSetter(Context context) {
        this.context = context;
    }

    public void setAlarm(int dayOfWeek, Alarm alarm, int Id) {
        Calendar cal_alarm = Calendar.getInstance();
        cal_alarm.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        cal_alarm.set(Calendar.HOUR_OF_DAY, alarm.getHour());
        cal_alarm.set(Calendar.MINUTE, alarm.getMinute());
        cal_alarm.set(Calendar.SECOND, 0);
        cal_alarm.set(Calendar.MILLISECOND, 0);
        if (cal_alarm.before(Calendar.getInstance())) {
            cal_alarm.add(Calendar.DAY_OF_WEEK, 7);
        }
        Intent intent = new Intent("MY.ACTION.ALARM");
        intent.setClass(context, AlarmReceiver.class);
        intent.putExtra("Alarm", ParcelableUtil.toByteArray(alarm));
        intent.putExtra("SNOOZE_COUNTER", 0);
        PendingIntent pendingIntent;
        final int _id = (Id * 7) - dayOfWeek;
        pendingIntent = PendingIntent.getBroadcast(context, _id, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (alarmManager != null) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);
            }
        } else {
            if (alarmManager != null) {
                alarmManager.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);
            }
        }
    }

    public void setAlarmAfterRun(int dayOfWeek, Alarm alarm, int Id) {
        Calendar cal_alarm = Calendar.getInstance();
        cal_alarm.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        cal_alarm.add(Calendar.DAY_OF_WEEK, 7);
        cal_alarm.set(Calendar.HOUR_OF_DAY, alarm.getHour());
        cal_alarm.set(Calendar.MINUTE, alarm.getMinute());
        cal_alarm.set(Calendar.SECOND, 0);
        cal_alarm.set(Calendar.MILLISECOND, 0);
        if (cal_alarm.before(Calendar.getInstance())) {
            cal_alarm.add(Calendar.DAY_OF_WEEK, 7);
        }
        Intent intent = new Intent("MY.ACTION.ALARM");
        intent.setClass(context, AlarmReceiver.class);
        intent.putExtra("Alarm", ParcelableUtil.toByteArray(alarm));
        intent.putExtra("SNOOZE_COUNTER", 0);
        PendingIntent pendingIntent;
        final int _id = (Id * 7) - dayOfWeek;
        pendingIntent = PendingIntent.getBroadcast(context, _id, intent, 0);
        Log.e("AlarmSetter", "Snooze set to: " + cal_alarm.getTime().toString());
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (alarmManager != null) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);
            }
        } else {
            if (alarmManager != null) {
                alarmManager.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);
            }
        }
    }

    public void setGroupAlarm(List<Alarm> alarms) {
        for (Alarm alarm : alarms) {
            List<Integer> days = alarm.getDates();
            for (int i = 0; i < days.size(); i++) {
                setAlarm(days.get(i), alarm, alarm.getId() + 1);
            }
        }
    }

    public void setSnoozeAlarm(int dayOfWeek, Alarm alarm, int Id, int snoozeCounter) {


        int snoozeTimeInMinutes = 1;
        Log.e("AlarmSetter", "alarmSnooze() - snoozeCounter = " + snoozeCounter);
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, snoozeTimeInMinutes);
        long nextSnoozeTime = c.getTimeInMillis();
        Intent intent = new Intent("MY.ACTION.ALARM");
        intent.setClass(context, AlarmReceiver.class);
        intent.putExtra("Alarm", ParcelableUtil.toByteArray(alarm));
        intent.putExtra("SNOOZE_COUNTER", snoozeCounter);
        PendingIntent pendingIntent;
        final int _id = (Id * 7) - dayOfWeek;
        pendingIntent = PendingIntent.getBroadcast(context, _id, intent, 0);
        Log.e("AlarmSetter", "Snooze set to: " + c.getTime().toString());
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (alarmManager != null) {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, nextSnoozeTime, pendingIntent);
            }
        } else {
            if (alarmManager != null) {
                alarmManager.set(AlarmManager.RTC_WAKEUP, nextSnoozeTime, pendingIntent);
            }
        }
    }
}

