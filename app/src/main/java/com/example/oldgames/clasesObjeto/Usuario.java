package com.example.oldgames.clasesObjeto;

import java.io.Serializable;

public class Usuario implements Serializable {


    private String alias;
    private String pass;
    private String correo;
    private String avatar;



    public Usuario(String alias, String pass, String correo, String avatar) {

        this.alias = alias;
        this.correo = correo;
        this.avatar = avatar;
        this.pass = pass;
    }

    public Usuario(){

    }


    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
