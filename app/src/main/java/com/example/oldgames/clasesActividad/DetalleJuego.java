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
import com.example.oldgames.clasesAdaptador.AdaptadorPaginaJuego;
import com.example.oldgames.clasesObjeto.Consola;
import com.example.oldgames.clasesObjeto.Juego;
import com.example.oldgames.clasesObjeto.Usuario;
import com.squareup.picasso.Picasso;

public class DetalleJuego extends AppCompatActivity {

    private Juego juego;
    private AdaptadorPaginaJuego adaptador;
    private ViewPager paginador;
    private Toolbar barra;
    private TabLayout tabLayout;
    private ImageView carJuego;
    private TextView titJuego;
    private Consola consola;
    private Usuario usuario;
    private Typeface fuenteDos;
    private Typeface fuente;
    private final String RUTA2="fuentes/ChelaOne.ttf";
    private final String RUTA="fuentes/juego.ttf";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_detalle_juego);


        juego = (Juego) getIntent().getExtras().getSerializable("juego");
        consola=(Consola)getIntent().getExtras().getSerializable("consola");
        usuario=(Usuario)getIntent().getExtras().getSerializable("user");

        fuente=Typeface.createFromAsset(getAssets(),RUTA2);
        fuenteDos=Typeface.createFromAsset(getAssets(),RUTA);

        barra=(Toolbar) findViewById(R.id.barraDtllJuego);
        setSupportActionBar(barra);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);




        carJuego=(ImageView)findViewById(R.id.detalleJiconoJuego);
        titJuego=(TextView) findViewById(R.id.detalleJtituloJ);
        titJuego.setTypeface(fuenteDos);

        titJuego.setText(juego.getNombre());
        Picasso.with(this).load(juego.getCaratula()).into(carJuego);

        adaptador= new AdaptadorPaginaJuego(getSupportFragmentManager());
        paginador=(ViewPager) findViewById(R.id.paginadorJuego);
        paginador.setAdapter(adaptador);

        tabLayout= (TabLayout) findViewById(R.id.pestaniasJuego);
        tabLayout.setupWithViewPager(paginador);
        fuenteTab();

    }

    //Metodos que usaré para recuperar datos desde los fragments.

    public Juego getJuego(){
        return juego;
    }
    public Usuario getUsuario() {return usuario;}

   //Acción que realiza el botón de  <- menú atras.
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                Intent intent = new Intent(DetalleJuego.this,DetalleConsola.class);
                intent.putExtra("consola",consola);
                intent.putExtra("user",usuario);
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
        Intent intent = new Intent(DetalleJuego.this,DetalleConsola.class);
        intent.putExtra("consola",consola);
        intent.putExtra("user",usuario);
        startActivity(intent);
        finish();
    }

    //Doy forma al TabLayout

    private void fuenteTab() {


        View view= LayoutInflater.from(this).inflate(R.layout.vista_pestanias,null);
        TextView tv=(TextView) view.findViewById(R.id.pestanias_txt);
        ImageView imv=(ImageView) view.findViewById(R.id.imagenPestanias);
        imv.setImageResource(R.drawable.detalles);
        tv.setTypeface(fuente);
        tv.setText("Detalles");
        tabLayout.getTabAt(0).setCustomView(view);

        view=LayoutInflater.from(this).inflate(R.layout.vista_pestanias,null);
        tv=(TextView) view.findViewById(R.id.pestanias_txt);
        imv=(ImageView) view.findViewById(R.id.imagenPestanias);
        imv.setImageResource(R.drawable.personajes);
        tv.setTypeface(fuente);
        tv.setText("Personajes");
        tabLayout.getTabAt(1).setCustomView(view);

        view=LayoutInflater.from(this).inflate(R.layout.vista_pestanias,null);
        tv=(TextView) view.findViewById(R.id.pestanias_txt);
        imv=(ImageView) view.findViewById(R.id.imagenPestanias);
        imv.setImageResource(R.drawable.trucos);
        tv.setTypeface(fuente);
        tv.setText("Trucos");
        tabLayout.getTabAt(2).setCustomView(view);

        view=LayoutInflater.from(this).inflate(R.layout.vista_pestanias,null);
        tv=(TextView) view.findViewById(R.id.pestanias_txt);
        imv=(ImageView) view.findViewById(R.id.imagenPestanias);
        imv.setImageResource(R.drawable.mensajes);
        tv.setTypeface(fuente);
        tv.setText("Opinión");
        tabLayout.getTabAt(3).setCustomView(view);

    }

}
