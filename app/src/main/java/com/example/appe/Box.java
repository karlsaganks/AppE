package com.example.appe;

public class Box {
    private int id;
    private String color;
    private boolean usado;

    public Box(){
        int unColor = ColorAleatorio.getOneColor();
        this.color = ColorAleatorio.getOneColorHex(unColor);
        this.id = unColor * -1;
        this.usado = false;
    }

    @Override
    public String toString() {
        return this.id + " - " +this.color + " - " + this.usado;
    }

    public int getId() {
        return id;
    }

    public String getColor() {
        return color;
    }

    public boolean isUsado() {
        return usado;
    }

    public void setUsado(boolean usado) {
        this.usado = usado;
    }
}