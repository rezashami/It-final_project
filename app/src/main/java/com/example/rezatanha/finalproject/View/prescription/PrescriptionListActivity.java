package com.example.rezatanha.finalproject.View.prescription;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.rezatanha.finalproject.Controller.prescription.PrescriptionViewModel;
import com.example.rezatanha.finalproject.Controller.prescriptionRecyclerAdapter.PrescriptionListAdapter;
import com.example.rezatanha.finalproject.Model.Prescription.Prescription;
import com.example.rezatanha.finalproject.R;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PrescriptionListActivity extends AppCompatActivity {
    public static final int NEW_WORD_ACTIVITY_REQUEST_CODE = 1;

    private PrescriptionViewModel medicineViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.prescription_content_recycler);
        final PrescriptionListAdapter adapter = new PrescriptionListAdapter(this, new PrescriptionListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Prescription item) {
                Intent myIntent = new Intent(getApplicationContext(), PrescriptionShowActivity.class);
                myIntent.putExtra("Medicine", item);
                startActivity(myIntent);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Get a new or existing ViewModel from the ViewModelProvider.
        medicineViewModel = ViewModelProviders.of(this).get(PrescriptionViewModel.class);

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        medicineViewModel.getAllPrescriptions().observe(this, words -> {
            // Update the cached copy of the words in the adapter.
            adapter.setWords(words);
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
