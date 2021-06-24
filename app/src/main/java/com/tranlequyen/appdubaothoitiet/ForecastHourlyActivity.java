package com.tranlequyen.appdubaothoitiet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tranlequyen.appdubaothoitiet.weatherapp.db.HourAdapter;
import com.tranlequyen.appdubaothoitiet.weatherapp.db.LocationCityHome;
import com.tranlequyen.appdubaothoitiet.weatherapp.db.weatherHour;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ForecastHourlyActivity extends AppCompatActivity {
    LocationCityHome locationCityHome;

    ImageView imgback;
    HourAdapter customAdapter;
    ArrayList<weatherHour> mangthoitiet;
    TextView txtviewtp;
    ListView lvview;
    String alang,bunits;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_forecast_hourly );
        Anhxa ();
        Intent intent = getIntent();
        String value1 = intent.getStringExtra("Key_1");
//        String value2 = intent.getStringExtra("Key_20");
//        String value3 = intent.getStringExtra("Key_30");
//            alang = value2;
//
//            bunits = value3;
        String citi = value1.toString ();
        findWeatherhour(citi);

        imgback.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                onBackPressed (); overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            }
        } );


    }
    private void Anhxa() { imgback=findViewById(R.id.imagebackhour);
        txtviewtp=findViewById(R.id.txtcitynamehour);
        lvview=findViewById(R.id.lvtaskhour);
        mangthoitiet = new ArrayList<weatherHour> ();
        customAdapter= new HourAdapter ( ForecastHourlyActivity.this,mangthoitiet );
        lvview.setAdapter ( customAdapter );

    }

    private void findWeatherhour(String citi) {

        String mWeatherURL;


            String BASE_URL = "https://api.openweathermap.org/data/2.5/forecast?q=";
            String API_KEY = "&appid=157187733bb90119ccc38f4d8d1f6da7&lang=vi&units=metric";
            String unitsURL;
            String LANG;


        mWeatherURL = BASE_URL + citi + API_KEY ;


        RequestQueue requestQueue = Volley.newRequestQueue(ForecastHourlyActivity.this);
    StringRequest stringRequest = new StringRequest( Request.Method.GET,mWeatherURL,
                new Response.Listener<String>() { @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                        String name = jsonObjectCity.getString("name"); txtviewtp.setText(name);
                        JSONArray jsonArrayList = jsonObject.getJSONArray("list");
                        for (int i = 0; i < jsonArrayList.length(); i++){
                            JSONObject jsonObjectList = jsonArrayList.getJSONObject(i);
                            String ngay = jsonObjectList.getString("dt");
                            long l = Long.valueOf(ngay); Date date = new Date(l*1000L);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, dd MMM ");
                            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("H:mm ");
                            String Hour = simpleDateFormat2.format(date);
                            String Day = simpleDateFormat.format(date);
                            JSONObject jsonObjectTemp = jsonObjectList.getJSONObject("main");
                            String max = jsonObjectTemp.getString("temp_min");
                            String min = jsonObjectTemp.getString("temp_max");
                            Double a = Double.valueOf(max);
                            Double b = Double.valueOf(min);
                            String Nhietdomax = String.valueOf(a.intValue());
                            String Nhietdomin = String.valueOf(b.intValue());
                            JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                            JSONObject jsonObjectWeather = jsonArrayWeather.getJSONObject(0);
                            String status = jsonObjectWeather.getString("description").toUpperCase ();
                            String icon = jsonObjectWeather.getString("icon");
                            mangthoitiet.add(new weatherHour (Hour,Day,status,icon,Nhietdomax,Nhietdomin));

                        }

                        customAdapter.notifyDataSetChanged();
                    } catch (JSONException e) { e.printStackTrace();
                    }
                }
                },
                new Response.ErrorListener() { @Override
                public void onErrorResponse(VolleyError error) {
                }	});
        requestQueue.add(stringRequest); }

}