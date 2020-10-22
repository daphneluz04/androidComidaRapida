package com.example.androidcomidarapida.ApiRestfull;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public interface onLoadData {
    void onJsonLoad(JSONObject data);
    void onJsonArrayLoad(JSONArray data);

   // void onFailure(int statusCode, Header[] headers, byte[] errorResponse ,Throwable e);
   void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse);


}























