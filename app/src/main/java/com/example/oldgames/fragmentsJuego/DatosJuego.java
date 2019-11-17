package com.example.oldgames.fragmentsJuego;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.example.oldgames.R;
import com.example.oldgames.clasesActividad.DetalleJuego;
import com.example.oldgames.clasesObjeto.Juego;

import com.squareup.picasso.Picasso;



public class DatosJuego extends Fragment {

    private TextView anio, descripcion, company,genero;
    private Typeface fuente;
    private final String RUTA="fuentes/ChelaOne.ttf";
    private FrameLayout layout;
    private float rotar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.fragment_datos_juego, container, false);

        fuente=Typeface.createFromAsset(getContext().getAssets(),RUTA);


        anio=(TextView)vista.findViewById(R.id.dtsJuegoanio);
        company=(TextView)vista.findViewById(R.id.dtsJuegoCompany);
        descripcion=(TextView)vista.findViewById(R.id.dtsJuegoDescripcion);
        genero=(TextView) vista.findViewById(R.id.dtsJuegoGenero);

        layout=(FrameLayout) vista.findViewById(R.id.layoutJuego);


        Juego juego= ((DetalleJuego)getActivity()).getJuego();

        if(juego.getorientacion().trim().equals("horizontal")){
            rotar=90.0f;
        }else{
            rotar=0.0f;
        }

        anio.setTypeface(fuente);
        company.setTypeface(fuente);
        genero.setTypeface(fuente);
        descripcion.setTypeface(fuente);


        anio.setText(String.valueOf("Año: "+juego.getAnio()));
        company.setText("Compañia: "+juego.getCompany());
        descripcion.setText(juego.getDescripcion());
        genero.setText("Génro: "+juego.getGenero());



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


        return vista;
    }

}
