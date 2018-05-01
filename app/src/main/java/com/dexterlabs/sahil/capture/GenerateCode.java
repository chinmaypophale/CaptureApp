package com.dexterlabs.sahil.capture;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.dexterlabs.sahil.capture.R;
import com.dexterlabs.sahil.capture.viewQRCode;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.ByteArrayOutputStream;

public class GenerateCode extends AppCompatActivity {

    private EditText editText;
    private Button button,bView;
    private ImageView imageView;
    byte[] byteArray;
    String text2qr;
    private Bitmap bitmap,bitmapSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_code);

        editText = (EditText) findViewById(R.id.editText3);
        button = (Button) findViewById(R.id.bsave);
        bView = (Button) findViewById(R.id.bViewQrCode);
        imageView = (ImageView) findViewById(R.id.image);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text2qr = editText.getText().toString().trim();
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try {
                    BitMatrix bitMatrix = multiFormatWriter.encode(text2qr, BarcodeFormat.QR_CODE,200,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    imageView.setImageBitmap(bitmap);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                    byteArray = byteArrayOutputStream.toByteArray();


                }
                catch (WriterException e){
                    e.printStackTrace();
                }
            }
        });


       bView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
                Intent intent = new Intent(GenerateCode.this, viewQRCode.class);
                intent.putExtra("Bitmap",byteArray);
                startActivity(intent);

           }
       });



    }
}
