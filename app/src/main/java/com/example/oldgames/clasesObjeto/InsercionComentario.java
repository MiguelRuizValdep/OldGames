package com.example.oldgames.clasesObjeto;

import android.support.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class InsercionComentario extends StringRequest {
    private static Map<String,String> params;
    private final static String URL=Servidor.getServidor()+"insetarMensaje.php";

    public InsercionComentario(Comentario comentario,Response.Listener<String> listener, @Nullable Response.ErrorListener errorListener) {
        super(Method.POST, URL, listener, errorListener);

        params= new HashMap<String,String>();

        params.put("idjuego",String.valueOf(comentario.getIdJuego()));
        params.put("alias",comentario.getAlias());
        params.put("mensaje",comentario.getMensaje());
        params.put("titulo",comentario.getTitulo());
    }

    @Override
    public  Map<String, String> getParams() {
        return params;
    }
}
