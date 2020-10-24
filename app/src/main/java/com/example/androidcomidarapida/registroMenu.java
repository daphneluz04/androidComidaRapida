package com.example.androidcomidarapida;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.androidcomidarapida.ui.dashboard.DashboardFragment;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static com.example.androidcomidarapida.registroCamara.PERMISION_CODE;
import static com.example.androidcomidarapida.registroCamara.code_camera;

public class registroMenu extends AppCompatActivity implements View.OnClickListener {
    Button TakePhoto;
    ImageView gvGalery;
    Button send, cancel, add;
    EditText titulo, precio, descripcion;
    List<String> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titulo = findViewById(R.id.nombreMenu);
        precio = findViewById(R.id.loginPass);
        descripcion = findViewById(R.id.descripcionMenu);
        gvGalery = findViewById(R.id.imagecamara2);
        send = findViewById(R.id.buttoninsertarMenu);
        cancel = findViewById(R.id.borrar);
        TakePhoto = findViewById(R.id.buttonfotoMenu);


        TakePhoto.setVisibility(View.INVISIBLE);
        if (requestPermission()) {
            TakePhoto.setVisibility(View.VISIBLE);
            add.setOnClickListener(this);
            cancel.setOnClickListener(this);
            send.setOnClickListener(this);
        }
    }

  //  @Override
   // public void onClick(View view) {
        //if (view.getId()==R.id.buttonfotoMenu){
         //   Intent in =new Intent(I)
       // }
    //    enviar();
   // }

    private void enviar() throws FileNotFoundException {
        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams req=new RequestParams();

        req.put("name",titulo.getText().toString()); //aqui del api las letras verdes
        req.put("precio",precio.getText().toString());
        req.put("description",descripcion.getText().toString());
        if (imageList.size()==0){
            Toast.makeText(registroMenu.this, "debe insertar una imagen",Toast.LENGTH_SHORT).show();
        }else {
            File[]fl=new File[imageList.size()];
            for (int i=0;i<imageList.size();i++){
                fl[i]= new File(imageList.get(i));
            }
            req.put("picture",fl); //picture   img
        }
        SharedP pref=new SharedP(registroMenu.this);
        req.put("idUser",pref); //picture

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

    private boolean requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            this.requestPermissions(new String[]{Manifest.permission.CAMERA},PERMISION_CODE);
        }
        return false;
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
        //enviar();

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
    public void onActivityResult(int requestCode, int resultCode, @NonNull Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == code_camera && resultCode == this.RESULT_OK){
            Bundle photo = data.getExtras();
            Bitmap imageBitmap = (Bitmap) photo.get("data");
            ImageView img = this.findViewById(R.id.imagecamara2);  //imageView es el nombre de donde se mostrara la imagen en contect registro
            img.setImageBitmap(imageBitmap);
        }
    }
}
