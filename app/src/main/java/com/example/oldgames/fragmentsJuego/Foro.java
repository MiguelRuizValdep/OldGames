package com.example.oldgames.fragmentsJuego;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.oldgames.R;
import com.example.oldgames.clasesActividad.DetalleJuego;
import com.example.oldgames.clasesAdaptador.AdaptadorMensajes;
import com.example.oldgames.clasesObjeto.Comentario;
import com.example.oldgames.clasesObjeto.InsercionComentario;
import com.example.oldgames.clasesObjeto.Juego;
import com.example.oldgames.clasesObjeto.Servidor;
import com.example.oldgames.clasesObjeto.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;


public class Foro extends Fragment {
    private TextView userName;
    private ImageView userImage;
    private EditText etMensaje, etAsunto;
    private Button btnNuevo,btnCancelar,btnOrdena,btnRecarga;
    private RecyclerView recycler;
    private RecyclerView.LayoutManager manager;
    private Usuario usuario;
    private Juego juego;
    private AdaptadorMensajes adaptador;
    private ArrayList<Comentario> comentarios;
    private Typeface fuente;
    private Typeface fuenteDos;
    private final String RUTA="fuentes/personaje.ttf";
    private final String RUTADOS="fuentes/ChelaOne.ttf";
    private LinearLayout lista, mensaje;
    private TextView tvMsj;
    private byte posicion;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista=inflater.inflate(R.layout.fragment_foro, container, false);

        usuario=((DetalleJuego)getActivity()).getUsuario();
        juego=((DetalleJuego)getActivity()).getJuego();

        comentarios= new ArrayList<Comentario>();

        posicion=0;

        fuente=Typeface.createFromAsset(getContext().getAssets(),RUTA);
        fuenteDos=Typeface.createFromAsset(getContext().getAssets(),RUTADOS);

        userName=(TextView) vista.findViewById(R.id.foroNombre);
        userImage=(ImageView)vista.findViewById(R.id.foroFoto);
        etMensaje=(EditText)vista.findViewById(R.id.foroMensaje);
        etAsunto=(EditText) vista.findViewById(R.id.foroAsunto);
        btnNuevo=(Button)vista.findViewById(R.id.foroBtnEnvia);
        btnCancelar=(Button) vista.findViewById(R.id.foroBtnCancela);
        btnOrdena=(Button) vista.findViewById(R.id.foroBtnOrdena);
        btnRecarga=(Button) vista.findViewById(R.id.foroBtnrecarga);
        btnCancelar.setEnabled(false);
        recycler=(RecyclerView)vista.findViewById(R.id.foroRecycler);

        tvMsj=(TextView)vista.findViewById(R.id.foroMsj) ;

        //Layouts
        lista=(LinearLayout) vista.findViewById(R.id.layoutLv);
        mensaje=(LinearLayout) vista.findViewById(R.id.layoutMns);


        userName.setTypeface(fuente);
        etMensaje.setTypeface(fuenteDos);
        btnNuevo.setTypeface(fuenteDos);
        btnCancelar.setTypeface(fuenteDos);
        btnRecarga.setTypeface(fuenteDos);
        btnOrdena.setTypeface(fuenteDos);


        userName.setText(usuario.getAlias());
        userImage.setImageResource(Integer.parseInt(usuario.getAvatar()));



