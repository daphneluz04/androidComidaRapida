package com.example.androidcomidarapida.ApiRestfull;

import com.example.androidcomidarapida.utils.EndPoints;
import com.example.androidcomidarapida.utils.UserDataServer;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MenuApi {
    private onLoadData interfaceevent;
    private AsyncHttpClient client;
    public MenuApi(onLoadData interfaceevent){
       this.interfaceevent=interfaceevent;
       client = new AsyncHttpClient();
    }
    //peticion d la api
    public void loadMenu(){
        client.addHeader("authorization", UserDataServer.TOKEN);
        client.get(EndPoints.MENU_SERVICE, new JsonHttpResponseHandler(){
           // @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                interfaceevent.onJsonArrayLoad(response);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                interfaceevent.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }
}











