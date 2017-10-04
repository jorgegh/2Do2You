package com.a2do2you.android.a2do2you;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeMap;

/**
 * Created by Jorge on 11/07/2017.
 */

public class ManejadorVista extends View implements View.OnTouchListener {

    private static float DEFAULT_RADIUS;

    private Circulo circuloPrincipal;

    private Circulo circuloMayor;
    private Circulo circuloMenor;
    private Circulo circulo;

    private Circulo circuloTareaPrincipal;


    private TreeMap<Integer, Circulo> circulos;
    private MainActivity controlador;
    private AttributeSet atributos;
    private int screenWidth;
    private int screenHeight;

    private float x;
    private float y;

    private float xActualUp;
    private float yActualUp;

    private float xDown;
    private float yDown;

    private float xMove;
    private float yMove;
    private float xMove2;

    private double currentAngle;
    private int currentColor;

    private Integer claveCirculo;
    private Random random;
    private int radio;
    private boolean primeraVez;


    private double anguloAnimation;
    private volatile boolean animationRunning;

    private boolean longPress;
    private boolean noPress;

    private boolean izquierda;

    private int velocidadAnimation;
    private int velocidadAnimacionOriginal;

    private double tiempoInicial;
    private double tiempoFinal;

    private Circulo circuloDedo;

    private Integer cuadranteActual, cuadranteAnterior;

    private MaskFilter mEmboss;
    private int contador = 0;


    /*Elementos que debemos recibir de la vista*/
    private Tarea tareaPrincipalControlador;
    private HashMap<Integer, Tarea> subtareasControlador;
    private ArrayList<Tarea> tareas;


    public ManejadorVista(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.controlador = (MainActivity) context;
        this.atributos = attrs;
        this.setOnTouchListener(this);
        circulos = new TreeMap<Integer, Circulo>();
        random = new Random();
        radio = 0;
        primeraVez = true;
        getScreenMeasures();
        this.circulo = new Circulo();
        animationRunning = false;
        cuadranteAnterior = 0;


    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paintMainCircle(canvas);

        if (circulos != null && !circulos.isEmpty()) {
            paintCircles(canvas);
        }
    }

    public void paintCircle(Circulo circulo, Canvas canvas) {
        Random random = new Random();
        Paint paint = new Paint();
        paint.setColor(circulo.getColor());
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.FILL);


        EmbossMaskFilter paintEmboss = new EmbossMaskFilter(new float[]{1, 1, 1}, 0.1f, 8f, 5f);
        paint.setMaskFilter(paintEmboss);


        paint.setShadowLayer(30, 30, 30, Color.GRAY);
        paint.setMaskFilter(null);

        paint.clearShadowLayer();
        paint.setMaskFilter(paintEmboss);
        //paint.setMaskFilter(new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL));


        paint.setAntiAlias(true);

        canvas.drawCircle((float) circulo.getX(), (float) circulo.getY(), (float) circulo.getRadius(), paint);

