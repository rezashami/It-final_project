package com.example.rezatanha.finalproject.View.medicine;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.rezatanha.finalproject.Controller.medicine.MedicineViewModel;
import com.example.rezatanha.finalproject.Controller.medicineRecyclerAdapter.ShowMedicineListAdapter;
import com.example.rezatanha.finalproject.Model.Medicine.Medicine;
import com.example.rezatanha.finalproject.R;

import java.io.Serializable;
import java.util.List;

import static com.example.rezatanha.finalproject.View.alarm.AlarmCreationActivity.EXTRA_REPLY;

public class ShowMedicineListActivity extends AppCompatActivity {


    private RecyclerView recyclerView;
    private MedicineViewModel medicineViewModel;
    private List<Medicine> medicineList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_medicine_list);
        recyclerView = findViewById(R.id.show_drug_list_view);
        final ShowMedicineListAdapter adapter = new ShowMedicineListAdapter(this);
        // Log.e("ADAPERT",adapter.simpleMedicineModels.size()+" and "+adapter.mWords.size());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);


        // Get a new or existing ViewModel from the ViewModelProvider.
        medicineViewModel = ViewModelProviders.of(this).get(MedicineViewModel.class);

        // Add an observer on the LiveData returned by getAlphabetizedWords.
        // The onChanged() method fires when the observed data changes and the activity is
        // in the foreground.
        medicineViewModel.getAllWords().observe(this, words -> {
            // Update the cached copy of the words in the adapter.
            adapter.setWords(words);
        });
        Button btn = findViewById(R.id.btn_simplify_add_drug);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                medicineList = adapter.getMedicines();
                Medicine[] array = new Medicine[medicineList.size()];
                medicineList.toArray(array);
                medicineViewModel.update(array);
                Log.e("Medicinetaf", String.valueOf(medicineList.size()));
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
            }
        });
    }


}

