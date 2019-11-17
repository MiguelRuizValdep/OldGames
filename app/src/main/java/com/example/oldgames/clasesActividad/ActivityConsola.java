package com.example.oldgames.clasesActividad;


import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.example.oldgames.clasesAdaptador.AdaptadorConsola;
import com.example.oldgames.clasesObjeto.Consola;
import com.example.oldgames.clasesObjeto.Servidor;
import com.example.oldgames.clasesObjeto.Usuario;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class ActivityConsola extends AppCompatActivity {

private final String URL= Servidor.getServidor()+"consola.php";
private ArrayList<Consola> consolas,todas;
private RecyclerView recycler;
private AdaptadorConsola adaptador;
private RecyclerView.LayoutManager manager;
private Typeface fuente;
private final String RUTA="fuentes/ChelaOne.ttf";
private ArrayList<String> companias;
private String [] orden;
private ArrayAdapter<String> adCompany,adOrden;
private Spinner spCompania,spOrden;
private TextView titulo;
private Toolbar tb;
private Usuario usuario;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_consola);

        usuario=(Usuario)getIntent().getExtras().getSerializable("user");

        spCompania=(Spinner) findViewById(R.id.spCompany);
        spOrden=(Spinner) findViewById(R.id.spAnio);



        tb=(Toolbar) findViewById(R.id.barraConsolas) ;
        setSupportActionBar(tb);
        ActionBar actionBar= getSupportActionBar();//Dede aquí modifico mi barra

        titulo=(TextView) findViewById(R.id.tituloConsola);
        titulo.setText("Videoconsolas");
        fuente=Typeface.createFromAsset(getAssets(),RUTA);
        titulo.setTypeface(fuente);



        consolas= new ArrayList<Consola>();
        todas= new ArrayList<Consola>();
        recycler=(RecyclerView) findViewById(R.id.recyclerConsola);
        recycler.setHasFixedSize(true);
        manager= new LinearLayoutManager(this);
        recycler.setLayoutManager(manager);





        RequestQueue request= Volley.newRequestQueue(this);

        JsonArrayRequest array= new JsonArrayRequest(Request.Method.GET, URL, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try{
                   for (int i=0; i<response.length(); i++){

                       JSONObject objeto= response.getJSONObject(i);
                       Consola consola= new Consola();
                       consola.setId(objeto.getInt("id"));
                       consola.setNombre(objeto.getString("nombre"));
                       consola.setAnio(objeto.getInt("anio"));
                       consola.setDescripcion(objeto.getString("descripcion"));
                       consola.setCompany(objeto.getString("company"));
                       consola.setLogo(objeto.getString("logo"));
                       consola.setFoto(objeto.getString("foto"));
                       consolas.add(consola);

                   }

                    todas.addAll(consolas);

                    adaptador= new AdaptadorConsola(consolas, ActivityConsola.this);
                    recycler.setAdapter(adaptador);

                    companias=llenaCompanias();
                    adCompany= new ArrayAdapter<String>(getApplicationContext(),R.layout.texto_sp_consolas,companias){

                        @Override
                        public View getView(int position, View convertView,  ViewGroup parent) {
                            View vista= super.getView(position, convertView, parent);
                            ((TextView) vista).setTypeface(fuente);
                            return vista;
                        }

                        @Override
                        public View getDropDownView(int position, View convertView,  ViewGroup parent) {
                            View v=super.getDropDownView(position, convertView, parent);
                            ((TextView)v).setTypeface(fuente);
                            return v;
                        }
                    };
                    spCompania.setAdapter(adCompany);

                    spCompania.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                         String comp= companias.get(i);
                         establecerCompania(comp);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    orden= new String[]{"Fecha ascendente","Fecha descendente"};
                    adOrden=new ArrayAdapter<String>(getApplicationContext(),R.layout.texto_sp_consolas,orden){

                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            View vista=super.getView(position, convertView, parent);
                            ((TextView) vista).setTypeface(fuente);
                            return vista;
                        }

                        @Override
                        public View getDropDownView(int position,  View convertView,  ViewGroup parent) {
                            View vista= super.getDropDownView(position, convertView, parent);
                            ((TextView) vista).setTypeface(fuente);
                            return vista;
                        }
                    };
                    spOrden.setAdapter(adOrden);


                    spOrden.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            String ord=orden[i];
                            establecerOrden(ord);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


                    adaptador.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        Consola consola=consolas.get(recycler.getChildAdapterPosition(view));
                            Intent intent = new Intent(ActivityConsola.this, DetalleConsola.class);
                            intent.putExtra("consola",(Serializable) consola);
                            intent.putExtra("user",(Serializable)usuario);
                            startActivity(intent);
                            finish();
                        }
                    });



               }catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "Error Json", Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Error Volley",Toast.LENGTH_LONG).show();
            }
        });
        request.add(array);


    }

    private ArrayList<String> llenaCompanias(){
        ArrayList<String> comp= new ArrayList<>();
        comp.add("Todas las compañias");
        for(Consola c: consolas){
            String company =c.getCompany();

            if(!comp.contains(company)){
                comp.add(company);
            }
        }
        return comp;
    }

    private void establecerOrden(String ord){

        if(ord.equals("Fecha descendente")){
          Collections.sort(consolas);
          adaptador.notifyDataSetChanged();
        }else if(ord.equals("Fecha ascendente")){
            Collections.sort(consolas,Collections.<Consola>reverseOrder());
            adaptador.notifyDataSetChanged();
        }
    }

    private void establecerCompania(String comp){

        if(!comp.equals("Todas las compañias")){
            consolas.clear();

            for(Consola c: todas){

                if(c.getCompany().equals(comp)){
                    consolas.add(c);
                }
            }
            adaptador.notifyDataSetChanged();
            establecerOrden(spOrden.getSelectedItem().toString());
        }else{
            consolas.clear();
            consolas.addAll(todas);
            adaptador.notifyDataSetChanged();
            establecerOrden(spOrden.getSelectedItem().toString());

        }

    }

    //Sobreescribo lo que hace el botón fisico atrás
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
