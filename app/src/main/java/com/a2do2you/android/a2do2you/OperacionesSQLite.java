package com.a2do2you.android.a2do2you;

import android.content.Context;
import android.database.Cursor;

/**
 * Created by Jorge on 04/06/2017.
 */

public class OperacionesSQLite {

    private ConectorBBDDSQLite conexion;

    public OperacionesSQLite(Context context){
        conexion = new ConectorBBDDSQLite(context);
    }

    public Cursor ejecutarSelect(String query) throws Exception{
        Cursor cursor = conexion.getReadableDatabase().rawQuery(query,null);
        return cursor;
    }

    public void ejecutarDML(String sentencia) throws Exception {
        conexion.getWritableDatabase().execSQL(sentencia);
    }

    public void cerrarConexion(){
        conexion.close();
    }
}
