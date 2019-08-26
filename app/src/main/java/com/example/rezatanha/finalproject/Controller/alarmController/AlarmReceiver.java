package com.example.rezatanha.finalproject.Controller.alarmController;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.rezatanha.finalproject.View.alarm.ShowAlarmLauncherActivity;

public class AlarmReceiver extends BroadcastReceiver {
    Context myContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.myContext = context;
        String action = intent.getAction();
        if (action != null && action.equals("MY.ACTION.ALARM")) {
            Bundle extras = intent.getExtras();
            Intent newIntent = new Intent("android.intent.action.MAIN");
            newIntent.setClass(context,ShowAlarmLauncherActivity.class);
            if (extras != null) {
                newIntent.putExtras(extras);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(newIntent);
            }
        }
        if (action != null && action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            MyJobIntent.enqueueLoadWork(context);
        }
    }
}
