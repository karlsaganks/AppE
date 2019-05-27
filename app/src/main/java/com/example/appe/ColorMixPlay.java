package com.example.appe;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

import static com.example.appe.Preferencias.getJugadores;

public class ColorMixPlay extends AppCompatActivity {

    private final String APP = "APP-E";
    private Tablero juego;
    private double tiempo;
    private boolean pausar;
    private LinearLayout padre;
    private Puntuacion participante;
    private Gson gson = new Gson();
    private int toke;
    private final int MAX_TOP = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_mix_play);
        this.padre = (LinearLayout) findViewById(R.id.rootcolormix);
        this.pausar = false;
        String player = getIntent().getStringExtra ("PLAYER");
        participante = gson.fromJson( player, Puntuacion.class);
        toke = participante.getToques();
        pintarTablero(participante.getFilas(), participante.getColumnas());
    }

    // Dibujar Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    // Acciones del Menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.pausa:
                padre.setVisibility( this.pausar ? View.VISIBLE : View.INVISIBLE);
                item.setIcon(this.pausar ? R.drawable.ic_pause_white_24dp : R.drawable.ic_play_arrow_white_24dp );
                this.pausar = !this.pausar;
                break;
            case R.id.salir:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void pintarTablero(int fil, int col){
        juego = new Tablero(fil, col);
        Log.i(APP, "Filas: "+ juego.getFilas() + " Elementos: "+ juego.getElementos()  );
        padre.removeAllViews();
        int filas = juego.getFilas();
        for (int i = 0; i < filas; i++){

            LinearLayout hijo = new LinearLayout(this);
            LinearLayout.LayoutParams parametrosPadre = new LinearLayout.LayoutParams( LinearLayout.LayoutParams.MATCH_PARENT, 0, 1F);
            hijo.setOrientation( LinearLayout.HORIZONTAL);
            hijo.setLayoutParams( parametrosPadre);
            hijo.setVisibility(View.VISIBLE);

            Box[] cajas = juego.getCaja().get(i);
            int cols = cajas.length;
            Log.i(APP, String.format("Fila: %d - Columnas: %d ", i+1, cols ) );
            for (int j = 0; j < cols; j++){

                LinearLayout nieto = new LinearLayout(this);
                LinearLayout.LayoutParams parametrosNieto = new LinearLayout.LayoutParams( 0, LinearLayout.LayoutParams.MATCH_PARENT, 1F);
                nieto.setId(cajas[j].getId());
                nieto.setOrientation( LinearLayout.VERTICAL);
                nieto.setLayoutParams( parametrosNieto);
                nieto.setBackgroundColor( Color.parseColor( cajas[j].getColor()));
                nieto.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        marcado((LinearLayout) v );
                    }
                });
                nieto.setVisibility(View.VISIBLE);
                hijo.addView(nieto);
            }
            padre.addView(hijo);
        }
        tiempo = System.currentTimeMillis();
    }

    public void marcado(LinearLayout v){
        Box bx = juego.getBox().get(v.getId());
        if (!bx.isUsado()){
            v.setBackgroundColor( getResources().getColor( R.color.blanco));
            bx.setUsado(true);
            toke -=1;
            juego.setTocados( juego.getTocados() +1);
            String msg = String.format(juego.getFilas() +" Filas - Tocados:  [" + juego.getTocados() + "/"+ juego.getElementos() + "] Faltan: " + toke);
            Log.i(APP, msg );
            Toast.makeText(getApplicationContext(), msg,Toast.LENGTH_SHORT).show();
            if(juego.getElementos() == juego.getTocados() || toke == 0 ) {
                participante.setTiempo( (System.currentTimeMillis() - tiempo) / 1000d);

                String top10Json = Preferencias.getJugadores(this);
                Puntuacion[] aPuntos = gson.fromJson(top10Json, Puntuacion[].class);
                ArrayList<Puntuacion> top10 = new ArrayList<Puntuacion>(Arrays.asList(aPuntos));
                top10.add(participante);
                Puntuacion.toTop(top10, MAX_TOP);
                Preferencias.setJugadores(this, gson.toJson(top10));

                msg = participante.getNombre()+ " Tiempo: "+ participante.getTiempo();
                Toast.makeText(getApplicationContext(), msg,Toast.LENGTH_LONG).show();

                MediaPlayer sonido = MediaPlayer.create(this, R.raw.fin);
                sonido.setLooping(false);
                sonido.setVolume(100, 100);
                sonido.start();
                this.finish();
            }
        }
    }
}
