package com.example.covid_info;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RespModelRegion {
    @SerializedName("data")
    @Expose
    JsonArray regions;

    public String getConfirmed(){
        int N=0;
        for (JsonElement region:
             regions) {
            JsonObject r = region.getAsJsonObject();
            N += r.get("confirmed").getAsInt();
        }
        return String.valueOf(N);
    }
    public String getConfirmedDif(){
        int N=0;
        for (JsonElement region:
                regions) {
            JsonObject r = region.getAsJsonObject();
            N += r.get("confirmed_diff").getAsInt();
        }
        return String.valueOf(N);
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
