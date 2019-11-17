package com.example.oldgames.clasesObjeto;

import java.util.Date;

public class Comentario implements Comparable<Comentario> {


    private int idJuego;
    private String alias;
    private String mensaje;
    private String ruta;
    private Date fecha;
    private String titulo;


    //Constructor para recuperación desde BD
    public Comentario(String alias, String mensaje,String ruta,Date fecha,String titulo) {
        this.alias = alias;
        this.mensaje = mensaje;
        this.ruta=ruta;
        this.fecha = fecha;
        this.titulo=titulo;
    }

    //Constructor para inserción en BD
    public Comentario(int idJuego, String alias, String mensaje,String titulo){
        this.idJuego = idJuego;
        this.alias = alias;
        this.mensaje = mensaje;
        this.titulo=titulo;
    }

    public Comentario(){

    }


    public int getIdJuego() {
        return idJuego;
    }

    public void setIdJuego(int idJuego) {
        this.idJuego = idJuego;
    }

    public String getAlias() {
        return alias;
    }

    public void setIdUsuario(String alias) { this.alias = alias; }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getRuta() {
        return ruta;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    @Override
    public int compareTo(Comentario o) {

        if(fecha.compareTo(o.fecha) >0){
         return -1;
        }
        if(fecha.compareTo(o.fecha) <0){
            return 1;
        }
        return 0;
    }
}
