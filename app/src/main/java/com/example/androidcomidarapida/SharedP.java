package com.example.androidcomidarapida;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedP {
    private String token;
    private String idUs;
    private String name;
    public SharedP(Context ctx){
        SharedPreferences pref=ctx.getSharedPreferences("datauser",Context.MODE_PRIVATE);
        token=pref.getString("token","");
        idUs=pref.getString("idUs","");
        name=pref.getString("name","");

    }
    public String getToken() {
        return token;
    }

    public String getIdUs() {
        return idUs;
    }

    public String getName() {
        return name;
    }

}
