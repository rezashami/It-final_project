package com.example.rezatanha.finalproject.Controller.medicine;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.example.rezatanha.finalproject.Controller.db.DaoAccess;
import com.example.rezatanha.finalproject.Controller.db.DatabaseHelper;
import com.example.rezatanha.finalproject.Model.Medicine.Medicine;

import java.util.List;

public class MedicineRepository {

    private DaoAccess mWordDao;
    private LiveData<List<Medicine>> mAllWords;


    public MedicineRepository(Application application) {
        DatabaseHelper db = DatabaseHelper.getDatabase(application);
        mWordDao = db.daoAccess();
        mAllWords = mWordDao.getAllMedicine();
    }

    LiveData<List<Medicine>> getAllWords() {
        return mAllWords;
    }

    void insert(Medicine word) {
        new insertAsyncTask(mWordDao).execute(word);
    }

    public void update(Medicine medicines) {
        new updateAsyncTask(mWordDao).execute(medicines);
    }

    public void updateMany(List<Medicine> medicines) {
        new updateManyAsyncTask(mWordDao,medicines).execute();
    }

    public void remove(Medicine medicine) {
        new removeAsyncTask(mWordDao).execute(medicine);
    }

    public LiveData<List<Medicine>> getAllMedicineByName(String searchText) {
        return mWordDao.getMedicinesByName(searchText);
    }

    private static class insertAsyncTask extends AsyncTask<Medicine, Void, Void> {

        private DaoAccess mAsyncTaskDao;

        insertAsyncTask(DaoAccess dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Medicine... params) {
            Log.e("Shiow", params[0].toString());
            Log.e("Shiow", String.valueOf(params.length));

            mAsyncTaskDao.insertMedicine(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Medicine, Void, Void> {

        private DaoAccess mAsyncTaskDao;

        updateAsyncTask(DaoAccess dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Medicine... medicines) {
            for (int i = 0; i < medicines.length; i++) {
                mAsyncTaskDao.updateMedicine(medicines[0]);
            }

            return null;
        }
    }

    private static class updateManyAsyncTask extends AsyncTask<Void, Void, Void> {

        private DaoAccess mAsyncTaskDao;
        private List<Medicine> medicines;
        updateManyAsyncTask(DaoAccess dao,List<Medicine> list) {
            this.mAsyncTaskDao = dao;
            this.medicines = list;
        }


        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < medicines.size(); i++) {
                mAsyncTaskDao.updateMedicine(medicines.get(i));
            }
            return null;
        }
    }

    private static class removeAsyncTask extends AsyncTask<Medicine, Void, Void> {

        private DaoAccess mAsyncTaskDao;

        removeAsyncTask(DaoAccess dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Medicine... params) {
            mAsyncTaskDao.removeMedicine(params[0]);
            return null;
        }
    }
}
