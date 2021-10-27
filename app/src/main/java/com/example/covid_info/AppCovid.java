package com.example.covid_info;

import com.example.covid_info.api.CovidApi;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppCovid {
    private static CovidApi covidApi;
    private Retrofit retrofit;

    public AppCovid(){
        retrofit = new Retrofit.Builder()
                .baseUrl("https://covid-api.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        covidApi = retrofit.create(CovidApi.class);
    }

    public static CovidApi getApi() {return covidApi;}
}
