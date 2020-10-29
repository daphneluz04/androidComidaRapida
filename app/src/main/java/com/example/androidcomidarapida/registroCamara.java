
package com.example.androidcomidarapida;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.example.androidcomidarapida.utils.BitmapStruct;
import com.example.androidcomidarapida.utils.EndPoints;
import com.example.androidcomidarapida.utils.UserDataServer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class registroCamara extends AppCompatActivity implements View.OnClickListener  {
    private ImageView imageViewImg;
    private Button btnoption ,btnsend;
    private String Carpeta_Root="MisImages/";
    private String RUTA_IMAGES=Carpeta_Root+"Mis_Fotos";
    private String path;

    private int CODE_GALLERY= 10;
    private int CODE_CAMERA= 20;
    private EndPoints HOST =new EndPoints();
    private Context root;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_camara);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent opciones = new Intent(registroCamara.this, opciones.class);
                registroCamara.this.startActivity(opciones);

                //Intent Menu = new Intent(registroCamara.this, Menu.class);
               // registroCamara.this.startActivity(Menu);

            }
          });

        checkPermission();
        root = this;

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        loadComponents();
    }

    private void checkPermission() {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            return ;

        }
        if(this.checkSelfPermission(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED  || this.checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED|| this.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED ){

            return ;
        }else{
            this.requestPermissions( new String[]{android.Manifest.permission.CAMERA, android.Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},99);
        }
        return ;


    }

    private void loadComponents() {
        imageViewImg = this.findViewById(R.id.imagefoto);
        btnoption=(Button)this.findViewById(R.id.photobtn);
        btnsend=(Button) this.findViewById(R.id.btnsend);
        btnsend.setOnClickListener(this);
        btnoption.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.photobtn){
            cargarImagenes();
        }
        if(v.getId()==R.id.btnsend) {
            try {
                enviarImg();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    private void enviarImg() throws FileNotFoundException {
        if ( imageViewImg.getDrawable() != null) {
            if (path != null) {
                File file = new File(path);
                RequestParams params = new RequestParams();
                params.put("fotolugar", file,"image/png");
                AsyncHttpClient client = new AsyncHttpClient();
                client.addHeader("authorization", UserDataServer.TOKEN);
                //  if (UserData.ID != null) {
                client.post(EndPoints.HOST+"/restaurante", params,new JsonHttpResponseHandler(){
                //client.post(HOST.getIp() , params, new JsonHttpResponseHandler(){
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            Toast.makeText(getApplicationContext(), "SE REGISTRO SATISFACTORIAMENTE ", Toast.LENGTH_SHORT).show();
                            //Intent intent = new Intent(root, LatLonMaps.class);
                            //root.startActivity(intent);
                            Toast.makeText(getApplicationContext(), response.getString("ok"), Toast.LENGTH_SHORT).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

                        Toast.makeText(getApplicationContext(),"FAIL",Toast.LENGTH_SHORT);
                    }
                });



            }
        } else {
            Toast.makeText(this, "No se ha sacado una foto", Toast.LENGTH_LONG).show();
        }
    }
    public void cargarImagenes(){

        final CharSequence[] options= {"Tomar Foto","Cargar de Galeria","Cancelar"};
        final AlertDialog.Builder alertopt= new AlertDialog.Builder(this);
        alertopt.setTitle("Seleccione una Opcion");
        alertopt.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (options[which].equals("Tomar Foto")) {
                    //  Toast.makeText(getApplication(), "camara", Toast.LENGTH_LONG).show();
                    LoadCamera();
                } else {
                    if (options[which].equals("Cargar de Galeria")) {

                        LoadMediaData();


                    } else {
                        dialog.dismiss();

                    }
                }
            }


        });
        alertopt.show();
    }

    private void LoadMediaData()  {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(imageFileName,  ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }

        path = image.getAbsolutePath();
        intent.setType("image/");
        startActivityForResult(intent.createChooser(intent, "Seleccionar la aplocacion"), 10);
    }

    private void LoadCamera( ){

        File fileimg =new File(Environment.getExternalStorageDirectory(),RUTA_IMAGES);
        boolean filecreado = fileimg.exists();
        String nameImg="";
        if(filecreado==false) {

            filecreado = fileimg.mkdirs();
        }if(filecreado==true){
            nameImg = "IMG_" +System.currentTimeMillis()/1000+ ".jpg";
        }
        path=Environment.getExternalStorageDirectory()+File.separator+RUTA_IMAGES+File.separator+nameImg;
        File imagen=new File(path);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        startActivityForResult(intent,20);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){

            switch (requestCode){
                case 10:
                    imageViewImg.setImageURI(data.getData());
                    break;
                case 20:
                    MediaScannerConnection.scanFile(this, new String[]{path}, null, new MediaScannerConnection.OnScanCompletedListener() {
                        @Override
                        public void onScanCompleted(String I, Uri uri) {
                            Log.i("Ruta de almacenamiento","PATH:"+path);
                        }
                    });
                    Bitmap bitmap= BitmapFactory.decodeFile(path);
                    //Bitmap ss = getSavedStateRegistry(imageViewImg);
                    imageViewImg.setImageBitmap(bitmap);
                    break;
            }
        }
    }
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}
