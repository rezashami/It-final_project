package com.example.rezatanha.finalproject.View.prescription;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.rezatanha.finalproject.Model.Medicine.Medicine;
import com.example.rezatanha.finalproject.Model.Prescription.Prescription;
import com.example.rezatanha.finalproject.R;
import com.example.rezatanha.finalproject.View.medicine.ShowMedicineListActivity;
import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import saman.zamani.persiandate.PersianDate;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.example.rezatanha.finalproject.View.alarm.AlarmCreationActivity.EXTRA_REPLY;

public class AddPrescriptionActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    Prescription prescription = null;

    EditText descriptionEditText;
    String description = "";

    List<Medicine> medicines;
    TextView medicinesEditText;

    EditText nameEditText;
    String name = "";

    Date date = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prescription);
        Toolbar toolbar = findViewById(R.id.add_prescription_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        descriptionEditText = findViewById(R.id.add_prescription_description);
        medicinesEditText = findViewById(R.id.add_prescription_medicine_name);
        nameEditText = findViewById(R.id.add_prescription_name);
        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            prescription = (Prescription) bundle.getSerializable("Prescription");
            if (prescription != null) {
                setTitle("تصحیح اطلاعات نسخه");
                nameEditText.setText(prescription.getName());
                descriptionEditText.setText(prescription.getDescription());
                StringBuilder allMedicine = new StringBuilder();
                for (int i = 0; i < prescription.getMedicine().size(); i++) {
                    allMedicine.append(prescription.getMedicine().get(i).getName()).append("\n");
                }
                medicinesEditText.setText(allMedicine.toString());
                date = prescription.getDate();
                name = prescription.getName();
                medicines = prescription.getMedicine();
                description = prescription.getDescription();
            } else {
                finish();
            }
        }
        findViewById(R.id.add_prescription_date_select_btn).setOnClickListener(v -> {
            PersianCalendar persianCalendar = new PersianCalendar();
            DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                    AddPrescriptionActivity.this,
                    persianCalendar.getPersianYear(),
                    persianCalendar.getPersianMonth(),
                    persianCalendar.getPersianDay()
            );
            datePickerDialog.show(this.getFragmentManager(), "Datepickerdialog");
        });
        findViewById(R.id.add_prescription_medicine_select_btn).setOnClickListener(v -> {
            Intent intent_upload = new Intent(this, ShowMedicineListActivity.class);
            startActivityForResult(intent_upload, 1);
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

    private boolean validate() {
        Log.e("Validate", String.format("%s,%s,%s,%s",medicines.size() != 0 , date != null ,!name.equals("") , !description.equals("")));
        return medicines.size() != 0 && date != null && !name.equals("") && !description.equals("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.save_button_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.save_button_in_menu) {
            save();
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void save() {
        Intent replyIntent = new Intent();
        name = nameEditText.getText().toString();
        description = descriptionEditText.getText().toString();
        if (validate()) {
            if (prescription == null) {
                prescription = new Prescription();
            }
            prescription.setDate(date);
            prescription.setDescription(description);
            prescription.setMedicine(medicines);
            prescription.setName(name);
            replyIntent.putExtra("Prescription", prescription);
            setResult(RESULT_OK, replyIntent);
        } else {
            setResult(RESULT_CANCELED, replyIntent);
        }
        finish();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        PersianDate persianDate = new PersianDate();
        int [] days = persianDate.toGregorian(year, monthOfYear, dayOfMonth);
        Calendar calendar = Calendar.getInstance();
        calendar.set(days[0], days[1], days[2]);
        date = calendar.getTime();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            Bundle b = data.getExtras();
            if (b != null) {
                medicines = (List<Medicine>) b.getSerializable(EXTRA_REPLY);
                TextView txt = findViewById(R.id.add_prescription_medicine_name);
                txt.setVisibility(View.VISIBLE);
                txt.setText("");
                for (int i = 0; i < medicines.size(); i++) {
                    String temp = txt.getText() + "\n" + medicines.get(i).getName();
                    txt.setText(temp);
                }
            }
        }
    }
}
