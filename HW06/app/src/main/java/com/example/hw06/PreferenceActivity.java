package com.example.hw06;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class PreferenceActivity extends android.preference.PreferenceActivity {
String whichactivitycalled;
    String country,city;
    ArrayList<City> savedcitieslist=new ArrayList<City>();
    String tempvalue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        if(getIntent()!=null) {
            whichactivitycalled = getIntent().getExtras().getString("WhichActivityCalled");
            country = getIntent().getExtras().getString("Country");
            city = getIntent().getExtras().getString("City");
            savedcitieslist = (ArrayList) getIntent().getExtras().getParcelableArrayList("SavedCitiesList");
        }
        Preference Preference = findPreference("prefUpdateTemperature");
        Preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if(newValue.equals("2")) {
                    Toast.makeText(PreferenceActivity.this, "Temperature Unit has been changed to ºF ", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(PreferenceActivity.this, "Temperature Unit has been changed to ºC ", Toast.LENGTH_LONG).show();

                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(whichactivitycalled.equals("MainActivity")){
            Intent i = new Intent(PreferenceActivity.this, MainActivity.class);
            startActivity(i);
        }
        else{
            Intent i = new Intent(PreferenceActivity.this, CityWeatherActivity.class);
            i.putExtra("Country", country);
            i.putExtra("City", city);
            i.putExtra("SavedCitiesList",savedcitieslist);
            startActivity(i);
        }

    }

}
