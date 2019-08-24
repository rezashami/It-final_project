package com.example.rezatanha.finalproject.Model.Medicine;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.rezatanha.finalproject.Controller.db.DateConverter;
import com.example.rezatanha.finalproject.Controller.db.ListConverter;

import java.io.Serializable;

@Entity(tableName = "medicine")
@TypeConverters({DateConverter.class, ListConverter.class})
public class Medicine implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private Long id;
    private String description;
    private String howUse;
    private String image;
    private Boolean isUsed;
    private String name;
    private String unit;
    private float valueOfUse;

    public Medicine() {
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

    public String getHowUse() {
        return howUse;
    }

    public void setHowUse(String howUse) {
        this.howUse = howUse;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getUsed() {
        return isUsed;
    }

    public void setUsed(Boolean used) {
        isUsed = used;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public float getValueOfUse() {
        return valueOfUse;
    }

    public void setValueOfUse(float valueOfUse) {
        this.valueOfUse = valueOfUse;
    }

    @NonNull
    @Ignore
    @Override
    public String toString() {
        return String.valueOf(id);
    }


    @NonNull
    @Ignore
    public String getString() {
        return "Medicine{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", howUse='" + howUse + '\'' +
                ", image='" + image + '\'' +
                ", isUsed=" + isUsed +
                ", name='" + name + '\'' +
                ", unit='" + unit + '\'' +
                ", valueOfUse=" + valueOfUse +
                '}';
    }
}