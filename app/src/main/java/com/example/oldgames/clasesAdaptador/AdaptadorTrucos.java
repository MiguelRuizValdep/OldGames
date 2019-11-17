package com.example.oldgames.clasesAdaptador;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.oldgames.R;
import com.example.oldgames.clasesObjeto.Truco;



import java.util.ArrayList;

public class AdaptadorTrucos extends RecyclerView.Adapter<AdaptadorTrucos.ViewHolderAdaptadorTrucos> {
    private ArrayList<Truco> trucos;
    private Context contexto;
    private final String RUTA="fuentes/ChelaOne.ttf";
    private Typeface fuente;

    public AdaptadorTrucos(Context contexto,ArrayList<Truco> trucos){
        this.contexto=contexto;
        this.trucos=trucos;
        fuente=Typeface.createFromAsset(contexto.getAssets(),RUTA);
    }



    @NonNull
    @Override
    public AdaptadorTrucos.ViewHolderAdaptadorTrucos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View vista= LayoutInflater.from(contexto).inflate(R.layout.item_truco,viewGroup,false);
        return new ViewHolderAdaptadorTrucos(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorTrucos.ViewHolderAdaptadorTrucos viewHolderAdaptadorTrucos, int i) {
       viewHolderAdaptadorTrucos.titulo.setText(trucos.get(i).getDescripcion());
       viewHolderAdaptadorTrucos.truco.setText(trucos.get(i).getTruco());

        viewHolderAdaptadorTrucos.titulo.setTypeface(fuente);
        viewHolderAdaptadorTrucos.truco.setTypeface(fuente);

    }

    @Override
    public int getItemCount() {
        return trucos.size();
    }

    public class ViewHolderAdaptadorTrucos extends RecyclerView.ViewHolder {
        private TextView titulo,truco;
        public ViewHolderAdaptadorTrucos(@NonNull View itemView) {
            super(itemView);
            titulo=(TextView)itemView.findViewById(R.id.trucoTitulo);
            truco=(TextView)itemView.findViewById(R.id.trucoTruco);
        }
    }
}
