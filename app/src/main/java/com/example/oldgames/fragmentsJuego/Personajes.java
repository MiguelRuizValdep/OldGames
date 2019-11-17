package com.example.oldgames.fragmentsJuego;


import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.oldgames.clasesActividad.DetalleJuego;
import com.example.oldgames.clasesAdaptador.AdaptadorGaleria;
import com.example.oldgames.clasesObjeto.Juego;
import com.example.oldgames.clasesObjeto.Personaje;
import com.example.oldgames.clasesObjeto.Servidor;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Personajes extends Fragment {

    private TextView nombre;
    private ImageView imagen;
    private RecyclerView recycler;
    private ArrayList<Personaje> personajes;
    private AdaptadorGaleria adaptador;
    private String URL;
    private Typeface fuente;
    private final String RUTA="fuentes/personaje.ttf";
    private ObjectAnimator objectAnimator;
    private AnimatorSet animatorSet;
    private final int DURACION =800;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_personajes, container, false);

        fuente= Typeface.createFromAsset(getContext().getAssets(),RUTA);

        nombre=(TextView) vista.findViewById(R.id.personajeNombre);
        imagen=(ImageView) vista.findViewById(R.id.personajeImagen);
        recycler=(RecyclerView) vista.findViewById(R.id.personajeRecycler);

        nombre.setTypeface(fuente);
         Juego juego= ((DetalleJuego)getActivity()).getJuego();

         personajes= new ArrayList<Personaje>();
         recycler.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));



        URL= Servidor.getServidor()+"personaje.php?id="+juego.getId();

        RequestQueue request= Volley.newRequestQueue(getContext());

        JsonArrayRequest array= new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                    for (int i=0; i<response.length(); i++){

                        JSONObject objeto= response.getJSONObject(i);
                        Personaje personaje= new Personaje();
                        personaje.setId(objeto.getInt("id"));
                        personaje.setNombre(objeto.getString("nombre"));
                        personaje.setFoto(objeto.getString("foto"));
                        personajes.add(personaje);

                    }
                    adaptador= new AdaptadorGaleria(personajes,getContext());
                    recycler.setAdapter(adaptador);

                    if(personajes.size()>0){
                        nombre.setText(personajes.get(0).getNombre());
                        Picasso.with(getContext()).load(personajes.get(0).getFoto()).into(imagen);
                    }else{
                        imagen.setImageResource(R.drawable.frame_27);
                        nombre.setText("No establecido");
                    }

                   adaptador.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Personaje personaje= personajes.get(recycler.getChildAdapterPosition(view));
                            nombre.setText(personaje.getNombre());
                            objectAnimator=ObjectAnimator.ofFloat(nombre,"y",-200f,nombre.getX());
                            objectAnimator.setDuration(DURACION);
                            animatorSet= new AnimatorSet();
                            animatorSet.play(objectAnimator);
                            animatorSet.start();

                            Picasso.with(getContext()).load(personaje.getFoto()).into(imagen);

                        }
                    });


                }catch (JSONException e) {
                    Toast.makeText(getContext(), "Error Json", Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),"Error Volley",Toast.LENGTH_LONG).show();
            }
        });
        request.add(array);

        return vista;
    }



}
