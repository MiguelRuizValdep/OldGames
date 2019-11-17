package com.example.oldgames.clasesObjeto;

import java.io.Serializable;

public class Truco implements Serializable {
    private String descripcion;
    private String truco;

    public Truco(String descripcion, String truco) {
        this.descripcion = descripcion;
        this.truco = truco;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTruco() {
        return truco;
    }

    public void setTruco(String truco) {
        this.truco = truco;
    }
}
