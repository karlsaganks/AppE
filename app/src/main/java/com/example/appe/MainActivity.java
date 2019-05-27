package com.example.appe;

import android.animation.ValueAnimator;
import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText txt;
    Button btn;
    private static final String URLB = "https://www.google.es/search?q=";

    private final String NOMBRE_FICHERO = "Preferencia";
    private final String CLAVE_TEXTO = "ultimo_texto";

    private int originalWidth;
    private static int DURATION = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editText = findViewById(R.id.editText);
        originalWidth = editText.getMinimumWidth();
        Log.d("MYAPP", Integer.toString(originalWidth));

        EditText eText = findViewById(R.id.txtbusqueda);
        SharedPreferences sp = getSharedPreferences(NOMBRE_FICHERO, MODE_PRIVATE);
        String txt = sp.getString(CLAVE_TEXTO, null);
        eText.setText( txt!= null ? txt : "Sin Texto");

    }

    @Override
    protected void onStop() {
        super.onStop();
        EditText editText = findViewById(R.id.txtbusqueda);
        String txt = editText.getText().toString();
        SharedPreferences sp = getSharedPreferences(NOMBRE_FICHERO, MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString(CLAVE_TEXTO, txt);
        ed.commit();
    }

    public void buscar1(View view) {
        txt = (EditText) findViewById(R.id.txtbusqueda);
        String s = txt.getText().toString();
        String www = URLB + s;
        Intent w = new Intent(Intent.ACTION_VIEW, Uri.parse(www));
        startActivity(w);
    }

    public void toIMC(View view) {
        Intent i = new Intent(this, IMC.class);
        startActivity(i);
    }

    public void buscar(View v) {
        final EditText editText = findViewById(R.id.editText);
        if (editText.getWidth() == originalWidth) { // Debo animar el expand
            final TextView textView = findViewById(R.id.textViewLabel);
            final ConstraintLayout.LayoutParams lparams = (ConstraintLayout.LayoutParams) editText.getLayoutParams();
            ValueAnimator anim = ValueAnimator.ofInt(editText.getWidth(), textView.getWidth());
            anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator valueAnimator) {
                    int val = (Integer) valueAnimator.getAnimatedValue();
                    lparams.width = val;
                    editText.setLayoutParams(lparams);
                }
            });
            anim.setDuration(DURATION);
            anim.start();
        } else {
            String texto = editText.getText().toString();
            if (texto.isEmpty()) {
                final ConstraintLayout.LayoutParams lparams = (ConstraintLayout.LayoutParams) editText.getLayoutParams();
                ValueAnimator anim = ValueAnimator.ofInt(editText.getWidth(), originalWidth);
                anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        int val = (Integer) valueAnimator.getAnimatedValue();
                        lparams.width = val;
                        editText.setLayoutParams( lparams);
                    }
                });
                anim.setDuration(DURATION);
                anim.start();
            } else {
                Log.d("MYAPP", Integer.toString(editText.getWidth()) + " Expandido");
                Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
                intent.putExtra(SearchManager.QUERY, texto);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        }
    }


    public void sendWsp(View view) {
        EditText msg = (EditText) findViewById(R.id.txtbusqueda);
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.setPackage("com.whatsapp");
        sendIntent.putExtra(Intent.EXTRA_TEXT, msg.getText().toString());
        sendIntent.setType("text/plain");
        if(sendIntent.resolveActivity( getPackageManager()) != null) {
            startActivity(sendIntent);
        } else {
            Toast.makeText(this, "Aplicacion no instalada", Toast.LENGTH_SHORT).show();
        }

    }

    public void toMapa(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:40.2938899,-3.7454672?z=17"));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void toWeb(View view) {

       // Intent intent = new Intent(this, WView.class);
       // startActivity(intent);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://dentaris-sa.com"));
        String t = "Con cual aplicacion desea continuar? ";
        Intent c = Intent.createChooser(intent, t);
        startActivity(c);
    }

    public void toColorMix(View view) {
        Intent i = new Intent(this, ColorMix.class);
        startActivity(i);
    }
}
