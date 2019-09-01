package com.example.rezatanha.finalproject.View.prescription;

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

import com.example.rezatanha.finalproject.Controller.prescription.PrescriptionViewModel;
import com.example.rezatanha.finalproject.Controller.prescriptionRecyclerAdapter.PrescriptionListAdapter;
import com.example.rezatanha.finalproject.Model.Prescription.Prescription;
import com.example.rezatanha.finalproject.R;

import java.util.Objects;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.example.rezatanha.finalproject.View.prescription.PrescriptionShowActivity.PRESCRIPTION_NOT_CHANGED;

public class PrescriptionListActivity extends AppCompatActivity {
    public static final int NEW_PRESCRIPTION_ACTIVITY_REQUEST_CODE = 1;
    public static final int SHOW_PRESCRIPTION_ACTIVITY_REQUEST_CODE = 2;
    public static final int PRESCRIPTION_DELETE_RESULT_CODE = 11;
    public static final int PRESCRIPTION_UPDATE_RESULT_CODE = 10;
    private PrescriptionViewModel prescriptionViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_list);
        Toolbar toolbar = findViewById(R.id.prescription_list_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.prescription_content_recycler);
        final PrescriptionListAdapter adapter = new PrescriptionListAdapter(this, item -> {
            Intent myIntent = new Intent(getApplicationContext(), PrescriptionShowActivity.class);
            myIntent.putExtra("Prescription", item);
            startActivityForResult(myIntent,SHOW_PRESCRIPTION_ACTIVITY_REQUEST_CODE);
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        prescriptionViewModel = ViewModelProviders.of(this).get(PrescriptionViewModel.class);
        prescriptionViewModel.getAllPrescriptions().observe(this, adapter::setPrescriptions);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(PrescriptionListActivity.this, AddPrescriptionActivity.class);
            startActivityForResult(intent, NEW_PRESCRIPTION_ACTIVITY_REQUEST_CODE);
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_PRESCRIPTION_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Prescription prescription = (Prescription) data.getSerializableExtra("Prescription");
            prescriptionViewModel.insert(prescription);
        } else if (requestCode == NEW_PRESCRIPTION_ACTIVITY_REQUEST_CODE && resultCode == RESULT_CANCELED) {
            Toast.makeText(PrescriptionListActivity.this, "همه موارد را پر کنید", Toast.LENGTH_SHORT).show();
        } else if (requestCode == SHOW_PRESCRIPTION_ACTIVITY_REQUEST_CODE && resultCode == PRESCRIPTION_DELETE_RESULT_CODE) {
            Prescription prescription = (Prescription) data.getSerializableExtra("Prescription");
            prescriptionViewModel.remove(prescription);
        } else if (requestCode == SHOW_PRESCRIPTION_ACTIVITY_REQUEST_CODE && resultCode == PRESCRIPTION_UPDATE_RESULT_CODE) {
            Prescription prescription = (Prescription) data.getSerializableExtra("Prescription");
            prescriptionViewModel.update(prescription);
        } else if (requestCode == SHOW_PRESCRIPTION_ACTIVITY_REQUEST_CODE && resultCode == PRESCRIPTION_NOT_CHANGED) {
            Toast.makeText(PrescriptionListActivity.this, "تغییری لحاظ نشد", Toast.LENGTH_SHORT).show();
        }
    }
}
