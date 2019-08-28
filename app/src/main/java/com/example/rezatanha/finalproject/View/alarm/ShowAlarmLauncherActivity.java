package com.example.rezatanha.finalproject.View.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.rezatanha.finalproject.Controller.alarmController.AlarmReceiver;
import com.example.rezatanha.finalproject.Controller.db.DatabaseHelper;
import com.example.rezatanha.finalproject.Controller.parcelableUtil.ParcelableUtil;
import com.example.rezatanha.finalproject.Model.Alarm.Alarm;
import com.example.rezatanha.finalproject.Model.Medicine.Medicine;
import com.example.rezatanha.finalproject.Model.Report.Report;
import com.example.rezatanha.finalproject.R;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import saman.zamani.persiandate.PersianDate;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;
import static java.lang.String.valueOf;

public class ShowAlarmLauncherActivity extends AppCompatActivity {
    private static final String TAG = "Showing Alarm";
    private SliderLayout mDemoSlider;
    int snoozeCounter;
    Alarm alarm;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(FLAG_FULLSCREEN, FLAG_FULLSCREEN);
        if (Build.VERSION.SDK_INT >= 27) {
            setShowWhenLocked(true);
            setTurnScreenOn(true);
        } else {
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                    | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                    | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                    | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
        setContentView(R.layout.activity_alarm_luncher);
        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            snoozeCounter = b.getInt("SNOOZE_COUNTER");
            alarm = ParcelableUtil.getFromByteArray( this.getIntent().getByteArrayExtra("Alarm"));
            Log.e("Alarm", "Time: " + alarm.getHour() + " : " + alarm.getMinute() + " Snooze counter: " + snoozeCounter);
        } else {
            finish();
        }
        mDemoSlider = findViewById(R.id.slider);
        List<Medicine> allMedicine = alarm.getMedicineList();
        for (int i = 0; i < allMedicine.size(); i++) {
            String description = "اسم: " + allMedicine.get(i).getName() + " واحد مصرفی: " + allMedicine.get(i).getValueOfUse() + " " + allMedicine.get(i).getUnit();
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView
                    .description(description)
                    .image(new File(allMedicine.get(i).getImage()))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            mDemoSlider.addSlider(textSliderView);
        }
        mp = MediaPlayer.create(getApplicationContext(), Uri.parse(alarm.getAlarmSound()));
        mp.setLooping(true);
        mp.start();
        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                rejectAlarm();
            }
        }, 30 * 1000);
    }

    public void acceptCLicked(View view) {
        acceptAlarm();
    }

    public void rejectClicked(View view) {
        rejectAlarm();
    }

    public void snoozeClicked(View view) {
        if (snoozeCounter < 5)
            snoozeAlarm();
        else
            rejectAlarm();
    }

    @Override
    public void onBackPressed() {
        mp.stop();
        rejectAlarm();
        super.onBackPressed();
    }

    private void acceptAlarm() {

        //Toast.makeText(getApplicationContext(),"Alarm Accept",Toast.LENGTH_LONG).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Report report = new Report();
                PersianDate pdate = new PersianDate(new Date().getTime());
                String header = "پذیرش هشدار، در تاریخ: " + pdate.toString();
                report.setHeader(header);
                report.setBody(alarm.toString());
                report.setDate(new Date());
                DatabaseHelper databaseHelper = DatabaseHelper.getDatabase(getApplicationContext());
                databaseHelper.daoAccess().insertReport(report);
            }
        }).start();

        mp.stop();
        setAlarm(alarm.getDayOfWeek(), alarm, alarm.getId() + 1);

        finish();
    }

    private void setAlarm(int dayOfWeek, Alarm alarm, int Id) {

        Calendar cal_alarm = Calendar.getInstance();
        //cal_alarm.set(Calendar.DAY_OF_WEEK, dayOfWeek);
        cal_alarm.add(Calendar.DAY_OF_WEEK, 7);
        cal_alarm.set(Calendar.HOUR_OF_DAY, alarm.getHour());
        cal_alarm.set(Calendar.MINUTE, alarm.getMinute());
        cal_alarm.set(Calendar.SECOND, 0);
        cal_alarm.set(Calendar.MILLISECOND, 0);
        Log.e("Alarm date", "hour: " + alarm.getHour() + " minute: " + alarm.getMinute());
        Log.e("Time in millis ", cal_alarm.getTime().toString());
//        Intent intent = new Intent(ShowAlarmLauncher.this, AlarmReceiver.class);
//        Bundle b = new Bundle(); b.putSerializable("Alarm", ic_alarm_clock); b.putInt("SNOOZE_COUNTER", 0);
//        intent.putExtras(b);
//        PendingIntent pendingIntent ;
//        final int _id =(Id*7) - dayOfWeek;
//        Log.e("ID Check", valueOf(_id));
//        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), _id, intent, 0);
//        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//        Log.e("Configured Alarm ", "Set to: " + cal_alarm.getTime().toString()+" and interval is: 7");
//        alarmManager.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);
        //7 * 24 * 60 * 60 * 1000
    }

    private void snoozeAlarm() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Report report = new Report();
                PersianDate pdate = new PersianDate(new Date().getTime());
                String header = "تاخیر در هشدار، در تاریخ: " + pdate.toString();
                report.setHeader(header);
                report.setBody(alarm.toString());
                report.setDate(new Date());
                DatabaseHelper databaseHelper = DatabaseHelper.getDatabase(getApplicationContext());
                databaseHelper.daoAccess().insertReport(report);
            }
        }).start();

        mp.stop();
        snoozeCounter++;
        int snoozeTimeInMinutes = 1;
        Log.e(TAG, "alarmSnooze() - snoozeCounter = " + snoozeCounter);
        // setup next snooze ic_alarm_clock time
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, snoozeTimeInMinutes);
        long nextSnoozeTime = c.getTimeInMillis();
        // set new snooze ic_alarm_clock
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        Bundle b = new Bundle();
        b.putSerializable("Alarm", alarm);
        b.putInt("SNOOZE_COUNTER", snoozeCounter);
        intent.putExtras(b);
        PendingIntent pendingIntent;
        final int _id = (int) System.currentTimeMillis();
        Log.e("ID Check", valueOf(_id));
        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), _id, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Log.e(TAG, "Snooze set to: " + c.getTime().toString());
        alarmManager
                .set(AlarmManager.RTC_WAKEUP, nextSnoozeTime, pendingIntent);
        finish();
    }

    private void rejectAlarm() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Report report = new Report();
                PersianDate pdate = new PersianDate(new Date().getTime());
                String header = "رد دادن هشدار، در تاریخ: " + pdate.toString();
                report.setHeader(header);
                report.setBody(alarm.toString());
                report.setDate(new Date());
                DatabaseHelper databaseHelper = DatabaseHelper.getDatabase(getApplicationContext());
                databaseHelper.daoAccess().insertReport(report);
            }
        }).start();

        mp.stop();
        finish();
    }
}
