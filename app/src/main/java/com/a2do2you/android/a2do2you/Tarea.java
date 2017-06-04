package com.a2do2you.android.a2do2you;

/**
 * Created by Jorge on 04/06/2017.
 */

public class Tarea {

    private int id;
    private String titulo;
    private String descripcion;
    private int estado;
    private int tipo;
    private int subtarea;


    public Tarea(){
        this.id = 0;
        this.titulo = "";
        this.descripcion = "";
        this.estado = 0;
        this.tipo = 0;
        this.subtarea = 0;
    }

    public Tarea(int id, String titulo, String descripcion, int estado, int tipo, int subtarea) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.tipo = tipo;
        this.subtarea = subtarea;
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

    public int getSubtarea() {
        return subtarea;
    }

    public void setSubtarea(int subtarea) {
        this.subtarea = subtarea;
    }
}
