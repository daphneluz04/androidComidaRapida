package com.example.androidcomidarapida;

import android.os.Bundle;

import com.example.androidcomidarapida.ApiRestfull.RecyclerViewAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ListaRestaurantes extends AppCompatActivity {

    List<ItemRestaurant> lstBook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_restaurantes);


        lstBook = new ArrayList<>();
        lstBook.add(new ItemRestaurant("el fogon","category book","Description book",R.drawable.imagen1));
        lstBook.add(new ItemRestaurant("la casona","category book","Description book",R.drawable.imagen2));
        lstBook.add(new ItemRestaurant("pollo rico","category book","Description book",R.drawable.imagen3));
        lstBook.add(new ItemRestaurant("vegetarian","category book","Description book",R.drawable.imagen4));


        RecyclerView myrv = (RecyclerView) findViewById(R.id.RecyclerView);
        RecyclerViewAdapter myadapter = new RecyclerViewAdapter(this,lstBook);
        myrv.setLayoutManager(new GridLayoutManager(this,2));
        myrv.setAdapter(myadapter);
    }
}