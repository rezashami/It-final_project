package com.example.rezatanha.finalproject.View.report;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.rezatanha.finalproject.Controller.report.ReportViewModel;
import com.example.rezatanha.finalproject.Controller.reportRecyclerAdapter.ReportListAdapter;
import com.example.rezatanha.finalproject.Model.Report.Report;
import com.example.rezatanha.finalproject.R;

public class ReportListActivity extends AppCompatActivity {
    public static final int SHOW_REPORT_REQUEST_CODE = 32;

    private ReportViewModel reportViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);
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
        // Get a new or existing ViewModel from the ViewModelProvider.
        reportViewModel = ViewModelProviders.of(this).get(ReportViewModel.class);


        // Update the cached copy of the words in the adapter.
        reportViewModel.getAllReports().observe(this, adapter::setReports);
        reportViewModel.getAllReports().observe(this, _reports -> adapter.setReports(_reports));

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SHOW_REPORT_REQUEST_CODE && resultCode == ReportShowActivity.REPORT_DELETE_RESULT_CODE) {
            Report report = (Report) data.getSerializableExtra("Removed item");
            reportViewModel.remove(report);
            Toast.makeText(getApplicationContext(), "گزارش پاک شد!", Toast.LENGTH_LONG).show();
        }
    }
}
