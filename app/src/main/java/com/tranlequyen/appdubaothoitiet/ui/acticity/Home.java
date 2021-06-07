package com.tranlequyen.appdubaothoitiet.ui.acticity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;
import com.tranlequyen.appdubaothoitiet.GPStracker;
import com.tranlequyen.appdubaothoitiet.NotificationCC;
import com.tranlequyen.appdubaothoitiet.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hotchemi.android.rate.AppRate;
import sendinblue.ApiClient;
import sendinblue.ApiException;
import sendinblue.Configuration;
import sendinblue.auth.ApiKeyAuth;
import sibApi.SmtpApi;
import sibModel.SendSmtpEmail;
import sibModel.SendSmtpEmailSender;
import sibModel.SendSmtpEmailTo;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int ACCESS_LOCATION_PERMISSIONS_REQUEST = 1;
    private TextView mTemperatureText;
    private TextView mLocationText;
    private TextView mDescriptionText;
    private TextView mHumidityText;
    private TextView mPressureText;
    private TextView mMinTempText;
    private TextView mMaxTempText;
    private TextView mWindSpeedText;
    private TextView mWindDegreesText;
    private TextView mSunriseText;
    private TextView mSunsetText;
    private TextView mIndexUV;
    private TextView mDay1Text;
    private TextView mDay2Text;
    private TextView mDay3Text;
    private TextView mDay4Text;
    private TextView mDay5Text;
    private TextView mDescription1Text;
    private TextView mDescription2Text;
    private TextView mDescription3Text;
    private TextView mDescription4Text;
    private TextView mDescription5Text;
    private TextView mTempForecast1;
    private TextView mTempForecast2;
    private TextView mTempForecast3;
    private TextView mTempForecast4;
    private TextView mTempForecast5;
    private AutoCompleteTextView mSearchField;
    private EditText mSharedUserEmailText;
    private String mSharedUserEmail;
    private String mHomeLocation;
    private String mLocationFinal;
    private String mSearchLocation;
    private String mFavouriteLocation1;
    private String mFavouriteLocation2;
    private String mFavouriteLocation3;
    private String mFavouriteLocation4;
    private String mFavouriteLocation5;
    private Boolean mSwitchOnOff;
    private Boolean mSwitchOnOff2;
    private ProgressDialog mProgressDialog;
    NotificationCC notificationCC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_home);
        AnhXa();
        AppRate.with(this)
                .setInstallDays(7)
                .setLaunchTimes(5)
                .setRemindInterval(2)
                .monitor();
        AppRate.showRateDialogIfMeetsConditions(this);
        //AppRate.with(this).showRateDialog(this);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M  ) {// > android 6
            if (ContextCompat.checkSelfPermission(Home.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                getPermissionToAccessLocation();
            }
            }else{
            Toast.makeText ( this,"Nhận diện thiết bị dưới anroid 6", Toast.LENGTH_SHORT).show ();
        }

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPrefs", MODE_PRIVATE);
        mSwitchOnOff = sharedPreferences.getBoolean("switchUnits", false);
        mSwitchOnOff2 = sharedPreferences.getBoolean("switchLangs", false);
        mHomeLocation = sharedPreferences.getString("homeLocation", "");
        mFavouriteLocation1 = sharedPreferences.getString("favouriteLocation1", "");
        mFavouriteLocation2 = sharedPreferences.getString("favouriteLocation2", "");
        mFavouriteLocation3 = sharedPreferences.getString("favouriteLocation3", "");
        mFavouriteLocation4 = sharedPreferences.getString("favouriteLocation4", "");
        mFavouriteLocation5 = sharedPreferences.getString("favouriteLocation5", "");




        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.drawer_view);
        navigationView.setNavigationItemSelectedListener(this);

        ImageView navDrawerMenu = findViewById(R.id.navDrawerMenu);
        navDrawerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer( Gravity.LEFT);
            }
        });

        ImageView searchIcon = findViewById(R.id.searchButton);
        searchIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSearchLocation = mSearchField.getText().toString();
                findWeather(mSearchLocation, "", "");
                findForecast(mSearchLocation, "", "");
            }
        });

        mSearchField = findViewById(R.id.searchField);
        mSearchField.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedLocation = mSearchField.getText().toString();
                findWeather(selectedLocation, "", "");
                findForecast(selectedLocation, "", "");
            }
        });

        mSearchField.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            mSearchLocation = mSearchField.getText().toString();
                            findWeather(mSearchLocation, "", "");
                            findForecast(mSearchLocation, "", "");
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });

        ImageView shareIcon = findViewById(R.id.share_icon);
        shareIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
                Toast.makeText(Home.this, "Weather report emailed to: " + mSharedUserEmail, Toast.LENGTH_LONG).show();
            }
        });

        mSharedUserEmailText = findViewById(R.id.shared_user_email);
        mSharedUserEmailText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    switch (keyCode) {
                        case KeyEvent.KEYCODE_DPAD_CENTER:
                        case KeyEvent.KEYCODE_ENTER:
                            sendMail();
                            Toast.makeText(Home.this, "Weather report emailed to: " + mSharedUserEmail, Toast.LENGTH_LONG).show();
                            return true;
                        default:
                            break;
                    }
                }
                return false;
            }
        });
