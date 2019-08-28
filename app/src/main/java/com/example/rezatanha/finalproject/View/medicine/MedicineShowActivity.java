package com.example.rezatanha.finalproject.View.medicine;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.rezatanha.finalproject.Model.Medicine.Medicine;
import com.example.rezatanha.finalproject.R;

import java.io.File;
import java.util.Objects;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static com.example.rezatanha.finalproject.View.medicine.MedicineListActivity.MEDICINE_UPDATE_RESULT_CODE;
import static com.example.rezatanha.finalproject.View.medicine.MedicineListActivity.NO_CHANGE_RESULT_CODE;

public class MedicineShowActivity extends AppCompatActivity {
    private final String TAG = MedicineShowActivity.class.getSimpleName();
    Medicine medicine = null;

    private static final int EDIT_MEDICINE_REQUEST_CODE = 100;

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
            Log.e(TAG, medicine.getString());
            name.setText(medicine.getName());
            description.setText(medicine.getDescription());
            unit.setText(medicine.getUnit());
            howUse.setText(medicine.getHowUse());
            value.setText(String.valueOf(medicine.getValueOfUse()));
            if (medicine.getImage() != null) {
                Glide.with(getApplicationContext())
                        .load(Uri.fromFile(new File(medicine.getImage()))).override(250, 250)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .placeholder(R.drawable.default_image)
                        .into(img);
            }else{
                Glide.with(getApplicationContext())
                        .load(R.drawable.default_image).override(250, 250)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .into(img);
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
                delete();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void delete() {
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("حذف دارو");
        alert.setMessage("آیا دارو حذف شود؟");
        alert.setPositiveButton("بلی", (dialog, which) -> {
            Intent replyIntent = new Intent();
            replyIntent.putExtra("Medicine", medicine);
            setResult(MedicineListActivity.MEDICINE_DELETE_RESULT_CODE, replyIntent);
            finish();
        });
        alert.setNegativeButton("خیر", (dialog, which) -> {
            Toast.makeText(getApplicationContext(), "دارو حذف نشد", Toast.LENGTH_SHORT).show();
        });
        alert.show();
    }

    private void edit() {
        Intent intent = new Intent(MedicineShowActivity.this, AddMedicineActivity.class);
        intent.putExtra("Medicine", medicine);
        startActivityForResult(intent, EDIT_MEDICINE_REQUEST_CODE);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDIT_MEDICINE_REQUEST_CODE && resultCode == RESULT_OK) {
            Intent replyIntent = new Intent();
            Medicine _medicine = (Medicine) data.getSerializableExtra("Medicine");
            replyIntent.putExtra("Medicine", _medicine);
            setResult(MEDICINE_UPDATE_RESULT_CODE, replyIntent);
            finish();
        } else if (requestCode == EDIT_MEDICINE_REQUEST_CODE && resultCode == RESULT_CANCELED) {
            Intent replyIntent = new Intent();
            setResult(NO_CHANGE_RESULT_CODE, replyIntent);
            finish();
        }
    }
}
