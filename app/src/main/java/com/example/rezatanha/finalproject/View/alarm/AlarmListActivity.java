package com.example.rezatanha.finalproject.View.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.example.rezatanha.finalproject.Controller.alarm.AlarmViewModel;
import com.example.rezatanha.finalproject.Controller.alarmController.AlarmReceiver;
import com.example.rezatanha.finalproject.Controller.alarmRecyclerAdapter.AlarmListAdapter;
import com.example.rezatanha.finalproject.Controller.alarmSetter.AlarmSetter;
import com.example.rezatanha.finalproject.Model.Alarm.Alarm;
import com.example.rezatanha.finalproject.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.example.rezatanha.finalproject.View.alarm.ShowOneAlarmActivity.ALARM_NOT_CHANGED;
import static java.lang.String.valueOf;

public class AlarmListActivity extends AppCompatActivity {
    public static final int NEW_ALARM_ACTIVITY_REQUEST_CODE = 1;
    public static final int ALARM_DELETE_RESULT_CODE = 11;
    public static final int ALARM_UPDATE_RESULT_CODE = 10;
    public static final int SHOW_ALARM_REQUEST_CODE = 32;

    private AlarmViewModel alarmViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);
        Toolbar toolbar = findViewById(R.id.alarm_list_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        RecyclerView recyclerView = findViewById(R.id.alarm_content_recycler);
        final AlarmListAdapter adapter = new AlarmListAdapter(this, item -> {
            Intent myIntent = new Intent(getApplicationContext(), ShowOneAlarmActivity.class);
            Bundle b = new Bundle();
            b.putSerializable("Alarm", item);
            myIntent.putExtras(b);
            startActivityForResult(myIntent, SHOW_ALARM_REQUEST_CODE);
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        alarmViewModel = ViewModelProviders.of(this).get(AlarmViewModel.class);

        alarmViewModel.getAllWords().observe(this, adapter::setWords);
        alarmViewModel.getAllWords().observe(this, adapter::setWords);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(AlarmListActivity.this, AlarmCreationActivity.class);
            startActivityForResult(intent, NEW_ALARM_ACTIVITY_REQUEST_CODE);
        });
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_ALARM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Alarm alarm = (Alarm) data.getSerializableExtra(AlarmCreationActivity.EXTRA_REPLY);
            int Id = alarmViewModel.insert(alarm);
            List<Integer> days = alarm.getDates();
            AlarmSetter alarmSetter = new AlarmSetter(getApplicationContext());
            for (int i = 0; i < days.size(); i++)
                alarmSetter.setAlarm(days.get(i), alarm, Id + 1);
        } else if (requestCode == SHOW_ALARM_REQUEST_CODE && resultCode == ALARM_UPDATE_RESULT_CODE) {
            Toast.makeText(
                    getApplicationContext(),
                    "تغییرات با موفقیت اعمال شد!",
                    Toast.LENGTH_LONG).show();
            Alarm word = (Alarm) data.getSerializableExtra(AlarmCreationActivity.EXTRA_REPLY);
            alarmViewModel.update(word);
        } else if (requestCode == SHOW_ALARM_REQUEST_CODE && resultCode == ALARM_DELETE_RESULT_CODE) {
            Toast.makeText(
                    getApplicationContext(),
                    "هشدار پاک شد",
                    Toast.LENGTH_LONG).show();
            Alarm word = (Alarm) data.getSerializableExtra(AlarmCreationActivity.EXTRA_REPLY);
            alarmViewModel.remove(word);
        } else if (requestCode == NEW_ALARM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_CANCELED) {
            Toast.makeText(
                    getApplicationContext(),
                    "تمامی فیلدها را پر کنید!!",
                    Toast.LENGTH_LONG).show();
        } else if (requestCode == SHOW_ALARM_REQUEST_CODE && resultCode == ALARM_NOT_CHANGED) {
            Toast.makeText(
                    getApplicationContext(),
                    "تغییری اعمال نشد!",
                    Toast.LENGTH_LONG).show();
        }
    }
//    private void setAlarm(int dayOfWeek, Alarm alarm, int Id) {
//        Calendar cal_alarm = Calendar.getInstance();
//
//        cal_alarm.set(Calendar.DAY_OF_WEEK, dayOfWeek);
//        cal_alarm.set(Calendar.HOUR_OF_DAY, alarm.getHour());
//        cal_alarm.set(Calendar.MINUTE, alarm.getMinute());
//        cal_alarm.set(Calendar.SECOND, 0);
//        cal_alarm.set(Calendar.MILLISECOND, 0);
//        if (cal_alarm.before(Calendar.getInstance())) {
//            cal_alarm.add(Calendar.DAY_OF_WEEK, 7);
//        }
//        Log.e("Alarm date", "hour: " + alarm.getHour() + " minute: " + alarm.getMinute());
//        Log.e("Time in millis ", new Date(cal_alarm.getTimeInMillis()).toString());
//        Intent intent = new Intent(AlarmListActivity.this, AlarmReceiver.class);
//        Bundle b = new Bundle();
//        b.putSerializable("Alarm", alarm);
//        b.putInt("SNOOZE_COUNTER", 0);
//        intent.putExtras(b);
//        PendingIntent pendingIntent;
//        final int _id = (Id * 7) - dayOfWeek;
//        Log.e("ID Check", valueOf(_id));
//        pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), _id, intent, 0);
//        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
//        Log.e("Configured Alarm ", "Set to: " + cal_alarm.getTime().toString() + " and interval is: 7");
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//            if (alarmManager != null) {
//                alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);
//            }
//        } else {
//            if (alarmManager != null) {
//                alarmManager.set(AlarmManager.RTC_WAKEUP, cal_alarm.getTimeInMillis(), pendingIntent);
//            }
//        }
//        //7 * 24 * 60 * 60 * 1000
//    }

}