// Menu navigation drawn
        final Menu menu = navigationView.getMenu();
        MenuItem navHomeLocation = menu.findItem(R.id.nav_home_location);
        navHomeLocation.setTitle(mHomeLocation);
        MenuItem favourite_location1 = menu.findItem(R.id.favourite_location1);
        favourite_location1.setTitle(mFavouriteLocation1);
        MenuItem favourite_location2 = menu.findItem(R.id.favourite_location2);
        favourite_location2.setTitle(mFavouriteLocation2);
        MenuItem favourite_location3 = menu.findItem(R.id.favourite_location3);
        favourite_location3.setTitle(mFavouriteLocation3);
        MenuItem favourite_location4 = menu.findItem(R.id.favourite_location4);
        favourite_location4.setTitle(mFavouriteLocation4);
        MenuItem favourite_location5 = menu.findItem(R.id.favourite_location5);
        favourite_location5.setTitle(mFavouriteLocation5);

        mProgressDialog.setMessage("Refreshing");
        mProgressDialog.show();

        GPStracker gpsTracker = new GPStracker(getApplicationContext());
        Location location = gpsTracker.getLocation();
        if (location != null) {

            double latitudeDouble = location.getLatitude();
            double longitudeDouble = location.getLongitude();
            String latitude = Double.toString(latitudeDouble);
            String longitude = Double.toString(longitudeDouble);

            findWeather("", latitude, longitude);
            findForecast("", latitude, longitude);

        } else {

            findWeather(mHomeLocation, "", "");
            findForecast(mHomeLocation, "", "");
        }
    }

    private void AnhXa() {

        mSharedUserEmailText = findViewById(R.id.shared_user_email);
        mTemperatureText = findViewById(R.id.temperatureText);
        mLocationText = findViewById(R.id.locationText);
        mDescriptionText = findViewById(R.id.descriptionText);
        mHumidityText = findViewById(R.id.humidityText);
        mPressureText = findViewById(R.id.pressureText);
        mMinTempText = findViewById(R.id.minTempText);
        mMaxTempText = findViewById(R.id.maxTempText);
        mWindSpeedText = findViewById(R.id.windSpeedText);
        mWindDegreesText = findViewById(R.id.windDegreesText);
        mSunriseText = findViewById(R.id.sunriseText);
        mSunsetText = findViewById(R.id.sunsetText);
        mIndexUV = findViewById(R.id.indexUVtext);
        mProgressDialog = new ProgressDialog (this);

        mDay1Text = findViewById(R.id.day1);
        mDay2Text = findViewById(R.id.day2);
        mDay3Text = findViewById(R.id.day3);
        mDay4Text = findViewById(R.id.day4);
        mDay5Text = findViewById(R.id.day5);
        mDescription1Text = findViewById(R.id.description1);
        mDescription2Text = findViewById(R.id.description2);
        mDescription3Text = findViewById(R.id.description3);
        mDescription4Text = findViewById(R.id.description4);
        mDescription5Text = findViewById(R.id.description5);
        mTempForecast1 = findViewById(R.id.tempForecast1);
        mTempForecast2 = findViewById(R.id.tempForecast2);
        mTempForecast3 = findViewById(R.id.tempForecast3);
        mTempForecast4 = findViewById(R.id.tempForecast4);
        mTempForecast5 = findViewById(R.id.tempForecast5);
        ImageView navDrawerMenu = findViewById(R.id.navDrawerMenu);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
    }

    @Override
    protected void onStop() {
        super.onStop();
        isDestroyed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        isDestroyed();
    }
