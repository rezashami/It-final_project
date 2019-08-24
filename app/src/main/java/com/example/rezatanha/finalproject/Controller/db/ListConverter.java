package com.example.rezatanha.finalproject.Controller.db;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ListConverter {

    Gson gson = new Gson();

    @TypeConverter
    public List<Date> stringToSomeObjectList(String data) {
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Date>>() {
        }.getType();

        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public String someObjectListToString(List<Date> someObjects) {
        return gson.toJson(someObjects);
    }

}
