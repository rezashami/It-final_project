package com.example.rezatanha.finalproject.Controller.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.rezatanha.finalproject.Model.Alarm.Alarm;
import com.example.rezatanha.finalproject.Model.Medicine.Medicine;
import com.example.rezatanha.finalproject.Model.Prescription.Prescription;
import com.example.rezatanha.finalproject.Model.Report.Report;

import java.util.List;

@Dao

public interface DaoAccess {

    @Query("SELECT * FROM medicine")
    LiveData<List<Medicine>> getAllMedicine();

    @Query("SELECT * FROM alarm")
    LiveData<List<Alarm>> getAllAlarms();

    @Query("SELECT * FROM alarm")
    List<Alarm> getListOfAlarms();

    @Query("SELECT * FROM report")
    LiveData<List<Report>> getAllReports();

    @Query("SELECT * FROM prescription")
    LiveData<List<Prescription>> getAllPrescriptions();

    @Query("SELECT * FROM medicine WHERE name LIKE :query")
    LiveData<List<Medicine>> getMedicinesByName(String query);

    @Insert
    void insertMedicine(Medicine val);

    @Insert
    long insertAlarm(Alarm val);

    @Insert
    void insertReport(Report report);

    @Insert
    void insertPrescription(Prescription prescription);


    @Delete
    void removeMedicine(Medicine medicine);

    @Update
    void updateMedicine(Medicine medicine);

    @Update
    void updateAlarm(Alarm alarm);

    @Delete
    void removeAlarm(Alarm alarm);

    @Update
    void updateReport(Report report);

    @Delete
    void removeReport(Report report);


}