package com.example.androidcomidarapida;

import android.content.Context;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class listaMenu extends AppCompatActivity {
    private ListView LIST;
    private Context root;
    private ArrayList<ItemList> LISTINFO;
    private CustonAdapter ADAPTER;

    private OnLoadImage interfaceevent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        root = this;

        LISTINFO = new ArrayList<ItemList>() ;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_menu);
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

    private void loadComponents() {
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
}