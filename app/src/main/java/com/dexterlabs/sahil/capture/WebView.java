package com.dexterlabs.sahil.capture;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class WebView extends AppCompatActivity {

    private android.webkit.WebView webView;
    public String link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.dexterlabs.sahil.capture.R.layout.activity_web_view);

        webView = (android.webkit.WebView) findViewById(com.dexterlabs.sahil.capture.R.id.websiteView);

        if (getIntent()!=null){

            link = getIntent().getStringExtra("temp");
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl(link);
        }

    }
}
