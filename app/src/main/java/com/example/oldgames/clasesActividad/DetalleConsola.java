package com.example.oldgames.clasesActividad;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.oldgames.R;
import com.example.oldgames.clasesAdaptador.AdaptadorPaginaConsola;
import com.example.oldgames.clasesObjeto.Consola;
import com.example.oldgames.clasesObjeto.Usuario;
import com.squareup.picasso.Picasso;

import java.io.Serializable;


public class DetalleConsola extends AppCompatActivity {

    private Consola consola;
    private AdaptadorPaginaConsola adaptador;
    private ViewPager paginador;
    private Toolbar barra;
    private TextView tvNombre;
    private ImageView icono;
    private final String RUTA="fuentes/consola.ttf";
    private Typeface fuente;
    private final String RUTA2="fuentes/ChelaOne.ttf";
    private Typeface fuente2;
    private Usuario usuario;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detalle_consola);

        usuario=(Usuario)getIntent().getExtras().getSerializable("user");
        consola=(Consola)getIntent().getExtras().getSerializable("consola");

        adaptador= new AdaptadorPaginaConsola(getSupportFragmentManager());
        paginador=(ViewPager)findViewById(R.id.paginadorConsola);
        paginador.setAdapter(adaptador);

        barra=(Toolbar) findViewById(R.id.barraTituloConsola);
        setSupportActionBar(barra);
        ActionBar actionBar= getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);




        fuente=Typeface.createFromAsset(getAssets(),RUTA);
        fuente2=Typeface.createFromAsset(getAssets(),RUTA2);
        tvNombre=(TextView)findViewById(R.id.titNombreCons);
        tvNombre.setText(consola.getNombre());
        tvNombre.setTypeface(fuente);



        icono=(ImageView) findViewById(R.id.iconoConsola);
        Picasso.with(this).load(consola.getFoto()).into(icono);
        tabLayout= (TabLayout) findViewById(R.id.pestaniasConsola);
        tabLayout.setupWithViewPager(paginador);
        fuenteTab();

    }

    //La obtendré desde los fragments que llame desde esta actividad.
    public Consola getConsola(){
        return consola;
    }
    public Usuario getUsuario() { return usuario; }

    //Acción que realiza el botón de  <- menú atras.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(DetalleConsola.this,ActivityConsola.class);
                intent.putExtra("user",(Serializable)usuario);
                startActivity(intent);
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);

    }

    //Sobreescribo lo que hace el botón fisico atrás
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(DetalleConsola.this,ActivityConsola.class);
        intent.putExtra("user",(Serializable)usuario);
        startActivity(intent);
        finish();
    }

    //Doy forma al TabLayout

    private void fuenteTab() {


            View view=LayoutInflater.from(this).inflate(R.layout.vista_pestanias,null);
            TextView tv=(TextView) view.findViewById(R.id.pestanias_txt);
            ImageView imv=(ImageView) view.findViewById(R.id.imagenPestanias);
            imv.setImageResource(R.drawable.detalles);
            tv.setTypeface(fuente2);
            tv.setText("Detalles");
            tabLayout.getTabAt(0).setCustomView(view);

            view=LayoutInflater.from(this).inflate(R.layout.vista_pestanias,null);
            tv=(TextView) view.findViewById(R.id.pestanias_txt);
            imv=(ImageView) view.findViewById(R.id.imagenPestanias);
            imv.setImageResource(R.drawable.marciano);
            tv.setTypeface(fuente2);
            tv.setText("Juegos");
            tabLayout.getTabAt(1).setCustomView(view);

           view=LayoutInflater.from(this).inflate(R.layout.vista_pestanias,null);
           tv=(TextView) view.findViewById(R.id.pestanias_txt);
           imv=(ImageView) view.findViewById(R.id.imagenPestanias);
           imv.setImageResource(R.drawable.tienda);
           tv.setTypeface(fuente2);
           tv.setText("Tienda");
           tabLayout.getTabAt(2).setCustomView(view);

    }




}
