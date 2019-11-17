package com.example.oldgames.fragmentsConsola;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oldgames.R;
import com.example.oldgames.clasesActividad.DetalleConsola;
import com.example.oldgames.clasesObjeto.Consola;
import com.squareup.picasso.Picasso;


public class DatosConsola extends Fragment {

    private TextView nombre,anio,company,descripcion,detalles;
    private ImageView imagen;
    private Consola consola;
    private final String RUTA="fuentes/consola.ttf";
    private final String RUTA2="fuentes/texto.ttf";
    private final String RUTA3="fuentes/ChelaOne.ttf";
    private Typeface fuente,fuente2,fuente3;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista=inflater.inflate(R.layout.fragment_datos_consola, container, false);

        fuente=Typeface.createFromAsset(getContext().getAssets(),RUTA);
        fuente2=Typeface.createFromAsset(getContext().getAssets(),RUTA2);
        fuente3=Typeface.createFromAsset(getContext().getAssets(),RUTA3);

        nombre=(TextView)vista.findViewById(R.id.dtsNombre);
        anio=(TextView)vista.findViewById(R.id.dtsanio);
        company=(TextView)vista.findViewById(R.id.dtsCompany);
        descripcion=(TextView)vista.findViewById(R.id.dtsDescripcion);
        detalles=(TextView)vista.findViewById(R.id.dtsDetalles);

        descripcion.setMovementMethod(new ScrollingMovementMethod());

        nombre.setTypeface(fuente);

        anio.setTypeface(fuente3);
        company.setTypeface(fuente3);
        descripcion.setTypeface(fuente2);
        detalles.setTypeface(fuente3);


        imagen=(ImageView)vista.findViewById(R.id.dtsFoto);

        consola=((DetalleConsola)getActivity()).getConsola();

        nombre.setText(consola.getNombre());
        anio.setText(String.valueOf("Año: "+consola.getAnio()));
        company.setText("Compañia: "+consola.getCompany());
        descripcion.setText(consola.getDescripcion());

        Picasso.with(getContext()).load(consola.getFoto()).into(imagen);

        return vista;
    }

}
