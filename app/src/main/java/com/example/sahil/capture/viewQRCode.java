package com.example.sahil.capture;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class viewQRCode extends AppCompatActivity implements View.OnClickListener{

    ImageView qrImage;
    Button bsave;
    Bitmap bmp;
    AlertDialog dialog;
    String Imagetext;
    Uri URI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_qrcode);

        bsave = (Button) findViewById(R.id.bsave);
        bsave.setOnClickListener(viewQRCode.this);
        qrImage = (ImageView) findViewById(R.id.qrImage);


        Bundle extras = getIntent().getExtras();
        byte[] byteArray = extras.getByteArray("Bitmap");
        bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);

        if (bmp != null) {
            qrImage.setImageBitmap(bmp);
        }

    }

    public static Bitmap viewToBitmap(View view, int width, int height){
        Bitmap bitmap = Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    @Override
    public void onClick(View view) {
        dialog = new AlertDialog.Builder(viewQRCode.this).create();
        dialog.setTitle("Save Image");
        dialog.setMessage("You sure to save your Image");
        dialog.setButton(dialog.BUTTON_POSITIVE,"OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startSave();
            }
        });
        dialog.setButton(dialog.BUTTON_NEGATIVE,"NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        dialog.show();

    }

    private void startSave() {
        FileOutputStream fileOutputStream = null;
        File file = getDisc();
        if (!file.exists() && !file.mkdirs()){
            Toast.makeText(viewQRCode.this,"Cant load te file",Toast.LENGTH_LONG).show();
            return;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyymmsshhmmss");
        String date = simpleDateFormat.format(new Date());
        String name = "Img"+date+".jpeg";
        String file_name = file.getAbsolutePath()+"/"+name;
        File new_file = new File(file_name);

        try {
            fileOutputStream = new FileOutputStream(new_file);
            Bitmap bitmap = viewToBitmap(qrImage,qrImage.getWidth(),qrImage.getHeight());
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
            Toast.makeText(viewQRCode.this,"Successfully saved",Toast.LENGTH_LONG).show();
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        refreshGallery(new_file);
    }

    private void refreshGallery(File file) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(file));
        sendBroadcast(intent);
    }

    private File getDisc(){
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        return new File(file,"QRCode Images");
    }
}




