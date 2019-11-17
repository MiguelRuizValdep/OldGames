package com.example.oldgames.fragmentsConsola;


import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
import com.example.oldgames.clasesActividad.ActivityTienda;
import com.example.oldgames.clasesActividad.DetalleConsola;
import com.example.oldgames.clasesAdaptador.AdaptadorListaTiendas;
import com.example.oldgames.clasesObjeto.Consola;
import com.example.oldgames.clasesObjeto.Servidor;
import com.example.oldgames.clasesObjeto.Tienda;
import com.example.oldgames.clasesObjeto.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;


public class ListaTiendas extends Fragment {
    private Consola consola;
    private Usuario usuario;;
    private Spinner spinner;
    private RecyclerView recycler;
    private Button boton;
    private EditText busqueda;
    private AdaptadorListaTiendas adaptador;
    private ArrayList<Tienda> tiendas,todas;
    private ArrayList<String> provincias;
    private Typeface fuente;
    private final String RUTA="fuentes/ChelaOne.ttf";




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.fragment_lista_tiendas, container, false);
        consola=((DetalleConsola)getActivity()).getConsola();
        usuario=((DetalleConsola)getActivity()).getUsuario();
        tiendas= new ArrayList<>();
        provincias= new ArrayList<>();
        todas= new ArrayList<>();

        fuente=Typeface.createFromAsset(getContext().getAssets(),RUTA);

        spinner=(Spinner)vista.findViewById(R.id.spinnerTiendas);
        boton=(Button) vista.findViewById(R.id.listTiendBtnBuscar);
        busqueda=(EditText) vista.findViewById(R.id.tiendaEtBuscar);
        busqueda.setTypeface(fuente);
        recycler=(RecyclerView)vista.findViewById(R.id.listaTiendas);
        recycler.setLayoutManager(new GridLayoutManager(getContext(),2));



        String url= Servidor.getServidor()+"tienda.php?id="+consola.getId();

        RequestQueue request= Volley.newRequestQueue(getContext());

        JsonArrayRequest array= new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try{
                    for(int i=0; i<response.length(); i++){

                        JSONObject objeto= response.getJSONObject(i);
                        Tienda tienda= new Tienda();
                        tienda.setId(objeto.getInt("id"));
                        tienda.setNombre(objeto.getString("nombre"));
                        tienda.setProvincia(objeto.getString("provincia"));
                        tienda.setCalle(objeto.getString("calle"));
                        tienda.setNumero(objeto.getString("numero"));
                        tienda.setCpostal(objeto.getString("cpostal"));
                        tienda.setTelefono(objeto.getString("telefono"));
                        tienda.setFoto(objeto.getString("foto"));
                        tienda.setWeb(objeto.getString("web"));

                        tiendas.add(tienda);

                    }

                    todas.addAll(tiendas);

                    adaptador= new AdaptadorListaTiendas(getContext(),tiendas);
                    recycler.setAdapter(adaptador);

                    provincias.addAll(llenarProvincias(tiendas));
                    ArrayAdapter<String> adapter=new ArrayAdapter<String>(getContext(),R.layout.texto_spinner,provincias){

                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View vista=super.getView(position, convertView, parent);
                            ((TextView) vista).setTypeface(fuente);
                            return vista;
                        }

                        @Override
                        public View getDropDownView(int position,  View convertView, ViewGroup parent) {
                            View vista=super.getDropDownView(position, convertView, parent);
                            ((TextView) vista).setTypeface(fuente);
                            return vista;
                        }
                    };
                    spinner.setAdapter(adapter);

                    //Acción del spinner
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            String provincia=provincias.get(i);
                            seleccionProvincia(provincia);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });




                    //Acción del boton buscar

                    boton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                          buscarPorNombre();
                        }
                    });

                    //Cambio de icono
                    busqueda.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                        }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            if(charSequence.length()<1){
                                boton.setBackground(getContext().getResources().getDrawable(R.drawable.recargar));
                            }else{
                                boton.setBackground(getContext().getResources().getDrawable(R.drawable.lupa));
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable editable) {

                        }
                    });

                    //Accion adaptador
                    adaptador.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                         Tienda tienda= tiendas.get(recycler.getChildAdapterPosition(view));
                         Intent intent= new Intent(getContext(), ActivityTienda.class);
                         intent.putExtra("tienda",(Serializable) tienda);
                         intent.putExtra("consola",(Serializable) consola);
                         intent.putExtra("user",(Serializable) usuario);

                         startActivity(intent);

                        }

                    });

                }catch (JSONException e){
                    Toast.makeText(getContext(),"Error Json",Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

        request.add(array);

        return vista;
    }

    private ArrayList<String> llenarProvincias(ArrayList<Tienda> tien){
      ArrayList<String> prov= new ArrayList<>();
      prov.add("Todas las provincias");
      for(Tienda t:tien){
          String nombre=t.getProvincia();
          if(!prov.contains(nombre)){
             prov.add(nombre);
          }
      }
      return  prov;
    }

    private void seleccionProvincia(String provincia){
        busqueda.getText().clear();
        if(provincia.equals("Todas las provincias")){
           tiendas.clear();
           tiendas.addAll(todas);
           adaptador.notifyDataSetChanged();
        }else{
            tiendas.clear();

            for(Tienda t: todas){
                if(t.getProvincia().equals(provincia)){
                    tiendas.add(t);
                }
            }
            adaptador.notifyDataSetChanged();
        }
    }

    private void buscarPorNombre(){
        String parametro=busqueda.getText().toString();
        parametro=parametro.toUpperCase();

        if(parametro!=null & !parametro.equals("")){
            tiendas.clear();

            for(Tienda t: todas){
                if(spinner.getSelectedItem().toString().equals("Todas las provincias")){
                    if(t.getNombre().toUpperCase().contains(parametro)){
                        tiendas.add(t);
                    }

                }else if(t.getProvincia().equals(spinner.getSelectedItem().toString())){
                    if(t.getNombre().toUpperCase().contains(parametro)){
                        tiendas.add(t);
                    }
                }
            }
            busqueda.getText().clear();
            adaptador.notifyDataSetChanged();
        }else if(busqueda.getText().toString().equals("")){
            tiendas.clear();
            if(spinner.getSelectedItem().toString().equals("Todas las provincias")){
               tiendas.addAll(todas);
               adaptador.notifyDataSetChanged();
            }else{
                for(Tienda t: todas){
                    if(t.getProvincia().equals(spinner.getSelectedItem().toString())){
                        tiendas.add(t);
                    }
                }
                adaptador.notifyDataSetChanged();
            }
        }
    }

}
