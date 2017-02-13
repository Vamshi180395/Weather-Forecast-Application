package com.example.hw06;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rama Vamshi Krishna on 10/19/2016.
 */
public class CityDAO  {
    private SQLiteDatabase db;
    public CityDAO(SQLiteDatabase db){
        this.db=db;
    }

    public long save(City city){
        ContentValues values=new ContentValues();
        values.put(CitiesTable.COLUMN_CITYNAME,city.getCityname());
        values.put(CitiesTable.COLUMN_COUNTRYNAME,city.getCountryname());
        values.put(CitiesTable.COLUMN_TEMPERATURE,city.getTemperature());
        values.put(CitiesTable.COLUMN_FAVOURITE,city.getFavourite());

        return db.insert(CitiesTable.TABLE_NAME,null,values);
    }
    public boolean update(City city){
        ContentValues values=new ContentValues();
        values.put(CitiesTable.COLUMN_CITYNAME,city.getCityname());
        values.put(CitiesTable.COLUMN_COUNTRYNAME,city.getCountryname());
        values.put(CitiesTable.COLUMN_TEMPERATURE,city.getTemperature());
        values.put(CitiesTable.COLUMN_FAVOURITE,city.getFavourite());
        return db.update(CitiesTable.TABLE_NAME,values,CitiesTable.COLUMN_CITYNAME+"=?",new String[]{city.getCityname()})>0;
    }
    public boolean delete(City city){
        return db.delete(CitiesTable.TABLE_NAME,CitiesTable.COLUMN_CITYNAME+"=?",new String[]{city.getCityname()})>0;
    }


    public City get(String cityname){
        City city=null;
        Cursor c= db.query(true,CitiesTable.TABLE_NAME,new String[]{CitiesTable.COLUMN_CITYNAME,CitiesTable.COLUMN_COUNTRYNAME,CitiesTable.COLUMN_TEMPERATURE,CitiesTable.COLUMN_FAVOURITE},CitiesTable.COLUMN_CITYNAME+"=?",new String[]{city.getCityname()},null,null,null,null,null);
        if(c!=null && c.moveToFirst()){

            city=  buildNOteFromCursor(c);
            if(!c.isClosed()){
                c.close();
            }
        }

        return city;
    }
    public List<City> getALL(){
        List<City> cities=new ArrayList<City>();
        Cursor c= db.query(CitiesTable.TABLE_NAME,new String[]{CitiesTable.COLUMN_CITYNAME,CitiesTable.COLUMN_COUNTRYNAME,CitiesTable.COLUMN_TEMPERATURE,CitiesTable.COLUMN_FAVOURITE},null,null,null,null,null);
        if(c!=null && c.moveToFirst()){
            do{
                City city=  buildNOteFromCursor(c);
                if(city!=null){
                    cities.add(city);
                }
            }while(c.moveToNext());

            if(!c.isClosed()){
                c.close();
            }
        }

        return cities;
    }
    private City buildNOteFromCursor(Cursor c){
        City city=null;
        if(c!=null){
            city=new City();
            city.setCityname(c.getString(0));
            city.setCountryname(c.getString(1));
            city.setTemperature(c.getString(2));
            city.setFavourite(c.getInt(3));
        }
        return city;
    }
}

