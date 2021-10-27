package com.example.covid_info;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    RespModelSummary summary;
    String date = "2020-06-01";
    AppCovid app;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        summary = new RespModelSummary();
        app = new AppCovid();
        //Получение статуса доступа к Интернет
        int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);
        final CalendarView calendarView = (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(CalendarView view, int year,
                                            int month, int dayOfMonth) {
                int mYear = year;
                int mMonth = month;
                int mDay = dayOfMonth;
                 date = new StringBuilder().append(mYear).append("-").append(mMonth+1)
                        .append("-").append(mDay).toString();
                 date = String.format("%04d-%02d-%02d",mYear,mMonth+1,mDay);


                 }
        });
        //Если есть разрешение
        if(permissionStatus == PackageManager.PERMISSION_GRANTED){
            reqSummary();
            reqCountryInfo("Russia");
            reqCityInfo("Moscow");

        }else{//Если нет разерешения
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET},1);
        }

        Button reqButton = (Button) findViewById(R.id.button_request);
        reqButton.setOnClickListener( new Button.OnClickListener(){
          @Override
          public void onClick(View view) {
              reqSummary();
              reqCountryInfo("Russia");
              reqCityInfo("Moscow");
          }
         }
        );
        Button plotButton = (Button)findViewById(R.id.button_goto_plot);
        plotButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                    Intent I = new Intent(MainActivity.this,
                            PlotActivity.class);
                    startActivity(I);
            }
        });
    }

    private void reqSummary() {
        //Выполнение запроса
        app.getApi().getSummary(date).enqueue(new Callback<RespModelSummary>() {
            @Override
            public void onResponse(Call<RespModelSummary> call, Response<RespModelSummary> response) {
                summary = response.body();
                try {
                    TextView tv_summary = (TextView) findViewById(R.id.textView_summary);
                    tv_summary.setText(summary.getConfirmed());
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<RespModelSummary> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Ошибка соединения", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void reqCountryInfo(String cCode) {
        //Выполнение запроса
        app.getApi().getRegionInfo(date,cCode).enqueue(new Callback<RespModelRegion>() {
            @Override
            public void onResponse(Call<RespModelRegion> call, Response<RespModelRegion> response) {
                try {
                    TextView tv_country = (TextView) findViewById(R.id.textView_country);
                    String str = response.body().getConfirmed();
                    String diff = response.body().getConfirmedDif();
                    tv_country.setText(str+" (+"+diff+")");
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespModelRegion> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Ошибка соединения", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void reqCityInfo(String cCode) {
        //Выполнение запроса
        app.getApi().getRegionInfo(date,cCode).enqueue(new Callback<RespModelRegion>() {
            @Override
            public void onResponse(Call<RespModelRegion> call, Response<RespModelRegion> response) {
                try {
                    TextView tv_city = (TextView) findViewById(R.id.textView_city);
                    String str = response.body().getConfirmed();
                    String diff = response.body().getConfirmedDif();
                    tv_city.setText(str+" (+"+diff+")");
                }catch (Exception e){
                    Toast.makeText(MainActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespModelRegion> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Ошибка соединения", Toast.LENGTH_SHORT).show();

            }
        });


    }
}