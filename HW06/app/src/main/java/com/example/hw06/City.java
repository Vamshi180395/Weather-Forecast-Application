package com.example.hw06;

import java.io.Serializable;

/**
 * Created by Rama Vamshi Krishna on 10/19/2016.
 */
public class City implements Serializable {
    private String cityname,countryname,temperature;
    private int favourite;

    public City(String cityname, String countryname, String temperature, int favourite) {
        this.cityname = cityname;
        this.countryname = countryname;
        this.temperature = temperature;
        this.favourite = favourite;
    }
    public City(){

    }

    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    public String getCountryname() {
        return countryname;
    }

    public void setCountryname(String countryname) {
        this.countryname = countryname;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public int getFavourite() {
        return favourite;
    }

    public void setFavourite(int favourite) {
        this.favourite = favourite;
    }

    @Override
    public String toString() {
        return "City{" +
                "cityname='" + cityname + '\'' +
                ", countryname='" + countryname + '\'' +
                ", temperature='" + temperature + '\'' +
                ", favourite=" + favourite +
                '}';
    }
}
