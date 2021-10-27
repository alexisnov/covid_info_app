package com.example.covid_info;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RespModelRegion {
    @SerializedName("data")
    @Expose
    JsonArray regions;

    public String getConfirmed(){
        JsonObject region = regions.get(0).getAsJsonObject();
        return region.get("confirmed").getAsString();
    }
    public String getConfirmedDif(){
        JsonObject region = regions.get(0).getAsJsonObject();
        return region.get("confirmed_diff").getAsString();
    }

    public JsonArray getRegions(){
        return regions;
    }
    public void setRegions(JsonArray regions){
        this.regions = regions;
    }

    public JsonArray getData() {
        return regions;
    }

    public void setData(JsonArray data) {
        this.regions = data;
    }
}
