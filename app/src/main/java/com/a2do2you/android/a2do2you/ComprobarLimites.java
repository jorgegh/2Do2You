package com.a2do2you.android.a2do2you;

/**
 * Created by Jorge on 13/08/2017.
 */

public class ComprobarLimites {

    public static final int CENTRO = 0;
    public static final int SUPERIOR_IZQUIERDO = 1;
    public static final int SUPERIOR_DERECHO = 2;
    public static final int INFERIOR_IZQUIERDO = 3;
    public static final int INFERIOR_DERECHO = 4;
    public static final int FUERA = 5;

    private static final double MARGEN_ERROR = 15;


    public static boolean dentro(double x, double y, double xCirculo, double yCirculo, double radio) {
        boolean resultado = false;
        if (Math.sqrt(Math.pow((xCirculo - x), 2) + (Math.pow((yCirculo - y), 2))) < radio - MARGEN_ERROR) {
            resultado = true;
        }
        return resultado;
    }

    public static boolean fuera(double x, double y, double xCirculo, double yCirculo, double radio) {
        boolean resultado = false;
        if (Math.sqrt(Math.pow((xCirculo - x), 2) + (Math.pow((yCirculo - y), 2))) > radio + MARGEN_ERROR) {
            resultado = true;
        }
        return resultado;
    }

    public static boolean borde(double x, double y, double xCirculo, double yCirculo, double radio) {
        boolean resultado = false;
        double resultadoOperacion = Math.sqrt(Math.pow((xCirculo - x), 2) + (Math.pow((yCirculo - y), 2)));
        if (resultadoOperacion <= radio + MARGEN_ERROR && resultadoOperacion >= radio - MARGEN_ERROR) {
            resultado = true;
        }
        return resultado;


    }

    public static boolean dentroCirculo(double x, double y, double xCirculo, double yCirculo, double radio) {
        boolean resultado = false;
        if (Math.sqrt(Math.pow((xCirculo - x), 2) + (Math.pow((yCirculo - y), 2))) < radio) {
            resultado = true;
        }
        return resultado;
    }

    public static boolean fueraCirculo(double x, double y, double xCirculo, double yCirculo, double radio) {
        boolean resultado = false;
        if (Math.sqrt(Math.pow((xCirculo - x), 2) + (Math.pow((yCirculo - y), 2))) > radio) {
            resultado = true;
        }
        return resultado;
    }

    public static boolean bordeCirculo(double x, double y, double xCirculo, double yCirculo, double radio) {
        boolean resultado = false;
        double resultadoOperacion = Math.sqrt(Math.pow((xCirculo - x), 2) + (Math.pow((yCirculo - y), 2)));
        if (resultadoOperacion <= radio && resultadoOperacion >= radio) {
            resultado = true;
        }
        return resultado;


    }

    public static boolean colisionCirculo(Circulo c1, Circulo c2) {
        /*boolean resultado=false;
        double resultadoOperacion = Math.sqrt(Math.pow((c1.getX() - c2.getX()), 2) + (Math.pow((c1.getY() - c2.getY()), 2)));
        if (resultadoOperacion < c1.getRadius() || (resultadoOperacion <= c1.getRadius() && resultadoOperacion >= c1.getRadius())) {
            resultado = true;
        }
        return resultado*/
        ;
        double xDif = c1.getX() - c2.getX();
        double yDif = c1.getY() - c2.getY();
        double distanceSquared = xDif * xDif + yDif * yDif;
        boolean collision = distanceSquared < (c1.getRadius() + c2.getRadius()) * (c1.getRadius() + c2.getRadius());
        return collision;


    }

    public static boolean entreCirculos(Circulo c1, Circulo cMayor, Circulo cMenor, Circulo cPrincipal) {
        boolean resultado = false;
        /*if((fueraCirculo(c1.getX(),c1.getY(),cMenor.getX(),cMenor.getY(),cMenor.getRadius())) && !colisionCirculo(c1,cMenor)
                && (dentroCirculo(c1.getX(),c1.getY(),cMayor.getX(),cMayor.getY(),cMayor.getRadius())) && colisionCirculo(c1,cMayor)) {
            resultado = true;
        }*/
        if (!colisionCirculo(c1, cMenor) && colisionDentroCirculo(cMayor, c1)) {
            resultado = true;
        }
        return resultado;
    }

