package com.example.rezatanha.finalproject.Controller.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.rezatanha.finalproject.Model.Alarm.Alarm;
import com.example.rezatanha.finalproject.Model.Medicine.Medicine;
import com.example.rezatanha.finalproject.Model.Prescription.Prescription;
import com.example.rezatanha.finalproject.Model.Report.Report;


@Database(entities = {Medicine.class, Alarm.class, Report.class, Prescription.class}, exportSchema = false, version = 1)
@TypeConverters({DateConverter.class, ListConverter.class})
public abstract class DatabaseHelper extends RoomDatabase {
    public abstract DaoAccess daoAccess();

    private static DatabaseHelper INSTANCE;
    private static final String DB_NAME = "drug.db";

    public static DatabaseHelper getDatabase(final Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, DatabaseHelper.class, DB_NAME).build();
        }
        return INSTANCE;

    }
}