package com.example.rezatanha.finalproject.Controller.prescription;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.rezatanha.finalproject.Controller.db.DaoAccess;
import com.example.rezatanha.finalproject.Controller.db.DatabaseHelper;
import com.example.rezatanha.finalproject.Model.Prescription.Prescription;

import java.util.List;

public class PrescriptionRepository {
    private DaoAccess daoAccess;
    private LiveData<List<Prescription>> allPrescriptions;


    PrescriptionRepository(Application application) {
        DatabaseHelper db = DatabaseHelper.getDatabase(application);
        daoAccess = db.daoAccess();
        allPrescriptions = daoAccess.getAllPrescriptions();
    }
    LiveData<List<Prescription>> getAllWords() {
        return allPrescriptions;
    }

    void insert(Prescription prescription) {
        new insertAsyncTask(daoAccess).execute(prescription);
    }

    void update(Prescription prescription) {
        new updateAsyncTask(daoAccess).execute(prescription);
    }

    void remove(Prescription prescription) {
        new deleteAsyncTask(daoAccess).execute(prescription);
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

    private static class updateAsyncTask extends AsyncTask<Prescription, Void, Void> {
        private DaoAccess mAsyncTaskDao;

        updateAsyncTask(DaoAccess alarmDao) {
            mAsyncTaskDao = alarmDao;
        }

        @Override
        protected Void doInBackground(Prescription... params) {
            mAsyncTaskDao.updatePrescription(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Prescription, Void, Void> {
        private DaoAccess mAsyncTaskDao;

        deleteAsyncTask(DaoAccess alarmDao) {
            mAsyncTaskDao = alarmDao;
        }

        @Override
        protected Void doInBackground(Prescription... params) {
            mAsyncTaskDao.removePrescription(params[0]);
            return null;
        }
    }
}
