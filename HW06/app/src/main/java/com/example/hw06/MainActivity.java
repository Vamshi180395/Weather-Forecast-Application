package com.example.hw06;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainAdapter.settingFavourites{

ProgressDialog pd;
    String tempvalue;
    EditText city,country;
    TextView savedcitiesheading,nfavouritesdisplay,txtnosaved;
    DatabaseDataManager dbmanager;
    RecyclerView mainrecycleview;
    List<City> savedcitieslist;
    ArrayList<City> svedcisties=new ArrayList<City>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewByIds();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        tempvalue = sharedPrefs.getString("prefUpdateTemperature","");
        dbmanager=new DatabaseDataManager(this);
        savedcitieslist= dbmanager.getAllCities();
       showsavedCities(savedcitieslist);
    }
    private void showsavedCities(List<City> savedcitieslist) {
        if(tempvalue.equals("2")){
            dbmanager=new DatabaseDataManager(this);
            savedcitieslist= dbmanager.getAllCities();
            savedcitieslist=convertFromCelsiusToFahrenheit(savedcitieslist);
        }
        ArrayList<City> sortedlist=new ArrayList<City>();
        ArrayList<City> notsortedlist=new ArrayList<City>();
        if(savedcitieslist!=null &&savedcitieslist.size()!=0){
            for(int k=0;k<savedcitieslist.size();k++){
                if(savedcitieslist.get(k).getFavourite()==1){
                    sortedlist.add(savedcitieslist.get(k));
                }
                else{
                    notsortedlist.add(savedcitieslist.get(k));
                }

            }
            sortedlist.addAll(notsortedlist);
            if(sortedlist!=null && sortedlist.size()!=0) {
                txtnosaved.setText(" ");
                mainrecycleview.setVisibility(View.VISIBLE);
                MainAdapter adapter = new MainAdapter(this, sortedlist, MainActivity.this);
                mainrecycleview.setAdapter(adapter);
                LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
                mainrecycleview.setLayoutManager(layoutManager);
               this.savedcitieslist = sortedlist;
            }
            else
            {
                mainrecycleview.setVisibility(View.GONE);
                txtnosaved.setText("There are no cities to display. Search the city from the search box and save.");

            }
        }
        else{
            mainrecycleview.setVisibility(View.GONE);
            txtnosaved.setText("There are no cities to display. Search the city from the search box and save.");

        }
    }

    public void goToCityWeatherActivity(View view) {
        if(isConnected()) {
            svedcisties.addAll(savedcitieslist);
            Intent i = new Intent(MainActivity.this, CityWeatherActivity.class);
            i.putExtra("Country", country.getText().toString());
            i.putExtra("City", city.getText().toString());
            i.putExtra("SavedCitiesList",svedcisties);
            startActivity(i);
        }
        else{
            Toast.makeText(this,"Please check your internet connection to proceed further.",Toast.LENGTH_LONG).show();
        }


    }
    private boolean isConnected() {
        ConnectivityManager cm= (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo ni= cm.getActiveNetworkInfo();
        if(ni!=null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_settings) {
            Intent in = new Intent(MainActivity.this, PreferenceActivity.class);
            in.putExtra("WhichActivityCalled","MainActivity");
            in.putExtra("Country", country.getText().toString());
            in.putExtra("City", city.getText().toString());
            in.putExtra("SavedCitiesList",svedcisties);
            startActivity(in);
        }
        return true;
        }
    private void findViewByIds() {
        city= (EditText) findViewById(R.id.txtboxcity);
        country= (EditText) findViewById(R.id.txtboxcountry);
        nfavouritesdisplay =(TextView) findViewById(R.id.txtnofavourites);
        savedcitiesheading=(TextView) findViewById(R.id.tvheading);
        txtnosaved=(TextView) findViewById(R.id.txtnosavedcities);
        mainrecycleview=(RecyclerView) findViewById(R.id.rview);
    }
    public List<City> convertFromCelsiusToFahrenheit(List<City> savedcitieslist){
        for (int i=0;i<savedcitieslist.size();i++) {
            double fahrenheit=(Double.parseDouble(savedcitieslist.get(i).getTemperature())*9/5.0)+32;
            fahrenheit=round(fahrenheit,2);
            savedcitieslist.get(i).setTemperature(fahrenheit+"");
        }
        return savedcitieslist;
    }
    @Override
    public void setFavourite(int position) {
        savedcitieslist= dbmanager.getAllCities();
        City city=savedcitieslist.get(position);
        if(city.getFavourite()==0){
            city.setFavourite(1);
        }
        else{
            city.setFavourite(0);
        }
        dbmanager=new DatabaseDataManager(this);
        dbmanager.updateCity(city);
        savedcitieslist= dbmanager.getAllCities();
        showsavedCities(savedcitieslist);

    }

    @Override
    public void deleteCity(int position) {
        City city=savedcitieslist.get(position);
        savedcitieslist.remove(position);
        dbmanager=new DatabaseDataManager(this);
        dbmanager.deleteCity(city);
        Toast.makeText(MainActivity.this, "City succesfully deleted from favourites!!!", Toast.LENGTH_SHORT).show();
        showsavedCities(savedcitieslist);

    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }


}
