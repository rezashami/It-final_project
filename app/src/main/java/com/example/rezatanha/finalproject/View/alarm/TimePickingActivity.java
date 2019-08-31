package com.example.rezatanha.finalproject.View.alarm;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TimePicker;

import com.example.rezatanha.finalproject.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class TimePickingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_picking);
        Bundle b = this.getIntent().getExtras();

        TimePicker timePicker = findViewById(R.id.alarm_time_picker);
        if (b != null) {
            String hour = b.getString("Hour");
            String minute = b.getString("Minute");
            if (minute != null && hour != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    timePicker.setHour(Integer.parseInt(hour));
                    timePicker.setMinute(Integer.parseInt(minute));
                } else {
                    timePicker.setCurrentHour(Integer.parseInt(hour));
                    timePicker.setCurrentMinute(Integer.parseInt(minute));
                }
            }

        }
        Button btn = findViewById(R.id.btn_added_time_to_alarm);
        btn.setOnClickListener(view -> {
            Intent replyIntent = new Intent();
            Bundle b1 = new Bundle();
            int hour, minute;
            if (Build.VERSION.SDK_INT < 23) {
                hour = timePicker.getCurrentHour();
                minute = timePicker.getCurrentMinute();

            } else {
                hour = timePicker.getHour();
                minute = timePicker.getMinute();

            }
            b1.putString("Hour", String.valueOf(hour));
            b1.putString("Minute", String.valueOf(minute));
            replyIntent.putExtras(b1);
            Log.i("Check for bundle", b1.toString() + hour + minute);
            setResult(RESULT_OK, replyIntent);
            finish();
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
