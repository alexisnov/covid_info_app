package com.example.covid_info;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RespModelSummary {
    @SerializedName("data")
    @Expose
    JsonObject summary;
    public String getConfirmed(){
        return summary.get("confirmed").getAsString();
    }

    public String getConfirmedDif(){
        return summary.get("confirmed_diff").getAsString();
    }

    public JsonObject getSummary(){
        return summary;
    }
    public void setSummary(JsonObject summary){
        this.summary = summary;
    }
}