        btnNuevo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnNuevo.getText().toString().toUpperCase().equals("NUEVO")){
                    btnNuevo.setText("ENVIAR");
                    btnCancelar.setEnabled(true);

                    LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)
                            lista.getLayoutParams();
                            params.weight=8.5f;
                            lista.requestLayout();

                    LinearLayout.LayoutParams parametros=(LinearLayout.LayoutParams)
                            mensaje.getLayoutParams();
                            parametros.weight=4.0f;
                            etMensaje.requestLayout();

                }else{

                    if(!etMensaje.getText().toString().trim().equals("") & !etAsunto.getText().toString().trim().equals("")){
                        if(etMensaje.getText().toString().trim().length()>240){
                            Toast.makeText(getContext(),"El mensaje excede de 240 caracteres",Toast.LENGTH_LONG).show();
                        }else if(etAsunto.getText().toString().trim().length()>40){
                            Toast.makeText(getContext(),"El asunto excede de 40 caracteres",Toast.LENGTH_LONG).show();
                        }else{
                            insertarMensaje();
                            etMensaje.getText().clear();
                            etAsunto.getText().clear();
                            btnNuevo.setText("NUEVO");
                            btnCancelar.setEnabled(false);

                            LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)
                                    lista.getLayoutParams();
                            params.weight=12.5f;
                            lista.requestLayout();

                            LinearLayout.LayoutParams parametros=(LinearLayout.LayoutParams)
                                    mensaje.getLayoutParams();
                            parametros.weight=0.0f;
                            etMensaje.requestLayout();
                        }

                    }else{
                        Toast.makeText(getContext(),"Se detectaron campos vacios",Toast.LENGTH_LONG).show();
                    }


                }

            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cancelar();
            }
        });

        btnOrdena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinearLayout.LayoutParams parametros=(LinearLayout.LayoutParams)
                        mensaje.getLayoutParams();

                if(parametros.weight==0.0f){
                   if(posicion==0){
                       Ordenar();
                     posicion=1;
                   }else{
                       OrdenarInverso();
                     posicion=0;
                    }
                }else{
                    Cancelar();
                    if(posicion==0){
                        Ordenar();
                        posicion=1;
                    }else{
                        OrdenarInverso();
                        posicion=0;
                    }
                }

            }
        });

        btnRecarga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayout.LayoutParams parametros=(LinearLayout.LayoutParams)
                        mensaje.getLayoutParams();

                if(parametros.weight==0.0f){
                    llenarLista();
                }else{
                    Cancelar();
                    llenarLista();
                }

            }
        });





        llenarLista();

        return vista;
    }


    private void insertarMensaje(){

        Response.Listener <String> response= new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject objeto= new JSONObject(response);
                    boolean exito= objeto.getBoolean("success");

                    if(exito){
                        llenarLista();
                    }else{
                        Toast.makeText(getContext(),"Fallo de inserción",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                   Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }

        };

        Response.ErrorListener error = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        };

        Comentario comentario = new Comentario(juego.getId(),usuario.getAlias(),etMensaje.getText().toString(),etAsunto.getText().toString());
        InsercionComentario insercion= new InsercionComentario(comentario,response,error);

        RequestQueue queue= Volley.newRequestQueue(getContext());
        queue.add(insercion);
        etMensaje.getText().clear();
    }


    private void llenarLista(){

        String ruta= Servidor.getServidor()+"mensajes.php?idjuego="+juego.getId();

        RequestQueue queue= Volley.newRequestQueue(getContext());

        JsonArrayRequest array= new JsonArrayRequest(Request.Method.GET,ruta,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                comentarios.clear();

                SimpleDateFormat formato= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date= null;
                try {
                    for(int i=0; i<response.length(); i++){
                        JSONObject obj=response.getJSONObject(i);
                        Comentario comentario= new Comentario(
                                obj.getString("alias"),
                                obj.getString("mensaje"),
                                obj.getString("ruta"),
                                date=formato.parse(obj.getString("fecha")),
                                obj.getString("titulo"));
                        comentarios.add(comentario);
                    }

                        adaptador= new AdaptadorMensajes(comentarios,getContext(),usuario);
                        recycler.setHasFixedSize(true);
                        manager= new LinearLayoutManager(getContext());
                        recycler.setLayoutManager(manager);
                        recycler.setAdapter(adaptador);

                        if(comentarios.size()<1){
                            tvMsj.setVisibility(View.VISIBLE);
                            tvMsj.setText("Todavía no hay mensajes");
                            tvMsj.setTypeface(fuenteDos);
                        }else{
                            tvMsj.setVisibility(View.GONE);
                        }

                    adaptador.notifyDataSetChanged();


                } catch (JSONException e) {
                  Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                } catch (ParseException e) {
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        queue.add(array);

    }


    private void Cancelar(){
        btnNuevo.setText("NUEVO");
        btnCancelar.setEnabled(false);

        LinearLayout.LayoutParams params=(LinearLayout.LayoutParams)
                lista.getLayoutParams();
        params.weight=13.0f;
        lista.requestLayout();

        LinearLayout.LayoutParams parametros=(LinearLayout.LayoutParams)
                mensaje.getLayoutParams();
        parametros.weight=0.0f;
        mensaje.requestLayout();

        etAsunto.getText().clear();
        etMensaje.getText().clear();


    }

    private void Ordenar(){
        Collections.sort(comentarios);
        adaptador.notifyDataSetChanged();
    }

    private void OrdenarInverso(){
        Comparator<Comentario> comparador= Collections.reverseOrder();
        Collections.sort(comentarios,comparador);
        adaptador.notifyDataSetChanged();
    }
}
