package com.a2do2you.android.a2do2you;

import java.util.ArrayList;

/**
 * Created by Jesus on 18/06/2017.
 */

public class Constantes {

    final static String SELECT_SUBTAREA = "SELECT * from tareas WHERE subtarea=";
    final static String SELECT_ALL = "SELECT * from tareas where subtarea = 0";
    final static String DELETE_ALL = "DELETE from tareas";
    final static String SELECT_PADRE = "SELECT * from tareas WHERE ID =";


    //prueba


    public static ArrayList<String> rellenarPrueba(){
        ArrayList<String> insertPrueba = new ArrayList<String>();//no se puede empezar en 0, ya que sino la recuperación de la tarea padre de 0 sería infinita
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(1, 'Prueba1','Descripcion prueba 1',0,0);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(2, 'Prueba2','Descripcion prueba 2',0,0);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(3, 'Prueba3','Descripcion prueba 3',0,1);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(4, 'Prueba4','Descripcion prueba 4',0,1);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(5, 'Prueba5','Descripcion prueba 5',0,2);");

        return insertPrueba;
    }
}
