package com.example.hw06;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Rama Vamshi Krishna on 10/19/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<WeatherItems> witemslist;
    List<WeatherItems> completelist;
    private Context mContext;
    static CityWeatherActivity activity;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat sdfs = new SimpleDateFormat("MMM dd yyyy");
    int count=0;
    public MyAdapter(Context context, List<WeatherItems> itemslist,CityWeatherActivity activity,List<WeatherItems> completelist) {
        witemslist = itemslist;
        mContext = context;
        this.activity=activity;
        this.completelist=completelist;
    }

    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View contactView = inflater.inflate(R.layout.row_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(contactView);
        contactView.getLayoutParams().width = getScreenWidth() / 3;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder viewHolder, int position) {
        WeatherItems item = witemslist.get(position);
        ImageView img=viewHolder.image;
        for(int j=0;j<completelist.size();j++){
            if(completelist.get(j).getTodate().substring(0,10).contains((item.getTodate()).substring(0,10))){
                count=count+1;
            }
        }
        if(count!=0){
            count=count/2;
            Picasso.with(activity).load("http://openweathermap.org/img/w/"+completelist.get(count).getSymbolforimage()+".png").fit().into(img);
            count=0;
        }
        else
        {
            Picasso.with(activity).load("http://openweathermap.org/img/w/"+item.getSymbolforimage()+".png").fit().into(img);

        }
        TextView textView = viewHolder.txtdate;
        TextView textViewtemp = viewHolder.txttemp;
        try {
            Date dt=sdf.parse(item.getFromdate().substring(0,10));
            textView.setText(sdfs.format(dt));


        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(activity.tempvalue.equals("1")) {
            textViewtemp.setText(item.getTemperature() + "\u00b0 C");
        }
        else{

            textViewtemp.setText(convertFromCelsiusToFahrenheit(item.getTemperature()) + "\u00b0 F");
        }

    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return witemslist.size();
    }

    public int getScreenWidth() {
        int screenWidth=0;
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }
        return screenWidth;

    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtdate,txttemp;
        public ImageView image;
        MaintainData2 mdata2;
        public ViewHolder(View itemView) {
            super(itemView);
            txtdate = (TextView) itemView.findViewById(R.id.txtdate);
            txttemp=(TextView) itemView.findViewById(R.id.txttemp);
            image = (ImageView) itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    activity.startNewRecycleView(position);
                }
            });
        }
        public interface MaintainData2{
            public void startNewRecycleView(int position);
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

