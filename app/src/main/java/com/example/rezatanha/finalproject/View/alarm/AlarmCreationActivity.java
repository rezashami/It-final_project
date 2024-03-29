package com.example.rezatanha.finalproject.View.alarm;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.rezatanha.finalproject.Model.Alarm.Alarm;
import com.example.rezatanha.finalproject.Model.Medicine.Medicine;
import com.example.rezatanha.finalproject.R;
import com.example.rezatanha.finalproject.View.medicine.ShowMedicineListActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AlarmCreationActivity extends AppCompatActivity {

    public static final String EXTRA_REPLY = "com.example.android.wordlistsql.REPLY";
    int hour = -1;
    int minute = -1;
    String soundPath = null;
    List<Medicine> medicineList = Collections.emptyList();
    Alarm alarm = null;
    List<Integer> days = null;
    CheckBox sunday, monday, tuesday, wednesday, thursday, friday, saturday;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_creation);
        Toolbar toolbar = findViewById(R.id.alarm_creation_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        sunday = findViewById(R.id.sunday_checkBox);
        monday = findViewById(R.id.monday_checkBox);
        tuesday = findViewById(R.id.tuesday_checkBox);
        wednesday = findViewById(R.id.wednesday_checkBox);
        thursday = findViewById(R.id.thursday_checkBox);
        friday = findViewById(R.id.friday_checkBox);
        saturday = findViewById(R.id.saturday_checkBox);
        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            alarm = (Alarm) b.getSerializable("Alarm");
            if (alarm != null) {
                hour = alarm.getHour();
                minute = alarm.getMinute();
                soundPath = alarm.getAlarmSound();
                medicineList = alarm.getMedicineList();
                TextView alarmSong = findViewById(R.id.txt_alarm_song_name);
                alarmSong.setVisibility(View.VISIBLE);
                alarmSong.setText(alarm.getAlarmSound());
                TextView drugList = findViewById(R.id.txt_alarm_drugs_name);
                drugList.setVisibility(View.VISIBLE);
                for (int i = 0; i < alarm.getMedicineList().size(); i++) {
                    drugList.setText(drugList.getText() + "\n" + alarm.getMedicineList().get(i).getName());
                }
                TextView alarmTime = findViewById(R.id.txt_alarm_time);
                alarmTime.setVisibility(View.VISIBLE);
                alarmTime.setText(alarm.getHour() + " : " + alarm.getMinute());
                initiateCheckBoxes(alarm.getDates());
            }
        }
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.save_button_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save_button_in_menu) {
            save();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void save() {
        getCheckBoxes();
        Intent replyIntent = new Intent();
        if (validate()) {
            if (alarm == null) {
                alarm = new Alarm();
            }
            alarm.setDates(days);
            alarm.setHour(hour);
            alarm.setMinute(minute);
            alarm.setSecond(0);
            alarm.setMedicineList(medicineList);
            alarm.setAlarmSound(soundPath);
            replyIntent.putExtra(EXTRA_REPLY, alarm);
            setResult(RESULT_OK, replyIntent);
        } else {
            setResult(RESULT_CANCELED, replyIntent);
        }
        finish();
    }

    private void initiateCheckBoxes(List<Integer> input) {
        for (int i = 0; i < input.size(); i++) {
            switch (input.get(i)) {
                case 1:
                    sunday.setChecked(true);
                    break;
                case 2:
                    monday.setChecked(true);
                    break;
                case 3:
                    tuesday.setChecked(true);
                    break;
                case 4:
                    wednesday.setChecked(true);
                    break;
                case 5:
                    thursday.setChecked(true);
                    break;
                case 6:
                    friday.setChecked(true);
                    break;
                case 7:
                    saturday.setChecked(true);
                    break;
            }
        }
    }

    private void getCheckBoxes() {

        days = new ArrayList<>();
        if (sunday.isChecked()) days.add(1);
        if (monday.isChecked()) days.add(2);
        if (tuesday.isChecked()) days.add(3);
        if (wednesday.isChecked()) days.add(4);
        if (thursday.isChecked()) days.add(5);
        if (friday.isChecked()) days.add(6);
        if (saturday.isChecked()) days.add(7);
    }

    public Boolean validate() {
        return soundPath != null && medicineList.size() != 0 && minute != -1 && hour != -1 && days != null;
    }

    public void onAddMusic(View view) {

        Intent intent_upload = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent_upload, 1);
    }

    public void onAddMedicine(View view) {
        Intent intent_upload = new Intent(this, ShowMedicineListActivity.class);
        startActivityForResult(intent_upload, 2);
    }

    public void onTimePicking(View view) {
        Intent intent_upload = new Intent(this, TimePickingActivity.class);
        if (alarm != null) {
            Bundle b = new Bundle();
            b.putString("Hour", String.valueOf(alarm.getHour()));
            b.putString("Minute", String.valueOf(alarm.getMinute()));
            intent_upload.putExtras(b);
        }
        startActivityForResult(intent_upload, 4);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*
         * Code == 1 yani sound
         * Code == 2 yani list medicine
         * Code == 3 yani date
         * Code == 4 yani time
         * */
        if (requestCode == 1 && resultCode == Activity.RESULT_OK) {
            Uri selectedMusic = data.getData();
            if (selectedMusic != null) {
                soundPath = selectedMusic.toString();
            }
            TextView txt = findViewById(R.id.txt_alarm_song_name);
            txt.setVisibility(View.VISIBLE);
            if (selectedMusic != null) {
                txt.setText(selectedMusic.toString());
            }
        } else if (requestCode == 2 && resultCode == Activity.RESULT_OK) {
            Bundle b = data.getExtras();
            if (b != null) {
                medicineList = (List<Medicine>) b.getSerializable(EXTRA_REPLY);
                TextView txt = findViewById(R.id.txt_alarm_drugs_name);
                txt.setVisibility(View.VISIBLE);
                txt.setText("");
                for (int i = 0; i < medicineList.size(); i++) {
                    medicineList.get(i).setUsed(true);
                    txt.setText(txt.getText() + "\n" + medicineList.get(i).getName());
                }
            }
            Log.e("TagComeback", String.valueOf(medicineList.size()));
        } else if (requestCode == 4 && resultCode == Activity.RESULT_OK) {
            Bundle b = data.getExtras();
            String stringHour = null;
            String stringMinute = null;
            if (b != null) {
                stringHour = b.getString("Hour");
                stringMinute = b.getString("Minute");
            }
            Log.i("Apply bundle", b.toString() + stringHour + stringMinute);
            minute = Integer.parseInt(stringMinute);
            hour = Integer.parseInt(stringHour);
            TextView txt = findViewById(R.id.txt_alarm_time);
            txt.setVisibility(View.VISIBLE);
            txt.setText(hour + " : " + minute);
        }
    }

}
