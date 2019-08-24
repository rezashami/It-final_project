package com.example.rezatanha.finalproject.Controller.prescription;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.rezatanha.finalproject.Controller.db.DaoAccess;
import com.example.rezatanha.finalproject.Controller.db.DatabaseHelper;
import com.example.rezatanha.finalproject.Model.Prescription.Prescription;

import java.util.List;

public class PrescriptionRepository {
    private DaoAccess mWordDao;
    private LiveData<List<Prescription>> allPrescriptions;


    PrescriptionRepository(Application application) {
        DatabaseHelper db = DatabaseHelper.getDatabase(application);
        mWordDao = db.daoAccess();
        allPrescriptions = mWordDao.getAllPrescriptions();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    LiveData<List<Prescription>> getAllWords() {
        return allPrescriptions;
    }

    void insert(Prescription prescription) {
        new insertAsyncTask(mWordDao).execute(prescription);
    }

    private static class insertAsyncTask extends AsyncTask<Prescription, Void, Void> {

        private DaoAccess mAsyncTaskDao;

        insertAsyncTask(DaoAccess dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Prescription... params) {
            mAsyncTaskDao.insertPrescription(params[0]);
            return null;
        }
    }
}
