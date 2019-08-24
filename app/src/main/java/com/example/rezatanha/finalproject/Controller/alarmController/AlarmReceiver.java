package com.example.rezatanha.finalproject.Controller.alarmController;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.example.rezatanha.finalproject.Controller.alarmController.android28.MyJobIntent;

public class AlarmReceiver extends BroadcastReceiver {
    Context myContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.myContext = context;
        String action = intent.getAction();
        if (action != null && action.equals("MY.ACTION.ALARM")) {
            Bundle extras = intent.getExtras();
            Intent myIntent = new Intent(context, AlarmSingleBackgroundService.class);
            if (extras != null) {
                myIntent.putExtras(extras);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                MyJobIntent.enqueueSingleWork(context, extras);
            } else {
                context.startService(myIntent);
            }
        }
        if (action != null && action.equals(Intent.ACTION_BOOT_COMPLETED)) {
            Intent myIntent = new Intent(context, AlarmGroupBackgroundService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                MyJobIntent.enqueueLoadWork(context);
            } else {
                context.startService(myIntent);
            }
        }
    }
}
