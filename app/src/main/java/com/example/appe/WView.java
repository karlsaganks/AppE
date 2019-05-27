package com.example.appe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WView extends AppCompatActivity {

    private WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wview);

        this.wv = (WebView) findViewById(R.id.rootwview);
       // getSupportActionBar().hide();

        WebSettings ws = this.wv.getSettings();
        ws.setJavaScriptEnabled(true);
        this.wv.setWebViewClient( new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("","");
                view.loadUrl(url);
                return true;
            }
        });
        this.wv.loadUrl("https://dentaris-sa.com/");

    }
}
