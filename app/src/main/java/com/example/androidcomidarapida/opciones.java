package com.example.androidcomidarapida;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class opciones extends AppCompatActivity implements View.OnClickListener {
    Button registroRestaurante,crearMenu,lugar,pedidos,ordenes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones);
        registroRestaurante = findViewById(R.id.actRestaurante);
        crearMenu = findViewById(R.id.actCrearMenu);
        lugar = findViewById(R.id.actLugar);
        pedidos = findViewById(R.id.actPedidos);
        ordenes = findViewById(R.id.actOrdenes);

        registroRestaurante.setOnClickListener(this);
        crearMenu.setOnClickListener(this);
        lugar.setOnClickListener(this);
        pedidos.setOnClickListener(this);
        ordenes.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId()==R.id.actRestaurante){
            Intent registro=new Intent(opciones.this,registroRestaurtant.class);
            startActivity(registro);
        }else if (view.getId()==R.id.actCrearMenu){
            Intent crearMenu=new Intent(opciones.this,Menu.class);
            startActivity(crearMenu);
        }else if (view.getId()==R.id.actLugar){
            Intent lugar=new Intent(opciones.this,ListaRestaurantes.class);
            startActivity(lugar);
        }else if (view.getId()==R.id.actPedidos){
            Intent pedidos=new Intent(opciones.this,listaMenu.class);
            startActivity(pedidos);
        }else if (view.getId()==R.id.actOrdenes){
            Intent ordenes=new Intent(opciones.this,register.class);
            startActivity(ordenes);
        }

    }
}