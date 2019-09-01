package com.example.rezatanha.finalproject.View.prescription;

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

import com.example.rezatanha.finalproject.Model.Prescription.Prescription;
import com.example.rezatanha.finalproject.R;

import java.util.Objects;

import saman.zamani.persiandate.PersianDate;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.example.rezatanha.finalproject.View.prescription.PrescriptionListActivity.NEW_PRESCRIPTION_ACTIVITY_REQUEST_CODE;
import static com.example.rezatanha.finalproject.View.prescription.PrescriptionListActivity.PRESCRIPTION_DELETE_RESULT_CODE;
import static com.example.rezatanha.finalproject.View.prescription.PrescriptionListActivity.PRESCRIPTION_UPDATE_RESULT_CODE;

public class PrescriptionShowActivity extends AppCompatActivity {

    Prescription prescription;
    TextView name, description, date, medicines;
    public static final int PRESCRIPTION_NOT_CHANGED = 13;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prescription_show);
        Toolbar toolbar = findViewById(R.id.prescription_show_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        name = findViewById(R.id.prescription_show_name);
        description = findViewById(R.id.prescription_show_description);
        date = findViewById(R.id.prescription_show_date);
        medicines = findViewById(R.id.prescription_show_medicines);

        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            prescription = (Prescription) b.getSerializable("Prescription");
            if (prescription != null) {
                name.setText(prescription.getName());
                description.setText(prescription.getDescription());
                PersianDate persianDate = new PersianDate(prescription.getDate().getTime());
                date.setText(persianDate.toString());
                for (int i = 0; i < prescription.getMedicine().size(); i++) {
                    String temp =medicines.getText() + "\n" + prescription.getMedicine().get(i).getName();
                    medicines.setText(temp);
                }
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
        menuInflater.inflate(R.menu.show_prescription_menu, menu);
        return true;
    }

    private void delete() {
        AlertDialog.Builder alert = new AlertDialog.Builder(PrescriptionShowActivity.this);
        alert.setTitle("حذف نسخه");
        alert.setMessage("آیا مطمئن هستید؟");
        alert.setPositiveButton("بلی", (dialog, which) -> {
            Intent replyIntent = new Intent();
            replyIntent.putExtra("Prescription", prescription);
            setResult(PRESCRIPTION_DELETE_RESULT_CODE, replyIntent);
            finish();
        });
        alert.setNegativeButton("خیر", (dialog, which) -> dialog.cancel());
        alert.show();
    }
    private void edit() {
        Intent intent = new Intent(PrescriptionShowActivity.this, AddPrescriptionActivity.class);
        Bundle b1 = new Bundle();
        b1.putSerializable("Prescription", prescription);
        intent.putExtras(b1);
        startActivityForResult(intent, NEW_PRESCRIPTION_ACTIVITY_REQUEST_CODE);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_prescription_edit_user:
                edit();
                return true;
            case R.id.delete_prescription_edit_user:
                delete();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_PRESCRIPTION_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Prescription prescription = (Prescription) data.getSerializableExtra("Prescription");
            Intent replyIntent = new Intent();
            replyIntent.putExtra("Prescription", prescription);
            setResult(PRESCRIPTION_UPDATE_RESULT_CODE, replyIntent);
            finish();
        } else if (requestCode == NEW_PRESCRIPTION_ACTIVITY_REQUEST_CODE && resultCode == RESULT_CANCELED) {
            Intent replyIntent = new Intent();
            setResult(PRESCRIPTION_NOT_CHANGED, replyIntent);
            finish();
        }
    }
}
