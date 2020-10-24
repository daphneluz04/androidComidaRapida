package com.example.androidcomidarapida.utils;

public class EndPoints {
    public static String ip = "http://192.168.122.1:8000";;
    public static String HOST = "http://192.168.122.1:8000/"; // 192.168.122.1 este es la ip de internet por ahi se conectara con el api
   // public static String LOGIN_SERVICE = HOST +  "localhost:8000/user";
    public static String RESTAURANT_SERVICE = HOST +  "localhost:8000/restaurante";
    public static String MENU_SERVICE = HOST +  "localhost:8000";
    public static String UPLOAD_RESTORANT = HOST +  "localhost:8000/restaurante";


    public String getIp() {
        return this.ip= "http://192.168.122.1:8000/restaurante";
    }
    //UPLOAD_RESTORANT






}
