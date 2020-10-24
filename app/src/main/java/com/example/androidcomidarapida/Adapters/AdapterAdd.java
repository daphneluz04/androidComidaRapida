package com.example.androidcomidarapida.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.androidcomidarapida.R;

import java.util.ArrayList;

public class AdapterAdd extends BaseAdapter {
    Context context;
    ArrayList<Uri> mArrayUri;
    LayoutInflater inflater;
    ImageView img;
    int pos=0;
    public AdapterAdd(Context context, ArrayList<Uri> mArrayUri){
        this.context=context;
        this.mArrayUri=mArrayUri;

    }
    @Override
    public int getCount() {
        return mArrayUri.size();
    }

    @Override
    public Object getItem(int position) {
        return mArrayUri.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        pos=position;
        inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView=inflater.inflate(R.layout.content_imagen_item_fragment1,parent,false);
        img=itemView.findViewById(R.id.ivGallery);
        img.setImageURI(mArrayUri.get(pos));
        return img;
    }
}




















