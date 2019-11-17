package com.example.oldgames.clasesActividad;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.example.oldgames.clasesObjeto.Servidor;
import com.example.oldgames.clasesObjeto.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

public class Login extends AppCompatActivity {
    private EditText etAlias,etPass;
    private TextView name;
    private Button btnLog,btnReg;
    private JsonArrayRequest jsArequest;
    private ImageView imagen;
    private Usuario usuario;
    private Typeface fuente,fuente2;
    private final String RUTA="fuentes/ChelaOne.ttf";
    private final String RUTA2="fuentes/personaje.ttf";
    private ObjectAnimator objectAnimator;
    private ObjectAnimator objectAnimatorText;
    private AnimatorSet animatorSet;
    private AnimatorSet animatorSetTetx;
    private final int DURACION =1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        fuente= Typeface.createFromAsset(getAssets(),RUTA);
        fuente2= Typeface.createFromAsset(getAssets(),RUTA2);

        etAlias=(EditText)findViewById(R.id.logalias);
        etPass=(EditText) findViewById(R.id.logpassword);
        name=(TextView) findViewById(R.id.logusuario);

        etAlias.setTypeface(fuente);
        etPass.setTypeface(fuente);
        name.setTypeface(fuente2);

        imagen=(ImageView) findViewById(R.id.logImvAvatar);

        btnLog=(Button) findViewById(R.id.btnLogin);
        btnReg=(Button) findViewById(R.id.btnlogRegister);

        btnReg.setTypeface(fuente);
        btnLog.setTypeface(fuente);

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(camposVacios()){
                    Toast.makeText(Login.this,"Se detectaron campos vacios.",Toast.LENGTH_LONG).show();
                }else{
                    btnLog.setEnabled(false);
                    validar(Servidor.getServidor()+"validar.php?alias="+etAlias.getText().toString());

                }

            }
        });

        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(Login.this,Registro.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void validar(String URL) {

        RequestQueue request = Volley.newRequestQueue(this);
        jsArequest= new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    JSONObject objeto=response.getJSONObject(0); // Como mucho uno

                        usuario= new Usuario();
                        usuario.setAlias(objeto.getString("alias"));
                        usuario.setCorreo(objeto.getString("correo"));
                        usuario.setPass(objeto.getString("clave"));
                        usuario.setAvatar(objeto.getString("ruta"));

                    if(usuario.getPass().equals(etPass.getText().toString())){
                        imagen.setImageResource(Integer.parseInt(usuario.getAvatar()));
                        name.setText(usuario.getAlias());

                        //Si se cumple la condición: animaciones e inicio del usuario.

                        animacionImagen();
                        animacionTexto();
                        iniciaConsolas(usuario);

                    }else{
                        Toast.makeText(Login.this,"Las contraseñas no coinciden",Toast.LENGTH_LONG).show();
                        btnLog.setEnabled(true);
                    }

                }catch (JSONException e){
                    Toast.makeText(Login.this,"Usuario no encontrado",Toast.LENGTH_LONG).show();
                    btnLog.setEnabled(true);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this,""+error,Toast.LENGTH_LONG).show();
                btnLog.setEnabled(true);
            }
        });

        request.add(jsArequest);
    }


    private void animacionImagen() {
        objectAnimator = ObjectAnimator.ofFloat(imagen, "x", -200f,imagen.getX());
        objectAnimator.setDuration(DURACION);
        animatorSet= new AnimatorSet();
        animatorSet.play(objectAnimator);
        animatorSet.start();

    }

    private void animacionTexto(){
        objectAnimatorText=ObjectAnimator.ofFloat(name,"x",600f,name.getX());
        objectAnimatorText.setDuration(DURACION);
        animatorSetTetx= new AnimatorSet();
        animatorSetTetx.play(objectAnimatorText);
        animatorSetTetx.start();
        name.setBackground(getResources().getDrawable(R.drawable.marco_seis));
    }

    private void iniciaConsolas(Usuario user){

        TimerTask task= new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(Login.this,ActivityConsola.class);
                intent.putExtra("user",(Serializable)usuario);
                startActivity(intent);
                finish();
            }
        };
        Timer timer= new Timer();
        timer.schedule(task,2000);
    }

    private boolean camposVacios(){
        boolean vacios=false;

        if(etAlias.getText().toString().isEmpty() | etPass.getText().toString().isEmpty()){
            vacios=true;
        }
        return vacios;
    }
}
