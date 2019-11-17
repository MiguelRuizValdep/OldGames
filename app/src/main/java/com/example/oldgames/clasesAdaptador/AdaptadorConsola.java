package com.example.oldgames.clasesAdaptador;

import android.animation.ObjectAnimator;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.oldgames.Interfaces.ItemLongClicListener;
import com.example.oldgames.R;

import com.example.oldgames.clasesObjeto.Consola;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdaptadorConsola extends RecyclerView.Adapter<AdaptadorConsola.ViewHolderConsola> implements View.OnClickListener{

    private ArrayList<Consola> consolas;
    private Context contexto;
    private View.OnClickListener listener;
    private ObjectAnimator animUno;
    private AlertDialog dialog;
    private Typeface fuente;
    private final String RUTA="fuentes/ChelaOne.ttf";

    public AdaptadorConsola(ArrayList<Consola>consolas,Context contexto){
        this.consolas=consolas;
        this.contexto=contexto;
    }

    @NonNull
    @Override
    public AdaptadorConsola.ViewHolderConsola onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View vista= LayoutInflater.from(contexto).inflate(R.layout.item_lista_consola,viewGroup,false);
        vista.setOnClickListener(this);
        return new ViewHolderConsola(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdaptadorConsola.ViewHolderConsola viewHolderConsola, final int i) {

        fuente=Typeface.createFromAsset(contexto.getAssets(),RUTA);
        Picasso.with(contexto).load(consolas.get(i).getLogo()).into(viewHolderConsola.imvLogo);
        Picasso.with(contexto).load(consolas.get(i).getFoto()).into(viewHolderConsola.imgConsola);
        viewHolderConsola.setItemLongClicListener(new ItemLongClicListener() {
            @Override
            public void onItenLongClick(View v, int pos) {
                final AlertDialog.Builder dialogo= new AlertDialog.Builder(contexto);
                View dVista=LayoutInflater.from(contexto).inflate(R.layout.dialogo_personalizado,null);
                TextView datos=(TextView)dVista.findViewById(R.id.dialogoDatos);
                TextView textUno=(TextView)dVista.findViewById(R.id.dialogoTxtOne);
                TextView textDos=(TextView)dVista.findViewById(R.id.dialogoTxtTwo);
                Button btn=(Button)dVista.findViewById(R.id.dialogoBtn);

                textUno.setText("Compañia: "+consolas.get(i).getCompany());
                textDos.setText("Año: "+String.valueOf(consolas.get(i).getAnio()));
                datos.setText(consolas.get(i).getNombre());
                textDos.setTypeface(fuente);
                textUno.setTypeface(fuente);
                datos.setTypeface(fuente);
                btn.setTypeface(fuente);




                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        cerrarDialogo();
                    }
                });
                dialogo.setView(dVista);
                dialog=dialogo.create();
                dialog.show();
            }

        });


    }

    @Override
    public int getItemCount() {
        return consolas.size();
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
        dialog.dismiss();
    }

    public class ViewHolderConsola extends RecyclerView.ViewHolder implements View.OnLongClickListener{

        private ImageView imvLogo,imgConsola;
        private ItemLongClicListener itemLongClicListener;

        public ViewHolderConsola(@NonNull View itemView) {
            super(itemView);
            imvLogo=(ImageView)itemView.findViewById(R.id.logoConsola);
            imgConsola=(ImageView) itemView.findViewById(R.id.fotoConsola);
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
