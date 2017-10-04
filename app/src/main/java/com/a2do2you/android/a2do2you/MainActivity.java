package com.a2do2you.android.a2do2you;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends Activity implements View.OnClickListener {

    private static OperacionesSQLite operaciones;
    //private static Cursor cursor;
    /*private  LinearLayout l1;
    private  LinearLayout l2;*/
    private ArrayList<Tarea> tareas;
    private ManejadorDB manejador;
    private Tarea tareaActual;
    private Tarea tareaPadre;
    private Tarea tareaAuxiliar;//tarea  para solucionar proble keydown, que no machaque el valor de tarea Padre tarea Actual
    private boolean pintarTodo;
    private int idPadreInsertar;

    /*Elementos que debemos pasar a la vista*/
    private Tarea tareaPrincipal;
    private HashMap<Integer, Tarea> subtareas;


    private ManejadorVista vista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        vista = (ManejadorVista) findViewById(R.id.canvas);

        manejador = new ManejadorDB(this);
        tareas = new ArrayList<Tarea>();

        try {
            //manejador.borrarTodo();
            //   manejador.insertarRegistroTest();
            inicializar();
        } catch (Exception e) {
            e.printStackTrace();
            toast(e.getMessage(), 1);
        }
    }

    public void inicializar() throws Exception {
        if (tareas != null) {
            tareas.clear();
        }
        tareas = manejador.selectAll(); //recoge todas las tareas de manejadorDB
        pintarBotones(null);
    }

    public void pintarBotones(Tarea tarea) {
        if (tarea == null) {
            vista.actualizarVistaTareaPrincipal(tareas);
        } else {
            if (!(tarea.getSubtarea().keySet().isEmpty())) {
                vista.actualizarVistaSubtareas(tareaActual, tareaActual.getSubtarea());
            } else {
                toast("esta tarea no tiene subtareas", 2);
                tareaActual = tareaAuxiliar;//restaurar el valor machacado tras la búsqueda de si tiene alguna subtarea
                llamadainsertarTarea(tarea);
            }
        }
    }

    public void buscarTarea(Integer id) {
        tareaAuxiliar = tareaActual;
        tareaActual = null;

        for (Tarea tarea : tareas) {//si es padre
            if (tarea.getId() == id) {
                tareaActual = tarea;
                if (tareaActual != null) {
                    break;
                }

            }
        }
        if (tareaActual == null) {//si es subtarea
            for (Tarea tarea : tareas) {
                tareaActual = tarea.encontrarTareaPorId(id);
                if (tareaActual != null) {
                    break;
                }
            }
        }
        pintarBotones(tareaActual);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //replaces the default 'Back' button action
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (tareaActual != null) {
                Toast.makeText(this, "Boton atras", Toast.LENGTH_SHORT).show();
                buscarPadre();
                pintarBotones(tareaPadre);
                if (tareaPadre != null) {
                    System.out.println("Tarea padre encontrada : " + tareaPadre.getId() + " " + tareaPadre.getDescripcion());
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
            pintarBotones(tareaPadre);


        } else {
            System.out.println("MainActivity.buscarPadre(): La tarea es nula y no se peude recuperar el padre");
        }
    }

    public void insertarTareaPadreId(int idPadre, Tarea t, int clave) {

        Tarea tareaPadre = null;

        for (Tarea tarea : tareas){ //busco coincidencia con la tarea del nivel del array, para checkear si está aqúi.
            if (tarea.getId() == idPadre) {
                tareaPadre = tarea;
            }
        }

        if(tareaPadre == null){//si la tarea sigue siendo nula significa que no está en el array, por lo que tendrá que ser una subtarea
            for (Tarea tarea : tareas) {
                    /*Busco el padre de la tareaActual*/
                tareaPadre = tarea.encontrarTareaPorId(idPadre);
                        /*Si lo encuentro, busco el padre del padre de la tarea actual para pintar el nivel del padre*/

                }
            }
        //vuelvo e recorrerlo porque siempre la va encontrar ya que está siempre en la vista o en el array
        for (Tarea tarea : tareas) {
            if (tareaPadre != null) {
                tareaPadre.getSubtarea().put(clave, t);
                tarea.añadirSubtareaPorId(tareaPadre);
            }
        }

}

    public void toast(String mensaje, int duracion) {
        if (duracion == 1) {
            Toast.makeText(MainActivity.this, mensaje, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(MainActivity.this, mensaje, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onClick(View view) {
        Button boton = (Button) view;
        int id = (Integer) boton.getTag();

        buscarTarea(id);
        pintarBotones(tareaActual);//buscada previamente por buscarTarea(id), al ser global.
    }

    public void llamadainsertarTarea(Tarea tarea){
        //System.out.println("llamadaInsertar tarea. ID -->" +tarea.getId() +" id padre ---> " +tarea.encontrarPadre(tarea.getId()));
        if(tareas.isEmpty()){
            idPadreInsertar = 0;
        }else{ if(tarea == null) {
            idPadreInsertar = 0;
            }else{
            idPadreInsertar = tarea.getId();
            }
        }

        FormularioInsertar form = new FormularioInsertar(this);
        form.show();
    }

    public void recogidainsertarTarea(String titulo,String descripcion,boolean correcto){
        if(correcto){
            try{
                int idtarea = manejador.insert(titulo,descripcion,idPadreInsertar);
                Tarea tarea = new Tarea();
                tarea.setTitulo(titulo);
                tarea.setDescripcion(descripcion);

                if(idPadreInsertar == 0){ //array nivel 0
                    tareas.add(tarea);
                    vista.insertarTarea(titulo, descripcion, correcto);
                }else{//Hashmap subniveles
                    insertarTareaPadreId(idPadreInsertar,tarea,idtarea);
                }

            }catch(Exception e){
                e.printStackTrace();
                correcto = false;
            }
        }
    }

}
