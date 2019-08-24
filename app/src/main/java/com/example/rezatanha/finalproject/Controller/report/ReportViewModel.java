package com.example.rezatanha.finalproject.Controller.report;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.rezatanha.finalproject.Model.Report.Report;

import java.util.List;

public class ReportViewModel extends AndroidViewModel {
    private ReportRepository mRepository;

    private LiveData<List<Report>> reports;

    public ReportViewModel(@NonNull Application application) {
        super(application);
        mRepository = new ReportRepository(application);
        reports = mRepository.getAllReports();
    }

    public LiveData<List<Report>> getAllReports() {
        return reports;
    }

    public void insert(Report report) {
        mRepository.insert(report);
    }

    public void update(Report report) {
        mRepository.update(report);
    }

    public void remove(Report report) {
        mRepository.remove(report);
    }
}
