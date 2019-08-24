package com.example.rezatanha.finalproject.Controller.alarmSetter;

import android.content.Context;

public class AlarmSetter {
    private final String TAG = AlarmSetter.class.getName();
    private final Context context;

    public AlarmSetter(Context context) {
        this.context = context;
    }

//    public void setGroupAlarm(List<DBAlarm> input) {
//        for (int i = 0; i < input.size(); i++) {
//            DBAlarm alarm = input.get(i);
//            Calendar cal_alarm = Calendar.getInstance();
//            if (alarm.getMyDate() == null) {
//                cal_alarm.setTime(new Date());
//                cal_alarm.add(Calendar.DATE, -1);
//                cal_alarm.set(Calendar.HOUR, 17);
//                cal_alarm.set(Calendar.MINUTE, 0);
//                cal_alarm.set(Calendar.SECOND, 0);
//            } else {
//                cal_alarm.setTime(alarm.getMyDate());
//                cal_alarm.set(Calendar.HOUR, 17);
//                cal_alarm.set(Calendar.MINUTE, 0);
//                cal_alarm.set(Calendar.SECOND, 0);
//            }
//            Intent intent = new Intent("MY.ACTION.ALARM");
//            intent.setClass(context, AlarmReceiver.class);
//            intent.putExtra("Alarm", ParcelableUtil.toByteArray(alarm));
//            PendingIntent pendingIntent;
//            pendingIntent = PendingIntent.getBroadcast(context, alarm.getId(), intent, 0);
//            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//            Log.e(TAG, "Set to: " + cal_alarm.getTime().toString());
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                if (alarmManager != null) {
//                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);
//                }
//            } else {
//                if (alarmManager != null) {
//                    alarmManager.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);
//                }
//            }
//        }
//    }
//
//    public void setOneAlarm(DBAlarm alarm) {
//        Calendar cal_alarm = Calendar.getInstance();
//        if (alarm.getMyDate() == null) {
//            cal_alarm.setTime(new Date());
//            cal_alarm.add(Calendar.DATE, -1);
//            cal_alarm.set(Calendar.HOUR, 17);
//            cal_alarm.set(Calendar.MINUTE, 0);
//            cal_alarm.set(Calendar.SECOND, 0);
//        } else {
//            cal_alarm.setTime(alarm.getMyDate());
//            cal_alarm.set(Calendar.HOUR, 17);
//            cal_alarm.set(Calendar.MINUTE, 0);
//            cal_alarm.set(Calendar.SECOND, 0);
//        }
//        Intent intent = new Intent(context, AlarmReceiver.class);
//        intent.putExtra("Alarm", ParcelableUtil.toByteArray(alarm));
//        PendingIntent pendingIntent;
//        pendingIntent = PendingIntent.getBroadcast(context, alarm.getId(), intent, FILL_IN_DATA);
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        Log.e(TAG, "Set to: " + cal_alarm.getTime().toString());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            if (alarmManager != null) {
//                alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);
//            }
//        } else {
//            if (alarmManager != null) {
//                alarmManager.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);
//            }
//        }
//    }
//
//    public void cancelAlarm(DBAlarm alarm) {
//        Intent intent = new Intent(context, AlarmReceiver.class);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarm.getId(), intent, 0);
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        alarmManager.cancel(pendingIntent);
//    }
//
//
//    public void showNotification(DBAlarm _alarm, Application application) {
//        class RUN implements Runnable {
//            private final DBAlarm dbAlarm;
//
//            private RUN(DBAlarm alarm) {
//                this.dbAlarm = alarm;
//            }
//
//            @Override
//            public void run() {
//                DBLogger report = new DBLogger();
//                PersianDate persianDate = new PersianDate(new Date().getTime());
//                String header = "نمایش هشدار، در تاریخ: " + persianDate.toString();
//                report.setHeader(header);
//                report.setBody(dbAlarm.toString());
//                report.setDate(new Date());
//                DatabaseManager databaseHelper = DatabaseManager.getDatabase(context);
//                databaseHelper.daoAccess().insertLog(report);
//            }
//        }
//        new Thread(new RUN(_alarm)).start();
//        InsuranceRepository insuranceRepository = new InsuranceRepository(application);
//        try {
//            DBAlarm dbAlarm = insuranceRepository.getOneDBAlarmByID(1);
//            if (dbAlarm.getId() != _alarm.getId()) {
//                for (int i = 0; i < _alarm.getUsers().size(); i++)
//                    dbAlarm.addUser(_alarm.getUsers().get(i));
//                insuranceRepository.removeDBAlarm(_alarm);
//            }
//            insuranceRepository.update(dbAlarm);
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        // start showing notification
//        Intent intent2 = new Intent(context, InsuranceListActivity.class);
//        intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent2, 0);
//        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
//                .setSmallIcon(R.drawable.logo)
//                .setContentTitle("هشدار اتمام بیمه")
//                .setContentText("برای مشاهده ضربه بزنید")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT).setContentIntent(pIntent)
//                .setAutoCancel(true)
//                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
//                .setLights(Color.RED, 3000, 3000).setSound(alarmSound).setOnlyAlertOnce(true);
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
//        notificationManager.notify(_alarm.getId(), builder.build());
//    }
}

