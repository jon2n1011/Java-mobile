package com.example.mp3jon.Objetos;

import java.util.ArrayList;
import java.util.Date;

public class PlayList {

    private int id;
    private String nom;
    private Date creacion;
    private int numcanciones;
    private ArrayList<Track> canciones;

    public PlayList(int id, String nom, Date creacion,int numcanciones ,ArrayList<Track> canciones) {
        this.id = id;
        this.nom = nom;
        this.creacion = creacion;
        this.canciones = canciones;
        this.numcanciones=numcanciones;
    }

    public int getNumcanciones() {
        return numcanciones;
    }

    public void setNumcanciones(int numcanciones) {
        this.numcanciones = numcanciones;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getCreacion() {
        return creacion;
    }

    public void setCreacion(Date creacion) {
        this.creacion = creacion;
    }

    public ArrayList<Track> getCanciones() {
        return canciones;
    }

    public void setCanciones(ArrayList<Track> canciones) {
        this.canciones = canciones;
    }
}
