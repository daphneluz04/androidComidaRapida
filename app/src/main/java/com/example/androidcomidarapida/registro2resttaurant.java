package com.example.androidcomidarapida;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class registro2resttaurant extends AppCompatActivity implements View.OnClickListener{
    static final int PERMISION_CODE = 123;
    static final int code_camera = 999;
    Button TakePhoto;
    private MapView map;
    private GoogleMap mMap;
    private Geocoder geocoder;
    private TextView street;
    private Button next;
    private LatLng mainposition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro2resttaurant);
        //mapa
        map = findViewById(R.id.mapView);
        map.onCreate(savedInstanceState);
        map.onResume();
        MapsInitializer.initialize(this);
        //map.getMapAsync(this);
        geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
       // street = findViewById(R.id.street);
       // next = findViewById(R.id.next);
       // sendData();

        //mapa

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Registrando datos espere por favor", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent registro2 = new Intent(registro2resttaurant.this, registroDos.class);
                registro2resttaurant.this.startActivity(registro2);
            }
        });

        loadComponents();
        //nit=findViewById(R.id.nitrest);

    }

    //mapa
    //@Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //-19.5783329,-65.7563853
        LatLng potosi = new LatLng(-19.5783329, -65.7563853);
        mainposition = potosi;
        mMap.addMarker(new MarkerOptions().position(potosi).title("Lugar").zIndex(17).draggable(true));
        mMap.setMinZoomPreference(16);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(potosi));

        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                mainposition = marker.getPosition();
                //String street_string = getStreet(marker.getPosition().latitude, marker.getPosition().longitude);
                // street.setText(street_string);
            }
        });
    }


    //mapa

    private void loadComponents() {
        TakePhoto = this.findViewById(R.id.photobtn);
        TakePhoto.setOnClickListener(this);
    }

    //***************************************permisos para el uso de la camara***********************************
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




    public boolean checkPermisiion(String permission){
        int result = this.checkCallingOrSelfPermission(permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void onClick(View view) {
        //aqui trasladar
        if (checkPermisiion(Manifest.permission.CAMERA)){
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
            ImageView img = this.findViewById(R.id.imageView);  //imageView es el nombre de donde se mostrara la imagen en contect registro
            img.setImageBitmap(imageBitmap);
        }
    }

    //***********************************************final camara***************************************************************




}