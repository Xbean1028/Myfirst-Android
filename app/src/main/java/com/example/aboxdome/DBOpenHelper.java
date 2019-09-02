package com.example.aboxdome;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper {

    public DBOpenHelper(Context context){
        super(context,"remote.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        StringBuffer buffer = new StringBuffer();
        buffer.append("create table if not exists buttons( ");
        buffer.append("  pos integer primary key,");
        buffer.append("  name text not null,");
        buffer.append("  flag integer not null");
        buffer.append(")");
        db.execSQL(buffer.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
        String sql = "drop table if exists button";
        db.execSQL(sql);

        onCreate(db);
    }
}
