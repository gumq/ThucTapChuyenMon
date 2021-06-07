package com.tranlequyen.appdubaothoitiet.service;

import org.jetbrains.annotations.NotNull;

public class APIService {


    public static String getBASEURL() {
        return BASEURL;
    }

    public static void setBASEURL(String BASEURL) {
        APIService.BASEURL = BASEURL;
    }

    public static String getCurrentWeather() {
        return CurrentWeather;
    }

    public static void setCurrentWeather(String currentWeather) {
        CurrentWeather = currentWeather;
    }

    public static String getListWeather() {
        return ListWeather;
    }

    public static void setListWeather(String listWeather) {
        ListWeather = listWeather;
    }

    public static String getDaily() {
        return Daily;
    }

    public static void setDaily(String daily) {
        Daily = daily;
    }

    public static String getUnitsAppid() {
        return UnitsAppid;
    }

    public static void setUnitsAppid(String unitsAppid) {
        UnitsAppid = unitsAppid;
    }

    public static String getUnitsAppidDaily() {
        return UnitsAppidDaily;
    }

    public static void setUnitsAppidDaily(String unitsAppidDaily) {
        UnitsAppidDaily = unitsAppidDaily;
    }
    private static String BASEURL= "http://api.openweathermap.org/data/2.5/";
    private static String CurrentWeather = "forecast?";;
    private static String ListWeather= "forecast?";
    private static String Daily  = "forecast/daily?";;
    private static String UnitsAppid = "&units=metric&appid=ef7eca8867f42ef32843365c94dd6b1b";
    private static String UnitsAppidDaily = "&units=metric&cnt=1&appid=ef7eca8867f42ef32843365c94dd6b1b";



   // public String  BASEURL = "http://api.openweathermap.org/data/2.5/";
//    public String  CurrentWeather = "weather?";
//    public String ListWeather = "forecast?";
//    public String Daily = "forecast/daily?";
//    public String UnitsAppid = "&units=metric&appid=ef7eca8867f42ef32843365c94dd6b1b";
//    public String UnitsAppidDaily = "&units=metric&cnt=1&appid=ef7eca8867f42ef32843365c94dd6b1b";
}
