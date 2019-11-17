package com.example.oldgames.clasesAdaptador;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.oldgames.R;
import com.example.oldgames.clasesObjeto.Personaje;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorGaleria extends RecyclerView.Adapter<AdaptadorGaleria.ViewHolderGaleria> implements View.OnClickListener  {

    private ArrayList<Personaje> personajes;
    private Context contexto;
    private View.OnClickListener listener;


    public AdaptadorGaleria(ArrayList<Personaje> personajes,Context contexto){
        this.personajes=personajes;
        this.contexto=contexto;
    }

    @NonNull
    @Override
    public ViewHolderGaleria onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View vista= LayoutInflater.from(contexto).inflate(R.layout.item_personaje_foto,null,false);
        vista.setOnClickListener(this);
        return new ViewHolderGaleria(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderGaleria viewHolderGaleria, int i) {
        Picasso.with(contexto).load(personajes.get(i).getFoto()).into(viewHolderGaleria.foto);
    }

    @Override
    public int getItemCount() {
        return personajes.size();
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

    public class ViewHolderGaleria extends RecyclerView.ViewHolder {
        private ImageView foto;
        public ViewHolderGaleria(@NonNull View itemView) {
            super(itemView);
            foto=itemView.findViewById(R.id.itemFotoPersonaje);
        }
    }
}
