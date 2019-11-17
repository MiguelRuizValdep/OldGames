package com.example.oldgames.clasesAdaptador;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oldgames.R;
import com.example.oldgames.clasesObjeto.Tienda;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorListaTiendas extends RecyclerView.Adapter<AdaptadorListaTiendas.ViewHolderListaTiendas> implements View.OnClickListener {

    private ArrayList<Tienda> tiendas;
    private Context contexto;
    private View.OnClickListener listener;

    public AdaptadorListaTiendas(Context contexto, ArrayList<Tienda> tiendas){
        this.contexto=contexto;
        this.tiendas=tiendas;
    }

    @NonNull
    @Override
    public AdaptadorListaTiendas.ViewHolderListaTiendas onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View vista= LayoutInflater.from(contexto).inflate(R.layout.item_lista_tiendas,viewGroup,false);
        vista.setOnClickListener(this);
        return new ViewHolderListaTiendas(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorListaTiendas.ViewHolderListaTiendas viewHolderListaTiendas, int i) {
     viewHolderListaTiendas.nombretienda.setText(tiendas.get(i).getNombre());
     Picasso.with(contexto).load(tiendas.get(i).getFoto()).into(viewHolderListaTiendas.fotoTienda);
    }

    @Override
    public int getItemCount() {
        return tiendas.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolderListaTiendas extends RecyclerView.ViewHolder {
        private final String RUTA="fuentes/juego.ttf";
        private Typeface fuente=Typeface.createFromAsset(contexto.getAssets(),RUTA);
        private TextView nombretienda;
        private ImageView fotoTienda;

        public ViewHolderListaTiendas(@NonNull View itemView) {
            super(itemView);
            nombretienda=(TextView) itemView.findViewById(R.id.lsTiendanombre);
            fotoTienda=(ImageView) itemView.findViewById(R.id.lsTiendaimagen);
            nombretienda.setTypeface(fuente);
        }
    }
}
