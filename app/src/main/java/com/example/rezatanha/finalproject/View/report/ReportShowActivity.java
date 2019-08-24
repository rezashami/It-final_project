package com.example.rezatanha.finalproject.View.report;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.rezatanha.finalproject.Model.Report.Report;
import com.example.rezatanha.finalproject.R;

import saman.zamani.persiandate.PersianDate;

public class ReportShowActivity extends AppCompatActivity {
    public static final int REPORT_DELETE_RESULT_CODE = 32;
    Report report;

    TextView txt_header, txt_body, txt_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_show);
        txt_header = findViewById(R.id.report_header_show);
        txt_body = findViewById(R.id.report_body_show);
        txt_date = findViewById(R.id.report_date_show);
        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            report = (Report) b.getSerializable("Report");
        }
        txt_header.setText(report.getHeader());
        txt_body.setText(report.getBody());
        PersianDate pdate = new PersianDate(report.getDate().getTime());
        txt_date.setText(pdate.toString());
        Button removeButton = findViewById(R.id.btn_remove_report);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ReportShowActivity.this);
                alert.setTitle("حذف گزارش");
                alert.setMessage("آیا مطمئن هستید؟");
                alert.setPositiveButton("بلی", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent replyIntent = new Intent();
                        replyIntent.putExtra("Removed item", report);
                        setResult(REPORT_DELETE_RESULT_CODE, replyIntent);
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
        Button shareButton = findViewById(R.id.btn_share_report);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = "سربرگ: " + report.getHeader() + "\nبدنه: " + report.getBody() + "\nتاریخ: " + report.getDate();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, text);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

    }
}
