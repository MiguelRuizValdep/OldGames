package com.example.oldgames.fragmentsConsola;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.oldgames.R;
import com.example.oldgames.clasesActividad.DetalleConsola;
import com.example.oldgames.clasesActividad.DetalleJuego;
import com.example.oldgames.clasesAdaptador.AdaptadorListaJuegos;
import com.example.oldgames.clasesObjeto.Consola;
import com.example.oldgames.clasesObjeto.Juego;
import com.example.oldgames.clasesObjeto.Servidor;
import com.example.oldgames.clasesObjeto.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;


public class ListaJuegos extends Fragment {

    private Consola consola;
    private Usuario usuario;
    private int idConsola;
    private ArrayList<Juego> juegos,todos;
    private Button btnBuscar;
    private RecyclerView recycler;
    private AdaptadorListaJuegos adaptador;
    private Spinner spiner;
    private ArrayList<String> genero;
    private EditText buscar;
    private final String RUTA="fuentes/ChelaOne.ttf";
    private Typeface fuente;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View vista=inflater.inflate(R.layout.fragment_lista_juegos, container, false);

        fuente=Typeface.createFromAsset(getContext().getAssets(),RUTA);
        genero= new ArrayList<String>();
        genero.add("Todos los generos");
        spiner=(Spinner)vista.findViewById(R.id.spinnerJuegos);
        buscar=(EditText)vista.findViewById(R.id.etBuscar) ;
        btnBuscar=(Button)vista.findViewById(R.id.listJueBtnBuscar) ;

        buscar.setTypeface(fuente);

        //Consigo consola y usuario desde la actividad.
        consola=((DetalleConsola)getActivity()).getConsola();
        usuario=((DetalleConsola)getActivity()).getUsuario();

        idConsola=consola.getId();
        String URL= Servidor.getServidor()+"juego.php?idconsola="+idConsola;
        juegos= new ArrayList<Juego>();
        todos= new ArrayList<Juego>();


        recycler=(RecyclerView)vista.findViewById(R.id.listaJuegos);
        recycler.setLayoutManager(new GridLayoutManager(getContext(),2));

        RequestQueue request= Volley.newRequestQueue(getContext());

        JsonArrayRequest array = new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

             try {
                 for (int i = 0; i < response.length(); i++) {
                     JSONObject objeto = response.getJSONObject(i);
                     Juego juego = new Juego();
                     juego.setId(objeto.getInt("id"));
                     juego.setIdConsola(objeto.getInt("idconsola"));
                     juego.setGenero(objeto.getString("genero"));
                     juego.setNombre(objeto.getString("nombre"));
                     juego.setAnio(objeto.getInt("anio"));
                     juego.setDescripcion(objeto.getString("descripcion"));
                     juego.setCompany(objeto.getString("company"));
                     juego.setCaratula(objeto.getString("caratula"));
                     juego.setorientacion(objeto.getString("orientacion"));
                     juegos.add(juego);
                 }


                 todos.addAll(juegos);
                 adaptador= new AdaptadorListaJuegos(getContext(),juegos);
                 recycler.setAdapter(adaptador);

                 adaptador.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         Juego juego= juegos.get(recycler.getChildAdapterPosition(view));
                         Intent intent= new Intent(getContext(), DetalleJuego.class);
                         intent.putExtra("juego",(Serializable) juego);
                         intent.putExtra("consola",(Serializable)consola);
                         intent.putExtra("user",(Serializable)usuario);
                         startActivity(intent);
                         getActivity().finish();

                     }
                 });



                 for(Juego j: todos){
                     String gen= j.getGenero();
                     if(!genero.contains(gen)){
                         genero.add(gen);
                     }
                 }


                 ArrayAdapter<String> adapter= new ArrayAdapter<String>(getContext(),R.layout.texto_spinner,genero){

                     @Override
                     public View getView(int position,  View convertView,  ViewGroup parent) {
                         View vista=super.getView(position, convertView, parent);
                         ((TextView) vista).setTypeface(fuente);
                         return vista;
                     }

                     @Override
                     public View getDropDownView(int position, View convertView,  ViewGroup parent) {
                         View vista= super.getDropDownView(position, convertView, parent);
                         ((TextView) vista).setTypeface(fuente);
                         return vista;
                     }
                 };
                 spiner.setAdapter(adapter);





                spiner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                     @Override
                     public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                         String string=genero.get(i);
                         seleccion(string);
                     }

                     @Override
                     public void onNothingSelected(AdapterView<?> adapterView) {

                     }
                 });



                btnBuscar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        filtroBusqueda();
                    }
                });

                buscar.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                     if(charSequence.length()<1){
                         btnBuscar.setBackground(getContext().getResources().getDrawable(R.drawable.recargar));
                     }else{
                         btnBuscar.setBackground(getContext().getResources().getDrawable(R.drawable.lupa));
                     }
                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });



             }catch(JSONException e){
                 Toast.makeText(getContext(),"Error Json",Toast.LENGTH_LONG).show();
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


    public void seleccion(String string) {
        buscar.getText().clear();
        if(string.equals("Todos los generos")){
            juegos.clear();
            juegos.addAll(todos);
            adaptador.notifyDataSetChanged();
        }else{
            juegos.clear();

            for(Juego j: todos){
                if(j.getGenero().equals(string)){
                    juegos.add(j);
                }
            }
            adaptador.notifyDataSetChanged();
        }
    }

    private void filtroBusqueda(){
        String filtro=buscar.getText().toString().toUpperCase();
        buscar.getText().clear();
        if(filtro!=null & !filtro.equals("")){
            juegos.clear();

            for(Juego j: todos){
                if(spiner.getSelectedItem().toString().equals("Todos los generos")){
                    if(j.getNombre().toUpperCase().contains(filtro)){
                        juegos.add(j);
                    }
                }else if(j.getGenero().equals(spiner.getSelectedItem().toString())){
                    if(j.getNombre().toUpperCase().contains(filtro)){
                        juegos.add(j);
                    }
                }

            }

            adaptador.notifyDataSetChanged();

        }else if(buscar.getText().toString().equals("")){
            juegos.clear();
            if(spiner.getSelectedItem().toString().equals("Todos los generos")){
                juegos.addAll(todos);
                adaptador.notifyDataSetChanged();
            }else{
                for(Juego j: todos){
                    if(j.getGenero().equals(spiner.getSelectedItem().toString())){
                        juegos.add(j);
                    }
                }
                adaptador.notifyDataSetChanged();
            }
        }
    }

}
