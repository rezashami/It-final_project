package com.example.rezatanha.finalproject.Model.Prescription;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.rezatanha.finalproject.Controller.db.ListConverter;
import com.example.rezatanha.finalproject.Controller.db.ListMedicineConverter;
import com.example.rezatanha.finalproject.Model.Medicine.Medicine;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity(tableName = "prescription")
@TypeConverters({ListConverter.class, ListMedicineConverter.class})
public class Prescription implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long id;

    private String description;
    private List<Medicine> medicine;
    private String name;
    private Date date;

    public Prescription() {
    }

    @NonNull
    public Long getId() {
        return id;
    }

    public void setId(@NonNull Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Medicine> getMedicine() {
        return medicine;
    }

    public void setMedicine(List<Medicine> medicine) {
        this.medicine = medicine;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}