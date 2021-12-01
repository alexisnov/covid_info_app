package com.example.covid_info;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class RespModelCountries {
    @SerializedName("data")
    @Expose
    JsonArray countries;

    public List<String> getCountries(){
        List<String> names = new ArrayList<>();//Имена стран
        for (JsonElement country:
                countries) {
            JsonObject c = country.getAsJsonObject();
            names.add(c.get("name").getAsString());
        }
        return names;
    }

    public List<String> getCodes(){
        List<String> codes = new ArrayList<>();//Имена стран
        for (JsonElement country:
                countries) {
            JsonObject c = country.getAsJsonObject();
            codes.add(c.get("iso").getAsString());
        }
        return codes;
    }



}