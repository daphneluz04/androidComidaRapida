package com.example.androidcomidarapida;

import android.Manifest;
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

import com.example.androidcomidarapida.Adapters.CustonAdapter;
import com.example.androidcomidarapida.Adapters.ItemList;
import com.example.androidcomidarapida.Adapters.OnLoadImage;
import com.example.androidcomidarapida.utils.EndPoints;
import com.example.androidcomidarapida.utils.UserDataServer;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.widget.Toolbar;

import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class Menu extends AppCompatActivity implements View.OnClickListener  {

    private ImageView imageViewImg;
    private EditText nombreMenu,precioMenu,descripcionMenu;
    private Button buttoninsertarMenu,buttonfotoMenu,borrar;
    private String Carpeta_Root="MisImages/";
    private String RUTA_IMAGES=Carpeta_Root+"Mis_Fotos";
    private String path;
    private int CODE_GALLERY= 10;
    private int CODE_CAMERA= 20;
    private EndPoints HOST =new EndPoints();
    private Context root;

    private ListView LIST;
    //private Context root;
    private ArrayList<ItemList> LISTINFO;
    private CustonAdapter ADAPTER;
    private OnLoadImage interfaceevent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        root = this;
        LISTINFO = new ArrayList<ItemList>() ;

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        nombreMenu=findViewById(R.id.nombreMenu);
        precioMenu=findViewById(R.id.precioMenu);
        descripcionMenu=findViewById(R.id.descripcionMenu);

        imageViewImg = this.findViewById(R.id.imageViewImg);
        buttoninsertarMenu=(Button)this.findViewById(R.id.buttoninsertarMenu);
        buttonfotoMenu=(Button) this.findViewById(R.id.buttonfotoMenu);
        borrar=(Button) this.findViewById(R.id.borrar);

        buttonfotoMenu.setOnClickListener(this);
        buttoninsertarMenu.setOnClickListener(this);
        borrar.setOnClickListener(this);

        checkPermission();
        root = this;

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        loadComponents();
        loadListaComponents();

    }

    private void loadInitialRestData(String keystr) {
        AsyncHttpClient client = new AsyncHttpClient();
        //aqui la direccion
        //client.get("",new JsonHttpResponseHandler()
        client.addHeader("authorization", UserDataServer.TOKEN);
        client.get(EndPoints.MENU_SERVICE,new JsonHttpResponseHandler(){
            @Override

            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // super.onSuccess(statusCode, headers, response
                try {
                    //JSONArray list = (JSONArray) response.get("menu"); //menu
                    for (int i=0;i<response.length();i++ ){

                        JSONObject itemJson = response.getJSONObject(i);

                        String picture= itemJson.getString("picture");
                        String nombre = itemJson.getString("name");
                        String precio = itemJson.getString("precio");
                        String descripcion = itemJson.getString("description");

                        String _id = itemJson.getString("_id");

                        ItemList item = new ItemList(picture,nombre,precio,descripcion,_id);
                        LISTINFO.add(item);
                    }
                    ADAPTER  = new CustonAdapter(root,LISTINFO);
                    LIST.setAdapter(ADAPTER);
                    Toast.makeText(getApplicationContext(), LISTINFO.size()+"hola", Toast.LENGTH_LONG).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                // super.onFailure(statusCode, headers, throwable, errorResponse);
                Toast.makeText(root, "FAIL",Toast.LENGTH_LONG);
            }
        });


    }


    private void loadListaComponents() {
        LIST = (ListView) this.findViewById(R.id.listviewlayout);
        // ADAPTER  = new CustonAdapter(this,LISTINFO);
        //aqui se probara si esta funcionando
        // LISTINFO.add(new ItemList("https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/651261e0-270d-49b6-bba3-3cd895b6ebbe/dbrtvqw-3740f1e0-8599-4977-a032-c0e5d337e25a.png/v1/fill/w_1024,h_1024,strp/jack_skeleton_jack_skellington_by_juanuzcategui93_dbrtvqw-fullview.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOiIsImlzcyI6InVybjphcHA6Iiwib2JqIjpbW3siaGVpZ2h0IjoiPD0xMDI0IiwicGF0aCI6IlwvZlwvNjUxMjYxZTAtMjcwZC00OWI2LWJiYTMtM2NkODk1YjZlYmJlXC9kYnJ0dnF3LTM3NDBmMWUwLTg1OTktNDk3Ny1hMDMyLWMwZTVkMzM3ZTI1YS5wbmciLCJ3aWR0aCI6Ijw9MTAyNCJ9XV0sImF1ZCI6WyJ1cm46c2VydmljZTppbWFnZS5vcGVyYXRpb25zIl19.mk6U_XiD-d-zvRcPt7_3tR0o_ML1bzjqVu4wNoStD4M","jack esqueletor","20","peli"));

        //ArrayAdapter //asi puede funcionar creando propia funcion

        EditText seach =(EditText)this.findViewById(R.id.seachMenu);
        //eventos
        seach.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence , int i, int i1, int i2) {
                String str =  charSequence.toString();
                loadInitialRestData(str);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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

    }
    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.buttonfotoMenu){
            cargarImagenes();
            Intent in =new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            in.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
            in.setType("image/");
        }else if (R.id.borrar== view.getId()) {
            Intent in = new Intent(Menu.this, Menu.class);
            startActivity(in);

        } else if(R.id.buttoninsertarMenu== view.getId()){
            enviar();

        }
    }

    private void enviar() {

        AsyncHttpClient client=new AsyncHttpClient();
        RequestParams req=new RequestParams();
        req.put("name",nombreMenu.getText().toString()); //aqui del api las letras verdes
        req.put("precio", precioMenu.getText().toString());
        req.put("description",descripcionMenu.getText().toString());
        //asi se manda la imagen
        if ( imageViewImg.getDrawable() != null){
            Toast.makeText(Menu.this, "debe insertar una imagen",Toast.LENGTH_SHORT).show();

        }if (path != null){
            File file = new File(path);
            RequestParams params = new RequestParams();
            try {
                req.put("picture", file,"image/png");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            //  if (UserData.ID != null) {
            client.post(EndPoints.MENU_SERVICE,req,new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    try {
                        String res = response.getString("message");
                        Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                  }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Toast.makeText(getApplicationContext(), errorResponse.toString(), Toast.LENGTH_LONG).show();
                }
            });

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
                            Log.i("Ruta de almacenamiento","PATH:"+path); //aqui
                        }
                    });
                    Bitmap bitmap= BitmapFactory.decodeFile(path);
                    imageViewImg.setImageBitmap(bitmap);
                    break;
            }
        }
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}