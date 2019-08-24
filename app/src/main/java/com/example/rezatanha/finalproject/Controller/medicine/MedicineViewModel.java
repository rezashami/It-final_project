package com.example.rezatanha.finalproject.Controller.medicine;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.rezatanha.finalproject.Model.Medicine.Medicine;

import java.util.List;

public class MedicineViewModel extends AndroidViewModel {

    private MedicineRepository mRepository;

    private LiveData<List<Medicine>> mAllWords;

    public MedicineViewModel(@NonNull Application application) {
        super(application);
        mRepository = new MedicineRepository(application);
        mAllWords = mRepository.getAllWords();
    }

    public LiveData<List<Medicine>> getAllWords() {
        return mAllWords;
    }

    public void insert(Medicine word) {
        mRepository.insert(word);
    }

    public void update(Medicine... medicines) {
        mRepository.update(medicines);
    }

    public void remove(Medicine medicine) {
        mRepository.remove(medicine);
    }

    public LiveData<List<Medicine>> getMedicinesByName(String searchText) {
        return mRepository.getAllMedicineByName(searchText);

    }
}
