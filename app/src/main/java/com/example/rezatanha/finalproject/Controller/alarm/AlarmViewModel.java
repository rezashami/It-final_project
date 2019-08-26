package com.example.rezatanha.finalproject.Controller.alarm;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.rezatanha.finalproject.Model.Alarm.Alarm;

import java.util.List;

public class AlarmViewModel extends AndroidViewModel {

    private AlarmRepository mRepository;

    private LiveData<List<Alarm>> alarms;

    public AlarmViewModel(@NonNull Application application) {
        super(application);
        mRepository = new AlarmRepository(application);
        alarms = mRepository.getAllAlarms();
    }

    public LiveData<List<Alarm>> getAllWords() {
        return alarms;
    }

    public int insert(Alarm word) {
        return mRepository.insert(word);
    }

    public void update(Alarm alarm) {
        mRepository.update(alarm);
    }

    public void remove(Alarm alarm) {
        mRepository.remove(alarm);
    }
}