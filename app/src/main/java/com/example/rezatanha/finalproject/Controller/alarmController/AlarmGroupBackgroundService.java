package com.example.rezatanha.finalproject.Controller.alarmController;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;


public class AlarmGroupBackgroundService extends Service {
    //InsuranceRepository insuranceRepository;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        insuranceRepository = new InsuranceRepository(getApplication());
//        try {
//            List<DBAlarm> alarms = insuranceRepository.getDBAlarms();
//            AlarmSetter alarmSetter = new AlarmSetter(getApplicationContext());
//            alarmSetter.setGroupAlarm(alarms);
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        stopSelf();
        return START_NOT_STICKY;
    }
}