    public static boolean puntoEntreCirculos(float x, float y, Circulo cMenor, Circulo cMayor) {
        boolean resultado = false;
        /*if((fueraCirculo(c1.getX(),c1.getY(),cMenor.getX(),cMenor.getY(),cMenor.getRadius())) && !colisionCirculo(c1,cMenor)
                && (dentroCirculo(c1.getX(),c1.getY(),cMayor.getX(),cMayor.getY(),cMayor.getRadius())) && colisionCirculo(c1,cMayor)) {
            resultado = true;
        }*/
        if (fueraCirculo(x, y, cMenor.getX(), cMenor.getY(), cMenor.getRadius()) && dentroCirculo(x, y, cMayor.getX(), cMayor.getY(), cMayor.getRadius())) {
            resultado = true;
        }
        return resultado;
    }

    public static boolean colisionDentroCirculo(Circulo c1, Circulo c2) {
        boolean resultado = false;
        double distance = Math.sqrt((c1.getX() - c2.getX()) * (c1.getX() - c2.getX()) + (c1.getY() - c2.getY()) * (c1.getY() - c2.getY()));
        if (distance <= Math.abs(c1.getRadius() - c2.getRadius())) {
            resultado = true;
        }
        // Inside
        return resultado;
    }

    public static int obtenerZonaPunto(double x, double y, double xCirculo, double yCirculo, double radio) {

        int ladoResultado;

        if (x == xCirculo && y == yCirculo) {
            ladoResultado = CENTRO;
        } else {
            if (x > xCirculo && y > yCirculo) {
                ladoResultado = INFERIOR_DERECHO;
            } else {
                if (x < xCirculo && y > yCirculo) {
                    ladoResultado = INFERIOR_IZQUIERDO;
                } else {
                    if (x > xCirculo && y < yCirculo) {
                        ladoResultado = SUPERIOR_DERECHO;
                    } else {

                        ladoResultado = SUPERIOR_IZQUIERDO;

                    }
                }
            }

        }


        return ladoResultado;
    }

    public static Punto obtenerPuntoValido(float x, float y, Circulo c) {
        System.out.println("Obteniendo punto valido...");
        int resultado = obtenerZonaPunto(x, y, c.getX(), c.getY(), c.getRadius());

        switch (resultado) {
            case SUPERIOR_DERECHO:
                System.out.println("Superior derecho");
                break;
            case SUPERIOR_IZQUIERDO:
                System.out.println("Superior izquierdo");
                break;
            case INFERIOR_DERECHO:
                System.out.println("Inferior derecho");
                break;
            case INFERIOR_IZQUIERDO:
                System.out.println("Inferior izquierdo");
                break;
        }

        boolean esBorde = bordeCirculo(x, y, c.getX(), c.getY(), c.getRadius());
        Punto punto = new Punto();

        if (esBorde) {
            punto.setX(x);
            punto.setY(y);
        } else {
            double xOriginal = x;
            double yOriginal = y;

            int tendenciaY = 0;
            int tendenciaX = 0;

            if (resultado == INFERIOR_DERECHO) {
                tendenciaY = -1;
                tendenciaX = -1;
            } else {
                if (resultado == INFERIOR_IZQUIERDO) {
                    tendenciaY = -1;
                    tendenciaX = 1;
                } else {
                    if (resultado == SUPERIOR_DERECHO) {
                        tendenciaY = 1;
                        tendenciaX = -1;
                    } else {
                        tendenciaY = 1;
                        tendenciaX = 1;
                    }
                }
            }
            boolean bordeEncontrado = false;
            while (!bordeEncontrado) {
                x += tendenciaX;
                y += tendenciaY;
                System.out.println("Probando con " + x + " : " + y);
                if (borde(x, y, c.getX(), c.getY(), c.getRadius())) {
                    bordeEncontrado = true;
                    punto.setX(x);
                    punto.setY(y);
                    System.out.println("Punto valido encontrado " + x + " : " + y);
                }
            }
        }


        return punto;
    }

    public static double getAngle(Punto punto, Circulo circulo) {
        double deltaX = punto.getX() - circulo.getX();
        double deltaY = punto.getY() - circulo.getY();
        return Math.toDegrees(Math.atan2(deltaY, deltaX)); // In radians
    }

    /*public static Punto getPointFromAngle(Circulo circulo,float angle){
        float x = (float)(circulo.getX() + (circulo.getRadius() * Math.cos(angle)));
        float y = (float)(circulo.getY() + (circulo.getRadius() * Math.sin(angle)));

        return new Punto(x,y);
    }*/

    public static Punto getPointOnCircle(float degress, Circulo circulo) {

        float x = circulo.getX();
        float y = circulo.getY();

        double rads = Math.toRadians(degress); // 0 becomes the top

        // Calculate the outter point of the line
        int xPosy = Math.round((float) (x + Math.cos(rads) * circulo.getRadius()));
        int yPosy = Math.round((float) (y + Math.sin(rads) * circulo.getRadius()));

        return new Punto(xPosy, yPosy);

    }
}

