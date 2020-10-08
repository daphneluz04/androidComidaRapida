package com.example.androidcomidarapida;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button registerButton;
    //static final int code_camera = 999;
    private MainActivity root = this;


    private MapView map;
    private GoogleMap mMap;
    private Geocoder geocoder;
    private TextView street;
    private Button next;
    private LatLng mainposition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //para ocultar las letas android comida rapida
        //getSupportActionBar().hide();

        //Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // if (camera.resolveActivity(root.getPackageManager()) != null){
         //    root.startActivityForResult(camera, code_camera);
        // }

         loadComponents();
            }

    private void loadComponents() {
          registerButton =this.findViewById(R.id.register);
          registerButton.setOnClickListener(new View.OnClickListener() {
              //metodo del boton al sig actividad
              @Override
              public void onClick(View view) {
                    Intent registerActivity = new Intent(root , registro2resttaurant.class);
                    root.startActivity(registerActivity);
              }
          });


    }





}






















