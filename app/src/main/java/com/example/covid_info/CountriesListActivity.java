package com.example.covid_info;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CountriesListActivity extends AppCompatActivity {
    RecyclerView countriesList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coutries);
        //Загрузка списка стран
        //Доступ к настройкам
        SharedPreferences prefs = getSharedPreferences("covid_app", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();
        Set<String> setCountries = prefs.getStringSet("countries",null); //список стран из настроек
        //Загрузка из настроек
        List<String> countries = new ArrayList<>();
        countries.addAll(setCountries);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CountriesListActivity.this,
                R.layout.support_simple_spinner_dropdown_item, countries);
        Set<String> setCodes = prefs.getStringSet("codes",null); //список стран из настроек
        //Загрузка из настроек
        List<String> codes = new ArrayList<>();
        codes.addAll(setCodes);
        List<Integer> numbers = new ArrayList<>();
        for (String a:
             codes) {
            numbers.add(Integer.parseInt("0"));
        }
        countriesList = (RecyclerView) findViewById(R.id.recyclerViewCountries);
        countriesList.setLayoutManager(new GridLayoutManager(this,3));
        ListRecyclerViewAdapter adapter1 = new ListRecyclerViewAdapter(this,countries,codes,numbers);
        countriesList.setAdapter(adapter1);

    }
}