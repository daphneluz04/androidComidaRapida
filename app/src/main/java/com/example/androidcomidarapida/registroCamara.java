package com.example.androidcomidarapida;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.example.androidcomidarapida.utils.BitmapStruct;
import com.example.androidcomidarapida.utils.EndPoints;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class registroCamara extends AppCompatActivity implements View.OnClickListener  {
    static final int PERMISION_CODE = 123;
    static final int code_camera = 999;
    Button TakePhoto;

    private ImageView IMG;
    private ImageButton btn;
    private Button SEND;
    private BitmapStruct DATAIMAGE;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_camara);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                //luego ponr la condicion de if
                Snackbar.make(view, "Registrando datos espere por favor", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent registrodos = new Intent(registroCamara.this, registroDos.class);
                registroCamara.this.startActivity(registrodos);
            }
        });
        loadComponents();
    }

    private void loadComponents() {
        TakePhoto = this.findViewById(R.id.photobtn);
        TakePhoto.setOnClickListener(this);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PERMISION_CODE){
            if (permissions.length > 0){
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    callCamera();
                }else {
                    Toast.makeText(this,"no puede seguir con el registro hasta que ponga una foto ", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            this.requestPermissions(new String[]{Manifest.permission.CAMERA},PERMISION_CODE);
        }
    }
    public boolean checkPermission(String permission){
        int result = this.checkCallingOrSelfPermission(permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }
    @Override
    public void onClick(View view) {
        //aqui trasladar
        if (checkPermission(Manifest.permission.CAMERA)){
            callCamera();
            //Toast.makeText(this, "tiene permiso  XD", Toast.LENGTH_LONG).show();
        }else{
            requestPermission();
            // Toast.makeText(this, "no tiene permiso  XD", Toast.LENGTH_LONG).show();
        }
    }
    //el codigo dejara que tenga los permisos de camara
    private void callCamera(){
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (camera.resolveActivity(this.getPackageManager()) != null){
            this.startActivityForResult(camera, code_camera);
        }
    }
    //es para recuperar la foto
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == code_camera && resultCode == RESULT_OK){
            Bundle photo = data.getExtras();
            Bitmap imageBitmap = (Bitmap) photo.get("data");
            ImageView img = this.findViewById(R.id.imagefoto);  //imageView es el nombre de donde se mostrara la imagen en contect registro
            img.setImageBitmap(imageBitmap);
        }
    }

    }
