package com.example.oldgames.clasesObjeto;

import java.io.Serializable;

public class Juego implements Serializable {

    private int id;
    private int idConsola;
    private String genero;
    private String nombre;
    private int anio;
    private String descripcion;
    private String company;
    private String caratula;
    private String orientacion;
    private final String SERVIDOR=Servidor.getServidor();

    public Juego (){

    }

    public Juego(int id, int idConsola,String genero, String nombre, int anio, String descripcion, String company, String caratula,String orientacion) {
        this.id = id;
        this.idConsola = idConsola;
        this.genero=genero;
        this.nombre = nombre;
        this.anio = anio;
        this.descripcion = descripcion;
        this.company = company;
        this.caratula = SERVIDOR+caratula;
        this.orientacion=orientacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdConsola() {
        return idConsola;
    }

    public void setIdConsola(int idConsola) {
        this.idConsola = idConsola;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
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

    public String getCaratula() {
        return caratula;
    }

    public void setCaratula(String caratula) {
        this.caratula = SERVIDOR+caratula;
    }

    public String getorientacion() {
        return orientacion;
    }

    public void setorientacion(String orientacion) {
        this.orientacion = orientacion;
    }

    public String getSERVIDOR() {
        return SERVIDOR;
    }
}
