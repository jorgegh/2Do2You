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


    public static ArrayList<String> rellenarPrueba() {
        ArrayList<String> insertPrueba = new ArrayList<String>();//no se puede empezar en 0, ya que sino la recuperación de la tarea padre de 0 sería infinita
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(1, 'Prueba1','Descripcion prueba 1',0,0);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(2, 'Prueba2','Descripcion prueba 2',0,1);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(3, 'Prueba3','Descripcion prueba 3',0,2);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(4, 'Prueba4','Descripcion prueba 4',0,3);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(5, 'Prueba5','Descripcion prueba 5',0,4);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(6, 'Prueba6','Descripcion prueba 6',0,0);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(7, 'Prueba7','Descripcion prueba 7',0,6);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(8, 'Prueba8','Descripcion prueba 8',0,7);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(9, 'Prueba9','Descripcion prueba 9',0,8);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(10, 'Prueba10','Descripcion prueba 10',0,8);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(11, 'Prueba11','Descripcion prueba 11',0,8);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(12, 'Prueba12','Descripcion prueba 12',0,8);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(13, 'Prueba13','Descripcion prueba 13',0,8);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(14, 'Prueba14','Descripcion prueba 14',0,8);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(15, 'Prueba15','Descripcion prueba 15',0,8);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(16, 'Prueba16','Descripcion prueba 16',0,8);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(17, 'Prueba17','Descripcion prueba 17',0,8);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(18, 'Prueba18','Descripcion prueba 18',0,8);");
        /*insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(19, 'Prueba19','Descripcion prueba 19',0,8);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(20, 'Prueba20','Descripcion prueba 20',0,8);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(21, 'Prueba21','Descripcion prueba 21',0,8);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(22, 'Prueba22','Descripcion prueba 22',0,8);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(23, 'Prueba23','Descripcion prueba 23',0,8);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(24, 'Prueba24','Descripcion prueba 24',0,8);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(25, 'Prueba25','Descripcion prueba 25',0,8);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(26, 'Prueba26','Descripcion prueba 26',0,8);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(27, 'Prueba27','Descripcion prueba 27',0,8);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(28, 'Prueba28','Descripcion prueba 28',0,8);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(29, 'Prueba29','Descripcion prueba 29',0,8);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(30, 'Prueba30','Descripcion prueba 30',0,11);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(31, 'Prueba31','Descripcion prueba 31',0,11);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(32, 'Prueba32','Descripcion prueba 32',0,11);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(33, 'Prueba33','Descripcion prueba 33',0,11);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(34, 'Prueba34','Descripcion prueba 34',0,11);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(35, 'Prueba35','Descripcion prueba 35',0,10);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(36, 'Prueba36','Descripcion prueba 36',0,10);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(37, 'Prueba37','Descripcion prueba 37',0,9);");
        insertPrueba.add("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(38, 'Prueba38','Descripcion prueba 38',0,9);");*/


        return insertPrueba;
    }
}
