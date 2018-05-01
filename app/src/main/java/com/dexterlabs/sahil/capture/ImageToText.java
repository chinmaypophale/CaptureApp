package com.dexterlabs.sahil.capture;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

public class ImageToText extends AppCompatActivity {

    ImageView ivImage;
    TextView textfromimage;
    Button buttonConvert;
    Integer REQUEST_CAMERA = 1, SELECT_FILE=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.dexterlabs.sahil.capture.R.layout.activity_image_to_text);
        Toolbar toolbar = (Toolbar) findViewById(com.dexterlabs.sahil.capture.R.id.toolbar);
        setSupportActionBar(toolbar);ivImage = (ImageView)findViewById(com.dexterlabs.sahil.capture.R.id.image_view);
        textfromimage = (TextView)findViewById(com.dexterlabs.sahil.capture.R.id.textFromImage);
        textfromimage.setMovementMethod(new ScrollingMovementMethod());

        buttonConvert = (Button) findViewById(com.dexterlabs.sahil.capture.R.id.buttonConvert);



        FloatingActionButton fab = (FloatingActionButton) findViewById(com.dexterlabs.sahil.capture.R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                SelectImage();

            }
       });
    }

    public void getTextFromImage(View view){
        Bitmap bitmap = ((BitmapDrawable) ivImage.getDrawable()).getBitmap();
        TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational()) {
            Toast.makeText(ImageToText.this,"Could not get the text",Toast.LENGTH_LONG).show();
        }
        else {
            Frame frame = new Frame.Builder().setBitmap(bitmap).build();
            SparseArray<TextBlock> iteams = textRecognizer.detect(frame);
            StringBuilder st = new StringBuilder();
            for (int i=0;i<iteams.size();++i) {
                TextBlock myItem = iteams.valueAt(i);
                st.append(myItem.getValue());
                //st.append("\n");
            }
            textfromimage.setText(st.toString());
//
//            Intent intent = new Intent(MainActivity.this,Main2Activity.class);
//            intent.putExtra("temp",textfromimage.getText().toString());
//            startActivity(intent);

        }
    }


    private void SelectImage() {
        final CharSequence[] items = {"Camera","Gallery","Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(ImageToText.this);
        builder.setTitle("Add Image");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Camera")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,REQUEST_CAMERA);
                }else if(items[i].equals("Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Select File"),SELECT_FILE);
                }else if(items[i].equals("Cancel")){
                    dialogInterface.dismiss();
                }

            }
        });
        builder.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode== Activity.RESULT_OK){

            if (requestCode==REQUEST_CAMERA) {
                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap) bundle.get("data");
                ivImage.setImageBitmap(bmp);

            }
            else if(requestCode==SELECT_FILE) {
                Uri selectImageUri = data.getData();
                ivImage.setImageURI(selectImageUri);
            }
        }

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    //    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.);
//    }

        public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(com.dexterlabs.sahil.capture.R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == com.dexterlabs.sahil.capture.R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
