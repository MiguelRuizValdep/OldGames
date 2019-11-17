package com.example.oldgames.clasesObjeto;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class Consola implements Serializable,Comparable<Consola> {

    private int id;
    private String nombre;
    private int anio;
    private String descripcion;
    private String company;
    private String logo;
    private String foto;
    private final String SERVIDOR=Servidor.getServidor();
    public Consola(){

    }

    public Consola(int id, String nombre, int anio, String descripcion, String company,String logo, String foto) {
        this.id = id;
        this.nombre = nombre;
        this.anio = anio;
        this.descripcion = descripcion;
        this.company = company;
        this.logo=SERVIDOR+logo;
        this.foto = SERVIDOR+foto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = SERVIDOR+logo;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = SERVIDOR+foto;
    }

    @Override
    public int compareTo(@NonNull Consola consola) {
        if(this.getAnio()<consola.getAnio()){
            return 1;
        }else if(this.getAnio()>consola.getAnio()){
            return -1;
        }
        return 0;
    }
}
