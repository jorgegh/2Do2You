package com.a2do2you.android.a2do2you;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Jesus on 09/09/2017.
 */

public class FormularioInsertar extends Dialog implements View.OnClickListener {
    EditText titulo,descripcion;
    Button aceptar,cancelar;
    MainActivity mainactivity;

    public FormularioInsertar(@NonNull Context context) {
        super(context);
        mainactivity = (MainActivity) context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_task);

        titulo = (EditText)findViewById(R.id.textTitle);
        descripcion = (EditText)findViewById(R.id.textDescription);

        aceptar = (Button)findViewById(R.id.buttonAceptar);
        cancelar = (Button)findViewById(R.id.buttonCancelar);

        aceptar.setOnClickListener(this);
        cancelar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(v.getId() == aceptar.getId()){

            if(!(titulo.getText().toString().isEmpty())){
                String title = titulo.getText().toString();
                String description = descripcion.getText().toString();

                mainactivity.recogidainsertarTarea(title,description,true);

            }else{
                Toast.makeText(mainactivity,"Rellena los datos",Toast.LENGTH_SHORT).show();
            }

        }else {
                mainactivity.recogidainsertarTarea("","",false);
        }
        this.cancel();
    }
}
