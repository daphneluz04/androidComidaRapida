package com.example.androidcomidarapida;

import android.Manifest;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.example.androidcomidarapida.Adapters.AdapterAdd;
import com.example.androidcomidarapida.ui.dashboard.DashboardFragment;
import com.example.androidcomidarapida.utils.EndPoints;
import com.example.androidcomidarapida.utils.UserDataServer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Add extends AppCompatActivity implements View.OnClickListener{

    EditText titulo, precio, descripcion;
    GridView gvGalery;
    Button send,cancel,add;
    final int CODE_PERMISSION=200;
    final int CODE_GALERIA=100;
    AdapterAdd adp;
    List<String> imageList;

    Boolean admin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent lista_restaurante = new Intent(Add.this, ListaRestaurantes.class);
                Add.this.startActivity(lista_restaurante);
            }
        });
        titulo=findViewById(R.id.addTitle);
        precio=findViewById(R.id.addPrice);
        descripcion=findViewById(R.id.addDescription);
        gvGalery=findViewById(R.id.addGv);
        send=findViewById(R.id.addEnviar);
        cancel=findViewById(R.id.addVolver);
        add=findViewById(R.id.addImagenes);

        add.setVisibility(View.INVISIBLE);
        if (presimision()){
            add.setVisibility(View.VISIBLE);
        }

        add.setOnClickListener(this);
        cancel.setOnClickListener(this);
        send.setOnClickListener(this);


    }

    private boolean presimision() {
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            return true;
        }if (this.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
            return true;
        }
        requestPermissions(new String[]{Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE},CODE_PERMISSION);
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (CODE_PERMISSION==requestCode){
            if (permissions.length==3){
                add.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.addImagenes){
            Intent in =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            in.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
            in.setType("image/");
            startActivityForResult(Intent.createChooser(in,"seleccione imagenes"),CODE_GALERIA);

            }else if (R.id.addVolver== view.getId()) {
            Intent in = new Intent(Add.this, DashboardFragment.class);
            startActivity(in);

        } else if(R.id.addEnviar== view.getId()){
            enviar();
        }

    }

    private void enviar() {
        try {

            AsyncHttpClient client=new AsyncHttpClient();
            RequestParams req=new RequestParams();
            client.addHeader("authorization", UserDataServer.TOKEN);

            req.put("name",titulo.getText().toString()); //aqui del api las letras verdes
            req.put("precio",precio.getText().toString());
            req.put("description",descripcion.getText().toString());
            //asi se manda la imagen
            if (imageList.size()==0){
                Toast.makeText(Add.this, "debe insertar una imagen",Toast.LENGTH_SHORT).show();
                return;
            }else {
                File[]fl=new File[imageList.size()];
                for (int i=0;i<imageList.size();i++){
                    fl[i]= new File(imageList.get(i));
                }
               req.put("picture",fl); //picture   img
            }
            SharedP pref=new SharedP(Add.this);
            req.put("idUser",pref); //picture

            client.post(EndPoints.MENU_SERVICE,req,new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        String res=response.getString("message");
                        Toast.makeText(Add.this, res,Toast.LENGTH_SHORT).show();
                    }catch (JSONException e){
                        Toast.makeText(Add.this, "error al insertar",Toast.LENGTH_SHORT).show();
                    }
                }


            });

        }catch (Exception e){
            Toast.makeText(Add.this, "falta llenar un campo",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode==CODE_GALERIA&&resultCode==RESULT_OK){
                imageList=new ArrayList<>();
                if (data.getClipData()!=null){
                    ClipData mClipData= data.getClipData();
                    ArrayList<Uri>mArrayUri=new ArrayList<>();
                    for (int i=0;i<mClipData.getItemCount();i++) {
                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        mArrayUri.add(uri);
                        imageList.add(getRealPath(Add.this, uri));

                    }
                    adp=new AdapterAdd(Add.this,mArrayUri);
                    gvGalery.setAdapter(adp);

                    gvGalery.setVerticalSpacing(gvGalery.getHorizontalSpacing());
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGalery
                            .getLayoutParams();
                    mlp.setMargins(0,gvGalery.getHorizontalSpacing(),0,0);


                }else{
                    Uri miImageUri=data.getData();
                    imageList.add(getRealPath(Add.this,miImageUri));
                    ArrayList<Uri>marrayUri=new ArrayList<>();
                    marrayUri.add(miImageUri);
                    adp=new AdapterAdd(Add.this,marrayUri);
                    gvGalery.setAdapter(adp);

                    gvGalery.setVerticalSpacing(gvGalery.getHorizontalSpacing());
                    ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGalery.getLayoutParams();
                    mlp.setMargins(0,gvGalery.getHorizontalSpacing(),0,0);
                }
            }else {
                Toast.makeText(getApplicationContext(), " error ",Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e){
            Toast.makeText(getApplicationContext(), " error ",Toast.LENGTH_SHORT).show();
        }
    }
    String getRealPath(Context ctx, Uri uri){
        String path=null;
        Cursor cursor=ctx.getContentResolver().query(uri, null,null,null,null);
        if (cursor!=null){
            cursor.moveToFirst();
            int i=cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            path=cursor.getString(i);
            cursor.close();
        }

        return path;
    }
}