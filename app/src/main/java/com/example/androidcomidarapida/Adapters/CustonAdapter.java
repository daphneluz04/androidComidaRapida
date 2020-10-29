package com.example.androidcomidarapida.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.androidcomidarapida.R;
import com.example.androidcomidarapida.utils.EndPoints;

import java.util.ArrayList;

public class CustonAdapter extends BaseAdapter implements OnLoadImage {
    private Context CONTEXT;
    private ArrayList<ItemList>LIST; //mostrara la lista que se debe mostrar
    public CustonAdapter(Context context, ArrayList<ItemList> list){
        this.CONTEXT = context;
        this.LIST=list;

    }

    @Override
    public int getCount() {
        return this.LIST.size();
    }

    @Override
    public Object getItem(int i) {
        return this.LIST.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) { //aqui se carga la iformacon layout
       //se inflara una vista para inflar otra vista
        if (view == null ){
            LayoutInflater inflater =(LayoutInflater) this.CONTEXT.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.item_layout, null);
            view = inflater.inflate(R.layout.item_layout, null);

        }
        TextView name = (TextView)view.findViewById(R.id.name);
        TextView precio = (TextView)view.findViewById(R.id.precio);
        TextView description = (TextView)view.findViewById(R.id.descripcion);

        name.setText(this.LIST.get(i).getName());
        precio.setText(this.LIST.get(i).getPrecio());
        description.setText(this.LIST.get(i).getDescription());
        //aqui se pondra la image    aqui cabiar img por picture
        ImageView img = (ImageView)view.findViewById(R.id.Poster);
       //cargar la imagen  //falta hilo para la imagen
        Glide.with(CONTEXT).load(EndPoints.ip+this.LIST.get(i).getPoster()).into(img);

        //TasKimg hilo = new TasKimg();
       // hilo.setLoadImage(img, this);
       // hilo.execute("https://images-wixmp-ed30a86b8c4ca887773594c2.wixmp.com/f/651261e0-270d-49b6-bba3-3cd895b6ebbe/dbrtvqw-3740f1e0-8599-4977-a032-c0e5d337e25a.png/v1/fill/w_1024,h_1024,strp/jack_skeleton_jack_skellington_by_juanuzcategui93_dbrtvqw-fullview.png?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1cm46YXBwOiIsImlzcyI6InVybjphcHA6Iiwib2JqIjpbW3siaGVpZ2h0IjoiPD0xMDI0IiwicGF0aCI6IlwvZlwvNjUxMjYxZTAtMjcwZC00OWI2LWJiYTMtM2NkODk1YjZlYmJlXC9kYnJ0dnF3LTM3NDBmMWUwLTg1OTktNDk3Ny1hMDMyLWMwZTVkMzM3ZTI1YS5wbmciLCJ3aWR0aCI6Ijw9MTAyNCJ9XV0sImF1ZCI6WyJ1cm46c2VydmljZTppbWFnZS5vcGVyYXRpb25zIl19.mk6U_XiD-d-zvRcPt7_3tR0o_ML1bzjqVu4wNoStD4M");//EndPoints.ip+this.LIST.get(i).getPoster());
      //  hilo.execute(EndPoints.ip+this.LIST.get(i).getPoster());//EndPoints.ip+this.LIST.get(i).getPoster());
        Log.i("image", "url: "+ EndPoints.ip+this.LIST.get(i).getPoster());

        return view;
    }

    @Override
    public void setLoadImage(ImageView container, Bitmap img) {

        container.setImageBitmap(img);
    }
}











