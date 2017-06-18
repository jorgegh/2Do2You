package com.a2do2you.android.a2do2you;

import android.content.DialogInterface;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.a2do2you.android.a2do2you.OperacionesSQLite;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static OperacionesSQLite operaciones;
    //private static Cursor cursor;
    private  LinearLayout l1;
    private  LinearLayout l2;
    private  ArrayList<Tarea> tareas;
    private ManejadorDB manejador;
    private Tarea tareaActual;
    private Tarea tareaPadre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        l1 = (LinearLayout)findViewById(R.id.linear);
        l2 = (LinearLayout)findViewById(R.id.linear2);

        manejador = new ManejadorDB(this);
        tareas = new ArrayList<Tarea>();

        try {
            manejador.borrarTodo();
            manejador.insertarRegistroTest();
            inicializar();
        } catch (Exception e) {
            e.printStackTrace();
            toast(e.getMessage(), 1);
        }
    }

    public void inicializar() throws Exception {
        tareas.clear();
        tareas = manejador.selectAll(); //recoge todas las tareas de manejadorDB
        pintarBotones(null);
    }

    public void pintarBotones(Tarea tarea){
        if (tarea == null){
            reiniciarVista();
            for(Tarea tareaActual : tareas) {
                pintarBoton(tareaActual);
            }
        }
        else {
            if (!(tarea.getSubtarea().keySet().isEmpty())) {
                reiniciarVista();
                for (Integer idTarea : tarea.getSubtarea().keySet()) {
                    Tarea tareaActual = tarea.getSubtarea().get(idTarea); //sentencia de búsqueda. Dame la subtarea que tenga como id idtarea
                    pintarBoton(tareaActual);
                }
            }
            else{
                toast("esta tarea no tiene subtareas",2);
            }
        }
    }

    public void pintarBoton(Tarea tarea){
        Button btn = new Button(this);
        btn.setId(tarea.getId());
        btn.setTag(tarea.getId());
        btn.setText(tarea.getTitulo());
        btn.setOnClickListener(this);

        if (tarea.getId() % 2 != 0) {
            l1.addView(btn);
        } else {
            l2.addView(btn);
        }
    }

    public void reiniciarVista(){
        l1.removeAllViews();
        l2.removeAllViews();
    }

    public void buscarTarea(Integer id){
        tareaActual = null;

        for(Tarea tarea : tareas){//si es padre
            if(tarea.getId() == id){
                tareaActual = tarea;
                if(tareaActual != null){
                    break;
                }

            }
        }
        if(tareaActual == null){//si es subtarea
            for(Tarea tarea : tareas){
                tareaActual = tarea.encontrarTareaPorId(id);
                if(tareaActual != null){
                    break;
                }
            }
        }
        toast(tareaActual.getDescripcion(),1);
    }


    public void toast (String mensaje, int duracion){
        if (duracion == 1){
            Toast.makeText(MainActivity.this,mensaje,Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(MainActivity.this,mensaje,Toast.LENGTH_SHORT).show();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //replaces the default 'Back' button action
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (tareaActual != null) {
                Integer tareaPadre = tareaActual.getId(); //recuperamos el id de la tarea padre y ya hacemos las mismas funciones que onClick

                buscarTarea(tareaPadre);
                pintarBotones(tareaActual);
            }
        }
        return true;
    }


    @Override
    public void onClick(View view) {
         Button boton = (Button) view;
         int id = (Integer) boton.getTag();

        buscarTarea(id);
        pintarBotones(tareaActual);//buscada previamente por buscarTarea(id), al ser global.
    }


}
