package com.example.appe;

import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.constraint.ConstraintLayout;
import android.animation.ValueAnimator;
import android.app.SearchManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class IMC extends AppCompatActivity {

    private int originalWidth;
    private static int DURATION = 1000;

    // Dibujar Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuimc, menu);
        return super.onCreateOptionsMenu(menu);
    }
    // Acciones del Menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.salir:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle saco) {
        super.onCreate(saco);
        Log.d("MIAPP", "Estoy en onCreate");
        setContentView(R.layout.activity_imc);


        ImageView img = (ImageView) findViewById(R.id.img);
        img.setImageResource(R.mipmap.ic_launcher);

        if (saco == null) {
            // Primera vez que se ejecuta

        } else {
            // Valores guardados en Saco
            boolean es  = saco.getBoolean("CARGADA");
            EditText peso = findViewById(R.id.peso);
            EditText altura = findViewById(R.id.altura);
            float p =  saco.getFloat("PESO");
            float a =  saco.getFloat("ALTURA");
            peso.setText(Float.toString(p));
            peso.setText(Float.toString(a));
        }

    }

    public void calcular(View view){
        EditText peso = findViewById(R.id.peso);
        EditText altura = findViewById(R.id.altura);

        Float p = peso.getText().length()<1 ? 0 : Float.parseFloat( peso.getText().toString());
        Float a = altura.getText().length() <1 ? 0 : Float.parseFloat( altura.getText().toString());

        Float r = p / (a * a);

        TextView resultado = findViewById(R.id.resultado);
        resultado.setText( estado(r));
    }

    public String estado(Float res){
        ImageView img = (ImageView) findViewById(R.id.img);
        String es = "IMC " + res + ": ";
        if (res < 16){
            es += "faltapeso";
            img.setImageResource(R.drawable.imc05);
        } else if (res < 18) {
            img.setImageResource(R.drawable.imc04);
            es += "Delgado";
        } else if (res < 25) {
            img.setImageResource(R.drawable.imc03);
            es += "Peso ideal";
        } else if (res < 31) {
            img.setImageResource(R.drawable.imc02);
            es += "Sobrepeso";
        } else  {
            img.setImageResource(R.drawable.imc01);
            es += "Obeso";
        }
        return es;
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("MIAPP", "Estoy en onStart");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MIAPP", "Estoy en onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MIAPP", "Estoy en onResume");
        Button c = findViewById(R.id.button2);
        calcular( c.getRootView());
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("MIAPP", "Estoy en onStop");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("MIAPP", "Estoy en onDestroy");
    }

    @Override
    public void onSaveInstanceState(Bundle saco, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(saco, outPersistentState);
        saco.putBoolean("CARGADA", true);
        EditText peso = findViewById(R.id.peso);
        EditText altura = findViewById(R.id.altura);
        saco.putFloat("PESO", Float.parseFloat( peso.getText().toString()));
        saco.putFloat("ALTURA", Float.parseFloat( altura.getText().toString()));
    }
}
