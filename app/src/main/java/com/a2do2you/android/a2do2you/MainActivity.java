package com.a2do2you.android.a2do2you;

import android.app.Activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;



import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;



public class MainActivity extends Activity {

    private TextView tarea;
    private TextView tareafb;

    //FIREBASE VARIABLES ************************
    private DatabaseReference dbTask;
    private ValueEventListener eventListener;
    /********************************************/

    // ANALYTICS
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        tareafb = (TextView)findViewById(R.id.lblTask);

        // RECOGER DATOS DE FIREBASE DB


      dbTask = FirebaseDatabase.getInstance().getReference().child("tasks");

        dbTask.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                TareaOnlyStrings tarea = dataSnapshot.child("1").getValue(TareaOnlyStrings.class);
                tareafb.setText(tarea.getDescripcion());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("Error Firebase", databaseError.toException().toString());
            }
        });



    }


}