// Thông báo cửa sổ yêu cầu quyền truy cập Localtion
    public void getPermissionToAccessLocation() {

        new AlertDialog.Builder(this)
                .setTitle( R.string.permissionLocation)
                .setMessage( R.string.whotoOnGPS)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                    ACCESS_LOCATION_PERMISSIONS_REQUEST);
                        }
                    }
                }).setNegativeButton( R.string.Cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).create().show();
    }
    //yêu cầu quyền truy cập Localtion
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {

        if (requestCode == ACCESS_LOCATION_PERMISSIONS_REQUEST) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(Home.this, R.string.GrantedLocation, Toast.LENGTH_LONG).show();
                recreate();
            } else {
                Toast.makeText(Home.this, R.string.DeniedLocation, Toast.LENGTH_LONG).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen( GravityCompat.START)) {
            drawer.closeDrawer( GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Bundle analyticsBundle = new Bundle ();
        int id = item.getItemId();

        if (id == R.id.nav_home_location) {
            findWeather(mHomeLocation, "", "");
            findForecast(mHomeLocation, "", "");

        } else if (id == R.id.nav_current_location) {

            GPStracker gpsTracker = new GPStracker(getApplicationContext());
            Location location = gpsTracker.getLocation();
            if (location != null) {
                double latitudeDouble = location.getLatitude();
                double longitudeDouble = location.getLongitude();
                String latitude = Double.toString(latitudeDouble);
                String longitude = Double.toString(longitudeDouble);

                findWeather("", latitude, longitude);
                findForecast("", latitude, longitude);
            } else {
                Toast.makeText(Home.this, R.string.WaintingLocation, Toast.LENGTH_LONG).show();
            }

        } else if (id == R.id.favourite_location1) {
            findWeather(mFavouriteLocation1, "", "");
            findForecast(mFavouriteLocation1, "", "");
        } else if (id == R.id.favourite_location2) {
            findWeather(mFavouriteLocation2, "", "");
            findForecast(mFavouriteLocation2, "", "");
        } else if (id == R.id.favourite_location3) {
            findWeather(mFavouriteLocation3, "", "");
            findForecast(mFavouriteLocation3, "", "");
        } else if (id == R.id.favourite_location4) {
            findWeather(mFavouriteLocation4, "", "");
            findForecast(mFavouriteLocation4, "", "");
        } else if (id == R.id.favourite_location5) {
            findWeather(mFavouriteLocation5, "", "");
            findForecast(mFavouriteLocation5, "", "");
        } else if (id == R.id.settings) {
            Intent j = new Intent (Home.this, Settings.class);
            startActivity(j);
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }
        else if (id == R.id.taskCity) {
            Intent cit = new Intent (Home.this, CityActivity.class);
            startActivity(cit);
            overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer( GravityCompat.START);
        return true;
    }

    private void findWeather(String city, String latitude, String longitude) {

        String mWeatherURL;

        if (city.equals("")) {
            String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?";
            String LAT_LON = "lat=" + latitude + "&lon=" + longitude;
            String API_KEY = "&appid=157187733bb90119ccc38f4d8d1f6da7";
            String unitsURL;
            String LANG;
            if (mSwitchOnOff) {
                unitsURL = "&units=imperial";
            } else {
                unitsURL = "&units=metric";
            }

            if (mSwitchOnOff2) {
                LANG = "&lang=vi";
            } else {
               LANG = "&lang=en";
            }
            mWeatherURL = BASE_URL + LAT_LON + API_KEY + unitsURL+LANG;
        } else {
            String BASE_URL = "https://api.openweathermap.org/data/2.5/weather?q=";
            String API_KEY = "&appid=157187733bb90119ccc38f4d8d1f6da7";
            String unitsURL;
            String LANG;

            if (mSwitchOnOff) {
                unitsURL = "&units=imperial";
            } else {
                unitsURL = "&units=metric";
            }
            if (mSwitchOnOff2) {
                LANG = "&lang=vi";
            } else {
                LANG = "&lang=en";
            }
            mWeatherURL = BASE_URL + city + API_KEY + unitsURL+LANG;
        }

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, mWeatherURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    //coord object
                    JSONObject coordObject = response.getJSONObject("coord");
                    String latitudeFinal = String.valueOf(coordObject.getString("lat"));
                    String longitudeFinal = String.valueOf(coordObject.getInt("lon"));

                    findIndexUV(latitudeFinal, longitudeFinal);

                    //sys object
                    JSONObject sysObject = response.getJSONObject("sys");
                    String country = String.valueOf(sysObject.getString("country"));
                    String sunrise = String.valueOf(sysObject.getInt("sunrise"));
                    String sunset = String.valueOf(sysObject.getInt("sunset"));

                    //weather array
                    JSONArray jsonArray = response.getJSONArray("weather");
                    JSONObject arrayObject = jsonArray.getJSONObject(0);
                    String description = arrayObject.getString("description");
                    String weatherId = arrayObject.getString("id");
                    int weatherIdInt = Integer.parseInt(weatherId);

                    //main object
                    JSONObject mainObject = response.getJSONObject("main");
                    String temp = String.valueOf(mainObject.getInt("temp"));
                    String humidity = String.valueOf(mainObject.getInt("humidity"));
                    String pressure = String.valueOf(mainObject.getInt("pressure"));
                    String tempMin = String.valueOf(mainObject.getInt("temp_min"));
                    String tempMax = String.valueOf(mainObject.getInt("temp_max"));

                    //wind object
                    JSONObject windObject = response.getJSONObject("wind");
                    String windSpeed = String.valueOf(windObject.getDouble("speed"));
                    double windSpeedDouble = Double.parseDouble(windSpeed);

                    //name object
                    String city = response.getString("name");

                    //unix time conversion
                    SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss (z)");
                    int sunriseInt = Integer.parseInt(sunrise);
                    long sunriseUnix = sunriseInt;
                    Date date2 = new java.util.Date(sunriseUnix * 1000L);
                    String sunriseFinal = (sdf.format(date2));
                    int sunsetInt = Integer.parseInt(sunset);
                    long sunsetUnix = sunsetInt;
                    Date date3 = new java.util.Date(sunsetUnix * 1000L);
                    String sunsetFinal = (sdf.format(date3));

                    //Thay đổi background Image
                    ConstraintLayout layout = findViewById(R.id.container2);
                    if (weatherIdInt >= 200 && weatherIdInt <= 232) {
                        layout.setBackgroundResource(R.mipmap.thunderstorm);
                    } else if (weatherIdInt >= 300 && weatherIdInt <= 321) {
                        layout.setBackgroundResource(R.mipmap.rain);
                    } else if (weatherIdInt >= 500 && weatherIdInt <= 531) {
                        layout.setBackgroundResource(R.mipmap.rain);
                    } else if (weatherIdInt >= 600 && weatherIdInt <= 622) {
                        layout.setBackgroundResource(R.mipmap.snow);
                    } else if (weatherIdInt >= 701 && weatherIdInt <= 721) {
                        layout.setBackgroundResource(R.mipmap.mist);
                    } else if (weatherIdInt == 731) {
                        layout.setBackgroundResource(R.mipmap.sand);
                    } else if (weatherIdInt == 741) {
                        layout.setBackgroundResource(R.mipmap.mist);
                    } else if (weatherIdInt >= 751 && weatherIdInt <= 761) {
                        layout.setBackgroundResource(R.mipmap.sand);
                    } else if (weatherIdInt >= 762 && weatherIdInt <= 781) {
                        layout.setBackgroundResource(R.mipmap.mist);
                    } else if (weatherIdInt == 800) {
                        layout.setBackgroundResource(R.mipmap.clear);
                    } else if (weatherIdInt >= 801 && weatherIdInt <= 803) {
                        layout.setBackgroundResource(R.mipmap.clouds);
                    } else if (weatherIdInt == 804) {
                        layout.setBackgroundResource(R.mipmap.overcast);
                    }

                    //final strings
                    mLocationFinal = (city + ", " + country);
                    String humidityFinal = (humidity + "%");
                    String PressureFinal = (pressure + "hPa");
                    String temperatureFinalMetric = (temp + "°C");
                    String temperatureFinalImperial = (temp + "°F");
                    String temperatureMinFinalMetric = (tempMin + "°C");
                    String temperatureMinFinalImperial = (tempMin + "°F");
                    String temperatureMaxFinalMetric = (tempMax + "°C");
                    String temperatureMaxFinalImperial = (tempMax + "°F");

                    //set views
                    mLocationText.setText(mLocationFinal);
                    mDescriptionText.setText(description);
                    mHumidityText.setText(humidityFinal);
                    mPressureText.setText(PressureFinal);
                    mSunriseText.setText(sunriseFinal);
                    mSunsetText.setText(sunsetFinal);

                    if (mSwitchOnOff) {
                        //converting wind speed
                        double windSpeedConvert = windSpeedDouble * 1.15078;
                        double windSpeedDoubleRounded = Math.round(windSpeedConvert * 10) / 10.0;
                        String finalWindSpeedImperial = windSpeedDoubleRounded + "mph";

                        mWindSpeedText.setText(finalWindSpeedImperial);
                        mMinTempText.setText(temperatureMinFinalImperial);
                        mMaxTempText.setText(temperatureMaxFinalImperial);
                        mTemperatureText.setText(temperatureFinalImperial);
                    } else {
                        //converting wind speed
                        double windSpeedConvert = windSpeedDouble * 3.6;
                        double windSpeedDoubleRounded = Math.round(windSpeedConvert * 10) / 10.0;
                        String finalWindSpeedMetric = windSpeedDoubleRounded + "kph";

                        mWindSpeedText.setText(finalWindSpeedMetric);
                        mMinTempText.setText(temperatureMinFinalMetric);
                        mMaxTempText.setText(temperatureMaxFinalMetric);
                        mTemperatureText.setText(temperatureFinalMetric);
                    }

                    String windDegrees = String.valueOf(windObject.getInt("deg"));
                    int windDegreesInt = Integer.parseInt(windDegrees);

                    //wind direction hướng gió (E ĐÔNG - W TÂY - S= NAM - N= BĂC)
                    if (windDegreesInt >= 23 && windDegreesInt <= 68) {
                        mWindDegreesText.setText("NE");
                    } else if (windDegreesInt >= 69 && windDegreesInt <= 114) {
                        mWindDegreesText.setText("E");
                    } else if (windDegreesInt >= 114 && windDegreesInt <= 159) {
                        mWindDegreesText.setText("SE");
                    } else if (windDegreesInt >= 159 && windDegreesInt <= 204) {
                        mWindDegreesText.setText("S");
                    } else if (windDegreesInt >= 204 && windDegreesInt <= 249) {
                        mWindDegreesText.setText("SW");
                    } else if (windDegreesInt >= 249 && windDegreesInt <= 294) {
                        mWindDegreesText.setText("W");
                    } else if (windDegreesInt >= 294 && windDegreesInt <= 339) {
                        mWindDegreesText.setText("NW");
                    } else {
                        mWindDegreesText.setText("N");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(Home.this);
        queue.add(jor);
    }

    private void findIndexUV(String latitude, String longitude) {

        String BASE_URL = "https://api.openweathermap.org/data/2.5/uvi?";
        String API_KEY = "appid=157187733bb90119ccc38f4d8d1f6da7";
        String LAT_LON = "&lat=" + latitude + "&lon=" + longitude;
        String FINAL_URL = BASE_URL + API_KEY + LAT_LON;

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, FINAL_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    double indexUV = response.getDouble("value");

                    if (indexUV >= 0 && indexUV <= 2) {
                        mIndexUV.setText("UV Index: Low");
                    } else if (indexUV >= 3 && indexUV <= 5) {
                        mIndexUV.setText("UV Index: Moderate");
                    } else if (indexUV >= 6 && indexUV <= 7) {
                        mIndexUV.setText("UV Index: High");
                    } else if (indexUV >= 8 && indexUV <= 10) {
                        mIndexUV.setText("UV Index: Very High");
                    } else {
                        mIndexUV.setText("UV Index: Extreme");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(Home.this);
        queue.add(jor);
    }

    private void findForecast(String city, String latitude, String longitude) {

        String mForecastURL;

        if (city.equals("")) {
            String BASE_URL = "https://api.openweathermap.org/data/2.5/forecast?";
            String LAT_LON = "lat=" + latitude + "&lon=" + longitude;
            String API_KEY = "&appid=157187733bb90119ccc38f4d8d1f6da7";
            String unitsURL;
            String LANG;

            if (mSwitchOnOff) {
                unitsURL = "&units=imperial";
            } else {
                unitsURL = "&units=metric";
            }
            if (mSwitchOnOff2) {
                LANG = "&lang=vi";
            } else {
                LANG = "&lang=en";
            }

            mForecastURL = BASE_URL + LAT_LON + API_KEY + unitsURL+LANG;
        } else {
            String BASE_URL = "https://api.openweathermap.org/data/2.5/forecast?q=";
            String API_KEY = "&appid=157187733bb90119ccc38f4d8d1f6da7";
            String unitsURL;
            String LANG;
            if (mSwitchOnOff) {
                unitsURL = "&units=imperial";
            } else {
                unitsURL = "&units=metric";
            }
            if (mSwitchOnOff2) {
                LANG = "&lang=vi";
            } else {
                LANG = "&lang=en";
            }
            mForecastURL = BASE_URL + city + API_KEY + unitsURL+LANG;
        }

        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.GET, mForecastURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd MMMM yyyy");

                    //list array
                    JSONArray listArray = response.getJSONArray("list");

                    //index 0 of the list array
                    JSONObject arrayObject0 = listArray.getJSONObject(7);

                    //date time
                    String dateTime0 = arrayObject0.getString("dt");

                    //main object inside list array
                    JSONObject mainObject0 = arrayObject0.getJSONObject("main");
                    String tempForecast0 = String.valueOf(mainObject0.getInt("temp"));

                    //weather object inside list array
                    JSONArray weatherArray0 = arrayObject0.getJSONArray("weather");
                    JSONObject weatherObject0 = weatherArray0.getJSONObject(0);
                    String description0 = weatherObject0.getString("description");
                    String forecastId0 = weatherObject0.getString("id");
                    int forecastIdInt0 = Integer.parseInt(forecastId0);

                    //converting dt
                    int dateTimeInt0 = Integer.parseInt(dateTime0);
                    long dateTimeUnix0 = dateTimeInt0;
                    Date date0 = new java.util.Date(dateTimeUnix0 * 1000L);
                    String dateTimeFinal0 = (sdf.format(date0));

                    //final strings
                    String temperatureForecastMetric0 = (tempForecast0 + "°C");
                    String temperatureForecastImperial0 = (tempForecast0 + "°F");

                    //index 1 of the list array
                    JSONObject arrayObject1 = listArray.getJSONObject(15);

                    //date time
                    String dateTime1 = arrayObject1.getString("dt");

                    //main object inside list array
                    JSONObject mainObject1 = arrayObject1.getJSONObject("main");
                    String tempForecast1 = String.valueOf(mainObject1.getInt("temp"));

                    //weather object inside list array
                    JSONArray weatherArray1 = arrayObject1.getJSONArray("weather");
                    JSONObject weatherObject1 = weatherArray1.getJSONObject(0);
                    String description1 = weatherObject1.getString("description");
                    String forecastId1 = weatherObject1.getString("id");
                    int forecastIdInt1 = Integer.parseInt(forecastId1);

                    //converting dt
                    int dateTimeInt1 = Integer.parseInt(dateTime1);
                    long dateTimeUnix1 = dateTimeInt1;
                    Date date1 = new java.util.Date(dateTimeUnix1 * 1000L);
                    String dateTimeFinal1 = (sdf.format(date1));

                    //final strings
                    String temperatureForecastMetric1 = (tempForecast1 + "°C");
                    String temperatureForecastImperial1 = (tempForecast1 + "°F");
                    //index 2 of the list array
                    JSONObject arrayObject2 = listArray.getJSONObject(23);

                    //date time
                    String dateTime2 = arrayObject2.getString("dt");

                    //main object inside list array
                    JSONObject mainObject2 = arrayObject2.getJSONObject("main");
                    String tempForecast2 = String.valueOf(mainObject2.getInt("temp"));

                    //weather object inside list array
                    JSONArray weatherArray2 = arrayObject2.getJSONArray("weather");
                    JSONObject weatherObject2 = weatherArray2.getJSONObject(0);
                    String description2 = weatherObject2.getString("description");
                    String forecastId2 = weatherObject2.getString("id");
                    int forecastIdInt2 = Integer.parseInt(forecastId2);

                    //converting dt
                    int dateTimeInt2 = Integer.parseInt(dateTime2);
                    long dateTimeUnix2 = dateTimeInt2;
                    Date date2 = new java.util.Date(dateTimeUnix2 * 1000L);
                    String dateTimeFinal2 = (sdf.format(date2));

                    //final strings
                    String temperatureForecastMetric2 = (tempForecast2 + "°C");
                    String temperatureForecastImperial2 = (tempForecast2 + "°F");

                    //index 3 of the list array
                    JSONObject arrayObject3 = listArray.getJSONObject(31);

                    //date time
                    String dateTime3 = arrayObject3.getString("dt");

                    //main object inside list array
                    JSONObject mainObject3 = arrayObject3.getJSONObject("main");
                    String tempForecast3 = String.valueOf(mainObject3.getInt("temp"));

                    //weather object inside list array
                    JSONArray weatherArray3 = arrayObject3.getJSONArray("weather");
                    JSONObject weatherObject3 = weatherArray3.getJSONObject(0);
                    String description3 = weatherObject3.getString("description");
                    String forecastId3 = weatherObject3.getString("id");
                    int forecastIdInt3 = Integer.parseInt(forecastId3);

                    //converting dt
                    int dateTimeInt3 = Integer.parseInt(dateTime3);
                    long dateTimeUnix3 = dateTimeInt3;
                    Date date3 = new java.util.Date(dateTimeUnix3 * 1000L);
                    String dateTimeFinal3 = (sdf.format(date3));

                    //final strings
                    String temperatureForecastMetric3 = (tempForecast3 + "°C");
                    String temperatureForecastImperial3 = (tempForecast3 + "°F");

                    //index 4 of the list array
                    JSONObject arrayObject4 = listArray.getJSONObject(39);

                    //date time
                    String dateTime4 = arrayObject4.getString("dt");

                    //main object inside list array
                    JSONObject mainObject4 = arrayObject4.getJSONObject("main");
                    String tempForecast4 = String.valueOf(mainObject4.getInt("temp"));

                    //weather object inside list array
                    JSONArray weatherArray4 = arrayObject4.getJSONArray("weather");
                    JSONObject weatherObject4 = weatherArray4.getJSONObject(0);
                    String description4 = weatherObject4.getString("description");
                    String forecastId4 = weatherObject4.getString("id");
                    int forecastIdInt4 = Integer.parseInt(forecastId4);

                    //converting dt
                    int dateTimeInt4 = Integer.parseInt(dateTime4);
                    long dateTimeUnix4 = dateTimeInt4;
                    Date date4 = new java.util.Date(dateTimeUnix4 * 1000L);
                    String dateTimeFinal4 = (sdf.format(date4));

                    //final strings
                    String temperatureForecastMetric4 = (tempForecast4 + "°C");
                    String temperatureForecastImperial4 = (tempForecast4 + "°F");

                    //change forecast icon
                    ImageView day1Icon = findViewById(R.id.day1Icon);
                    if (forecastIdInt0 >= 200 && forecastIdInt0 <= 232) {
                        day1Icon.setImageResource(R.drawable.icon_thunderstorm);
                    } else if (forecastIdInt0 >= 300 && forecastIdInt0 <= 321) {
                        day1Icon.setImageResource(R.drawable.icon_drizzle);
                    } else if (forecastIdInt0 >= 500 && forecastIdInt0 <= 531) {
                        day1Icon.setImageResource(R.drawable.icon_rain);
                    } else if (forecastIdInt0 >= 600 && forecastIdInt0 <= 622) {
                        day1Icon.setImageResource(R.drawable.icon_snow);
                    } else if (forecastIdInt0 >= 701 && forecastIdInt0 <= 781) {
                        day1Icon.setImageResource(R.drawable.icon_mist);
                    } else if (forecastIdInt0 == 800) {
                        day1Icon.setImageResource(R.drawable.icon_clear);
                    } else if (forecastIdInt0 >= 801 && forecastIdInt0 <= 803) {
                        day1Icon.setImageResource(R.drawable.icon_clouds);
                    } else if (forecastIdInt0 == 804) {
                        day1Icon.setImageResource(R.drawable.icon_overcast);
                    }

                    //change forecast icon
                    ImageView day2Icon = findViewById(R.id.day2Icon);
                    if (forecastIdInt1 >= 200 && forecastIdInt1 <= 232) {
                        day2Icon.setImageResource(R.drawable.icon_thunderstorm);
                    } else if (forecastIdInt1 >= 300 && forecastIdInt1 <= 321) {
                        day2Icon.setImageResource(R.drawable.icon_drizzle);
                    } else if (forecastIdInt1 >= 500 && forecastIdInt1 <= 531) {
                        day2Icon.setImageResource(R.drawable.icon_rain);
                    } else if (forecastIdInt1 >= 600 && forecastIdInt1 <= 622) {
                        day2Icon.setImageResource(R.drawable.icon_snow);
                    } else if (forecastIdInt1 >= 701 && forecastIdInt1 <= 781) {
                        day2Icon.setImageResource(R.drawable.icon_mist);
                    } else if (forecastIdInt1 == 800) {
                        day2Icon.setImageResource(R.drawable.icon_clear);
                    } else if (forecastIdInt1 >= 801 && forecastIdInt1 <= 803) {
                        day2Icon.setImageResource(R.drawable.icon_clouds);
                    } else if (forecastIdInt1 == 804) {
                        day2Icon.setImageResource(R.drawable.icon_overcast);
                    }

                    //change forecast icon
                    ImageView day3Icon = findViewById(R.id.day3Icon);
                    if (forecastIdInt2 >= 200 && forecastIdInt2 <= 232) {
                        day3Icon.setImageResource(R.drawable.icon_thunderstorm);
                    } else if (forecastIdInt2 >= 300 && forecastIdInt2 <= 321) {
                        day3Icon.setImageResource(R.drawable.icon_drizzle);
                    } else if (forecastIdInt2 >= 500 && forecastIdInt2 <= 531) {
                        day3Icon.setImageResource(R.drawable.icon_rain);
                    } else if (forecastIdInt2 >= 600 && forecastIdInt2 <= 622) {
                        day3Icon.setImageResource(R.drawable.icon_snow);
                    } else if (forecastIdInt2 >= 701 && forecastIdInt2 <= 781) {
                        day3Icon.setImageResource(R.drawable.icon_mist);
                    } else if (forecastIdInt2 == 800) {
                        day3Icon.setImageResource(R.drawable.icon_clear);
                    } else if (forecastIdInt2 >= 801 && forecastIdInt2 <= 803) {
                        day3Icon.setImageResource(R.drawable.icon_clouds);
                    } else if (forecastIdInt2 == 804) {
                        day3Icon.setImageResource(R.drawable.icon_overcast);
                    }

                    //change forecast icon
                    ImageView day4Icon = findViewById(R.id.day4Icon);
                    if (forecastIdInt3 >= 200 && forecastIdInt3 <= 232) {
                        day4Icon.setImageResource(R.drawable.icon_thunderstorm);
                    } else if (forecastIdInt3 >= 300 && forecastIdInt3 <= 321) {
                        day4Icon.setImageResource(R.drawable.icon_drizzle);
                    } else if (forecastIdInt3 >= 500 && forecastIdInt3 <= 531) {
                        day4Icon.setImageResource(R.drawable.icon_rain);
                    } else if (forecastIdInt3 >= 600 && forecastIdInt3 <= 622) {
                        day4Icon.setImageResource(R.drawable.icon_snow);
                    } else if (forecastIdInt3 >= 701 && forecastIdInt3 <= 781) {
                        day4Icon.setImageResource(R.drawable.icon_mist);
                    } else if (forecastIdInt3 == 800) {
                        day4Icon.setImageResource(R.drawable.icon_clear);
                    } else if (forecastIdInt3 >= 801 && forecastIdInt3 <= 803) {
                        day4Icon.setImageResource(R.drawable.icon_clouds);
                    } else if (forecastIdInt3 == 804) {
                        day4Icon.setImageResource(R.drawable.icon_overcast);
                    }

                    //change forecast icon
                    ImageView day5Icon = findViewById(R.id.day5Icon);
                    if (forecastIdInt4 >= 200 && forecastIdInt4 <= 232) {
                        day5Icon.setImageResource(R.drawable.icon_thunderstorm);
                    } else if (forecastIdInt4 >= 300 && forecastIdInt4 <= 321) {
                        day5Icon.setImageResource(R.drawable.icon_drizzle);
                    } else if (forecastIdInt4 >= 500 && forecastIdInt4 <= 531) {
                        day5Icon.setImageResource(R.drawable.icon_rain);
                    } else if (forecastIdInt4 >= 600 && forecastIdInt4 <= 622) {
                        day5Icon.setImageResource(R.drawable.icon_snow);
                    } else if (forecastIdInt4 >= 701 && forecastIdInt4 <= 781) {
                        day5Icon.setImageResource(R.drawable.icon_mist);
                    } else if (forecastIdInt4 == 800) {
                        day5Icon.setImageResource(R.drawable.icon_clear);
                    } else if (forecastIdInt4 >= 801 && forecastIdInt4 <= 803) {
                        day5Icon.setImageResource(R.drawable.icon_clouds);
                    } else if (forecastIdInt4 == 804) {
                        day5Icon.setImageResource(R.drawable.icon_overcast);
                    }

                    mDay1Text.setText(dateTimeFinal0);
                    mDay2Text.setText(dateTimeFinal1);
                    mDay3Text.setText(dateTimeFinal2);
                    mDay4Text.setText(dateTimeFinal3);
                    mDay5Text.setText(dateTimeFinal4);
                    mDescription1Text.setText(description0.toUpperCase());
                    mDescription2Text.setText(description1.toUpperCase());
                    mDescription3Text.setText(description2.toUpperCase());
                    mDescription4Text.setText(description3.toUpperCase());
                    mDescription5Text.setText(description4.toUpperCase());

                    if (mSwitchOnOff) {
                        mTempForecast1.setText(temperatureForecastImperial0);
                        mTempForecast2.setText(temperatureForecastImperial1);
                        mTempForecast3.setText(temperatureForecastImperial2);
                        mTempForecast4.setText(temperatureForecastImperial3);
                        mTempForecast5.setText(temperatureForecastImperial4);
                    } else {
                        mTempForecast1.setText(temperatureForecastMetric0);
                        mTempForecast2.setText(temperatureForecastMetric1);
                        mTempForecast3.setText(temperatureForecastMetric2);
                        mTempForecast4.setText(temperatureForecastMetric3);
                        mTempForecast5.setText(temperatureForecastMetric4);
                    }
                    mProgressDialog.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(Home.this);
        queue.add(jor);
    }

    private void sendMail() {

        mSharedUserEmail = mSharedUserEmailText.getText().toString().trim();
        if (mSharedUserEmail.trim().isEmpty()) {
            return;
        }

        String temperature = mTemperatureText.getText().toString();
        String minTemp = mMinTempText.getText().toString();
        String maxTemp = mMaxTempText.getText().toString();
        String description = mDescriptionText.getText().toString().toUpperCase();
        String humidity = mHumidityText.getText().toString();
        String pressure = mPressureText.getText().toString();
        String windSpeed = mWindSpeedText.getText().toString();
        String windDirection = mWindDegreesText.getText().toString();
        String sunrise = mSunriseText.getText().toString();
        String sunset = mSunsetText.getText().toString();
        String indexUV = mIndexUV.getText().toString();

        String day1 = mDay1Text.getText().toString();
        String day2 = mDay2Text.getText().toString();
        String day3 = mDay3Text.getText().toString();
        String day4 = mDay4Text.getText().toString();
        String day5 = mDay5Text.getText().toString();
        String descrDay1 = mDescription1Text.getText().toString().toUpperCase();
        String descrDay2 = mDescription2Text.getText().toString().toUpperCase();
        String descrDay3 = mDescription3Text.getText().toString().toUpperCase();
        String descrDay4 = mDescription4Text.getText().toString().toUpperCase();
        String descrDay5 = mDescription5Text.getText().toString().toUpperCase();
        String tempDay1 = mTempForecast1.getText().toString();
        String tempDay2 = mTempForecast2.getText().toString();
        String tempDay3 = mTempForecast3.getText().toString();
        String tempDay4 = mTempForecast4.getText().toString();
        String tempDay5 = mTempForecast5.getText().toString();

        ApiClient defaultClient = Configuration.getDefaultApiClient();

        ApiKeyAuth apiKey = (ApiKeyAuth) defaultClient.getAuthentication("api-key");
      //  apiKey.setApiKey("AAAA9MLcx-0:APA91bE-08OhcvCrp9FreGyd357w3d_IYGA-txUlWrVvhgTS9RMAAHZbxU07yHoHEMf9ARSoCnlKE8GCbK6VqJtm-Brl5nZkYqv_cGfW3I9q4gHYRd21NI4918wvH0T_bscYTJn_abth");
        apiKey.setApiKey ( "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" );
        final SmtpApi apiInstance = new SmtpApi();

        List<SendSmtpEmailTo> emailArrayList = new ArrayList<> ();
        emailArrayList.add(new SendSmtpEmailTo().email(mSharedUserEmail));

        final SendSmtpEmail sendSmtpEmail = new SendSmtpEmail();
        sendSmtpEmail.sender(new SendSmtpEmailSender().email("tranquyen14042000@gmail.com").name("WeatherShare"));
        sendSmtpEmail.to(emailArrayList);
        sendSmtpEmail.subject("You've received a Shared Weather Report");
        sendSmtpEmail.htmlContent("WATHER TODAY");

        Thread thread = new Thread ( new Runnable () {
            @Override
            public void run() {
                try {
                    apiInstance.sendTransacEmail(sendSmtpEmail);
                } catch (ApiException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

    }
}