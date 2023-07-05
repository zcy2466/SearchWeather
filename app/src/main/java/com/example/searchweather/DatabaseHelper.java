package com.example.searchweather;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DB_NAME="getWeather.db";
    public static final int VERSION=1;
    public static final String TABLE_NAME="interested_City";
    public static final String TABLE_NAME1="Temp_City";
    public static final String TABLE_NAME2="Index";
    public DatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //建表语句
    public static final String CREATE_Weather = "create table interested_City (CityId text primary key,CityName text)";

    public static final String CREATE_Temp_City = "create table Temp_City(Xuhao integer primary key autoincrement,CityId text,Province text,Cityname text,Updatetime text,temperature text,humidity text)";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_Weather);
        db.execSQL(CREATE_Temp_City);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
