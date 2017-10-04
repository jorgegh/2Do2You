package com.a2do2you.android.a2do2you;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by Jesus on 18/06/2017.
 */

public class ManejadorDB {

    private Context contexto;
    private OperacionesSQLite operaciones;
    private ArrayList<Tarea> tareas;

    public ManejadorDB(Context contexto) {
        this.contexto = contexto;
        this.operaciones = new OperacionesSQLite(contexto);
        this.tareas = new ArrayList<Tarea>();
    }

    public void borrarTodo() throws Exception {
        operaciones.ejecutarDML(Constantes.DELETE_ALL);
    }

    public void insertarRegistroTest() throws Exception {

        for (String Insert : Constantes.rellenarPrueba()) {
            operaciones.ejecutarDML(Insert);
        }
    }

    public int insert(String title, String description,int idPadre) throws Exception{
        Cursor cursor = operaciones.ejecutarSelect("SELECT MAX(tarea) FROM TAREAS");
        int idTarea = 0;
        if (cursor.moveToFirst()) {
            idTarea = cursor.getInt(0);
            idTarea += 1;
        }
        operaciones.ejecutarDML("INSERT INTO tareas(tarea,title,descripcion,tipo,subtarea) VALUES("+idTarea +", '" +title +"','" +description +"',0," +idPadre +" );");
        return idTarea;
    }

    public ArrayList<Tarea> selectAll() throws Exception {
        Cursor cursor = operaciones.ejecutarSelect(Constantes.SELECT_ALL);
        recolectarDatos(cursor, null);
        return tareas;
    }

    private void recolectarDatos(Cursor cursor, Tarea subtarea) throws Exception {

        if (cursor.moveToFirst()) {
            do {
                Tarea tarea = new Tarea();
                tarea.setId(cursor.getInt(0));
                tarea.setTitulo(cursor.getString(1));
                tarea.setDescripcion(cursor.getString(2));
                tarea.setEstado(cursor.getInt(3));
                tarea.setTipo(cursor.getInt(4));

                Cursor cursorSub = operaciones.ejecutarSelect(Constantes.SELECT_SUBTAREA + tarea.getId());
                recolectarDatos(cursorSub, tarea);

                if (subtarea != null) {
                    subtarea.addSubtarea(tarea);
                } else {
                    tareas.add(tarea);
                }
            } while (cursor.moveToNext());
        }

    }


}
