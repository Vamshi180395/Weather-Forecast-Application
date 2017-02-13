package com.example.hw06;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
/**
 * Created by Rama Vamshi Krishna on 10/19/2016.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {
private List<City> savedcitieslist;
    DatabaseDataManager dbmanager;
    Uri imageuri;
    static  MainActivity activity;
        private Context mContext;
        public MainAdapter(Context context, List<City> itemslist,MainActivity activity) {
            savedcitieslist = itemslist;
            mContext = context;
            this.activity=activity;
        }

        @Override
        public MainAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);
            View contactView = inflater.inflate(R.layout.row_layout_main, parent, false);
            contactView.setBackgroundColor(Color.GRAY);
            ViewHolder viewHolder = new ViewHolder(contactView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final MainAdapter.ViewHolder viewHolder, final int position) {
            final int pos=position;
            City item = savedcitieslist.get(position);
            TextView textViewnames = viewHolder.txtnames;
            TextView textViewavgtemp = viewHolder.avgtemp;
            TextView textViewtodaydate = viewHolder.texttdate;
            final ImageView starimage =viewHolder.starimage;
            textViewnames.setText(" "+item.getCityname()+", "+item.getCountryname()+" ");
            if(activity.tempvalue.equals("2")){
                textViewavgtemp.setText(item.getTemperature()+"\u00b0 F");
            }
            else {
                textViewavgtemp.setText(item.getTemperature() + "\u00b0 C");
            }
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
            String date= sdf.format(cal.getTime()).toString();
            textViewtodaydate.setText(date);
            if(item.getFavourite()==1){
                imageuri= Uri.parse("android.resource://com.example.hw06/drawable/star_gold");
                starimage.setImageURI(imageuri);
            }
            else
            {
                imageuri= Uri.parse("android.resource://com.example.hw06/drawable/star_gray");
                starimage.setImageURI(imageuri);
            }
            starimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(imageuri.equals( Uri.parse("android.resource://com.example.hw06/drawable/star_gray"))){
                        imageuri= Uri.parse("android.resource://com.example.hw06/drawable/star_gold");
                        Toast.makeText(activity, "City added to favourites successfully!!!", Toast.LENGTH_SHORT).show();

                    }
                    else{
                        imageuri= Uri.parse("android.resource://com.example.hw06/drawable/star_gray");
                        Toast.makeText(activity, "City removed from favourites successfully!!!", Toast.LENGTH_SHORT).show();

                    }

                    viewHolder.starimage.setImageURI(imageuri);
              activity.setFavourite(pos);
                }
            });
        }

        // Returns the total count of items in the list
        @Override
        public int getItemCount() {
            return savedcitieslist.size();
        }



public static class ViewHolder extends RecyclerView.ViewHolder {
    public TextView txtnames,avgtemp,texttdate;
    public ImageView starimage;
    public ViewHolder(View itemView) {
        super(itemView);
        txtnames = (TextView) itemView.findViewById(R.id.txtnames);
        avgtemp=(TextView) itemView.findViewById(R.id.txtavgtemp);
        texttdate=(TextView) itemView.findViewById(R.id.txttdate);
        starimage = (ImageView) itemView.findViewById(R.id.starimage);
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int position=getAdapterPosition();
                try {
                    activity.deleteCity(position);
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                return true;
            }
        });
    }


}
    public interface settingFavourites{
        public void setFavourite(int position);
        public void deleteCity(int position);
    }
}

