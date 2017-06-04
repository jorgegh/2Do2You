package com.a2do2you.android.a2do2you;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ConectorBBDDSQLite extends SQLiteOpenHelper {

    public final static String NOMBREBD = "tareas.sdb";
    public static final int VERSION = 1;

    public ConectorBBDDSQLite(Context context) {
        super(context, NOMBREBD, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists tareas (tarea integer primary key autoincrement not null,title text, descripcion text, status integer,tipo integer,subtarea integer);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // db.execSQL("DROP TABLE tareas");
    }
}