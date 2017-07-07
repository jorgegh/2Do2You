//F/FIREBASE INTEGRACION INITIAL COMMIT en BRANCH F/FirebaseIntegrationDB
package com.a2do2you.android.a2do2you;

import android.app.Activity;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.a2do2you.android.a2do2you.OperacionesSQLite;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener,View.OnTouchListener{

    private static OperacionesSQLite operaciones;
    //private static Cursor cursor;
    private  LinearLayout l1;
    private  LinearLayout l2;
    private  ArrayList<Tarea> tareas;
    private ManejadorDB manejador;
    private Tarea tareaActual;
    private Tarea tareaPadre;
    private Tarea tareaAuxiliar;//tarea  para solucionar proble keydown, que no machaque el valor de tarea Padre tarea Actual
    private boolean pintarTodo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

       /*vista = new ManejadorVista(this);
        vista.setOnTouchListener(this);

        vista.addRect(1,new Rect(500,500,200,800));
        vista.addRect(2,new Rect(150,120,300,900));*/


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //this.setContentView(vista);

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
                tareaActual = tareaAuxiliar;//restaurar el valor machacado tras la búsqueda de si tiene alguna subtarea
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
        tareaAuxiliar = tareaActual;
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

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //replaces the default 'Back' button action
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (tareaActual != null) {
                System.out.println("Tarea actual : " +tareaActual.getId() + " " + tareaActual.getDescripcion());
                buscarPadre();
                pintarBotones(tareaPadre);
                if(tareaPadre != null){
                    System.out.println("Tarea padre encontrada : " +tareaPadre.getId() + " " + tareaPadre.getDescripcion());
                }

            }
        }
        return true;
    }

    public void buscarPadre() {
        this.pintarTodo = false;
        /*Primero busco el padre en el arrayList*/
        tareaPadre = null;
        if (tareaActual != null) {
            System.out.println("MainActivity.buscarPadre(): Buscando el padre");
            for (Tarea tarea1 : tareas) {
                System.out.println("MainActivity.buscarPadre(): Comparando " + tarea1.getId() + " con " + tareaActual.getId());
                if (tarea1.getId() == tareaActual.getId()) {
                    System.out.println("MainActivity.buscarPadre(): Encontrada clave " + tarea1.getId() + " en el arrayList");
                    tareaActual = null;
                    /*Se activa pintarTodo para pintar el primer nivel entero*/
                    pintarTodo = true;
                    break;
                }
            }
            /*Si no lo encuentro, empiezo a buscarlo de forma recursiva en los hijos*/
            if (tareaPadre == null && !pintarTodo) {
                System.out.println("MainActivity.buscarPadre(): La tarea padre no se encuentra en el arrayList y la busco en los hijos de forma recursiva");
                for (Tarea tarea : tareas) {
                    /*Busco el padre de la tareaActual*/
                    tareaPadre = tarea.encontrarPadre(tareaActual.getId());
                    /*Si lo encuentro, busco el padre del padre de la tarea actual para pintar el nivel del padre*/
                    if (tareaPadre != null) {
                        tareaActual = tareaPadre;
                        break;
                    }

                }
            }

        }
        else{
            System.out.println("MainActivity.buscarPadre(): La tarea es nula y no se peude recuperar el padre");
        }
                /*if (tareaPadre != null) {
                    tareaActual = tareaPadre;
                }**/
    }


    public void toast (String mensaje, int duracion){
        if (duracion == 1){
            Toast.makeText(MainActivity.this,mensaje,Toast.LENGTH_SHORT).show();
        }
        else{
            Toast.makeText(MainActivity.this,mensaje,Toast.LENGTH_SHORT).show();
        }
    }




    @Override
    public void onClick(View view) {
        Button boton = (Button) view;
        int id = (Integer) boton.getTag();

        buscarTarea(id);
        pintarBotones(tareaActual);//buscada previamente por buscarTarea(id), al ser global.
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        /*for(Integer id:vista.getRectangulos().keySet()){
            if(vista.getRectangulos().get(id).contains((int)motionEvent.getX(),(int)motionEvent.getY())){
                Toast.makeText(MainActivity.this,"Has tocado el rectangulo " + id,Toast.LENGTH_LONG).show();
            }
        }*/


        return false;
    }
}
