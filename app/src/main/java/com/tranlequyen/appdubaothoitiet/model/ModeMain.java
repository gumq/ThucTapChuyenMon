package com.tranlequyen.appdubaothoitiet.model;

import java.io.Serializable;

public class ModeMain implements Serializable {
private String timeNow;
private String descWeather;
private int currentTemp;
    private int tempMax;
    private int tempMin;

    public ModeMain(String timeNow, String descWeather, int currentTemp, int tempMax, int tempMin) {
        this.timeNow = timeNow;
        this.descWeather = descWeather;
        this.currentTemp = currentTemp;
        this.tempMax = tempMax;
        this.tempMin = tempMin;
    }

    public String getTimeNow() {
        return timeNow;
    }

    public void setTimeNow(String timeNow) {
        this.timeNow = timeNow;
    }

    public String getDescWeather() {
        return descWeather;
    }

    public void setDescWeather(String descWeather) {
        this.descWeather = descWeather;
    }

    public int getCurrentTemp() {
        return currentTemp;
    }

    public void setCurrentTemp(int currentTemp) {
        this.currentTemp = currentTemp;
    }

    public int getTempMax() {
        return tempMax;
    }

    public void setTempMax(int tempMax) {
        this.tempMax = tempMax;
    }

    public int getTempMin() {
        return tempMin;
    }

    public void setTempMin(int tempMin) {
        this.tempMin = tempMin;
    }
}
