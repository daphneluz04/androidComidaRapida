package com.example.androidcomidarapida.ui.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.androidcomidarapida.R;

import java.util.ArrayList;

public class DashboardFragment extends Fragment  {


    private DashboardViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        final TextView textView = root.findViewById(R.id.text_dashboard); //aqui llamar
        //root.findViewById(R.id.)
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);

            }
        });
        return root;

    }

    public void onStart() {
        super.onStart();
        ListView list = this.getActivity().findViewById(R.id.milistamenu);
        ArrayList<String> datos = new ArrayList<>();
        for (int i=0; i <= 100 ; i++){
            datos.add("Item  " + i);
        }
        ArrayAdapter adapter = new ArrayAdapter(this.getContext(),android.R.layout.simple_list_item_1, datos);
        list.setAdapter(adapter);
    }



    //aqui camara
}