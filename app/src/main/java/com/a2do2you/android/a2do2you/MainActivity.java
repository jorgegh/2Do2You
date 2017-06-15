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
    private static Cursor cursor;
    private static LinearLayout l1;
    private static LinearLayout l2;
    private static ArrayList<Tarea> tareas;



    private final String SELECT_SUBTAREA = "SELECT * from tareas WHERE subtarea=";
    private final String SELECT_ALL = "SELECT * from tareas";
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
        cursor = null;


        try {
            operaciones.ejecutarDML("DELETE from tareas");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo,subtarea) VALUES('Prueba1','Descripcion prueba 1',0,null);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo,subtarea) VALUES('Prueba2','Descripcion prueba 2',0,1);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo,subtarea) VALUES('Prueba3','Descripcion prueba 3',0,1);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo,subtarea) VALUES('Prueba4','Descripcion prueba 4',0,1);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo,subtarea) VALUES('Prueba5','Descripcion prueba 5',0,2);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo,subtarea) VALUES('Prueba6','Descripcion prueba 6',0,2);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo,subtarea) VALUES('Prueba7','Descripcion prueba 7',0,2);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo,subtarea) VALUES('Prueba8','Descripcion prueba 8',0,3);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo,subtarea) VALUES('Prueba9','Descripcion prueba 9',0,3);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo,subtarea) VALUES('Prueba10','Descripcion prueba 10',0,3);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo,subtarea) VALUES('Prueba11','Descripcion prueba 11',0,4);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo,subtarea) VALUES('Prueba12','Descripcion prueba 12',0,4);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo,subtarea) VALUES('Prueba13','Descripcion prueba 13',0,4);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo,subtarea) VALUES('Prueba14','Descripcion prueba 14',0,5);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo,subtarea) VALUES('Prueba15','Descripcion prueba 15',0,5);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo,subtarea) VALUES('Prueba16','Descripcion prueba 16',0,5);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo,subtarea) VALUES('Prueba17','Descripcion prueba 17',0,6);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo,subtarea) VALUES('Prueba18','Descripcion prueba 18',0,6);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo,subtarea) VALUES('Prueba19','Descripcion prueba 19',0,6);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo,subtarea) VALUES('Prueba20','Descripcion prueba 20',0,7);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo,subtarea) VALUES('Prueba21','Descripcion prueba 21',0,7);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo,subtarea) VALUES('Prueba22','Descripcion prueba 22',0,7);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo,subtarea) VALUES('Prueba23','Descripcion prueba 23',0,8);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo,subtarea) VALUES('Prueba24','Descripcion prueba 24',0,8);");

        }
        catch(Exception e){
            Toast.makeText(this,"Ha ocurrido un error al escribir los datos",Toast.LENGTH_SHORT).show();
        }

        recolectarDatos(SELECT_ALL);

    }

    public void recolectarDatos(String select){
        System.out.println("recolectarDatos");
        try {
            i = 0;

            cursor = operaciones.ejecutarSelect(select);

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
                btn.setOnClickListener(this);


                if(i%2 == 0){
                    l1.addView(btn);
                }
                else {
                    l2.addView(btn);
                }
                i++;
            }

            //if (i>1){poblarDatos();}

        }catch(Exception e){e.printStackTrace();}
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



    public void añadirTarea(Cursor cursor) throws Exception{
        Tarea tarea = new Tarea();
        tarea.setId(cursor.getInt(0));
        tarea.setTitulo(cursor.getString(1));
        tarea.setDescripcion(cursor.getString(2));
        tarea.setEstado(cursor.getInt(3));
        tarea.setTipo(cursor.getInt(4));
        //tarea.setSubtarea(cursor.getInt(5)); commento esto
        tareas.add(tarea);
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
