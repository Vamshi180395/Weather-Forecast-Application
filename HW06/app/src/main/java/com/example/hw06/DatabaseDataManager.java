package com.example.hw06;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by Rama Vamshi Krishna on 10/14/2016.
 */
public class DatabaseDataManager {
    private Context mcontext;
    private DatabaseOpenHelper dbopenhelper;
    private SQLiteDatabase db;
    private CityDAO citydao;

    public DatabaseDataManager(Context mcontext) {
        this.mcontext = mcontext;
        dbopenhelper = new DatabaseOpenHelper(this.mcontext);
        db = dbopenhelper.getWritableDatabase();
        citydao = new CityDAO(db);
    }

    public void close() {
        if (db != null) {
            db.close();
        }
    }

    public long saveCity(City city) {
        return this.citydao.save(city);

    }

    public boolean updateCity(City city) {
        return this.citydao.update(city);
    }
    public boolean deleteCity(City city) {
        return this.citydao.delete(city);
    }
    public City getCity(String cityname)
    {
        return this.citydao.get(cityname);
    }
    public List<City> getAllCities(){
        return this.citydao.getALL();
    }
}