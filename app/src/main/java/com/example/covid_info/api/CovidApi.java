package com.example.covid_info.api;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import com.example.covid_info.RespModelCountries;
import com.example.covid_info.RespModelRegion;
import com.example.covid_info.RespModelSummary;

public interface CovidApi {
    //Информация по миру
    @GET("reports/total")
    Call<RespModelSummary> getSummary(@Query("date") String date);
    //Информация по региону
    @GET("reports")
    Call<RespModelRegion> getRegionInfo(@Query("date") String date, @Query("q") String cCode);
    //Информация по списку стран
    @GET("regions")
    Call<RespModelCountries> getCountries();

}