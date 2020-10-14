package com.example.androidcomidarapida;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidcomidarapida.utils.EndPoints;
import com.example.androidcomidarapida.utils.UserDataServer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.entity.mime.Header;

public class MainActivity extends AppCompatActivity {
    Button registerButton;
    Button loginButton;
    //static final int code_camera = 999;
    private MainActivity root = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // if (camera.resolveActivity(root.getPackageManager()) != null){
         //    root.startActivityForResult(camera, code_camera);
        // }

         loadComponents();
            }

    private void loadComponents() {
          loginButton = this.findViewById(R.id.login);
          registerButton =this.findViewById(R.id.register);
          registerButton.setOnClickListener(new View.OnClickListener() {
              //metodo del boton al sig actividad
              @Override
              public void onClick(View view) {
                    Intent registerActivity = new Intent(root , registroRestaurtant.class);
                    root.startActivity(registerActivity);


                  //aqui envio de la api
        AsyncHttpClient client = new AsyncHttpClient();
        EditText nombre =  root.findViewById(R.id.nombre);
        EditText email =root.findViewById(R.id.email);
        EditText password = root.findViewById(R.id.email);

        RequestParams params = new RequestParams();

        params.add("nombre",nombre.getText().toString());  //la palabra en en verde debe ser la misma de la api
        params.add("email",email.getText().toString());
        params.add("password",password.getText().toString());

        client.post(EndPoints.LOGIN_SERVICE,params, new JsonHttpResponseHandler(){

          //  @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    if (response.has("msn")){
                        UserDataServer.MSM = response.getString("msn");
                    }
                    if (response.has("token")){
                        UserDataServer.TOKEN = response.getString("token");
                    }
                    if (UserDataServer.TOKEN.length()> 150){
                        Intent intent = new Intent(root, MainActivity.class);
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






















