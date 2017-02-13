package com.example.hw06;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.HorizontalScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CityWeatherActivity extends AppCompatActivity implements GetWeatherDataAsync.IData,MyAdapter.ViewHolder.MaintainData2{
ProgressDialog pd;
    TextView txtheader,txtdownheader,txttime;
    String city,country;
    GetWeatherDataAsync gwda=new GetWeatherDataAsync(CityWeatherActivity.this);
    ArrayList<WeatherItems> weatheritemslist=new ArrayList<WeatherItems>();
    ArrayList<WeatherItems> upweatheritemslist=new ArrayList<WeatherItems>();
    ArrayList<City> savedcitieslist=new ArrayList<City>();
    String averagetemp,tempvalue;
    int count;

DatabaseDataManager dbmanager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_weather);
        getSupportActionBar().setTitle("City Weather");
        findViewByIds();
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(CityWeatherActivity.this);
        tempvalue = sharedPrefs.getString("prefUpdateTemperature","");

        if (getIntent().getExtras() != null) {
            pd=new ProgressDialog(this);
            pd.setCancelable(false);
            pd.setMessage("Loading Data");
            country=getIntent().getExtras().getString("Country");
            city=getIntent().getExtras().getString("City");
            savedcitieslist=(ArrayList) getIntent().getExtras().getParcelableArrayList("SavedCitiesList");
            if (city.contains(" ")){
                city=city.replace(" ","_");
            }
            String xmlurl="http://api.openweathermap.org/data/2.5/forecast?q=" + city + "," + country + "&mode=xml&appid=920b33fdd87d23595910096691e9d468";
            gwda.execute(xmlurl);
            if (city.contains("_")){
                city=city.replace("_"," ");
            }
            txtheader.setText("Daily Forcast for "+city+", "+country);

        }
    }

    private void findViewByIds() {
        txtheader=(TextView) findViewById(R.id.txtheader);
        txtdownheader=(TextView) findViewById(R.id.txtdownheader);
        txttime=(TextView) findViewById(R.id.txttime);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.city_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_settings) {
            Intent in = new Intent(CityWeatherActivity.this, PreferenceActivity.class);
            in.putExtra("WhichActivityCalled","CityWeatherActivity");
            in.putExtra("Country", country);
            in.putExtra("City", city);
            in.putExtra("SavedCitiesList",savedcitieslist);
            startActivity(in);
        }
        else if (id == R.id.add_save) {
     if(city.length()!=0&&country.length()!=0){
for(int l=0;l<savedcitieslist.size();l++){
    if (city.equals(savedcitieslist.get(l).getCityname())){
        count=1;
    }
}
         if(count==1){
             averagetemp = calculateAverageTemperature(upweatheritemslist);
             dbmanager = new DatabaseDataManager(this);
             dbmanager.updateCity(new City(city, country, averagetemp, 0));
             count=0;
             Toast.makeText(CityWeatherActivity.this, "City Updated Succesfully!!!", Toast.LENGTH_LONG).show();

         }
         else {
             averagetemp = calculateAverageTemperature(upweatheritemslist);
             dbmanager = new DatabaseDataManager(this);
             dbmanager.saveCity(new City(city, country, averagetemp, 0));
             Toast.makeText(CityWeatherActivity.this, "City Saved Succesfully!!!", Toast.LENGTH_LONG).show();

         }

}
        }
        return true;
    }

    private String calculateAverageTemperature(ArrayList<WeatherItems> list) {
        double avgtemp=0;
        ArrayList<WeatherItems> temparralist=new ArrayList<WeatherItems>();
        for (int i=0;i<weatheritemslist.size();i++){
          if(weatheritemslist.get(i).getFromdate().contains(list.get(0).getFromdate().substring(0,9))){
              temparralist.add(weatheritemslist.get(i));
          }
        }
       for(int m=0;m<temparralist.size();m++){
           avgtemp+=Double.parseDouble(temparralist.get(m).getTemperature());
       }
avgtemp=avgtemp/temparralist.size();
        avgtemp=round(avgtemp,2);
        return avgtemp+"";
    }

    @Override
    public void maintainWeatherItemsList(ArrayList<WeatherItems> weatheritemslist) {
         this.weatheritemslist=weatheritemslist;
        for(int i=0; i<weatheritemslist.size();i++){
            if(weatheritemslist.get(i).getTodate().contains("21:00:00")){
                upweatheritemslist.add(weatheritemslist.get(i));
            }

        }

        RecyclerView myrecycleview=(RecyclerView) findViewById(R.id.my_recycler_view);
        MyAdapter adapter = new MyAdapter(this, upweatheritemslist,CityWeatherActivity.this,weatheritemslist);
        myrecycleview.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        layoutManager.setSmoothScrollbarEnabled(true);
        myrecycleview.setLayoutManager(layoutManager);


    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    @Override
    public void startNewRecycleView(int position) {
        ArrayList<WeatherItems> downweatheritemslist=new ArrayList<WeatherItems>();
        WeatherItems item= upweatheritemslist.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfs = new SimpleDateFormat("MMM dd yyyy");
        try {
            Date dt=sdf.parse(item.getFromdate().substring(0,10));
            txtdownheader.setText("Three Hourly ForeCast On,"+sdfs.format(dt));


        } catch (ParseException e) {
            e.printStackTrace();
        }
        String tdatetocompare=item.getFromdate().substring(0,10);
        for(int i=0;i<weatheritemslist.size();i++){
            if(weatheritemslist.get(i).getFromdate().contains(tdatetocompare)){
               downweatheritemslist.add(weatheritemslist.get(i));
            }
        }
        if(downweatheritemslist.size()!=0){
            RecyclerView downrecycleview=(RecyclerView) findViewById(R.id.down_recycler_view);
            DownAdapter adapter = new DownAdapter(this, downweatheritemslist,CityWeatherActivity.this);
            downrecycleview.setAdapter(adapter);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
            layoutManager.setSmoothScrollbarEnabled(true);
            downrecycleview.setLayoutManager(layoutManager);

        }

    }

    public void onBackPressed() {
        super.onBackPressed();
        Intent i = new Intent(CityWeatherActivity.this, MainActivity.class);
        startActivity(i);
    }
}
