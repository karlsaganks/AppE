package com.example.appe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.appe.Jugador;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class ColorMix extends AppCompatActivity {

    private final String APP = "APP-E";
    private NumberPicker maxFil;
    private NumberPicker maxCol;
    private EditText nom;
    private NumberPicker maxToq;
    private TextView recJug;
    private ArrayList<Puntuacion> top10 = new ArrayList<Puntuacion>();
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_mix);

        maxFil = (NumberPicker) findViewById(R.id.maxfilas);
        maxCol = (NumberPicker) findViewById(R.id.maxcolumnas);
        nom = (EditText) findViewById(R.id.txtJugador);
        maxToq = (NumberPicker) findViewById(R.id.maxtoques);
        recJug = (TextView) findViewById(R.id.txtrecord) ;
    }

    @Override
    protected void onResume() {
        super.onResume();

        String top10Json = Preferencias.getJugadores(this);
        Puntuacion[] aPuntos = gson.fromJson(top10Json, Puntuacion[].class);
        top10 = new ArrayList<Puntuacion>(Arrays.asList(aPuntos));

        int nTop10 = top10.size() -1;
        Puntuacion ultimoTop = top10.get( nTop10);

        maxFil.setMinValue(2);
        maxFil.setMaxValue(99);
        maxFil.setValue( ultimoTop.getFilas());

        maxCol.setMinValue(2);
        maxCol.setMaxValue(99);
        maxCol.setValue( ultimoTop.getColumnas());

        maxToq.setMinValue(2);
        maxToq.setMaxValue(9999);
        maxToq.setValue( ultimoTop.getToques());

        nom.setText( top10.get( nTop10).getNombre());

        String record = "";
        for(int i = 0; i<= nTop10; i++) {
            record += (i+1) +" "+ top10.get(i) + "\n";
            Log.i(APP, i+": " + top10.get(i).getNombre());
        }
        recJug.setText(record);

    }

    public void toPlayMix(View view) {
        Puntuacion nuevoJuego = new Puntuacion();
        nuevoJuego.setNombre( nom.getText().toString());
        nuevoJuego.setFilas( maxFil.getValue());
        nuevoJuego.setColumnas( maxCol.getValue());
        nuevoJuego.setToques( maxToq.getValue());
        Preferencias.setJugadores(this, gson.toJson(top10));

        Intent i = new Intent(this, ColorMixPlay.class );
        i.putExtra("PLAYER", gson.toJson( nuevoJuego));
        startActivity(i);
    }

}
