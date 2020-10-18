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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.androidcomidarapida.Adapters.StructMenu;
import com.example.androidcomidarapida.Adapters.adapterMenu;
import com.example.androidcomidarapida.ApiRestfull.MenuApi;
import com.example.androidcomidarapida.ApiRestfull.onLoadData;
import com.example.androidcomidarapida.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class DashboardFragment extends Fragment implements View.OnClickListener{
    //private host HOST =new host();
    static final int PERMISION_CODE = 123;
    static final int code_camera = 999;
    Button TakePhoto;

    private DashboardViewModel dashboardViewModel;
    private ListView list;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard); //aqui llamar desde el boton al fragment
        //es para ir del boton crear menu a el fracment menu
        // root.findViewById(R.id.buttonCrearMenu);
        //root.findViewById(R.id.)
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);

            }
        });
        return root;

    }
    //***************************************permisos para el uso de la camara***********************************
    public void onStart() {
        super.onStart();

        TakePhoto = this.getActivity().findViewById(R.id.buttonfotoMenu);
        TakePhoto.setOnClickListener(this);

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

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            this.requestPermissions(new String[]{Manifest.permission.CAMERA},PERMISION_CODE);
        }
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

    //***********************************************final camara***************************************************************

   /* public void onStart() {
        super.onStart();
         list = this.getActivity().findViewById(R.id.milistamenu);
         ArrayList<StructMenu> datos = new ArrayList<>();
         MenuApi api = new MenuApi(this);
         api.loadMenu();

       /* for (int i=0; i <= 100 ; i++){
            //datos.add("Item  " + i);
            StructMenu item = new StructMenu();
            item.setName("itemNombre"+ i);
            item.setPrecio("itemPrecio"+ i);
            item.setDescription("itemDescripcion"+i);
            item.setPicture("NO IMAGE"+i);
            datos.add(item);

        }
    }

    @Override
    public void onJsonLoad(JSONObject data) {

    }

    @Override
    public void onJsonArrayLoad(JSONArray data) {
        ArrayList<StructMenu> datos = new ArrayList<>();
        for (int i = 0 ;i<data.length();i++){
            StructMenu item = new StructMenu();
            try {
                if (data.getJSONObject(i).has("itemNombre")){ //aqui cambiar si no da
                    item.setName(data.getJSONObject(i).getString("itemNombre"));
                }else{
                    item.setName("");
                }
                if (data.getJSONObject(i).has("itemPrecio")){
                    item.setPrecio(data.getJSONObject(i).getString("itemPrecio"));
                }else{
                    item.setPrecio("");
                }
                if(data.getJSONObject(i).has("itemDescripcion")){
                    item.setDescription(data.getJSONObject(i).getString("itemDescripcion"));
                }else{
                    item.setDescription("");
                }
                if(data.getJSONObject(i).has("itemImagen")){
                    item.setPicture(data.getJSONObject(i).getString("itemImagen"));
                }else{
                    item.setPicture("");
                }
                datos.add(item);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        adapterMenu adapter = new adapterMenu(datos, this.getContext());
        // ArrayAdapter adapter = new ArrayAdapter(this.getContext(),android.R.layout.simple_list_item_1, datos);
        list.setAdapter(adapter);
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

    }


    //aqui camara
}
*/




}
