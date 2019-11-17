package com.example.oldgames.fragmentsJuego;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
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
import com.example.oldgames.clasesAdaptador.AdaptadorTrucos;
import com.example.oldgames.clasesObjeto.Juego;
import com.example.oldgames.clasesObjeto.Servidor;
import com.example.oldgames.clasesObjeto.Truco;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Trucos extends Fragment {

    private ArrayList<Truco> trucos;
    private RecyclerView rv;
    private AdaptadorTrucos adaptador;
    private Juego juego;
    private TextView tvMns;
    private Typeface fuente;
    private FrameLayout layout;
    private final String RUTA="fuentes/ChelaOne.ttf";
    private float rotar;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista= inflater.inflate(R.layout.fragment_trucos, container, false);

        juego=((DetalleJuego)getActivity()).getJuego();
        trucos= new ArrayList<Truco>();
        tvMns=(TextView) vista.findViewById(R.id.trucosMensaje);
        fuente=Typeface.createFromAsset(getContext().getAssets(),RUTA);
        layout=(FrameLayout)vista.findViewById(R.id.frTrucos);

        if(juego.getorientacion().trim().equals("horizontal")){
            rotar=90.0f;
        }else{
            rotar=0.0f;
        }

        final ImageView img = new ImageView(getContext());
        Picasso.with(img.getContext()).load(juego.getCaratula()).rotate(rotar).into(img, new com.squareup.picasso.Callback() {
            @Override
            public void onSuccess() {
                layout.setBackground(img.getDrawable());
            }

            @Override
            public void onError() {
            }
        });


        rv=(RecyclerView) vista.findViewById(R.id.recyclerTrucos);
        rv.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));

        final String URL=Servidor.getServidor()+"trucos.php?idJuego="+juego.getId();

        RequestQueue request= Volley.newRequestQueue(getContext());

        JsonArrayRequest array= new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try{
                    for(int i=0;i<response.length();i++){
                        JSONObject obj=response.getJSONObject(i);
                        Truco truco= new Truco(obj.getString("descripcion"),obj.getString("truco"));
                        trucos.add(truco);
                    }

                }catch (JSONException e){
                    Toast.makeText(getContext(), "Error Json", Toast.LENGTH_LONG).show();
                }

                if(trucos.size()<1){
                    tvMns.setVisibility(View.VISIBLE);
                    tvMns.setTypeface(fuente);
                    tvMns.setText("No se han establecido trucos para este juego");
                }else{

                    adaptador= new AdaptadorTrucos(getContext(),trucos);
                    rv.setAdapter(adaptador);
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
