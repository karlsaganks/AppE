package com.example.appe;

// https://itunes.apple.com/es/app/locutorio-point-k/id1463595297?mt=8

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Tablero {

    private int elementos;
    private int filas;
    private int columnas;
    private int tocados;
    private ArrayList<Box[]> caja;
    private HashMap<Integer, Box> box = new HashMap<Integer,Box>();

    public Tablero() {
        this.filas = hasta(10);
        this.columnas = 10;
        this.elementos = 0;
        this.tocados = 0;
        this.caja = new ArrayList<Box[]>(this.filas);
        this.generar(this.filas);
    }
    public Tablero(int filasTablero, int columnasTablero){
        this.filas = hasta( filasTablero);
        this.columnas = columnasTablero;
        this.elementos = 0;
        this.tocados = 0;
        this.caja = new ArrayList<Box[]>(this.filas);
        this.generar(this.filas);
    }

    private void generar(int filas){
        for( int i = 0; i< filas; i++){
            int cols = hasta( this.columnas);
            Box[] colCaja = new Box[cols];
            for( int j = 0; j < cols; j++){
                Box bx = new Box();
                colCaja[j] = bx;
                box.put( bx.getId(), bx);
                this.elementos += 1;
            }
            this.caja.add(colCaja);
        }
    }

    public ArrayList<Box[]> getCaja(){
        return caja;
    }

    public int getElementos(){
        return this.elementos;
    }
    public int getFilas(){
        return this.filas;
    }

   // public Box[] getBoxEnFila(int fila){
   //     return caja.get(fila);
   // }

    private int hasta(int mxm) {
        int res = -1;
        do {
            res = new Random().nextInt(mxm);
        } while( res < 2);
        return res;
    }

    public int getTocados() {
        return tocados;
    }

    public void setTocados(int tocados) {
        this.tocados = tocados;
    }

    public HashMap<Integer, Box> getBox() {
        return box;
    }

    public void setBox(HashMap<Integer, Box> box) {
        this.box = box;
    }
}

