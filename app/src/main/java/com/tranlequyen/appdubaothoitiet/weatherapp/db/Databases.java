package com.tranlequyen.appdubaothoitiet.weatherapp.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Databases extends SQLiteOpenHelper {
   String Data_PATH ="/databases/";

    public Databases(@Nullable Context context, @Nullable String name,
                     @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    //Truy vấn không trả kết quả (CREATE, INSERT, UPDATE, DELETE, ...)
    public void QueryData(String sql){

        SQLiteDatabase  db = getWritableDatabase();
        db.execSQL(sql);
    }

    //Truy vấn trả về kết quả (SELECT)
    public Cursor GetData(String sql){
        SQLiteDatabase db = getReadableDatabase();
        return db.rawQuery(sql,null);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
