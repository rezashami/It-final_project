package com.example.rezatanha.finalproject.View.alarm;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.rezatanha.finalproject.Controller.alarmSetter.AlarmSetter;
import com.example.rezatanha.finalproject.Controller.db.DatabaseHelper;
import com.example.rezatanha.finalproject.Controller.parcelableUtil.ParcelableUtil;
import com.example.rezatanha.finalproject.Model.Alarm.Alarm;
import com.example.rezatanha.finalproject.Model.Medicine.Medicine;
import com.example.rezatanha.finalproject.Model.Report.Report;
import com.example.rezatanha.finalproject.R;

import java.io.File;
import java.util.Date;
import java.util.List;

import saman.zamani.persiandate.PersianDate;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN;

public class ShowAlarmLauncherActivity extends AppCompatActivity {
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
        } else {
            finish();
        }
        SliderLayout mDemoSlider = findViewById(R.id.slider);
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
        handler.postDelayed(this::rejectAlarm, 30 * 1000);
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
        new Thread(() -> {
            Report report = new Report();
            PersianDate pdate = new PersianDate(new Date().getTime());
            String header = "پذیرش هشدار، در تاریخ: " + pdate.toString();
            report.setHeader(header);
            report.setBody(alarm.toString());
            report.setDate(new Date());
            DatabaseHelper databaseHelper = DatabaseHelper.getDatabase(getApplicationContext());
            databaseHelper.daoAccess().insertReport(report);
        }).start();
        mp.stop();
        new AlarmSetter(getApplicationContext()).setAlarmAfterRun(alarm.getDayOfWeek(), alarm, alarm.getId() + 1);
        finish();
    }

    private void snoozeAlarm() {
        new Thread(() -> {
            Report report = new Report();
            PersianDate persianDate = new PersianDate(new Date().getTime());
            String header = "تاخیر در هشدار، در تاریخ: " + persianDate.toString();
            report.setHeader(header);
            report.setBody(alarm.toString());
            report.setDate(new Date());
            DatabaseHelper databaseHelper = DatabaseHelper.getDatabase(getApplicationContext());
            databaseHelper.daoAccess().insertReport(report);
        }).start();
        new AlarmSetter(getApplicationContext()).setSnoozeAlarm(alarm.getDayOfWeek(), alarm, alarm.getId() + 1,snoozeCounter);
        mp.stop();
        snoozeCounter++;
        finish();
    }

    private void rejectAlarm() {
        new Thread(() -> {
            Report report = new Report();
            PersianDate pdate = new PersianDate(new Date().getTime());
            String header = "رد دادن هشدار، در تاریخ: " + pdate.toString();
            report.setHeader(header);
            report.setBody(alarm.toString());
            report.setDate(new Date());
            DatabaseHelper databaseHelper = DatabaseHelper.getDatabase(getApplicationContext());
            databaseHelper.daoAccess().insertReport(report);
        }).start();

        mp.stop();
        finish();
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
