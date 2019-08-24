package com.example.rezatanha.finalproject.Model.Report;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.rezatanha.finalproject.Controller.db.DateConverter;
import com.example.rezatanha.finalproject.Controller.db.ListConverter;
import com.example.rezatanha.finalproject.Controller.db.ListMedicineConverter;

import java.io.Serializable;
import java.util.Date;

@Entity(tableName = "report")
@TypeConverters({DateConverter.class, ListConverter.class, ListMedicineConverter.class})
public class Report implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;
    private Date date;
    private String header;
    private String body;

    public Report() {
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}