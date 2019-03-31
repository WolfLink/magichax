package me.nathanp.magiccreatures.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import androidx.room.TypeConverter;

public class Converters {
    @TypeConverter
    public String arrayListToString(ArrayList<String> list) {
        return (new Gson()).toJson(list);
    }

    @TypeConverter
    public ArrayList<String> stringToArrayList(String string) {
        Type listType = new TypeToken<ArrayList<String>>() {}.getType();
        return (new Gson()).fromJson(string, listType);
    }
}
