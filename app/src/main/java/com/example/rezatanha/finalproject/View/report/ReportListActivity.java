package com.example.rezatanha.finalproject.View.report;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.example.rezatanha.finalproject.Controller.report.ReportViewModel;
import com.example.rezatanha.finalproject.Controller.reportRecyclerAdapter.ReportListAdapter;
import com.example.rezatanha.finalproject.Model.Report.Report;
import com.example.rezatanha.finalproject.R;

import java.util.Objects;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ReportListActivity extends AppCompatActivity {
    public static final int SHOW_REPORT_REQUEST_CODE = 32;

    private ReportViewModel reportViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);
        Toolbar toolbar = findViewById(R.id.report_list_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.report_content_recycler);
        final ReportListAdapter adapter = new ReportListAdapter(this, item -> {
            Intent myIntent = new Intent(getApplicationContext(), ReportShowActivity.class);
            Bundle b = new Bundle();
            b.putSerializable("Report", item);
            myIntent.putExtras(b);
            startActivityForResult(myIntent, SHOW_REPORT_REQUEST_CODE);
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        reportViewModel = ViewModelProviders.of(this).get(ReportViewModel.class);
        reportViewModel.getAllReports().observe(this, adapter::setReports);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SHOW_REPORT_REQUEST_CODE && resultCode == ReportShowActivity.REPORT_DELETE_RESULT_CODE) {
            Report report = (Report) data.getSerializableExtra("Removed item");
            reportViewModel.remove(report);
            Toast.makeText(ReportListActivity.this, "گزارش پاک شد!", Toast.LENGTH_LONG).show();
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
}
