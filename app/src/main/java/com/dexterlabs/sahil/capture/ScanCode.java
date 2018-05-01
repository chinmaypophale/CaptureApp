package com.dexterlabs.sahil.capture;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCode extends AppCompatActivity {

    private ZXingScannerView scannerView;
    private Button button;
    String temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.dexterlabs.sahil.capture.R.layout.activity_scan_code);

    }

    public void scanCode(View view) {
        scannerView = new ZXingScannerView(this);
        scannerView.setResultHandler(new ZXingScannerResultHandler());
        setContentView(scannerView);
        scannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    class ZXingScannerResultHandler implements ZXingScannerView.ResultHandler {


        @Override
        public void handleResult(com.google.zxing.Result result) {
            String resultCode = result.getText();
            Toast.makeText(ScanCode.this, resultCode, Toast.LENGTH_LONG).show();

            setContentView(com.dexterlabs.sahil.capture.R.layout.activity_main);
            scannerView.stopCamera();

            Intent intent = new Intent(ScanCode.this,WebView.class);
            intent.putExtra("temp", resultCode);
            startActivity(intent);

        }
    }
}