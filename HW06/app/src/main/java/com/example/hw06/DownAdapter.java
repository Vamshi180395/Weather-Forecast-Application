package com.example.hw06;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by Rama Vamshi Krishna on 10/20/2016.
 */
public class DownAdapter extends RecyclerView.Adapter <DownAdapter.ViewHolder> {

private List<WeatherItems> witemslist;
private Context mContext;
        CityWeatherActivity activity;
    SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
    SimpleDateFormat sdfs = new SimpleDateFormat("hh:mm a");
public DownAdapter(Context context, List<WeatherItems> itemslist,CityWeatherActivity activity) {
        witemslist = itemslist;
        mContext = context;
        this.activity=activity;
        }

@Override
public DownAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.row_down_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
        }

@Override
public void onBindViewHolder(DownAdapter.ViewHolder viewHolder, int position) {
        WeatherItems item = witemslist.get(position);
    TextView temperature1, condition1,pressure1,humidity1,wind1,time;
    ImageView networkImage;
        temperature1 = viewHolder.temperature;
        condition1 = viewHolder.condition;
     pressure1 = viewHolder.pressure;
    humidity1 = viewHolder.humidity;
     wind1 = viewHolder.wind;
    networkImage=viewHolder.image;
    time=viewHolder.time;
    try {
        Date dt=sdf.parse(item.getFromdate().substring(11,item.getFromdate().length()));
        time.setText(sdfs.format(dt));
    } catch (ParseException e) {
        e.printStackTrace();
    }
    if(activity.tempvalue.equals("1")) {
        temperature1.setText(item.getTemperature() + "\u00b0 C");
    }
    else{

        temperature1.setText(convertFromCelsiusToFahrenheit(item.getTemperature()) + "\u00b0 F");
    }
    condition1 .setText(item.getClouds());
    pressure1 .setText(item.getPressure()+" "+ item.getPunit());
    humidity1 .setText(item.getHumidity() + " " + item.getHunit());
    wind1.setText("       "+item.getWindSpeed()+"mps, "+item.getWinddegrees()+"°"+ item.getWinddirection());
    Picasso.with(activity).load("http://openweathermap.org/img/w/"+item.getSymbolforimage()+".png").fit().into(networkImage);
}

// Returns the total count of items in the list
@Override
public int getItemCount() {
        return witemslist.size();
        }



public static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView temperature, condition,pressure,humidity,wind,time;
    public ImageView image;

    public ViewHolder(View itemView) {
        super(itemView);
        time=(TextView) itemView.findViewById(R.id.txttime);
        temperature = (TextView) itemView.findViewById(R.id.stemp);
        condition = (TextView) itemView.findViewById(R.id.scondition);
        pressure = (TextView) itemView.findViewById(R.id.spressure);
        humidity = (TextView) itemView.findViewById(R.id.shumidity);
        wind = (TextView) itemView.findViewById(R.id.swind);
        image=(ImageView) itemView.findViewById(R.id.imageView2);

    }

}

    public String convertFromCelsiusToFahrenheit(String temp){

        double fahrenheit=(((Double.parseDouble(temp))*9/5.0)+32);
        fahrenheit=round(fahrenheit,2);
        return fahrenheit+ "";

    }
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}





