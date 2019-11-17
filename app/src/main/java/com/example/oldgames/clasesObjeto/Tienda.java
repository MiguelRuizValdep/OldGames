package com.example.oldgames.clasesObjeto;

import java.io.Serializable;

public class Tienda implements Serializable {

    private int id;
    private String nombre;
    private String provincia;
    private String calle;
    private String numero;
    private String cpostal;
    private String telefono;
    private String foto;
    private String web;

    public Tienda(int id, String nombre, String provincia, String calle, String numero, String cpostal, String telefono,String foto,String web) {
        this.id = id;
        this.nombre = nombre;
        this.provincia = provincia;
        this.calle = calle;
        this.numero = numero;
        this.cpostal = cpostal;
        this.telefono = telefono;
        this.foto = foto;
        this.web=web;
    }

    public Tienda(){

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

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getCpostal() {
        return cpostal;
    }

    public void setCpostal(String cpostal) {
        this.cpostal = cpostal;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = Servidor.getServidor()+foto;
    }

    public String getWeb() {
        return web;
    }
    public void setWeb(String web) {
        this.web = web;
    }
}
