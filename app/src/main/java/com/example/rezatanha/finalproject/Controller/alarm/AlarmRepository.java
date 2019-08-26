package com.example.rezatanha.finalproject.Controller.alarm;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.rezatanha.finalproject.Controller.db.DaoAccess;
import com.example.rezatanha.finalproject.Controller.db.DatabaseHelper;
import com.example.rezatanha.finalproject.Model.Alarm.Alarm;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class AlarmRepository {

    private DaoAccess AlarmDao;
    private LiveData<List<Alarm>> alarms;


    public AlarmRepository(Application application) {
        DatabaseHelper db = DatabaseHelper.getDatabase(application);
        AlarmDao = db.daoAccess();
        alarms = AlarmDao.getAllAlarms();
    }

    LiveData<List<Alarm>> getAllAlarms() {
        return alarms;
    }

    public List<Alarm> getListOfAlarms() throws ExecutionException, InterruptedException {
      return new getListClass(AlarmDao).execute().get();
    }

    int insert(Alarm word) {
        int result = -1;
        insertAsyncTask insert = new insertAsyncTask(AlarmDao);
        try {
            result = insert.execute(word).get().intValue();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return result;
    }

    void update(Alarm alarm) {
        new updateAsyncTask(AlarmDao).execute(alarm);
    }

    void remove(Alarm alarm) {
        new deleteAsyncTask(AlarmDao).execute(alarm);
    }

    private static class insertAsyncTask extends AsyncTask<Alarm, Void, Long> {

        private DaoAccess mAsyncTaskDao;

        insertAsyncTask(DaoAccess dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Long doInBackground(final Alarm... params) {
            return mAsyncTaskDao.insertAlarm(params[0]);

        }
    }


    private static class getListClass extends AsyncTask<Void, Void, List<Alarm>> {

        private DaoAccess mAsyncTaskDao;

        getListClass(DaoAccess dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<Alarm> doInBackground(Void... voids) {
            return mAsyncTaskDao.getListOfAlarms();
        }
    }

    private static class updateAsyncTask extends AsyncTask<Alarm, Void, Void> {
        private DaoAccess mAsyncTaskDao;

        updateAsyncTask(DaoAccess alarmDao) {
            mAsyncTaskDao = alarmDao;
        }

        @Override
        protected Void doInBackground(Alarm... alarms) {
            mAsyncTaskDao.updateAlarm(alarms[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Alarm, Void, Void> {
        private DaoAccess mAsyncTaskDao;

        deleteAsyncTask(DaoAccess alarmDao) {
            mAsyncTaskDao = alarmDao;
        }

        @Override
        protected Void doInBackground(Alarm... alarms) {
            mAsyncTaskDao.removeAlarm(alarms[0]);
            return null;
        }
    }
}