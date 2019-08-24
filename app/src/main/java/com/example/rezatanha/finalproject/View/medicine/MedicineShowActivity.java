package com.example.rezatanha.finalproject.View.medicine;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rezatanha.finalproject.Model.Medicine.Medicine;
import com.example.rezatanha.finalproject.R;

import java.util.Objects;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MedicineShowActivity extends AppCompatActivity {
    private final String TAG = MedicineShowActivity.class.getSimpleName();
    Medicine medicine = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_medicine);
        Toolbar toolbar = findViewById(R.id.show_medicine_toolbar);
        setSupportActionBar(toolbar);
        setTitle("اطلاعات دارو");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        TextView name = findViewById(R.id.drug_show_name), description = findViewById(R.id.drug_show_description), unit = findViewById(R.id.drug_show_unit), howUse = findViewById(R.id.drug_show_how_use), value = findViewById(R.id.drug_show_value);
        ImageView img = findViewById(R.id.drug_show_img);
        Bundle b = this.getIntent().getExtras();

        if (b != null) {
            medicine = (Medicine) b.getSerializable("Medicine");
        }
        if (medicine == null)
            finish();
        else {
            Log.e(TAG,medicine.getString());
            name.setText(medicine.getName());
            description.setText(medicine.getDescription());
            unit.setText(medicine.getUnit());
            howUse.setText(medicine.getHowUse());
            value.setText(String.valueOf(medicine.getValueOfUse()));
            Uri imgUri = Uri.parse(medicine.getImage());
            if (imgUri != null) {
                img.setImageURI(null);
                img.setImageURI(imgUri);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.show_medicine_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.show_user_edit_user:
                edit();
                return true;
            case R.id.delete_user_edit_user:
                Intent replyIntent = new Intent();
                replyIntent.putExtra("Medicine", medicine);
                setResult(MedicineListActivity.MEDICINE_DELETE_RESULT_CODE, replyIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    // must be changed
    private void edit() {
        Intent replyIntent = new Intent();
        replyIntent.putExtra("qef", medicine);
        setResult(MedicineListActivity.MEDICINE_UPDATE_RESULT_CODE, replyIntent);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

}
