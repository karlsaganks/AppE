package com.example.appe;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferencias {

    private static String ARCHIVO = "Preferencia";
    private static String JUGADORES = "jugadores";

    public static void setJugadores(Context ctx, String texto){
        SharedPreferences file = ctx.getSharedPreferences(ARCHIVO, Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = file.edit();
        ed.putString(JUGADORES, texto);
        ed.commit();
    }

    public static String getJugadores(Context ctx){
        SharedPreferences file = ctx.getSharedPreferences(ARCHIVO, Context.MODE_PRIVATE);
        return file.getString(JUGADORES, "[{}]");
    }


}
