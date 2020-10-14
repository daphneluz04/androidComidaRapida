package com.example.androidcomidarapida;

import android.content.Intent;
import android.location.Geocoder;
import android.os.Bundle;

import com.example.androidcomidarapida.utils.EndPoints;
import com.example.androidcomidarapida.utils.UserDataServer;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.model.LatLng;
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

import cz.msebera.android.httpclient.entity.mime.Header;

public class registroRestaurtant extends AppCompatActivity {
    Button registerButton;
    //static final int code_camera = 999;
    private registroRestaurtant root = this;


    private MapView map;
    private GoogleMap mMap;
    private Geocoder geocoder;
    private TextView street;
    private Button next;
    private LatLng mainposition;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_restaurtant);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        loadComponents();
    }

    private void loadComponents() {
        registerButton =this.findViewById(R.id.login);
        registerButton.setOnClickListener(new View.OnClickListener() {
            //metodo del boton al sig actividad
            @Override
            public void onClick(View view) {
                Intent registerActivity = new Intent(root , registro2resttaurant.class);
                root.startActivity(registerActivity);
                //aqui envio de la api
        AsyncHttpClient client = new AsyncHttpClient();
        EditText restaurant =  root.findViewById(R.id.nombreLogin);
        EditText nit =root.findViewById(R.id.emailLogin);
        EditText propietario = root.findViewById(R.id.emailLogin);
        EditText calle = root.findViewById(R.id.botonpropietario);
        EditText telefono = root.findViewById(R.id.botonpropietario);


        RequestParams params = new RequestParams();

        params.add("name",restaurant.getText().toString());  //la palabra en en verde debe ser la misma de la api
        params.add("nit",nit.getText().toString());
        params.add("propietario",propietario.getText().toString());
        params.add("street",calle.getText().toString());
        params.add("telephone",telefono.getText().toString());

        client.post(EndPoints.RESTAURANT_SERVICE,params, new JsonHttpResponseHandler(){

          //  @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.has("msn")){
                        UserDataServer.MSN = response.getString("msn");
                    }
                    if (response.has("token")){
                        UserDataServer.TOKEN = response.getString("token");
                    }
                    if (UserDataServer.TOKEN.length()> 150){
                        Intent intent = new Intent(root, registroRestaurtant.class);
                        root.startActivity(intent);
                    }else {
                        Toast.makeText(root, response.getString("msn"), Toast.LENGTH_LONG).show();
                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }); //hasta aqui la api de registro
            }

        });
    }

}

