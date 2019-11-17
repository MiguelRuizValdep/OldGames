package com.example.oldgames.clasesObjeto;



import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class Insercion extends StringRequest {


    private static Map<String,String> params;
    private final static String URL=Servidor.getServidor()+"insertar.php";

    public Insercion(Usuario user,Response.Listener<String> listener,Response.ErrorListener error){
        super(Method.POST,URL,listener,error);

        params= new HashMap<String,String>();

        params.put("alias",user.getAlias());
        params.put("clave",user.getPass());
        params.put("correo",user.getCorreo());
        params.put("ruta",user.getAvatar());

    }

    @Override
    public Map<String, String> getParams() {
        return params;
    }
}
