package com.example.mp3jon.Objetos;

import android.graphics.Bitmap;

public class Track {

    private int id;
    private Bitmap imagen;
    private String nombre;
    private String path;

    public Track(int id, Bitmap imagen, String nombre, String path) {
        this.id = id;
        this.imagen = imagen;
        this.nombre = nombre;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public Bitmap setImagen(Bitmap imagen) {
        this.imagen = imagen;
        return  imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
