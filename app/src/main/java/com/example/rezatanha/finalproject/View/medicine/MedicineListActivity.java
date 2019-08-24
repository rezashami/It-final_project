package com.example.rezatanha.finalproject.View.medicine;

import android.app.SearchManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.rezatanha.finalproject.Controller.medicine.MedicineViewModel;
import com.example.rezatanha.finalproject.Controller.medicineRecyclerAdapter.MedicineListAdapter;
import com.example.rezatanha.finalproject.Model.Medicine.Medicine;
import com.example.rezatanha.finalproject.R;

import java.util.Objects;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MedicineListActivity extends AppCompatActivity {
    public static final int MEDICINE_ADD_ACTIVITY_REQUEST_CODE = 1;
    public static final int MEDICINE_SHOW_ACTIVITY_REQUEST_CODE = 2;
    public static final int MEDICINE_DELETE_RESULT_CODE = 11;
    public static final int MEDICINE_UPDATE_RESULT_CODE = 10;
    public static final int NO_CHANGE_RESULT_CODE = 12;

    private MedicineViewModel medicineViewModel;
    private MedicineListAdapter adapter;
    private boolean isSearchOpen;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_list);
        Toolbar toolbar = findViewById(R.id.medicine_list_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        RecyclerView recyclerView = findViewById(R.id.medicine_recycler);
        adapter = new MedicineListAdapter(this, item -> {
            Intent myIntent = new Intent(getApplicationContext(), MedicineShowActivity.class);
            myIntent.putExtra("Medicine", item);
            startActivityForResult(myIntent, MEDICINE_SHOW_ACTIVITY_REQUEST_CODE);
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        medicineViewModel = ViewModelProviders.of(this).get(MedicineViewModel.class);

        medicineViewModel.getAllWords().observe(this, adapter::setMedicines);


        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MedicineListActivity.this, AddMedicineActivity.class);
            startActivityForResult(intent, MEDICINE_ADD_ACTIVITY_REQUEST_CODE);
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

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.user_list_toolbar, menu);
        MenuItem search = menu.findItem(R.id.user_list_search);

        search.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem menuItem) {
                medicineViewModel.getAllWords().observe(MedicineListActivity.this, adapter::setMedicines);
                isSearchOpen = true;
                fab.hide();
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem menuItem) {
                medicineViewModel.getAllWords().observe(MedicineListActivity.this, adapter::setMedicines);
                fab.show();
                isSearchOpen = false;
                return true;
            }
        });
        SearchView searchView = (SearchView) search.getActionView();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("جست و جو کاربر");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                getDealsFromDb(s);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                getDealsFromDb(s);
                return true;
            }

            private void getDealsFromDb(String searchText) {
                searchText = "%" + searchText + "%";
                medicineViewModel.getMedicinesByName(searchText).observe(MedicineListActivity.this, adapter::setMedicines);
            }
        });
        return true;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MEDICINE_ADD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            Medicine medicine = (Medicine) data.getSerializableExtra(AddMedicineActivity.EXTRA_REPLY);
            medicineViewModel.insert(medicine);
        } else if (requestCode == MEDICINE_SHOW_ACTIVITY_REQUEST_CODE && resultCode == MEDICINE_UPDATE_RESULT_CODE) {
            Medicine medicine = (Medicine) data.getSerializableExtra(AddMedicineActivity.EXTRA_REPLY);
            medicineViewModel.update(medicine);
        } else if (requestCode == MEDICINE_SHOW_ACTIVITY_REQUEST_CODE && resultCode == MEDICINE_DELETE_RESULT_CODE) {
            Medicine medicine = (Medicine) data.getSerializableExtra(AddMedicineActivity.EXTRA_REPLY);
            medicineViewModel.remove(medicine);
        } else if (requestCode == MEDICINE_SHOW_ACTIVITY_REQUEST_CODE && resultCode == NO_CHANGE_RESULT_CODE) {
            Toast.makeText(getApplicationContext(), "تغییری لحاظ نشد!!", Toast.LENGTH_LONG).show();
        } else if (requestCode == MEDICINE_ADD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_CANCELED) {
            Toast.makeText(getApplicationContext(), "تمامی فیلدها را پر کنید!!", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        if (isSearchOpen) {
            fab.show();
            isSearchOpen = false;
        } else {
            super.onBackPressed();
        }
    }
}
