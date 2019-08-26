package com.example.rezatanha.finalproject.View.medicine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.rezatanha.finalproject.BuildConfig;
import com.example.rezatanha.finalproject.Model.Medicine.Medicine;
import com.example.rezatanha.finalproject.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddMedicineActivity extends AppCompatActivity {
    public static final String EXTRA_REPLY = "Medicine";
    public static final int CAMERA_REQUEST_CODE = 1;
    public static final int GALLERY_REQUEST_CODE = 2;
    private EditText name;
    private EditText description;
    private Spinner howToUse;
    private Spinner unit;
    private EditText value;
    private String img;
    Medicine medicine = null;
    ImageView imageView;

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
        imageView = findViewById(R.id.added_drug_img);
        Bundle b = this.getIntent().getExtras();
        if (b != null) {
            medicine = (Medicine) b.getSerializable("Medicine");
            if (medicine == null) {
                finish();
                return;
            }
            imageView.setVisibility(View.VISIBLE);
            Glide.with(getApplicationContext())
                    .load(new File(Uri.parse(medicine.getImage()).getPath())).override(250,250)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.default_image)
                    .into(imageView);

            name.setText(medicine.getName());
            description.setText(medicine.getDescription());
            value.setText(String.valueOf(medicine.getValueOfUse()));
            img = medicine.getImage();
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
        return !TextUtils.isEmpty(name.getText()) &&
                !TextUtils.isEmpty(description.getText()) && !TextUtils.isEmpty(value.getText());
    }

    public void onImageAddFromGallery(View view) {
        startActivityForResult(new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI), GALLERY_REQUEST_CODE);
    }

    public void onImageAddFromCamera(View view) {
        try {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", createImageFile()));
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageURI(Uri.parse(img));
        } else if (requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri selectedImage = data.getData();
            if (selectedImage != null) {
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                // Move to first row
                if (cursor != null) {
                    cursor.moveToFirst();
                    //Get the column index of MediaStore.Images.Media.DATA
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    //Gets the String value in the column
                    String imgDecodableString = cursor.getString(columnIndex);
                    img = imgDecodableString;
                    cursor.close();
                    // Set the Image in ImageView after decoding the String
                    imageView.setVisibility(View.VISIBLE);
                    imageView.setImageBitmap(BitmapFactory.decodeFile(imgDecodableString));
                }
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
            if (medicine != null)
            {
                med.setId(medicine.getId());
            }
            replyIntent.putExtra(EXTRA_REPLY, med);
            setResult(RESULT_OK, replyIntent);
        }
        finish();
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        //This is the directory in which the file will be created. This is the default location of Camera photos
        File storageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Medicines");
        storageDir.mkdirs();
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for using again
        img = "file://" + image.getAbsolutePath();
        return image;
    }

//    @SuppressLint("StaticFieldLeak")
//    public class LoadImage extends AsyncTask<Void, Void, Void> {
//
//        final Uri selectedImage;
//        final Context context;
//        final ImageView imageView;
//        Bitmap bitmap;
//        byte[] imageFile;
//
//        LoadImage(Uri selectedImage, Context context, ImageView imageView) {
//            this.selectedImage = selectedImage;
//            this.context = context;
//            this.imageView = imageView;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            runOnUiThread(() -> Glide.with(context).load(R.raw.loading).into(imageView));
//            super.onPreExecute();
//        }
//
//        @Override
//        protected Void doInBackground(Void... voids) {
//
//            try {
//                //Bitmap bitmapImage = MediaStore.Images.Media.getBitmap(context.getContentResolver(), selectedImage);
////                int nh = (int) ( bitmapImage.getHeight() * (128.0 / bitmapImage.getWidth()) );
////                bitmap = Bitmap.createScaledBitmap(bitmapImage, 128, nh, true);
//                bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), selectedImage);
//                ByteArrayOutputStream blob = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.PNG, 0 , blob);
//                imageFile = blob.toByteArray();
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            imageView.setVisibility(View.VISIBLE);
//            Glide.with(getApplicationContext())
//                    .load(bitmap).override(250,250)
//                    .into(imageView);
//            super.onPostExecute(aVoid);
//        }
//
//        private void savePhoto(){
//            OutputStream out;
//            String root = Environment.getExternalStorageDirectory().getAbsolutePath()+"/";
//            File createDir = new File(root+"ReminderPhotos"+File.separator);
//            if(!createDir.exists()) {
//                createDir.mkdir();
//            }
//            File file = new File(root + "ReminderPhotos" + File.separator +"Name of File");
//            try {
//                file.createNewFile();
//                out = new FileOutputStream(file);
//                out.write(imageFile);
//                out.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//
//        }
//    }
}
