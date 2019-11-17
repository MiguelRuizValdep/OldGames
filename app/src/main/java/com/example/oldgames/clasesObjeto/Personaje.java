package com.example.oldgames.clasesObjeto;

import java.io.Serializable;

public class Personaje implements Serializable {
    private String nombre;
    private String foto;
    private int id;
    private final String SERVIDOR=Servidor.getServidor();

    public Personaje(){

    }

    public Personaje(String nombre, String foto, int id) {
        this.nombre = nombre;
        this.foto = SERVIDOR+foto;
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = SERVIDOR+foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
