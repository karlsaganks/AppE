package com.example.appe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Puntuacion {

    private String nombre;
    private int filas;
    private int columnas;
    private int toques;
    private double tiempo;


    public Puntuacion() {
        this.nombre = "Anonimo";
        this.filas = 10;
        this.columnas = 10;
        this.toques = 10;
        this.tiempo = 999;
    }
    public Puntuacion(String nombre, int filas, int columnas, int toques, double tiempo) {
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
    @Override
    public String toString() {
        return String.format("%s - [F:%d] [C:%d] [T:%d] Tiempo %.2f Seg", this.nombre, this.filas, this.columnas, this.toques, this.tiempo);
    }
    public static void toTop(ArrayList<Puntuacion> top, int Max_top){
        Collections.sort(top, new Comparator<Puntuacion>() {
            @Override
            public int compare(Puntuacion o1, Puntuacion o2) {
                return new Double( o1.getTiempo()).compareTo(o2.getTiempo());
            }
        });
        if(top.size()>Max_top){
            for (int j = Max_top; j<top.size(); j++){
                top.remove(j);
            }
        }
    }

}
