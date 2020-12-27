package com.Nick.myapplication.adapters.amigos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Nick.myapplication.R;
import com.Nick.myapplication.adapters.Utilidades;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AdapterAmigos
        extends RecyclerView.Adapter<HolderAmigos>
        implements View.OnClickListener {

    private final ArrayList<InfoDeAmigo> listMensaje;
    private final Context context;
    private View.OnClickListener listener;

    public AdapterAmigos(Context context , ArrayList<InfoDeAmigo> listMensaje)
    {
        this.context = context;
        this.listMensaje=listMensaje;
        notifyItemInserted(listMensaje.size());
    }

    @NonNull
    @Override
    public HolderAmigos onCreateViewHolder( @NonNull ViewGroup parent, int viewType ){
        View v;
        if (Utilidades.visualizacion == Utilidades.LIST) {
            v = LayoutInflater.from(context).inflate(R.layout.card_view_amigos ,parent ,false);
        }
        else {
            v = LayoutInflater.from(context).inflate(R.layout.grid_view_amigos ,parent ,false);
        }

        v.setOnClickListener(this);
        return new HolderAmigos(v);
    }


    public void setOnClickListener(View.OnClickListener listener)
    { this.listener = listener;}


    @Override
    public void onBindViewHolder( @NonNull HolderAmigos holder, int position ){
        if (Utilidades.visualizacion == Utilidades.LIST) {
            String valores = listMensaje.get(position).getNombre()+" "+listMensaje.get(position).getGenero()+" "
                             +listMensaje.get(position).getEdad()+". "+listMensaje.get(position).getPais(),
                    descripcion = "Descripción: "+listMensaje.get(position).getDescripcion();

            holder.getNombre().setText(valores);
            holder.getEstado().setText(listMensaje.get(position).getEstado());
            holder.getDescripcion().setText(descripcion);
        }else {
            holder.getNombre().setText(listMensaje.get(position).getNombre());
        }
        Glide.with(context).load(listMensaje.get(position).getUrlFoto()).into(holder.getFotoPerfil());
    }


    @Override
    public int getItemCount(){
        return listMensaje.size();
    }


    @Override
    public void onClick( View v ){
        if(listener != null)
        {
            listener.onClick(v);
        }
    }
}


