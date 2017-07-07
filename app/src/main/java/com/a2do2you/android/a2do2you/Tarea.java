package com.a2do2you.android.a2do2you;

import java.security.Timestamp;
import java.util.HashMap;

/**
 * Created by Jorge on 04/06/2017.
 */

public class Tarea {

    private int id;
    private String titulo;
    private String descripcion;
    private int estado;
    private int tipo;
    private String timestamp;
    private HashMap<Integer,Tarea> subtareas;


    public Tarea(){
        this.id = 0;
        this.titulo = "";
        this.descripcion = "";
        this.estado = 0;
        this.tipo = 0;
        this.subtareas = new HashMap<Integer,Tarea>();
        this.timestamp = "";
    }

    public Tarea(int id, String titulo, String descripcion, int estado, int tipo, int subtarea, String timestamp) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.tipo = tipo;
        this.timestamp = timestamp;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public HashMap<Integer,Tarea> getSubtarea() {
        return subtareas;
    }

    public void setSubtarea(HashMap<Integer,Tarea> subtarea) {
        this.subtareas = subtarea;
    }

    public void addSubtarea(Tarea tarea){
        if(this.subtareas != null){
            this.subtareas.put(tarea.getId(),tarea);
        }
    }

    public Tarea encontrarTareaPorId(Integer idTarea){
        /*Llamo con un id. Si ese id = id de una tarea. Si el Hash Map de subtareas de esa tarea(id)
        */

        Tarea tareaADevolver = null;
        if(subtareas != null && !subtareas.isEmpty()){
            if(subtareas.containsKey(idTarea)){
                tareaADevolver = subtareas.get(idTarea);
            }
            else {
                for(Integer id:subtareas.keySet()){
                    tareaADevolver = subtareas.get(id).encontrarTareaPorId(idTarea);
                    if(tareaADevolver != null){
                        break;
                    }
                }
            }

        }
        return tareaADevolver;
    }

    public Tarea encontrarPadre(Integer idTarea){
        Tarea tareaADevolver = null;

        if(subtareas  != null && !subtareas.isEmpty()){
            if(subtareas.containsKey(idTarea)){
                tareaADevolver = this;
            }else{
                 for(Integer id : subtareas.keySet()){
                     tareaADevolver = subtareas.get(id).encontrarPadre(idTarea);
                     if(tareaADevolver != null){
                         break;
                     }
                 }
            }
        }
        return tareaADevolver;
    }

}
