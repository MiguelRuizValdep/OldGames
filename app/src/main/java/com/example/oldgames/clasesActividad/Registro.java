package com.example.oldgames.clasesActividad;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.oldgames.R;
import com.example.oldgames.clasesObjeto.Insercion;
import com.example.oldgames.clasesObjeto.Servidor;
import com.example.oldgames.clasesObjeto.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registro extends AppCompatActivity {
    private ImageButton izq,der;
    private Button registro;
    private EditText alias,email,password;
    private ImageView avatar;
    private TextView tv;
    private Typeface fuente;
    private final String RUTA="fuentes/ChelaOne.ttf";
    private ArrayList<Integer> avatares;
    private Integer indice=0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);



        izq=(ImageButton) findViewById(R.id.imbIzquierda);
        der=(ImageButton) findViewById(R.id.imbDerecha);
        registro=(Button) findViewById(R.id.btnregistro);


        tv=(TextView) findViewById(R.id.avatar);

        fuente= Typeface.createFromAsset(getAssets(),RUTA);

        tv.setTypeface(fuente);
        registro.setTypeface(fuente);

        alias=(EditText) findViewById(R.id.alias);
        alias.setTypeface(fuente);
        email=(EditText) findViewById(R.id.correo);
        email.setTypeface(fuente);
        password=(EditText) findViewById(R.id.regpassword);
        password.setTypeface(fuente);

        avatar=(ImageView) findViewById(R.id.imvAvatar);
        avatares=cargaAvatares();

        avatar.setImageResource(avatares.get(indice));

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(alias.getText().length()>0 & email.getText().length()>0 & password.getText().length()>0){
                    if(validarAlias(alias.getText().toString()) & validarCorreo(email.getText().toString()) & validarPass(password.getText().toString())){
                        registro.setEnabled(false);
                        String URL= Servidor.getServidor()+"allusers.php";
                        verificaDuplicados(URL);
                    }
                }
                else{
                    Toast.makeText(Registro.this,"Se detectaron campos vacios",Toast.LENGTH_LONG).show();
                    registro.setEnabled(false);
                }
            }
        });

        izq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(indice>0){
                    indice --;
                }
                avatar.setImageResource(avatares.get(indice));

            }
        });

        der.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(indice<avatares.size()-1){
                   indice++;
               }
                avatar.setImageResource(avatares.get(indice));

            }
        });
    }

    public ArrayList <Integer> cargaAvatares(){
        ArrayList<Integer> avat= new ArrayList<>();

        avat.add(R.drawable.axel);
        avat.add(R.drawable.blaze);
        avat.add(R.drawable.ryu);
        avat.add(R.drawable.chun_li);
        avat.add(R.drawable.sonic);
        avat.add(R.drawable.mario);
        avat.add(R.drawable.luigi);
        avat.add(R.drawable.paul);
        return avat;
    }

    private boolean validarAlias(String cad){
        if(cad.length()<3 | cad.length()>20){
            Toast.makeText(Registro.this,"El alias no puede tener menos de 3 caracteres o más de 20 caracteres",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean validarPass(String cad){
        if(cad.length()<4 | cad.length()>20){
            Toast.makeText(Registro.this,"La clave es una cadena alfanumérica formada por entre 4 y 20 caracteres ",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private boolean validarCorreo(String cad){
        boolean retorno=false;

        Pattern patron=Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher=patron.matcher(cad);

        if(matcher.find()==true & cad.length()<=50){
            retorno=true;

        }else if(matcher.find()==true & cad.length()>50){
            Toast.makeText(Registro.this,"El correo electrónico no puede exceder de 50 caracteres",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(Registro.this,"El correo electrónico no parece correcto",Toast.LENGTH_LONG).show();
        }

        return retorno;
    }

    private void verificaDuplicados(String URL){

        JsonArrayRequest jsArequest;

        RequestQueue request = Volley.newRequestQueue(this);
        jsArequest= new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                boolean duplicado= false;
                try{
                    for (int i=0; i<response.length(); i++){
                        JSONObject objeto=response.getJSONObject(i);

                        Usuario usuario= new Usuario();
                        usuario.setAlias(objeto.getString("alias"));
                        usuario.setCorreo(objeto.getString("correo"));
                        usuario.setPass(objeto.getString("clave"));
                        usuario.setAvatar(objeto.getString("ruta"));

                        if(usuario.getAlias().equals(alias.getText().toString())){
                            Toast.makeText(Registro.this,"El alias ya existe en la base de datos",Toast.LENGTH_LONG).show();
                            duplicado=true;
                            registro.setEnabled(true);
                            break;
                        }

                        if(usuario.getCorreo().equals(email.getText().toString())){
                            Toast.makeText(Registro.this,"El correo electrónico ya existe en la base de datos",Toast.LENGTH_LONG).show();
                            duplicado=true;
                            registro.setEnabled(true);
                            break;
                        }
                    }

                    if(!duplicado){

                           final String al=alias.getText().toString();
                           final String pass=password.getText().toString();
                           final String correo=email.getText().toString();
                           final String ruta=avatares.get(indice).toString();

                           Response.Listener <String> listener= new Response.Listener<String>() {
                               @Override
                               public void onResponse(String response) {
                                   try {
                                       JSONObject obj=new JSONObject(response);
                                       boolean success=obj.getBoolean("success");

                                       if(success){
                                           Toast.makeText(Registro.this,"Usuario insertado correctamente",Toast.LENGTH_LONG).show();
                                           Intent intent= new Intent(Registro.this,ActivityConsola.class);
                                           intent.putExtra("user",(Serializable)new Usuario(al,pass,correo,ruta));
                                           startActivity(intent);
                                           finish();
                                       }else{
                                           Toast.makeText(Registro.this,"Fallo de inserción",Toast.LENGTH_LONG).show();
                                           registro.setEnabled(true);
                                       }

                                   } catch (JSONException e) {
                                       Toast.makeText(Registro.this,e.getMessage(),Toast.LENGTH_LONG).show();
                                       registro.setEnabled(true);
                                   }
                               }
                           };

                           Response.ErrorListener error= new Response.ErrorListener() {
                               @Override
                               public void onErrorResponse(VolleyError error) {
                                   Toast.makeText(Registro.this,error.getMessage(),Toast.LENGTH_LONG).show();
                                   registro.setEnabled(true);
                               }
                           };

                           Usuario user= new Usuario(al,pass,correo,ruta);
                           Insercion insercion= new Insercion(user,listener,error);

                           RequestQueue queue=Volley.newRequestQueue(Registro.this);
                           queue.add(insercion);


                    }

                }catch (JSONException e){
                    Toast.makeText(Registro.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    registro.setEnabled(true);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Registro.this,""+error,Toast.LENGTH_LONG).show();
                registro.setEnabled(true);
            }
        });

        request.add(jsArequest);


    }

    //Sobreescribo lo que hace el botón fisico atrás
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(Registro.this,Login.class);
        startActivity(intent);
        finish();
    }


}



