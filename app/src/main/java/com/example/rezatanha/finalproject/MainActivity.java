package com.example.rezatanha.finalproject;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.rezatanha.finalproject.View.alarm.AlarmListActivity;
import com.example.rezatanha.finalproject.View.medicine.MedicineListActivity;
import com.example.rezatanha.finalproject.View.prescription.PrescriptionListActivity;
import com.example.rezatanha.finalproject.View.report.ReportListActivity;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_ALL = 1;

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        askForPermissions();
    }

    private void askForPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] PERMISSIONS = {
                    android.Manifest.permission.DISABLE_KEYGUARD,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.WAKE_LOCK,
                    android.Manifest.permission.RECEIVE_BOOT_COMPLETED,
            };
            if (!hasPermissions(getApplicationContext(), PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_ALL);
            }
        } else {
            start();
        }
    }

    private void start() {
        setContentView(R.layout.activity_main);
        findViewById(R.id.main_content_medicines).setOnClickListener(v -> {
            Intent myIntent = new Intent(getApplicationContext(), MedicineListActivity.class);
            startActivity(myIntent);
        });
        findViewById(R.id.main_content_alarms).setOnClickListener(v -> {
            Intent myIntent = new Intent(getApplicationContext(), AlarmListActivity.class);
            startActivity(myIntent);
        });
        findViewById(R.id.main_content_history).setOnClickListener(v -> {
            Intent myIntent = new Intent(getApplicationContext(), ReportListActivity.class);
            startActivity(myIntent);
        });
        findViewById(R.id.main_content_prescription).setOnClickListener(v -> {
            Intent myIntent = new Intent(getApplicationContext(), PrescriptionListActivity.class);
            startActivity(myIntent);
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_ALL) {
            boolean flag = true;
            Log.e("MainActivity",String.format("length %s",grantResults.length));
            if (grantResults.length > 0) {
                for (int i=0;i<grantResults.length;i++ ) {
                    int item = grantResults[i];
                    Log.e("MainActivity",String.format("item %s, PKG: %s",item , PackageManager.PERMISSION_GRANTED));
                    if (item != PackageManager.PERMISSION_GRANTED) {
                        flag = false;
                        break;
                    }
                }
                Log.e("MainActivity",String.format("flag %s",flag));
                if (flag) {
                    start();
                } else {
                    Toast.makeText(MainActivity.this, "دسترسی داده نشد.", Toast.LENGTH_LONG).show();
                    finish();
                }
            } else {
                Toast.makeText(MainActivity.this, "دسترسی داده نشد.", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }
}
