package com.example.rezatanha.finalproject.Controller.prescription;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.rezatanha.finalproject.Model.Prescription.Prescription;

import java.util.List;

public class PrescriptionViewModel extends AndroidViewModel {
    private PrescriptionRepository mRepository;

    private LiveData<List<Prescription>> allPrescriptions;

    public PrescriptionViewModel(@NonNull Application application) {
        super(application);
        mRepository = new PrescriptionRepository(application);
        allPrescriptions = mRepository.getAllWords();
    }

    public LiveData<List<Prescription>> getAllPrescriptions() {
        return allPrescriptions;
    }

    public void insert(Prescription prescription) {
        mRepository.insert(prescription);
    }

    public void update(Prescription prescription) {
        mRepository.update(prescription);
    }

    public void remove(Prescription prescription) {
        mRepository.remove(prescription);
    }
}
