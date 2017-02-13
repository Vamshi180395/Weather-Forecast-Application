package com.example.hw06;

import java.io.Serializable;

/**
 * Created by Rama Vamshi Krishna on 10/4/2016.
 */
public class WeatherItems implements Serializable {
    String todate,fromdate, temperature, symbolforimage , clouds, windSpeed, winddirection, humidity, pressure,punit,winddegrees,hunit;

    public String getHunit() {
        return hunit;
    }

    public void setHunit(String hunit) {
        this.hunit = hunit;
    }

    public String getWinddegrees() {
        return winddegrees;
    }

    public void setWinddegrees(String winddegrees) {
        this.winddegrees = winddegrees;
    }

    public String getPunit() {
        return punit;
    }

    public void setPunit(String punit) {
        this.punit = punit;
    }

    public String getWinddirection() {
        return winddirection;
    }

    public void setWinddirection(String winddirection) {
        this.winddirection = winddirection;
    }

    public String getTodate() {
        return todate;
    }

    public void setTodate(String todate) {
        this.todate = todate;
    }

    public String getFromdate() {
        return fromdate;
    }

    public void setFromdate(String fromdate) {
        this.fromdate = fromdate;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getSymbolforimage() {
        return symbolforimage;
    }

    public void setSymbolforimage(String symbolforimage) {
        this.symbolforimage = symbolforimage;
    }

    public String getClouds() {
        return clouds;
    }

    public void setClouds(String clouds) {
        this.clouds = clouds;
    }


    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }
}


