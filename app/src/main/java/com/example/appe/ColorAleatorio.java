package com.example.appe;

public class ColorAleatorio {

    public static int getOneColor() {
        return ((int) (Math.random() * 16777215)) | (0xFF << 24);
    }

    public static String getOneColorHex() {
        return "#" + Integer.toHexString(getOneColor()).substring(2);
    }

    public static String getOneColorHex(int oneColor) {
        return "#" + Integer.toHexString(oneColor).substring(2);
    }
}