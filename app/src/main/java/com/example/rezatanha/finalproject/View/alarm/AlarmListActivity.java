package com.example.rezatanha.finalproject.View.alarm;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.rezatanha.finalproject.Controller.alarm.AlarmViewModel;
import com.example.rezatanha.finalproject.Controller.alarmRecyclerAdapter.AlarmListAdapter;
import com.example.rezatanha.finalproject.Controller.alarmSetter.AlarmSetter;
import com.example.rezatanha.finalproject.Controller.medicine.MedicineRepository;
import com.example.rezatanha.finalproject.Model.Alarm.Alarm;
import com.example.rezatanha.finalproject.R;

import java.util.List;
import java.util.Objects;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.example.rezatanha.finalproject.View.alarm.ShowOneAlarmActivity.ALARM_NOT_CHANGED;

public class AlarmListActivity extends AppCompatActivity {
    public static final int NEW_ALARM_ACTIVITY_REQUEST_CODE = 1;
    public static final int ALARM_DELETE_RESULT_CODE = 11;
    public static final int ALARM_UPDATE_RESULT_CODE = 10;
    public static final int SHOW_ALARM_REQUEST_CODE = 32;

    private AlarmViewModel alarmViewModel;
    private MedicineRepository medicineRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_list);
        Toolbar toolbar = findViewById(R.id.alarm_list_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        medicineRepository = new MedicineRepository(getApplication());
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
            medicineRepository.updateMany(alarm.getMedicineList());
            List<Integer> days = alarm.getDates();
            AlarmSetter alarmSetter = new AlarmSetter(getApplicationContext());
            for (int i = 0; i < days.size(); i++)
                alarmSetter.setAlarm(days.get(i), alarm, Id + 1);
            Toast.makeText(
                    AlarmListActivity.this,
                    "هشدار ذخیره شد!",
                    Toast.LENGTH_LONG).show();
        } else if (requestCode == SHOW_ALARM_REQUEST_CODE && resultCode == ALARM_UPDATE_RESULT_CODE) {
            Toast.makeText(
                    AlarmListActivity.this,
                    "تغییرات با موفقیت اعمال شد!",
                    Toast.LENGTH_LONG).show();
            Alarm word = (Alarm) data.getSerializableExtra(AlarmCreationActivity.EXTRA_REPLY);
            alarmViewModel.update(word);
        } else if (requestCode == SHOW_ALARM_REQUEST_CODE && resultCode == ALARM_DELETE_RESULT_CODE) {
            Toast.makeText(
                    AlarmListActivity.this,
                    "هشدار پاک شد",
                    Toast.LENGTH_LONG).show();
            Alarm word = (Alarm) data.getSerializableExtra(AlarmCreationActivity.EXTRA_REPLY);
            alarmViewModel.remove(word);
        } else if (requestCode == NEW_ALARM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_CANCELED) {
            Toast.makeText(
                    AlarmListActivity.this,
                    "تمامی فیلدها را پر کنید!!",
                    Toast.LENGTH_LONG).show();
        } else if (requestCode == SHOW_ALARM_REQUEST_CODE && resultCode == ALARM_NOT_CHANGED) {
            Toast.makeText(
                    AlarmListActivity.this,
                    "تغییری اعمال نشد!",
                    Toast.LENGTH_LONG).show();
        }
    }
}
