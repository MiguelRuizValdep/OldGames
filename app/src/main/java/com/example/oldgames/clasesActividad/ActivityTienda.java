package com.example.oldgames.clasesActividad;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oldgames.R;
import com.example.oldgames.clasesObjeto.Consola;
import com.example.oldgames.clasesObjeto.Tienda;
import com.example.oldgames.clasesObjeto.Usuario;
import com.squareup.picasso.Picasso;

public class ActivityTienda extends AppCompatActivity {
    private Usuario usuario;
    private Consola consola;
    private Tienda tienda;
    private Toolbar barra;
    private TextView calle, cpostal, provincia, titulo;
    private Button btnLlamar, btnWeb;
    private ImageView ftoTienda;
    private Typeface fuente1, fuente2;
    private final String RUTA = "fuentes/ChelaOne.ttf";
    private final String RUTA2 = "fuentes/juego.ttf";
    private final int MY_PERMISSIONS_CALL=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_tienda);

        usuario = (Usuario) getIntent().getExtras().getSerializable("user");
        consola = (Consola) getIntent().getExtras().getSerializable("consola");
        tienda = (Tienda) getIntent().getExtras().getSerializable("tienda");

        fuente1 = Typeface.createFromAsset(getAssets(), RUTA);
        fuente2 = Typeface.createFromAsset(getAssets(), RUTA2);

        barra = (Toolbar) findViewById(R.id.barraTiendas);
        setSupportActionBar(barra);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        calle = (TextView) findViewById(R.id.dlgCalleNumero);
        cpostal = (TextView) findViewById(R.id.dlgCpostal);
        provincia = (TextView) findViewById(R.id.dlgProvincia);
        titulo = (TextView) findViewById(R.id.tituloTienda);

        calle.setTypeface(fuente1);
        cpostal.setTypeface(fuente1);
        provincia.setTypeface(fuente1);
        titulo.setTypeface(fuente2);


        titulo.setText(tienda.getNombre());
        calle.setText("C/ " + tienda.getCalle() + ", " + tienda.getNumero());
        cpostal.setText(tienda.getCpostal());
        provincia.setText(tienda.getProvincia());

        ftoTienda = (ImageView) findViewById(R.id.dialogFoto);

        Picasso.with(this).load(tienda.getFoto()).into(ftoTienda);

        btnLlamar = (Button) findViewById(R.id.btnDlgLLamar);
        btnWeb = (Button) findViewById(R.id.btnDlgWeb);

        btnLlamar.setTypeface(fuente1);
        btnWeb.setTypeface(fuente1);


        btnWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             navegar();
            }
        });

        btnLlamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1) {
                    //Version igual o mayor a Marshmallow

                    if (ContextCompat.checkSelfPermission(getApplicationContext(),
                            Manifest.permission.CALL_PHONE) !=
                            PackageManager.PERMISSION_GRANTED) {

                        if (ActivityCompat.shouldShowRequestPermissionRationale(ActivityTienda.this,
                                Manifest.permission.CALL_PHONE)) {
                            Toast.makeText(getApplicationContext(),
                                    "Debe conceder permiso a la app", Toast.LENGTH_LONG).show();
                        } else {
                            //Pedimos permiso al usuario
                            ActivityCompat.requestPermissions(ActivityTienda.this,
                                    new String[]{Manifest.permission.CALL_PHONE}, MY_PERMISSIONS_CALL);
                        }

                    } else {
                        //Hay permiso
                       hacerLlamada();
                    }

                } else {
                    //Versiones anteriores a  Marshmallow
                    hacerLlamada();
                }
            }

        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case MY_PERMISSIONS_CALL:{
                if(grantResults.length>0 &&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    hacerLlamada();
                }else{
                    //Permiso denegado
                }
            }return;

        }
    }

    public void hacerLlamada(){
        Intent intent= new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+tienda.getTelefono()));
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.CALL_PHONE) !=
                PackageManager.PERMISSION_GRANTED)
            return;
            startActivity(intent);
    }

    public void navegar(){
        Intent intent= new Intent(Intent.ACTION_VIEW,Uri.parse(tienda.getWeb()));
        startActivity(intent);
    }




}
