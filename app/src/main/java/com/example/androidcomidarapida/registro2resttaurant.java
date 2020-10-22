package com.example.androidcomidarapida;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidcomidarapida.utils.EndPoints;
import com.example.androidcomidarapida.utils.UserDataServer;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class registro2resttaurant extends AppCompatActivity implements OnMapReadyCallback{
    //mapa
    private MapView map;
    private GoogleMap mMap;
    private Geocoder geocoder;
    private LatLng mainposition;
    private TextView street;
    private Button fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro2resttaurant);
        //mapa
        map = findViewById(R.id.mapView);
        map.onCreate(savedInstanceState);
        map.onResume();
        MapsInitializer.initialize(this);
        map.getMapAsync(this);
        geocoder = new Geocoder(getBaseContext(), Locale.getDefault());
        //calle = findViewById(R.id.calle);
        //hasta aqui mapa



        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                sendData();
                //luego ponr la condicion de if
                Snackbar.make(view, "Registrando datos espere por favor", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                //Intent registroCamara = new Intent(registro2resttaurant.this, registroCamara.class);
               // registro2resttaurant.this.startActivity(registroCamara);

                }
        });
    }

    private void sendData() {
        AsyncHttpClient client = new AsyncHttpClient();
        client.addHeader("authorization", UserDataServer.TOKEN);

        RequestParams rq=new RequestParams();
        rq.put("Lat", String.valueOf(""));
        rq.put("Log", String.valueOf(""));

        client.post(EndPoints.HOST+"/restaurante", rq,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                AlertDialog alertDialog = new AlertDialog.Builder(registro2resttaurant.this).create();
                try {
                    String res=response.getString("message");
                    alertDialog.setTitle("RESPONSE SERVER");
                    alertDialog.setMessage(res);
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    alertDialog.show();
                    Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

        });
        Intent camera = new Intent(registro2resttaurant.this, registroCamara.class);
        registro2resttaurant.this.startActivity(camera);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng potosi = new LatLng(-19.5783329, -65.7563853);
        mainposition = potosi;
        mMap.addMarker(new MarkerOptions().position(potosi).title("Lugar").zIndex(17).draggable(true));
        mMap.setMinZoomPreference(16);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(potosi, 15));

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
                String street_string = getStreet(marker.getPosition().latitude, marker.getPosition().longitude);
                street.setText(street_string);
            }
        });
    }
    public String getStreet (Double lat, Double lon) {
        List<Address> address;
        String result = "";
        try {
            address = geocoder.getFromLocation(lat, lon, 1);
            result += address.get(0).getThoroughfare();


        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}