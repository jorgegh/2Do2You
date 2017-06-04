package com.a2do2you.android.a2do2you;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private OperacionesSQLite operaciones;
    private Cursor cursor;
    private LinearLayout l1;
    private LinearLayout l2;
    private ArrayList<Tarea> tareas;

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
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo) VALUES('Prueba1','Descripcion prueba 1',0);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo) VALUES('Prueba2','Descripcion prueba 2',0);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo) VALUES('Prueba3','Descripcion prueba 3',0);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo) VALUES('Prueba1','Descripcion prueba 1',0);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo) VALUES('Prueba2','Descripcion prueba 2',0);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo) VALUES('Prueba3','Descripcion prueba 3',0);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo) VALUES('Prueba1','Descripcion prueba 1',0);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo) VALUES('Prueba2','Descripcion prueba 2',0);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo) VALUES('Prueba3','Descripcion prueba 3',0);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo) VALUES('Prueba1','Descripcion prueba 1',0);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo) VALUES('Prueba2','Descripcion prueba 2',0);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo) VALUES('Prueba3','Descripcion prueba 3',0);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo) VALUES('Prueba1','Descripcion prueba 1',0);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo) VALUES('Prueba2','Descripcion prueba 2',0);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo) VALUES('Prueba3','Descripcion prueba 3',0);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo) VALUES('Prueba1','Descripcion prueba 1',0);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo) VALUES('Prueba2','Descripcion prueba 2',0);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo) VALUES('Prueba3','Descripcion prueba 3',0);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo) VALUES('Prueba1','Descripcion prueba 1',0);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo) VALUES('Prueba2','Descripcion prueba 2',0);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo) VALUES('Prueba3','Descripcion prueba 3',0);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo) VALUES('Prueba1','Descripcion prueba 1',0);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo) VALUES('Prueba2','Descripcion prueba 2',0);");
            operaciones.ejecutarDML("INSERT INTO tareas(title,descripcion,tipo) VALUES('Prueba3','Descripcion prueba 3',0);");

        }
        catch(Exception e){
            Toast.makeText(this,"Ha ocurrido un error al escribir los datos",Toast.LENGTH_SHORT).show();
        }

        try {
            cursor = operaciones.ejecutarSelect("SELECT * from tareas");

            while(cursor.moveToNext()){
                añadirTarea(cursor);
            }

            int i = 0;

            for(Tarea tareaActual:tareas){

                Button btn = new Button(this);
                btn.setId(tareaActual.getId());
                btn.setTag(Integer.valueOf(i));
                btn.setText(tareaActual.getTitulo());
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Button boton = (Button) view;
                        Integer indice = (Integer)boton.getTag();
                        System.out.println(indice);
                        Toast.makeText(MainActivity.this,tareas.get(indice).getDescripcion(),Toast.LENGTH_SHORT).show();
                    }
                });
                //btn.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
                if(i%2 == 0){
                    l1.addView(btn);
                }
                else {
                    l2.addView(btn);
                }

                i++;
                //Toast.makeText(this,cursor.getInt(0) + " " +  + " " + cursor.getString(2),Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(this,"Ha ocurrido un error al recuperar los datos",Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        finally{
            if(cursor != null){
                cursor.close();
            }
            operaciones.cerrarConexion();
        }

    }

    public void añadirTarea(Cursor cursor) throws Exception{
        Tarea tarea = new Tarea();
        tarea.setId(cursor.getInt(0));
        tarea.setTitulo(cursor.getString(1));
        tarea.setDescripcion(cursor.getString(2));
        tarea.setEstado(cursor.getInt(3));
        tarea.setTipo(cursor.getInt(4));
        tarea.setSubtarea(cursor.getInt(5));
        tareas.add(tarea);
    }
}
