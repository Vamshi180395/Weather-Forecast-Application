package com.example.hw06;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by Rama Vamshi Krishna on 10/19/2016.
 */
public class CitiesTable{
    static final String TABLE_NAME="cities";
        static final String COLUMN_CITYNAME="cityname";
        static final String COLUMN_COUNTRYNAME="countryname";
        static final String COLUMN_TEMPERATURE="temperature";
        static final String COLUMN_FAVOURITE="favourite";


        static public void onCreate(SQLiteDatabase db){
            StringBuilder sb=new StringBuilder();
            sb.append("CREATE TABLE "+TABLE_NAME+ " (");
            sb.append(COLUMN_CITYNAME + " text primary key, ");
            sb.append(COLUMN_COUNTRYNAME + " text not null, ");
            sb.append(COLUMN_TEMPERATURE + " text not null, ");
            sb.append(COLUMN_FAVOURITE + " integer not null );");
            try {
                db.execSQL(sb.toString());
            }
            catch(SQLException ex){
                ex.printStackTrace();
            }


        }

        static public void onUpgrade(SQLiteDatabase db, int oldversion,int newversion){
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
            CitiesTable.onCreate(db);

        }



}

