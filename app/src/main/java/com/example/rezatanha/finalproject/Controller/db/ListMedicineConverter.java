package com.example.rezatanha.finalproject.Controller.db;

import android.arch.persistence.room.TypeConverter;

import com.example.rezatanha.finalproject.Model.Medicine.Medicine;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class ListMedicineConverter {
    Gson gson = new Gson();

    @TypeConverter
    public List<Medicine> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Medicine>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public String someObjectListToString(List<Medicine> someObjects) {
        return gson.toJson(someObjects);
    }
}
