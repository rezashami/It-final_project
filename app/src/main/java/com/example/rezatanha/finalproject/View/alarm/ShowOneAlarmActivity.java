package com.example.rezatanha.finalproject.View.alarm;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rezatanha.finalproject.Model.Alarm.Alarm;
import com.example.rezatanha.finalproject.R;

import java.util.List;

import static com.example.rezatanha.finalproject.View.alarm.AlarmCreationActivity.EXTRA_REPLY;
import static com.example.rezatanha.finalproject.View.alarm.AlarmListActivity.ALARM_DELETE_RESULT_CODE;
import static com.example.rezatanha.finalproject.View.alarm.AlarmListActivity.ALARM_UPDATE_RESULT_CODE;
import static com.example.rezatanha.finalproject.View.alarm.AlarmListActivity.NEW_ALARM_ACTIVITY_REQUEST_CODE;

public class ShowOneAlarmActivity extends AppCompatActivity {

    private Alarm alarm;
    TextView alarmSound, date, time, allMedicine;
    public static final int ALARM_NOT_CHANGED = 13;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_one_alarm);
        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            alarm = (Alarm) b.getSerializable("Alarm");
            Log.e("Sound", alarm.toString());
        }
        alarmSound = findViewById(R.id.txt_alarm_song_name_show);
        alarmSound.setText(getFileName(alarm.getAlarmSound()));
        showDate(alarm.getDates());
        time = findViewById(R.id.txt_alarm_time_show);
        time.setText(alarm.getHour() + ":" + alarm.getMinute() + ":" + alarm.getSecond());
        allMedicine = findViewById(R.id.txt_alarm_medicine_list_show);
        for (int i = 0; i < alarm.getMedicineList().size(); i++) {
            allMedicine.setText(allMedicine.getText() + "\n" + alarm.getMedicineList().get(i).getName());
        }
        Button btn = findViewById(R.id.btn_edit_alarm_info);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShowOneAlarmActivity.this, AlarmCreationActivity.class);
                Bundle b = new Bundle();
                b.putSerializable("Alarm", alarm);
                intent.putExtras(b);
                startActivityForResult(intent, NEW_ALARM_ACTIVITY_REQUEST_CODE);
            }
        });
        Button deleteButton = findViewById(R.id.btn_remove_alarm);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ShowOneAlarmActivity.this);
                alert.setTitle("حذف اعلان");
                alert.setMessage("آیا مطمئن هستید؟");
                alert.setPositiveButton("بلی", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent replyIntent = new Intent();
                        replyIntent.putExtra(EXTRA_REPLY, alarm);
                        setResult(ALARM_DELETE_RESULT_CODE, replyIntent);
                        finish();
                    }
                });
                alert.setNegativeButton("خیر", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // close dialog
                        dialog.cancel();
                    }
                });
                alert.show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_ALARM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Alarm word = (Alarm) data.getSerializableExtra(EXTRA_REPLY);
            Intent replyIntent = new Intent();
            replyIntent.putExtra(EXTRA_REPLY, word);
            setResult(ALARM_UPDATE_RESULT_CODE, replyIntent);
            finish();
        } else if (requestCode == NEW_ALARM_ACTIVITY_REQUEST_CODE && resultCode == RESULT_CANCELED) {
            Intent replyIntent = new Intent();
            setResult(ALARM_NOT_CHANGED, replyIntent);
            finish();
        }
    }

    @NonNull
    private String getFileName(String name) {
        Uri uri = Uri.parse(name);
        String path = getRealPathFromURI(uri);
        return path.substring(path.lastIndexOf("/") + 1);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result = "";
        try (Cursor cursor = getContentResolver().query(contentURI, null, null, null, null)) {
            if (cursor != null && cursor.moveToFirst()) {
                result = cursor.getString(
                        cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
            }
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
        }
        return result;
    }

    private void showDate(List<Integer> input) {
        date = findViewById(R.id.txt_alarm_date_show);
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < input.size(); i++) {
            switch (input.get(i)) {
                case 1:
                    temp.append("یک شنبه \n");
                    break;
                case 2:
                    temp.append("دوشنبه \n");
                    break;
                case 3:
                    temp.append("سه شنبه \n");
                    break;
                case 4:
                    temp.append("چهارشنبه \n");
                    break;
                case 5:
                    temp.append("پنج شنبه \n");
                    break;
                case 6:
                    temp.append("جمعه \n");
                    break;
                case 7:
                    temp.append("شنبه \n");
                    break;
            }
        }

        date.setText(temp);
    }
}
