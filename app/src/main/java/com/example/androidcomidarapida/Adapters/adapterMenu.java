package com.example.androidcomidarapida.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.androidcomidarapida.R;

import java.util.ArrayList;

public class adapterMenu extends BaseAdapter {
    private ArrayList<StructMenu> MENUDATA;
    private Context context;
    public adapterMenu(ArrayList<StructMenu> data, Context context){
        MENUDATA = data;
        this.context = context;
    }

    @Override
    public int getCount() { //cuantos elementos tendra ka lista

        return MENUDATA.size();
    }

    @Override
    public Object getItem(int i) {

        return MENUDATA.get(i);
    }

    @Override
    public long getItemId(int i) {

        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) { //infla la vista
       //aqui poner datos e inflar la vista y verificar si la vista existe
        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_menu,null);
           //cargar la lista
            //nombre
            TextView  itemNombre = view.findViewById(R.id.itemNombre);
            itemNombre.setText(MENUDATA.get(i).getName());
            //precio
            TextView  itemPrecio = view.findViewById(R.id.itemPrecio);
            itemPrecio.setText(MENUDATA.get(i).getPrecio());
            //descripcion
            TextView  itemDescripcion = view.findViewById(R.id.itemDescripcion);
            itemDescripcion.setText(MENUDATA.get(i).getDescription());
            //imagen
            ImageView imageView = view.findViewById(R.id.itemImagen);

            //para la imagen
            if (MENUDATA.get(i).getPicture().equals("NO IMAGE")){
//https://thumbs.dreamstime.com/z/plato-comida-caliente-icono-del-restaurante-134749600.jpg
                Glide.with(context)
                        .load("https://thumbs.dreamstime.com/z/plato-comida-caliente-icono-del-restaurante-134749600.jpg")
                        .centerCrop()
                        .into(imageView);

            }else{
                Glide.with(context)
                        .load(MENUDATA.get(i).getPicture())
                        .centerCrop()
                        .into(imageView);
            }
        }
        return view;
    }
}
















