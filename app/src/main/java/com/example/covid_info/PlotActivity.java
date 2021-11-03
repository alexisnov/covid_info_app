package com.example.covid_info;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.Date;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.snackbar.BaseTransientBottomBar;

import java.util.ArrayList;

//import java.time.Duration;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlotActivity extends AppCompatActivity {
    private LineChart mChart;//Объект графика
    private Button plotBtn;
    private AppCovid app;
    private ProgressBar progressBar;
    private int res=0;
    int daysN=0;
    View lotieAnim;
    ArrayList<Entry> values;//Массив координат
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plot);
        mChart = findViewById(R.id.chart);
        mChart.setTouchEnabled(true);
        mChart.setPinchZoom(true);
        plotBtn = (Button) findViewById(R.id.buttonPlot);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        values = new ArrayList<>();
        lotieAnim = findViewById(R.id.animationView);
        lotieAnim.setVisibility(View.INVISIBLE);
        plotBtn.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                //Тестовые данные
                values.clear();
                daysN=0;
                progressBar.setVisibility(View.VISIBLE);
                lotieAnim.setVisibility(View.VISIBLE);
                //values.add(new Entry(1, 50));
                //values.add(new Entry(2, 100));
                //Прочесть граничные даты
                TextView tvDateStart = (TextView) findViewById(R.id.dateStart);
                boolean b1 = Pattern.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}",
                        tvDateStart.getText());
                TextView tvDateEnd = (TextView) findViewById(R.id.dateEnd);
                boolean b2 = Pattern.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}",
                        tvDateEnd.getText());

                if(b1 && b2)//Если проходит проверка
                {
                    SimpleDateFormat format =
                            new SimpleDateFormat("yyyy-MM-dd");
                    Date dateStart;
                    try {
                        dateStart = format.parse(tvDateStart.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return;
                    }
                    Date dateEnd;
                    try {
                        dateEnd = format.parse(tvDateEnd.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                        return;
                    }
                    long diff = dateEnd.getTime() - dateStart.getTime();
                    //TimeUnit.DAYS.convert(diff,TimeUnit.MILLISECONDS);
                    daysN = (int) (diff/(1000*60*60*24));
                    //Цикл запросов с записью результатов
                    Calendar c = Calendar.getInstance();
                    c.setTime(dateStart);
                    for(int i =0;i<daysN;i++){
                        c.add(Calendar.DATE,1);
                        String date = format.format(c.getTime());
                        reqRegionInfo("Moscow",date,i);

                    }
                }else{
                    Toast.makeText(PlotActivity.this,
                            "Ошибка в дате", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void reqRegionInfo(String cCode,String date,final int id) {
        //int result = 0;
        //Выполнение запроса
        app.getApi().getRegionInfo(date,cCode).enqueue(new Callback<RespModelRegion>() {
            @Override
            public void onResponse(Call<RespModelRegion> call, Response<RespModelRegion> response) {
                try {
                    TextView tv_country = (TextView) findViewById(R.id.textView_country);
                    String str = response.body().getConfirmed();
                    String diff = response.body().getConfirmedDif();
                    res =  Integer.parseInt(diff);
                    values.add(new Entry(id, res));
                    //Подсчёт процентов
                    int p = (int) (((float)values.size())/daysN*100);
                    progressBar.setProgress(p);
                    if(values.size()==daysN){//Если получили все данные
                        //Отображение
                        //Сортировка асинхронно полученных значений
                        Collections.sort(values, new Comparator<Entry>() {
                            @Override
                            public int compare(Entry entry, Entry t1) {
                                if (entry.getX() > t1.getX()) {
                                    return 1;
                                }else{
                                    return -1;
                                }
                            }
                        });
                        //Установка значений
                        setData(values);
                        //Команда на перерисовку
                        mChart.invalidate();
                        progressBar.setVisibility(View.INVISIBLE);
                        lotieAnim.setVisibility(View.INVISIBLE);
                    }
                }catch (Exception e){
                    Toast.makeText(PlotActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RespModelRegion> call, Throwable t) {
                Toast.makeText(PlotActivity.this, "Ошибка соединения", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void setData(ArrayList<Entry> data){
        LineDataSet set1 = new LineDataSet(data,"Москва");
        set1.setDrawIcons(false);
        set1.setColor(Color.DKGRAY);
        set1.setCircleColor(Color.DKGRAY);
        set1.setLineWidth(1f);
        set1.setCircleRadius(3f);
        set1.setDrawCircleHole(false);
        set1.setValueTextSize(9f);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        LineData lineData = new LineData(dataSets);
        mChart.setData(lineData);
    }
}