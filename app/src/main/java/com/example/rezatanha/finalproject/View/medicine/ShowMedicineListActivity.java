package com.example.rezatanha.finalproject.View.medicine;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.rezatanha.finalproject.Controller.medicine.MedicineViewModel;
import com.example.rezatanha.finalproject.Controller.medicineRecyclerAdapter.ShowMedicineListAdapter;
import com.example.rezatanha.finalproject.Model.Medicine.Medicine;
import com.example.rezatanha.finalproject.R;

import java.io.Serializable;
import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.example.rezatanha.finalproject.View.alarm.AlarmCreationActivity.EXTRA_REPLY;

public class ShowMedicineListActivity extends AppCompatActivity {
    private List<Medicine> medicineList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_medicine_list);
        RecyclerView recyclerView = findViewById(R.id.show_drug_list_view);
        final ShowMedicineListAdapter adapter = new ShowMedicineListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        MedicineViewModel medicineViewModel = ViewModelProviders.of(this).get(MedicineViewModel.class);
        medicineViewModel.getAllWords().observe(this, adapter::setWords);
        Button btn = findViewById(R.id.btn_simplify_add_drug);
        btn.setOnClickListener(view -> {
            medicineList = adapter.getMedicines();
            Intent replyIntent = new Intent();
            if (medicineList.size() != 0) {
                Bundle b = new Bundle();
                b.putSerializable(EXTRA_REPLY, (Serializable) medicineList);
                replyIntent.putExtras(b);
                setResult(RESULT_OK, replyIntent);
            } else {
                setResult(RESULT_CANCELED, replyIntent);
            }
            finish();
        });
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}

