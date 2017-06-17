package com.a2do2you.android.a2do2you;

import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.a2do2you.android.a2do2you.OperacionesSQLite;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static OperacionesSQLite operaciones;
    //private static Cursor cursor;
    private static LinearLayout l1;
    private static LinearLayout l2;
    private static ArrayList<Tarea> tareas;



    private final String SELECT_SUBTAREA = "SELECT * from tareas WHERE subtarea=";
    private final String SELECT_ALL = "SELECT * from tareas where subtarea = 0";
    private final String SELECT_PADRE = "SELECT * from tareas WHERE ID =";
    static int i = 0;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        l1 = (LinearLayout)findViewById(R.id.linear);
        l2 = (LinearLayout)findViewById(R.id.linear2);

        operaciones = new OperacionesSQLite(this);
        tareas = new ArrayList<Tarea>();
        //cursor = null;


        try {
            operaciones.ejecutarDML("DELETE from tareas");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            operaciones.ejecutarDML("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(1, 'Prueba1','Descripcion prueba 1',0,0);");
            operaciones.ejecutarDML("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(2, 'Prueba2','Descripcion prueba 2',0,1);");
           // operaciones.ejecutarDML("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(3, 'Prueba3','Descripcion prueba 3',0,2);");
            //operaciones.ejecutarDML("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(4, 'Prueba4','Descripcion prueba 4',0,3);");
            operaciones.ejecutarDML("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(5, 'Prueba5','Descripcion prueba 5',0,1);");
            operaciones.ejecutarDML("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(6, 'Prueba6','Descripcion prueba 6',0,2);");
            operaciones.ejecutarDML("INSERT INTO tareas(tarea, title,descripcion,tipo,subtarea) VALUES(7, 'Prueba7','Descripcion prueba 7',0,2);");
        }
        catch(Exception e){
            Toast.makeText(this,"Ha ocurrido un error al escribir los datos",Toast.LENGTH_SHORT).show();
        }

        try {

            Cursor cursor = operaciones.ejecutarSelect(SELECT_ALL);
            recolectarDatos(cursor);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void recolectarDatos(Cursor cursor) throws Exception {
        System.out.println("recolectarDatos");


        //if (cursor.moveToFirst()) {
            l1.removeAllViews();
            l2.removeAllViews();
            tareas.clear();
            añadirTarea(cursor,null);


            pintarBotones();


            //if (i>1){poblarDatos();}


       // }
    }


    public void pintarBotones(){
        i = 0;
        for(final Tarea tareaActual:tareas) {

            Button btn = new Button(this);
            btn.setId(tareaActual.getId());
            btn.setTag(Integer.valueOf(i));
            btn.setText(tareaActual.getTitulo());
            btn.setOnClickListener(this);


            if(i%2 == 0){
                l1.addView(btn);
            }
            else {
                l2.addView(btn);
            }
            i++;
        }
    }

    public void poblarDatos() {

        i = 0;
        try{
            for(final Tarea tareaActual:tareas) {

                Button btn = new Button(this);
                btn.setId(tareaActual.getId());
                btn.setTag(Integer.valueOf(i));
                btn.setText(tareaActual.getTitulo());


                if(i%2 == 0){
                    l1.addView(btn);
                }
                else {
                    l2.addView(btn);
                }
                i++;
            }

        }catch(Exception e){e.printStackTrace();}
    }

        /*for (final Tarea tareaActual : tareas) {

            Button btn = new Button(this);
            btn.setId(tareaActual.getId());
            btn.setTag(Integer.valueOf(i + 1));
            btn.setText(tareaActual.getTitulo());

            i++;

            if (i % 2 == 0) {
                l1.addView(btn);
            } else {
                l2.addView(btn);
            }

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Button boton = (Button) view;
                    Integer indice = (Integer) boton.getTag();
                    System.out.println(indice);
                }
            });
        }*/



    public void añadirTarea(Cursor cursor,Tarea subtarea) throws Exception{

        if (cursor.moveToFirst()){
            do {
                Tarea tarea = new Tarea();
                tarea.setId(cursor.getInt(0));
                tarea.setTitulo(cursor.getString(1));
                tarea.setDescripcion(cursor.getString(2));
                tarea.setEstado(cursor.getInt(3));
                tarea.setTipo(cursor.getInt(4));
                //if (cursor.getInt(5) != 0) {

                Cursor cursorSub = operaciones.ejecutarSelect(SELECT_SUBTAREA + tarea.getId());
                añadirTarea(cursorSub, tarea);
                //}
                //tarea.setSubtarea(cursor.getInt(5));
                if(subtarea != null){
                    subtarea.addSubtarea(tarea);
                }
                else {
                    tareas.add(tarea);
                }
            } while (cursor.moveToNext());
        }

    }



    /*public boolean onKeyDown(int keyCode, KeyEvent event){
        if (keyCode == event.KEYCODE_BACK){
            try {
                int i = 0;

                cursor = operaciones.ejecutarSelect(SELECT_ALL);

                if(cursor.moveToFirst()){
                    l1.removeAllViews();
                    l2.removeAllViews();
                    tareas.clear();
                    do {añadirTarea(cursor);
                    }while(cursor.moveToNext());
                }

                for(final Tarea tareaActual:tareas) {

                    Button btn = new Button(this);
                    btn.setId(tareaActual.getId());
                    btn.setTag(Integer.valueOf(i));
                    btn.setText(tareaActual.getTitulo());


                    if(i%2 == 0){
                        l1.addView(btn);
                    }
                    else {
                        l2.addView(btn);
                    }
                    i++;
                }

            }catch(Exception e){e.printStackTrace();}
        }
        return false;
    }*/

    @Override
    public void onClick(View view) {
         Button boton = (Button) view;
         int i = (Integer) boton.getTag();
         Toast.makeText(this,"Ha pulsado la tarea " + tareas.get(i).getTitulo(),Toast.LENGTH_SHORT).show();
    }
}
