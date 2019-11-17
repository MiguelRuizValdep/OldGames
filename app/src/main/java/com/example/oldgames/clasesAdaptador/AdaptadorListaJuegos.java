package com.example.oldgames.clasesAdaptador;


import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.oldgames.Interfaces.ItemLongClicListener;
import com.example.oldgames.R;
import com.example.oldgames.clasesObjeto.Juego;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorListaJuegos extends RecyclerView.Adapter<AdaptadorListaJuegos.ViewHolderListaJuegos> implements View.OnClickListener {


    private ArrayList<Juego> juegos;
    private Context contexto;
    private View.OnClickListener listener;
    private final String RUTA="fuentes/juego.ttf";
    private final String RUTA2="fuentes/ChelaOne.ttf";
    private Typeface fuente,fuente2;
    private AlertDialog dialogo;

    public AdaptadorListaJuegos(Context contexto,ArrayList<Juego> juegos){
        this.contexto=contexto;
        this.juegos=juegos;

        fuente=Typeface.createFromAsset(contexto.getAssets(),RUTA);
        fuente2=Typeface.createFromAsset(contexto.getAssets(),RUTA2);
    }

    @NonNull
    @Override
    public AdaptadorListaJuegos.ViewHolderListaJuegos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View vista= LayoutInflater.from(contexto).inflate(R.layout.item_lista_juegos,viewGroup,false);
        vista.setOnClickListener(this);
        return new ViewHolderListaJuegos(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorListaJuegos.ViewHolderListaJuegos viewHolderListaJuegos, int i) {

        viewHolderListaJuegos.titulo.setText(juegos.get(i).getNombre());
        Picasso.with(contexto).load(juegos.get(i).getCaratula()).into(viewHolderListaJuegos.imagen);

        viewHolderListaJuegos.setItemLongClicListener(new ItemLongClicListener() {
            @Override
            public void onItenLongClick(View v, int pos) {
                AlertDialog.Builder builder=new AlertDialog.Builder(contexto);
                View dVista=LayoutInflater.from(contexto).inflate(R.layout.dialogo_personalizado,null);
                TextView datos=(TextView)dVista.findViewById(R.id.dialogoDatos);
                TextView textUno=(TextView)dVista.findViewById(R.id.dialogoTxtOne);
                TextView textDos=(TextView)dVista.findViewById(R.id.dialogoTxtTwo);
                Button btn=(Button)dVista.findViewById(R.id.dialogoBtn);

                textUno.setText("Genero: "+juegos.get(pos).getGenero());
                textDos.setText("Compañia: "+String.valueOf(juegos.get(pos).getCompany()));
                datos.setText(juegos.get(pos).getNombre());
                textDos.setTypeface(fuente2);
                textUno.setTypeface(fuente2);
                datos.setTypeface(fuente2);
                btn.setTypeface(fuente2);

                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cerrarDialogo();
                    }
                });
                builder.setView(dVista);
                dialogo=builder.create();
                dialogo.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return juegos.size();
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


    private void cerrarDialogo(){
       dialogo.dismiss();
        }



    public class ViewHolderListaJuegos extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        private ImageView imagen;
        private TextView titulo;
        private ItemLongClicListener itemLongClicListener;

        public ViewHolderListaJuegos(@NonNull View itemView) {
            super(itemView);
            imagen=(ImageView)itemView.findViewById(R.id.lsJimagen);
            titulo=(TextView) itemView.findViewById(R.id.lsJnombre);
            titulo.setTypeface(fuente);
            itemView.setOnLongClickListener(this);

        }

        public void setItemLongClicListener(ItemLongClicListener clickLong){
            this.itemLongClicListener=clickLong;
        }


        @Override
        public boolean onLongClick(View view) {
            this.itemLongClicListener.onItenLongClick(view,getLayoutPosition());
            return true;
        }
    }



}


