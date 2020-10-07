package com.example.androidcomidarapida;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class camara extends AppCompatActivity {
    private  final int CODE = 100;
    private  final int CODE_PERMISSIONS = 101;
    private ImageView IMG;
    private ImageButton btn;
    private Button SEND;
    //private BitmapStruct DATAIMAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camara);
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
        btn = findViewById(R.id.camera);

        SEND = findViewById(R.id.register);
        IMG = findViewById(R.id.image);
        btn.setVisibility(View.INVISIBLE);
       // if (reviewPermissions()) {
        //    btn.setVisibility(View.VISIBLE);

       // }
        //Intent camera = new Intent(MainActivity.this, camara.class);
       // MainActivity.this.startActivity(camera);

    }
}