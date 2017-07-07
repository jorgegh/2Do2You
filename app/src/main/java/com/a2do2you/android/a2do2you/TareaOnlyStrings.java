package com.a2do2you.android.a2do2you;

/**
 * Created by Dani on 07/07/2017.
 */

public class TareaOnlyStrings {

    private String id;
    private String titulo;
    private String descripcion;
    private String estado;
    private String tipo;
    private String timestamp;

    private String subtarea;

    public TareaOnlyStrings() {
        this.id = "0";
        this.titulo = "titulonull";
        this.descripcion = "descnull";
        this.estado = "0";
        this.tipo = "0";
        this.timestamp = "00";
        this.subtarea = "subtarea null";
    }

    public TareaOnlyStrings(String id, String titulo, String descripcion, String estado, String tipo, String timestamp, String subtarea) {

        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.estado = estado;
        this.tipo = tipo;
        this.timestamp = timestamp;
        this.subtarea = subtarea;
    }

    public String getSubtarea() {
        return subtarea;
    }

    public void setSubtarea(String subtarea) {
        this.subtarea = subtarea;
    }

    public String getTimestamp(){
        return timestamp;
    }

    public void setTimestamp(String timestamp){
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "TareaOnlyStrings{" +
                "id='" + id + '\'' +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", estado='" + estado + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
