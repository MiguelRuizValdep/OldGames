package com.example.oldgames.clasesAdaptador;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oldgames.R;
import com.example.oldgames.clasesObjeto.Comentario;
import com.example.oldgames.clasesObjeto.Usuario;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdaptadorMensajes extends RecyclerView.Adapter<AdaptadorMensajes.ViewHolderMensajes>   {

    private Usuario usuario;
    private ArrayList<Comentario> comentarios;
    private Context contexto;
    private Typeface fuente;
    private Typeface fuenteDos;
    private final String RUTA="fuentes/personaje.ttf";
    private final String RUTADOS="fuentes/ChelaOne.ttf";

    public AdaptadorMensajes(ArrayList<Comentario>comentarios,Context contexto,Usuario usuario){
     this.comentarios=comentarios;
     this.contexto=contexto;
     this.usuario=usuario;
     try{
         fuente=Typeface.createFromAsset(contexto.getAssets(),RUTA);
         fuenteDos=Typeface.createFromAsset(contexto.getAssets(),RUTADOS);
     }catch (NullPointerException e){
         e.getStackTrace();
     }

    }

    @NonNull
    @Override
    public ViewHolderMensajes onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View vista= LayoutInflater.from(contexto).inflate(R.layout.item_foro,viewGroup,false);
        return new ViewHolderMensajes(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderMensajes viewHolderMensajes, int i) {

       Date fecha = new Date();
       fecha=comentarios.get(i).getFecha();
       String fFecha=(new SimpleDateFormat("dd-MM-yyyy hh:mm:ss").format(fecha));

       viewHolderMensajes.nomUser.setText(comentarios.get(i).getAlias());
       viewHolderMensajes.mensaje.setText(comentarios.get(i).getMensaje());
       viewHolderMensajes.titulo.setText(comentarios.get(i).getTitulo());
       viewHolderMensajes.fecha.setText(fFecha);
       viewHolderMensajes.fotoUser.setImageResource(Integer.parseInt(comentarios.get(i).getRuta()));

       if(comentarios.get(i).getAlias().equals(usuario.getAlias())){
           viewHolderMensajes.fotoUser.setBackgroundResource(R.drawable.marco_mensaje_foto_self);
       }else{
           viewHolderMensajes.fotoUser.setBackgroundResource(R.drawable.marco_mensajes_foto);
       }

       viewHolderMensajes.layout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               if(viewHolderMensajes.mensaje.getVisibility()==View.GONE){
                   viewHolderMensajes.mensaje.setVisibility(View.VISIBLE);
               }else{
                   viewHolderMensajes.mensaje.setVisibility(View.GONE);
               }
           }
       });

    }

    @Override
    public int getItemCount() {
        return comentarios.size();
    }



    public class ViewHolderMensajes extends RecyclerView.ViewHolder {
        private TextView nomUser,mensaje,fecha,titulo;
        private ImageView fotoUser;
        private LinearLayout layout;


        public ViewHolderMensajes(@NonNull View itemView) {
            super(itemView);
            nomUser=(TextView) itemView.findViewById(R.id.texvIconUsers);
            mensaje=(TextView) itemView.findViewById(R.id.texvIconMensaje);
            fecha=(TextView) itemView.findViewById(R.id.texvIconFecha);
            titulo=(TextView) itemView.findViewById(R.id.texvIconTitulo);
            fotoUser=(ImageView) itemView.findViewById(R.id.imgIconUsers);
            layout=(LinearLayout) itemView.findViewById(R.id.layoutForoMns);
            nomUser.setTypeface(fuente);

            mensaje.setTypeface(fuenteDos);
            titulo.setTypeface(fuenteDos);
            fecha.setTypeface(fuenteDos);


        }
    }
}
