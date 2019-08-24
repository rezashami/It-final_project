package com.example.rezatanha.finalproject.Controller.report;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.rezatanha.finalproject.Controller.db.DaoAccess;
import com.example.rezatanha.finalproject.Controller.db.DatabaseHelper;
import com.example.rezatanha.finalproject.Model.Report.Report;

import java.util.List;

public class ReportRepository {
    private DaoAccess ReportDao;
    private LiveData<List<Report>> reports;

    public ReportRepository(Application application) {
        DatabaseHelper db = DatabaseHelper.getDatabase(application);
        ReportDao = db.daoAccess();
        reports = ReportDao.getAllReports();
    }

    LiveData<List<Report>> getAllReports() {
        return reports;
    }

    void insert(Report word) {
        new insertAsyncTask(ReportDao).execute(word);
    }

    void update(Report alarm) {
        new updateAsyncTask(ReportDao).execute(alarm);
    }

    void remove(Report alarm) {
        new deleteAsyncTask(ReportDao).execute(alarm);
    }

    private static class insertAsyncTask extends AsyncTask<Report, Void, Void> {

        private DaoAccess mAsyncTaskDao;

        insertAsyncTask(DaoAccess dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Report... params) {
            mAsyncTaskDao.insertReport(params[0]);
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<Report, Void, Void> {
        private DaoAccess mAsyncTaskDao;

        public updateAsyncTask(DaoAccess alarmDao) {
            mAsyncTaskDao = alarmDao;
        }

        @Override
        protected Void doInBackground(Report... alarms) {
            mAsyncTaskDao.updateReport(alarms[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Report, Void, Void> {
        private DaoAccess mAsyncTaskDao;

        public deleteAsyncTask(DaoAccess alarmDao) {
            mAsyncTaskDao = alarmDao;
        }

        @Override
        protected Void doInBackground(Report... alarms) {
            mAsyncTaskDao.removeReport(alarms[0]);
            return null;
        }
    }
}
