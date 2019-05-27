package com.example.appe;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Jugador {

    private final int MAX_TOP = 5;
    private String nombre;
    private int filas;
    private int columnas;
    private int toques;
    private double tiempo;

    private static ArrayList<Jugador> top = new ArrayList<Jugador>();

    public Jugador(){
        this.nombre = "Anonimo";
        this.filas = 10;
        this.columnas = 10;
        this.toques = 10;
        this.tiempo = 99999.0;
    }

    public Jugador(String nombre, int filas, int columnas, int toques, double tiempo) {
        this.nombre = nombre;
        this.filas = filas;
        this.columnas = columnas;
        this.toques = toques;
        this.tiempo = tiempo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getFilas() {
        return filas;
    }

    public void setFilas(int filas) {
        this.filas = filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }

    public int getToques() {
        return toques;
    }

    public void setToques(int toques) {
        this.toques = toques;
    }

    public double getTiempo() {
        return tiempo;
    }

    public void setTiempo(double tiempo) {
        this.tiempo = tiempo;
    }

    public static ArrayList<Jugador> getTop() {
        return top;
    }

    public static void setTop(ArrayList<Jugador> top) {
        Jugador.top = top;
    }

    /*
        Metodos que ordena los Top Jugadores con Menor Tiempo
     */

    public void toTop(){
        Collections.sort(this.top, new Comparator<Jugador>() {
            @Override
            public int compare(Jugador o1, Jugador o2) {
                return new Double( o1.getTiempo()).compareTo(o2.getTiempo());
            }
        });
        if(top.size()>MAX_TOP){
          for (int j = MAX_TOP; j<top.size(); j++){
              top.remove(j);
          }
        }
    }

    /*
        Metodos de Conversion Top Jugadores a JSON
    */

    public String topJSON(){
        if(!this.top.isEmpty()) {
            JSONArray aTop = new JSONArray();
            for (int i = 0; i < this.top.size(); i++){
                aTop.put( toJSON( this.top.get(i)) );
            }
            return aTop.toString();
        }
        return "[{}]";
    }

    /*
        Metodos de Conversion JSON a Array de Jugadores
     */

    public static ArrayList<Jugador> fromJSON(String s){
        try {
            ArrayList<Jugador> jugadores = new ArrayList<Jugador>();
            JSONArray ja = new JSONArray(s);
            for (int i = 0; i < ja.length(); i++){
                jugadores.add( deJSON( ja.getJSONObject(i).toString()));
            }
            return jugadores;
        } catch ( JSONException ex) {
            Log.i("APP-E", "Error String JSON");
        }
        return new ArrayList<Jugador>();
    }

    /*
        Metodos de Conversion de Jugador a JSON
    */
    public static JSONObject toJSON(Jugador j){
        try {
            JSONObject o = new JSONObject();
            o.put("nombre", j.getNombre());
            o.put("filas", j.getFilas());
            o.put("columnas", j.getColumnas());
            o.put("toques", j.getToques());
            o.put("tiempo", j.getTiempo());
            return o ;
        } catch (JSONException ex){
            Log.i("APP-E", "Error Array JSON");
           return null;
        }
    }

    /*
        Metodos de Conversion de JSON a Jugador
    */
    public static Jugador deJSON(String s){
        try {
            JSONObject o = new JSONObject(s);
            return new Jugador(
                    o.getString("nombre"),
                    o.getInt("filas"),
                    o.getInt("columnas"),
                    o.getInt("toques"),
                    o.getDouble("tiempo"));
        } catch (JSONException ex){
            Log.i("APP-E", "Jugador Anonimo - JSON ");
          return new Jugador();
        }
    }

    @Override
    public String toString() {
        return String.format("%s - [F:%d] [C:%d] [T:%d] Tiempo %.2f Seg", this.nombre, this.filas, this.columnas, this.toques, this.tiempo);
    }

}

/*
        // Libreria Gson
        private Gson gson = new Gson();
        String sgs = gson.toJson(top10.get(nTop10));
        Jugador[] ar = gson.fromJson(top10Json, Jugador[].class);
        ArrayList<Jugador> aa = new ArrayList<Jugador>( Arrays.asList(ar));
        for (int l = 0; l<aa.size(); l++){
            Log.i(APP, "Gson: "+ aa.get(l).toString());
        }
        //
 */