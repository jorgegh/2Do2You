package com.a2do2you.android.a2do2you;

/**
 * Created by Jorge on 13/08/2017.
 */

public class Punto {
    private float x;
    private float y;

    public Punto() {
        x = 0;
        y = 0;
    }

    public Punto(float x, float y) {
        this.x = x;
        this.y = y;
    }

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
}
