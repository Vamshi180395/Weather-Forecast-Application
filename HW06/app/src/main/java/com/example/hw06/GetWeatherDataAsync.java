package com.example.hw06;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.xmlpull.v1.XmlPullParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;

/**
 * Created by Rama Vamshi Krishna on 10/4/2016.
 */
public class GetWeatherDataAsync extends AsyncTask<String,Void,ArrayList<WeatherItems>>{
    BufferedReader reader;
    CityWeatherActivity activity1;

    public GetWeatherDataAsync(CityWeatherActivity activity) {
        this.activity1 = activity;
    }

    @Override
    protected ArrayList<WeatherItems> doInBackground(String... strings) {
        for (int i=0; i<100; i++) {
            for (int j = 0; j < 10000000; j++) {
            }

        }

        try {

            URL url = new URL(strings[0]);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statuscode = con.getResponseCode();
            if (statuscode == HttpURLConnection.HTTP_OK) {
                InputStream in= con.getInputStream();
                return WeatherItemsUtil.ParseWeatherItems.doXmlPullParsing(in);
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        activity1.pd.show();
    }


    @Override
    protected void onPostExecute(ArrayList<WeatherItems> weatherItemses) {
        super.onPostExecute(weatherItemses);
        activity1.pd.dismiss();
        if(weatherItemses!=null) {
            activity1.maintainWeatherItemsList(weatherItemses);
        }
        else{
            Toast.makeText(activity1, "No such city found. Please enter appropriate details to proceed further", Toast.LENGTH_SHORT).show();
        }
    }


    public static interface  IData{
        public void maintainWeatherItemsList(ArrayList<WeatherItems> weatherItemses);
    }
}

