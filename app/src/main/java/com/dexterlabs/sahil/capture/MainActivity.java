package com.dexterlabs.sahil.capture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button bReal,bImagetoText,bScan,bGenerate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.dexterlabs.sahil.capture.R.layout.activity_main);

        bGenerate =(Button) findViewById(com.dexterlabs.sahil.capture.R.id.bGenerate);
        bImagetoText = (Button) findViewById(com.dexterlabs.sahil.capture.R.id.bImageToText);
        bScan = (Button) findViewById(com.dexterlabs.sahil.capture.R.id.bScan);
        bReal = (Button) findViewById(com.dexterlabs.sahil.capture.R.id.bRealTime);

        bScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ScanCode.class);
                startActivity(intent);
            }
        });

        bGenerate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,GenerateCode.class);
                startActivity(intent);
            }
        });

        bReal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RealTimeImageToText.class);
                startActivity(intent);
            }
        });

        bImagetoText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ImageToText.class);
                startActivity(intent);

            }
        });

    }
}
