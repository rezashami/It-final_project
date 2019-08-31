package com.example.rezatanha.finalproject.View.report;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.rezatanha.finalproject.Model.Report.Report;
import com.example.rezatanha.finalproject.R;

import java.util.Objects;

import saman.zamani.persiandate.PersianDate;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ReportShowActivity extends AppCompatActivity {
    public static final int REPORT_DELETE_RESULT_CODE = 32;
    Report report;

    TextView txt_header, txt_body, txt_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_show);
        Toolbar toolbar = findViewById(R.id.report_show_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        txt_header = findViewById(R.id.report_header_show);
        txt_body = findViewById(R.id.report_body_show);
        txt_date = findViewById(R.id.report_date_show);
        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            report = (Report) b.getSerializable("Report");
            if (report != null) {
                txt_header.setText(report.getHeader());
                txt_body.setText(report.getBody());
                PersianDate persianDate = new PersianDate(report.getDate().getTime());
                txt_date.setText(persianDate.toString());
            } else
                finish();

        } else
            finish();
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
        menuInflater.inflate(R.menu.show_report_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_report_share_report:
                share();
                return true;
            case R.id.show_report_delete_report:
                delete();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void delete() {
        AlertDialog.Builder alert = new AlertDialog.Builder(ReportShowActivity.this);
        alert.setTitle("حذف گزارش");
        alert.setMessage("آیا مطمئن هستید؟");
        alert.setPositiveButton("بلی", (dialog, which) -> {
            Intent replyIntent = new Intent();
            replyIntent.putExtra("Removed item", report);
            setResult(REPORT_DELETE_RESULT_CODE, replyIntent);
            finish();
        });
        alert.setNegativeButton("خیر", (dialog, which) -> dialog.cancel());
        alert.show();
    }

    private void share() {
        String text = "سربرگ: " + report.getHeader() + "\nبدنه: " + report.getBody() + "\nتاریخ: " + report.getDate();
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}
