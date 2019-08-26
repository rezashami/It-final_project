package com.example.rezatanha.finalproject.Controller.alarmController;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;

import com.example.rezatanha.finalproject.Controller.alarm.AlarmRepository;
import com.example.rezatanha.finalproject.Controller.alarmSetter.AlarmSetter;
import com.example.rezatanha.finalproject.Model.Alarm.Alarm;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MyJobIntent extends JobIntentService {
    static final int ALARM_SET_JOB_ID = 100;
    private static final String TAG = MyJobIntent.class.getSimpleName();
    private static final String ACTION_LOAD = "action.LOAD_DATA";
    private static final String ACTION_SINGLE = "action.SINGLE";

    public static void enqueueLoadWork(Context context) {
        Intent intent = new Intent(context, MyJobIntent.class);
        intent.setAction(ACTION_LOAD);
        enqueueWork(context, MyJobIntent.class, ALARM_SET_JOB_ID, intent);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        if (intent.getAction() != null) {
            if (ACTION_LOAD.equals(intent.getAction())) {
                AlarmRepository alarmRepository = new AlarmRepository(getApplication());
                List<Alarm> alarms = null;
                try {
                    alarms = alarmRepository.getListOfAlarms();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (alarms == null)
                    return;
                AlarmSetter alarmSetter = new AlarmSetter(getApplicationContext());
                alarmSetter.setGroupAlarm(alarms);
            }
        }
    }
}
