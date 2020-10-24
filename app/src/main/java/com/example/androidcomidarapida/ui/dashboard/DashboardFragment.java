package com.example.androidcomidarapida.ui.dashboard;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.androidcomidarapida.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
public class DashboardFragment extends Fragment implements View.OnClickListener{
    //private host HOST =new host();
    static final int PERMISION_CODE = 123;
    static final int code_camera = 999;
    Button TakePhoto;
    ImageView gvGalery;
    Button send,cancel,add;
    EditText titulo, precio, descripcion;
    List<String> imageList;

    private DashboardViewModel dashboardViewModel;
    private ListView list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        //final TextView textView = root.findViewById(R.id.text_dashboard); //aqui llamar desde el boton al fragment
        //es para ir del boton crear menu a el fracment menu
        // root.findViewById(R.id.buttonCrearMenu);
        //root.findViewById(R.id.)
        //*********************registrando datos****************


      /*  titulo = getActivity().findViewById(R.id.nombreMenu);
        precio = getActivity().findViewById(R.id.precioMenu);
        descripcion = getActivity().findViewById(R.id.descripcionMenu);

        gvGalery = getActivity().findViewById(R.id.imagecamara2);
        send = getActivity().findViewById(R.id.buttoninsertarMenu);
        cancel = getActivity().findViewById(R.id.borrar);
        TakePhoto = getActivity().findViewById(R.id.buttonfotoMenu);*/

       // TakePhoto.setVisibility(View.INVISIBLE);
       // if (requestPermission()){
      //      TakePhoto.setVisibility(View.VISIBLE);
        //}

     /*   add.setOnClickListener(this);
        cancel.setOnClickListener(this);
        send.setOnClickListener(this);*/


        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }

        });
        return root;

    }


    //***************************************permisos para el uso de la camara***********************************
    public void onStart() {
        super.onStart();

        TakePhoto = this.getActivity().findViewById(R.id.buttonfotoMenu);
        TakePhoto.setOnClickListener(this);

        list = this.getActivity().findViewById(R.id.milistamenu);

      //  ArrayList<StructMenu>datos = new ArrayList<>();
     //   StructMenu item = new StructMenu();
      //        MenuApi api = new MenuApi(this);
      //        api.loadMenu();
      /*  for (int i =0 ; i<100;i++){
          //StructMenu item = new StructMenu();
          item.setName("name" + i);
          item.setPrecio("precio"+i);
          item.setDescription("descripcion" +i);
          item.setPicture("picture");
          datos.add(item);
        }*/
     //   adapterMenu adapter = new adapterMenu(this.getContext(), datos);
       // ArrayList<String>adapter = new ArrayAdapter(this.getContext(), android.R.layout.simple_list_item_1);
    //    list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

      //  ArrayList<StructMenu> datos = new ArrayList<>();
       // MenuApi api = new MenuApi(this);
      //  api.loadMenu();

       /* ArrayList<StructMenu> datos = new ArrayList<>();
        MenuApi api = new MenuApi(this);
        api.loadMenu();*/

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == PERMISION_CODE){
            if (permissions.length > 0){
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    callCamera();
                }else {
                    Toast.makeText(this.getActivity(),"no puede seguir con el registro hasta que ponga una foto ", Toast.LENGTH_LONG).show();
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
        int result = this.getActivity().checkCallingOrSelfPermission(permission);
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
        if (camera.resolveActivity(this.getActivity().getPackageManager()) != null){
            this.startActivityForResult(camera, code_camera);
        }
    }

    //es para recuperar la foto
    @Override
    public void onActivityResult(int requestCode, int resultCode, @NonNull Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == code_camera && resultCode == this.getActivity().RESULT_OK){
            Bundle photo = data.getExtras();
            Bitmap imageBitmap = (Bitmap) photo.get("data");
            ImageView img = this.getActivity().findViewById(R.id.imagecamara2);  //imageView es el nombre de donde se mostrara la imagen en contect registro
            img.setImageBitmap(imageBitmap);
        }
    }
   /* @Override
    public void onJsonArrayLoad(JSONArray data) {
       ArrayList<StructMenu> datos = new ArrayList<>();
        for (int i = 0 ;i < data.length();i++){
            StructMenu item = new StructMenu();
            try {
                if (data.getJSONObject(i).has("name")){ //aqui cambiar si no da
                    item.setName(data.getJSONObject(i).getString("name"));
                }else{
                    item.setName("");
                }
                if (data.getJSONObject(i).has("precio")){
                    item.setPrecio(data.getJSONObject(i).getString("precio"));
                }else{
                    item.setPrecio("");
                }
                if(data.getJSONObject(i).has("description")){
                    item.setDescription(data.getJSONObject(i).getString("description"));
                }else{
                    item.setDescription("");
                }
                if(data.getJSONObject(i).has(" picture")){
                    item.setPicture(data.getJSONObject(i).getString(" picture"));
                }else{
                    item.setPicture(" picture");
                }
                datos.add(item);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        adapterMenu adapter = new adapterMenu(this.getContext(), datos);
        // ArrayAdapter adapter = new ArrayAdapter(this.getContext(),android.R.layout.simple_list_item_1, datos);
        list.setAdapter(adapter);
       }

    @Override
    public void onJsonLoad(JSONObject data) {

    }
    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

    }


    //***********************************************final camara***************************************************************
    */
}