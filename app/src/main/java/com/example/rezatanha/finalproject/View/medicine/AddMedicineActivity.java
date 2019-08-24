package com.example.rezatanha.finalproject.View.medicine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.example.rezatanha.finalproject.Model.Medicine.Medicine;
import com.example.rezatanha.finalproject.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddMedicineActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "Medicine";
    public static final int GET_IMAGE_REQUEST_CODE = 3;
    private EditText name;
    private EditText description;
    private Spinner howToUse;
    private Spinner unit;
    private EditText value;
    private String img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        Toolbar toolbar = findViewById(R.id.add_medicine_toolbar);
        setSupportActionBar(toolbar);
        setTitle("افزودن دارو");
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        img = null;
        name = findViewById(R.id.drug_input_name);
        description = findViewById(R.id.drug_input_description);
        howToUse = findViewById(R.id.how_to_use);
        unit = findViewById(R.id.drug_input_unit);
        value = findViewById(R.id.drug_input_value);
        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            Medicine medicine = (Medicine) b.getSerializable("Medicine");
            if (medicine == null) {
                finish();
                return;
            }
            ImageView imageView = findViewById(R.id.added_drug_img);
            imageView.setVisibility(View.VISIBLE);

            Glide.with(getApplicationContext())
                    .load(new File(Uri.parse(medicine.getImage()).getPath()))
                    .into(imageView);

            name.setText(medicine.getName());
            description.setText(medicine.getDescription());
            value.setText(String.valueOf(medicine.getValueOfUse()));
        }


        ArrayAdapter<CharSequence> unitAdapter = ArrayAdapter.createFromResource(
                this, R.array.unit_array, android.R.layout.simple_spinner_item);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        unit.setAdapter(unitAdapter);
        ArrayAdapter<CharSequence> useAdapter = ArrayAdapter.createFromResource(
                this, R.array.use_array, android.R.layout.simple_spinner_item);
        useAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        howToUse.setAdapter(useAdapter);
    }

    private Boolean validate() {
        return img != null && !TextUtils.isEmpty(name.getText()) &&
                !TextUtils.isEmpty(description.getText()) && !TextUtils.isEmpty(value.getText());
    }

    public void onImageAddFromGallery(View view) {
        startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), GET_IMAGE_REQUEST_CODE);
    }

    public void onImageAddFromCamera(View view) {
        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePicture, GET_IMAGE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            if (selectedImage != null) {
                img = selectedImage.toString();
            }
            Bitmap bitmap;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);

                ByteArrayOutputStream blob = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* Ignored for PNGs */, blob);

                ImageView imageView = findViewById(R.id.added_drug_img);
                imageView.setVisibility(View.VISIBLE);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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
        if (!validate()) {
            setResult(RESULT_CANCELED, replyIntent);
        } else {
            Medicine med = new Medicine();
            med.setName(name.getText().toString());
            med.setDescription(description.getText().toString());
            med.setHowUse(howToUse.getSelectedItem().toString());
            med.setUnit(unit.getSelectedItem().toString());
            med.setValueOfUse(Float.parseFloat(value.getText().toString()));
            med.setImage(img);
            replyIntent.putExtra(EXTRA_REPLY, med);
            setResult(RESULT_OK, replyIntent);
        }
        finish();
    }
}
