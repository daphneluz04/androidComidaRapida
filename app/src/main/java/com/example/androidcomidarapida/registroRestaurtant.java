package com.example.androidcomidarapida;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import com.example.androidcomidarapida.utils.EndPoints;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class registroRestaurtant extends AppCompatActivity implements View.OnClickListener{

    EditText restaurant,nit,propietario,calle,telf;
    Button send,back;
    //private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_restaurtant);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        //fab = findViewById(R.id.restauran1Send);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Bienvenido a las actividades", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                Intent intent = new Intent(registroRestaurtant.this, registro2resttaurant.class);
                registroRestaurtant.this.startActivity(intent);

            }
        });
        restaurant=findViewById(R.id.nombrerest);
        nit=findViewById(R.id.nitrest);
        propietario=findViewById(R.id.propietarioRest);
        calle=findViewById(R.id.CalleRest);
        telf=findViewById(R.id.telefonoRest);
        send=findViewById(R.id.restauran1Send);
        back=findViewById(R.id.bankrestaurant1);

        send.setOnClickListener(this);
        back.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.restauran1Send){
            enviar();
            //Intent in = new Intent(registroRestaurtant.this,registro2resttaurant.class);

            Intent in = new Intent(registroRestaurtant.this,registroCamara.class);
            startActivity(in);
            Snackbar.make(v, "Registrando datos", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();

            //para cancelar el registro
        }else if (v.getId()==R.id.bankrestaurant1){
            Intent in = new Intent(registroRestaurtant.this,registroRestaurtant.class);
            startActivity(in);
        }
    }

    private void enviar() {
        //direccion de la ip d
        //metodo post
        //restaurant,nit,propietario,calle,telf;
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams rq=new RequestParams();
        rq.put("name",restaurant.getText().toString());
        rq.put("nit",nit.getText().toString());
        rq.put("propietario",propietario.getText().toString());
        rq.put("street",calle.getText().toString());
        rq.put("telephone",telf.getText().toString()); //la palabra verde viene de el api

        client.post(EndPoints.HOST+"/restaurante", rq,new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    String res=response.getString("message");
                    Toast.makeText(getApplicationContext(), res, Toast.LENGTH_SHORT).show();
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

        });

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}