        Paint paint2 = new Paint();
        paint2.setColor(Color.BLACK);
        paint2.setTextSize(50);
        contador += 1;
        canvas.drawText(circulo.getTarea().getTitulo(), (float) circulo.getX(), (float) circulo.getY(), paint2);
    }

    public void paintCircles(Canvas canvas) {
        for (Integer id_circulo : circulos.keySet()) {
            paintCircle(circulos.get(id_circulo), canvas);
        }
    }

    public void paintMainCircle(Canvas canvas) {
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.MULTIPLY);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        circuloPrincipal = new Circulo();
        circuloPrincipal.setX(this.screenWidth / 2);
        circuloPrincipal.setY(this.screenHeight / 2);
        circuloPrincipal.setRadius(this.screenWidth / 3.25f);
        canvas.drawCircle((float) circuloPrincipal.getX(), (float) circuloPrincipal.getY(), (float) circuloPrincipal.getRadius(), paint);
        DEFAULT_RADIUS = circuloPrincipal.getRadius() / 3 - 7.5f;
        paint.setColor(Color.TRANSPARENT);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        circuloMayor = new Circulo();
        circuloMayor.setX(this.screenWidth / 2);
        circuloMayor.setY(this.screenHeight / 2);
        circuloMayor.setRadius((this.screenWidth / 2.5f) + (this.circuloPrincipal.getRadius() / 4));
        canvas.drawCircle((float) circuloMayor.getX(), (float) circuloMayor.getY(), (float) circuloMayor.getRadius(), paint);
        paint.setColor(Color.TRANSPARENT);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        circuloMenor = new Circulo();
        circuloMenor.setX(this.screenWidth / 2);
        circuloMenor.setY(this.screenHeight / 2);
        circuloMenor.setRadius((this.screenWidth / 3.25f) - (this.circuloPrincipal.getRadius() / 4));
        canvas.drawCircle((float) circuloMenor.getX(), (float) circuloMenor.getY(), (float) circuloMenor.getRadius(), paint);
        if (tareaPrincipalControlador != null) {
            if (circuloTareaPrincipal == null) {
                circuloTareaPrincipal = new Circulo();
                circuloTareaPrincipal.setX(this.screenWidth / 2);
                circuloTareaPrincipal.setY(this.screenHeight / 2);
                circuloTareaPrincipal.setRadius(DEFAULT_RADIUS);
                circuloTareaPrincipal.setTarea(tareaPrincipalControlador);
            }
            paintCircle(circuloTareaPrincipal, canvas);
        }


    }

    public void getScreenMeasures() {
        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) controlador).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        this.screenHeight = metrics.heightPixels;
        this.screenWidth = metrics.widthPixels;
        getMeasures();
    }

    public void getMeasures() {
        Toast.makeText(controlador, "Width : " + this.screenWidth + " : Height : " + this.screenHeight, Toast.LENGTH_SHORT).show();
    }

    public Circulo getCirculo() {
        return circuloPrincipal;
    }

    public void setCirculo(Circulo circulo) {
        this.circuloPrincipal = circulo;
    }

    public Circulo getCirculoPrincipal() {
        return circuloPrincipal;
    }

    public void setCirculoPrincipal(Circulo circuloPrincipal) {
        this.circuloPrincipal = circuloPrincipal;
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        x = motionEvent.getX();
        y = motionEvent.getY();

        Circulo c = new Circulo();
        c.setX(x);
        c.setY(y);
        radio = (int) DEFAULT_RADIUS;
        c.setRadius(radio);
        boolean flagDentro = false;
        boolean flagColision = false;

        if (!animationRunning) {

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    claveCirculo = null;
                    xDown = x;
                    yDown = y;

                    circuloDedo = new Circulo();
                    circuloDedo.setX(x);
                    circuloDedo.setY(y);
                    circuloDedo.setRadius(radio - 70); //pendiente


                    if (ComprobarLimites.puntoEntreCirculos(x, y, circuloMenor, circuloMayor)) { //Asegurarse de que no hay un círculo dónde has tocado

                        if (circulos != null && !circulos.isEmpty()) {
                            for (Integer id_circulo : circulos.keySet()) {
                                Circulo circulo = circulos.get(id_circulo);
                                if (ComprobarLimites.dentroCirculo(x, y, circulo.getX(), circulo.getY(), circulo.getRadius())) { //estás dentro de un círculo
                                    flagDentro = true;
                                    /*Lanzamos el thread de longPress*/
                                    longPress = true;
                                    noPress = false;
                                    claveCirculo = id_circulo;
                                    onShortPress();
                                    /******************/
                                    xActualUp = circulo.getX();
                                    yActualUp = circulo.getY();
                                    currentAngle = ComprobarLimites.getAngle(new Punto(xActualUp, yActualUp), circuloPrincipal);
                                    currentColor = circulo.getColor();
                                    break;
                                }

                            }

                        }
                        if (!flagDentro) {
                            /*Punto punto = ComprobarLimites.obtenerPuntoValido(x, y, circuloPrincipal);
                            c.setX(punto.getX());
                            c.setY(punto.getY());*/
                            c.setX(x);
                            c.setY(y);

                            for (Integer id_circulo : circulos.keySet()) {
                                Circulo circulo = circulos.get(id_circulo);
                                if (!id_circulo.equals(claveCirculo)) {
                                    if (ComprobarLimites.colisionCirculo(circulos.get(id_circulo), c)) {
                                        flagColision = true;
                                        break;
                                    }
                                }
                            }
                            if (!flagColision) {
                                controlador.llamadainsertarTarea(tareaPrincipalControlador);

                            } else {
                                Toast.makeText(controlador, "No puedes poner un circulo que invada a otro", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            if (ComprobarLimites.fuera(x, y, circuloPrincipal.getX(), circuloPrincipal.getY(), circuloMayor.getRadius())) {
                                tiempoInicial = System.currentTimeMillis();
                            }
                        }

                    }
                    return true;

                case MotionEvent.ACTION_MOVE:
                    //   longPress = false;
                    //   noPress = true;
                    //Toast.makeText(context,"Move",Toast.LENGTH_SHORT).show();

                    //if(ComprobarLimites.entreCirculos(c,circuloMayor,circuloMenor,circuloPrincipal)){
                    //Toast.makeText(context,"Move X:" + x + " Y :" + y  ,Toast.LENGTH_SHORT).show();
                 /*   if (claveCirculo != null) {
                        if (circulos != null && !circulos.isEmpty()) {

                            // if(!flagColision){
                            Circulo circulo = new Circulo(); //circulo nuevo
                            circulo.setColor(circulos.get(claveCirculo).getColor()); //recogemos el color

                            //double  angulo = ComprobarLimites.getAngle(new Punto(circuloAmover.getX(),circuloAmover.getY()),circuloPrincipal);
                            double angulo2 = ComprobarLimites.getAngle(new Punto(x, y), circuloPrincipal);
                            Punto punto = ComprobarLimites.getPointOnCircle((float) (angulo2), circuloPrincipal);

                            circulo.setX(punto.getX());
                            circulo.setY(punto.getY());
                            circulo.setRadius(radio);

                          for (Integer id_circulo : circulos.keySet()) {
                                Circulo circuloActual = circulos.get(id_circulo);
                                if (!id_circulo.equals(claveCirculo)) {
                                    if (ComprobarLimites.colisionCirculo(circulo, circuloActual)) {
                                        flagColision = true;
                                        break;
                                    }
                                }
                            }
                            int colorCirculo = currentColor;

                            if(flagColision){
                                colorCirculo = getColorWithAlpha(colorCirculo,50,true);
                            }
                            else {
                                colorCirculo = getColorWithAlpha(colorCirculo,75,false);
                            }
                            System.out.println("Se devuelve el color " + colorCirculo);
                            circulo.setColor(colorCirculo);
                            circulos.put(claveCirculo, circulo);

                            //Toast.makeText(context,"Se mueve un circulo con id " + claveCirculo,Toast.LENGTH_SHORT).show();
                            //System.out.println(circulo.toString());
                            invalidate();
                            //   }


                        }

                    } else {*/
                    if (!limiteCirculo(circuloDedo, new Punto(x, y))) {
                        longPress = false;
                        if (ComprobarLimites.puntoEntreCirculos(x, y, circuloMenor, circuloMayor)) {
                            float grados = ((screenWidth / 360)) * 3;
                            int tendencia = 0;

                            if (xMove > x) {
                                izquierda = true;
                                System.out.println("izquierda");
                            } else {
                                System.out.println("derecha");
                                izquierda = false;
                            } //derecha } */

                            for (Integer id_circulo : circulos.keySet()) {
                                Circulo c1 = circulos.get(id_circulo);
                                double angulo = ComprobarLimites.getAngle(new Punto(c1.getX(), c1.getY()), circuloPrincipal);
                                //condición porque va de 180 a 0, y de -180 a 0.

                                int cuadrante = ComprobarLimites.obtenerZonaPunto(c1.getX(), c1.getY(), circuloPrincipal.getX(), circuloPrincipal.getY(), circuloPrincipal.getRadius());

                                if (izquierda && (y >= circuloPrincipal.getY())) { //vayamos a la izquierda y estemos por debajo de la mitad
                                    tendencia = 1;
                                    System.out.println("1");
                                } else {
                                    if (izquierda && (y < circuloPrincipal.getY())) {
                                        tendencia = -1;
                                        System.out.println("2");
                                    } else {
                                        if (!izquierda && (y >= circuloPrincipal.getY())) {
                                            tendencia = -1;
                                            System.out.println("3");
                                        } else {
                                            tendencia = 1;
                                            System.out.println("4");
                                        }
                                    }
                                }

                                double operacion = (angulo + (grados * tendencia));
                                double operacionCambiada = operacion;

                                Punto punto = ComprobarLimites.getPointOnCircle((float) operacionCambiada, circuloPrincipal);


                                c1.setX(punto.getX());
                                c1.setY(punto.getY());
                                circulos.put(id_circulo, c1);
                                xMove = x;
                                yMove = y;
                                invalidate();
                            }
                            circuloDedo.setX(x);
                            circuloDedo.setY(y);
                        }
                    }
                    return true;

                case MotionEvent.ACTION_UP:
                    longPress = false;
                    circuloDedo = null;
                    tiempoFinal = System.currentTimeMillis();

                    if ((xDown > x + screenWidth * 0.2 && (tiempoFinal > tiempoInicial + 250))) {
                        velocidadAnimation = (Math.round(xDown) - Math.round(x));
                        System.out.println("xDown --> " + xDown + " x --> " + x + " Porcentaje pantalla " + (screenWidth * 0.2) + " velocidadAnimation --> " + velocidadAnimation);

                        if (velocidadAnimation < 0) {
                            velocidadAnimation = Math.round(xDown) - Math.round(x);
                        }
                        System.out.println("Animacion!");
                        System.out.println("velocidaAnimacion final --> " + velocidadAnimation);
                        makeAnimation();
                    } else {
                        noPress = true;
                    }
                /*

                    if (claveCirculo != null && circulo != null && circulos != null && !circulos.isEmpty()) {
                        //Le devolvemos el color al circulo sin alpha
                        Circulo circuloColor = circulos.get(claveCirculo);
                        circuloColor.setColor(currentColor);
                        circulos.put(claveCirculo,circuloColor);
                        invalidate();
                        circulo = new Circulo(); //circulo nuevo
                        circulo.setColor(circulos.get(claveCirculo).getColor()); //recogemos el color

                        //double  angulo = ComprobarLimites.getAngle(new Punto(circuloAmover.getX(),circuloAmover.getY()),circuloPrincipal);
                        double angulo2 = ComprobarLimites.getAngle(new Punto(x, y), circuloPrincipal);
                        Punto punto = ComprobarLimites.getPointOnCircle((float) (angulo2), circuloPrincipal);

                        circulo.setX(punto.getX());
                        circulo.setY(punto.getY());
                        circulo.setRadius(radio);

                        for (Integer id_circulo : circulos.keySet()) {
                            Circulo circuloActual = circulos.get(id_circulo);
                            if (!id_circulo.equals(claveCirculo)) {
                                if (ComprobarLimites.colisionCirculo(circulo, circuloActual)) {
                                    flagColision = true;
                                    break;
                                }
                            }
                        }

                        if (flagColision) {
                            System.out.println("Colosion");
                            makeAnimation();

                            invalidate();
                        }
                        return true;
                    }*/

                default:
                    return false;
            }
        }
        return false;
    }

    public void makeAnimation() {
        AnimationTask animation;
        animation = new AnimationTask();
        animation.execute();
    }

    public void onLongPress() {
        OnLongPressTask longPress;
        longPress = new OnLongPressTask();
        longPress.execute();
    }

    public void onShortPress() {
        OnShortPressTask shortPress;
        shortPress = new OnShortPressTask();
        shortPress.execute();
    }

    public void crearYPintarCirculos(int points, float radius, float xCirculo, float yCirculo, Canvas canvas) {

        //double slice = 2 * Math.PI * points;
        double slice = 360 / points;
        for (int i = 0; i < points; i++) {
            double angle = slice * i;
            float xPos = (float) radius * (float) Math.cos(Math.toRadians(angle)) + xCirculo;
            float yPos = (float) radius * (float) Math.sin(Math.toRadians(angle)) + yCirculo;
            Circulo circulo = new Circulo();
            circulo.setX(xPos);
            circulo.setY(yPos);
            circulo.setRadius((float) DEFAULT_RADIUS);
            circulos.put(circulos.size(), circulo);
            paintCircle(circulo, canvas);
        }
    }

    public void changeCircleId(boolean derecha, float angulo) {
        TreeMap<Integer, Circulo> copia;
        System.out.println("Entro cambio id circulos");
        int tendenciaId = 0;
        if (derecha) {
            tendenciaId = 1;
        } else {
            tendenciaId = -1;
        }
        if (circulos != null && !circulos.isEmpty()) {
            copia = copiaMap(circulos);
            for (Integer id_circulo : circulos.keySet()) {
                Circulo circuloActual = circulos.get(id_circulo);
                int idCalculado = id_circulo + tendenciaId;
                if (idCalculado == circulos.size()) {
                    idCalculado = 0;
                } else {
                    if (idCalculado < 0) {
                        idCalculado = circulos.size() - 1;
                    }
                }


                Circulo circuloSiguiente = copia.get(idCalculado);
                System.out.println("Circulo 1 X: " + circuloActual.getX() + " Y: " + circuloActual.getY());
                System.out.println("Circulo 2 X: " + circuloSiguiente.getX() + " Y: " + circuloSiguiente.getY());
                circuloSiguiente.setX(circuloActual.getX());
                circuloSiguiente.setY(circuloActual.getY());

                System.out.println("El id " + id_circulo + " se queda con el id " + idCalculado + " y las coordenadas " + circuloSiguiente.getX() + " : " + circuloSiguiente.getY());
                copia.put(idCalculado, circuloSiguiente);


            }
            circulos = copia;
            invalidate();
        }
    }

    public TreeMap<Integer, Circulo> copiaMap(TreeMap<Integer, Circulo> mapaACopiar) {
        TreeMap<Integer, Circulo> copia = new TreeMap<Integer, Circulo>();
        for (Integer id : mapaACopiar.keySet()) {
            Circulo circulo = null;
            try {
                circulo = (Circulo) mapaACopiar.get(id).clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            copia.put(id, circulo);
        }
        return copia;
    }

    public int getColorWithAlpha(int color, int alpha, boolean redFilter) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        if (redFilter) {
            red = 255;
            green = 0;
            blue = 0;
        }

        return Color.argb(alpha, red, green, blue);
    }

    public int getColorWithoutAlpha(int color) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.rgb(red, green, blue);
    }

    private void reajustarIds() {
        TreeMap<Integer, Circulo> copia = copiaMap(circulos);
        circulos.clear();
        for (Integer id : copia.keySet()) {
            Circulo circulo = copia.get(id);
            circulos.put(circulos.size(), circulo);
        }

    }

    private boolean limiteCirculo(Circulo circulo, Punto punto) {
        boolean resultado = false;

        if (ComprobarLimites.dentroCirculo(punto.getX(), punto.getY(), circulo.getX(), circulo.getY(), circulo.getRadius())) {
            resultado = true;
        }
        return resultado;
    } //devuelve true si está dentro del círculo. Sino, devuelve false;

    public void actualizarVistaTareaPrincipal(ArrayList<Tarea> tareas) {
        circuloTareaPrincipal = null;
        this.subtareasControlador = null;
        this.tareas = tareas;
        tareaPrincipalControlador = null;
        introducirValores();
    }

    public void actualizarVistaSubtareas(Tarea tareaPrincipalControlador, HashMap<Integer, Tarea> subtareasControlador) {
        circuloTareaPrincipal = null;
        this.tareaPrincipalControlador = tareaPrincipalControlador;
        this.tareas = null;
        this.subtareasControlador = subtareasControlador;
        introducirValores();

    }

    public void solicitarHijos(Integer idTarea) {
        controlador.buscarTarea(idTarea);
    }

    private void introducirValores() {
        circulos.clear();
        float radius = (this.screenWidth / 3.25f) / 3 - 7.5f;
        if (tareas != null) {
            double slice = 360 / tareas.size();
            int indice = 0;

            for (Tarea tarea : tareas) {
                double angle = slice * indice;
                float xPos = (float) this.screenWidth / 3.25f * (float) Math.cos(Math.toRadians(angle)) + screenWidth / 2;
                float yPos = (float) this.screenWidth / 3.25f * (float) Math.sin(Math.toRadians(angle)) + screenHeight / 2;
                Circulo circulo = new Circulo();
                circulo.setX(xPos);
                circulo.setY(yPos);
                circulo.setRadius(radius);
                circulo.setTarea(tarea);
                circulos.put(tarea.getId(), circulo);
                indice++;
            }
        } else {
            double slice = 360 / subtareasControlador.size();
            int indice = 0;
            for (Integer id : subtareasControlador.keySet()) {
                double angle = slice * indice;
                float xPos = (float) this.screenWidth / 3.25f * (float) Math.cos(Math.toRadians(angle)) + screenWidth / 2;
                float yPos = (float) this.screenWidth / 3.25f * (float) Math.sin(Math.toRadians(angle)) + screenHeight / 2;
                Circulo circulo = new Circulo();
                circulo.setX(xPos);
                circulo.setY(yPos);
                circulo.setRadius(radius);
                circulo.setTarea(subtareasControlador.get(id));
                circulos.put(subtareasControlador.get(id).getId(), circulo);
                indice++;
            }
        }
        invalidate();

    }

    public void insertarTarea(String title,String description,boolean correcto){

        if(correcto){

            Circulo c = new Circulo();
            c.setX(x);
            c.setY(y);
            c.setRadius(DEFAULT_RADIUS);

            Tarea tarea = new Tarea();
            tarea.setTitulo(title);
            tarea.setDescripcion(description);
            c.setTarea(tarea);
            circulos.put(circulos.size(), c);
            currentColor = circulos.get(circulos.size() - 1).getColor();
            //Toast.makeText(context,"Se inserta in circulo con id " + String.valueOf(circulos.size()),Toast.LENGTH_SHORT).show();
            invalidate();
        }else{
            Toast.makeText(controlador,"Tarea no Insertada",Toast.LENGTH_SHORT).show();
        }
    }

    private class AnimationTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            /*final int velocidad = 1;
            animationRunning = true;
            do {
                System.out.println("Se llama a makeAnimation");
                anguloAnimation = ComprobarLimites.getAngle(new Punto(circulo.getX(), circulo.getY()), circuloPrincipal);
                Punto punto = ComprobarLimites.getPointOnCircle((float) (Math.round(anguloAnimation) - velocidad), circuloPrincipal);

                //System.out.println("Velocidad :" + velocidad);
                circulo.setX(punto.getX());
                circulo.setY(punto.getY());
                //System.out.println("Clave Circulo--> " + claveCirculo);
                circulos.put(claveCirculo, circulo);
                try {
                    Thread.currentThread().sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress();
                //System.out.println("CurrentAngle --> " + currentAngle + " angulo2--> " + anguloAnimation);
            }while((int)currentAngle != (int)anguloAnimation);*/

            animationRunning = true;
            velocidadAnimacionOriginal = velocidadAnimation;
            tiempoInicial = System.currentTimeMillis();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println("actual --> " +tiempoInicial +"tiempoFinal --> " +(tiempoFinal + 2000));
                    while (tiempoInicial > (tiempoFinal + 2000)) { // resta el ángulo que va a mover.
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();

            while (tiempoInicial > (tiempoFinal + 2000)) {

                try {
                    int operacion =+ 10;

                    Thread.currentThread().sleep(operacion); //La animación sea más fluida y menos fluida. antes de que pinte, se duerme
                    System.out.println("OPeracion --> " +operacion + " velocidad --> " +velocidadAnimation);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                for (Integer id_circulo : circulos.keySet()) {
                    Circulo c1 = circulos.get(id_circulo);

                    anguloAnimation = ComprobarLimites.getAngle(new Punto(c1.getX(), c1.getY()), circuloPrincipal);
                    //Punto punto = ComprobarLimites.getPointOnCircle((float) (Math.round(anguloAnimation) - velocidadAnimation), circuloPrincipal);
                    Punto punto = ComprobarLimites.getPointOnCircle(Math.round(anguloAnimation) - 5, circuloPrincipal);

                    //System.out.println("Velocidad :" + velocidad);
                    c1.setX(punto.getX());
                    c1.setY(punto.getY());
                    //System.out.println("Clave Circulo--> " + claveCirculo);
                    circulos.put(id_circulo, c1);
                    publishProgress();

                }
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            invalidate();
            animationRunning = false;
            claveCirculo = null;
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            invalidate();
        }
    }

    private class OnLongPressTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                Thread.currentThread().sleep(800);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {
            if (longPress) {
                if (claveCirculo != null) {
                    /* circulos.remove(claveCirculo);
                     claveCirculo = null;
                     reajustarIds();
                     invalidate();*/
                    Toast.makeText(ManejadorVista.this.controlador, circulos.get(claveCirculo).getTarea().getDescripcion(), Toast.LENGTH_SHORT).show();

                }

            }
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            invalidate();
        }
    }

    private class OnShortPressTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                Thread.currentThread().sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "Executed";
        }

        @Override
        protected void onPostExecute(String result) {

            if (!longPress && noPress) {
                Toast.makeText(ManejadorVista.this.controlador, "Toque corto", Toast.LENGTH_SHORT).show();
                //Toast.makeText(ManejadorVista.this.controlador,"Toque corto",Toast.LENGTH_SHORT).show();
                solicitarHijos(circulos.get(claveCirculo).getTarea().getId());
            } else {
                onLongPress();
            }
        }

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            invalidate();
        }
    }


}
