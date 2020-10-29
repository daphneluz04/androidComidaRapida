package com.example.androidcomidarapida.Adapters;

public class ItemList { //clase para la estructura
    private String poster;
    private String name;
    private String precio;
    private String description;
    private String _id;


    public ItemList(String poster, String name, String precio, String description, String _id){
        this.poster=poster;
        this.name=name;
        this.precio=precio;
        this.description=description;
        this._id=_id;

    }

    public String getPoster() {
        return poster;
    }
    public String getName() {
        return this.name;
    }

    public String getPrecio() {
        return this.precio;
    }

    public String getDescription() {
        return this.description;
    }

    public String get_id() {
        return _id;
    }

}
