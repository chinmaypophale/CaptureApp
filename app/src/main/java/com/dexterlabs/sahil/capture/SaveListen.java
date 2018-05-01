package com.dexterlabs.sahil.capture;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Scroller;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveListen extends AppCompatActivity {

    Button bSave,bLoad;
    EditText editMessageCaptured,saveFileName;
    String messageText,File_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.dexterlabs.sahil.capture.R.layout.activity_save_listen);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1000);
        }

        saveFileName = (EditText)findViewById(com.dexterlabs.sahil.capture.R.id.editSaveTextFile);
        bSave = (Button) findViewById(com.dexterlabs.sahil.capture.R.id.bSave);
        bLoad = (Button)findViewById(com.dexterlabs.sahil.capture.R.id.bLoad);
        editMessageCaptured = (EditText)findViewById(com.dexterlabs.sahil.capture.R.id.editMessageCaptured);
        //editMessageCaptured.setScroller(new Scroller(this));
        //editMessageCaptured.setMaxLines(1);
        editMessageCaptured.setVerticalScrollBarEnabled(true);
        editMessageCaptured.setMovementMethod(new ScrollingMovementMethod());
        messageText = getIntent().getStringExtra("temp");

        File_name = saveFileName.getText().toString().trim();
        editMessageCaptured.setText(messageText);



        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String file_name = saveFileName.getText().toString().trim();
                String content1 = editMessageCaptured.getText().toString().trim();
                if (!file_name.equals("") && !content1.equals("")){
                    saveTextAsFile(file_name,content1);
                }

            }
        });

        bLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SaveListen.this,LoadAudio.class);
                startActivity(intent);
            }
        });

    }

    private void saveTextAsFile(String filename,String content){
        String fileName = filename+".txt";
        //create file
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(),fileName);

        //write to file

        try {
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content.getBytes());
            fos.close();
            Toast.makeText(SaveListen.this,"File saved successfully",Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(SaveListen.this,"File not found",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(SaveListen.this,"Error",Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1000:
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    Toast.makeText(SaveListen.this,"Permission Granted Successfully",Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(SaveListen.this,"Permission not Granted",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
        }
    }
}
