package com.a2do2you.android.a2do2you;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by Jorge on 13/08/2017.
 */

public class Circulo implements Cloneable {

    private float x;
    private float y;
    private float radius;
    private int color;
    private Tarea tarea;
    private Random random;


    public Circulo() {
        random = new Random();
        color = Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    ;

    public Circulo(Circulo c) {
        this.x = c.getX();
        this.y = c.getY();
        this.radius = c.getRadius();
        this.color = c.getColor();
    }

    ;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public String toString() {
        return "Punto X = " + getX() + " Punto Y " + getY();
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Tarea getTarea() {
        return tarea;
    }

    public void setTarea(Tarea tarea) {
        this.tarea = tarea;
    }
}
