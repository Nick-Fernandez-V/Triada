package com.Nick.myapplication.adapters.amigos;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Nick.myapplication.R;
import com.Nick.myapplication.adapters.Utilidades;

import de.hdodenhof.circleimageview.CircleImageView;

public class HolderAmigos extends RecyclerView.ViewHolder {

    private TextView Nombre;
    private TextView Estado;
    private TextView Descripcion;
    private CircleImageView fotoPerfil;

    public HolderAmigos( @NonNull View itemView ){
        super(itemView);
        if (Utilidades.visualizacion==Utilidades.LIST) {
            Nombre = itemView.findViewById(R.id.tVNombre_Amigos);
            Estado = itemView.findViewById(R.id.tVEstado_Amigos);
            fotoPerfil = itemView.findViewById(R.id.iVFoto_Amigos);
            Descripcion = itemView.findViewById(R.id.tVDescripcion_Amigos);
        }
        else {
            Nombre = itemView.findViewById(R.id.tVNombre_Amigos);
            fotoPerfil = itemView.findViewById(R.id.iVFoto_Amigos);
        }
    }

    public TextView getNombre(){
        return Nombre;
    }

    public void setNombre( TextView nombre ){
        Nombre = nombre;
    }

    public TextView getEstado(){
        return Estado;
    }

    public void setEstado( TextView estado ){
        Estado = estado;
    }

    public CircleImageView getFotoPerfil(){
        return fotoPerfil;
    }

    public void setFotoPerfil( CircleImageView fotoPerfil ){
        this.fotoPerfil = fotoPerfil;
    }

    public TextView getDescripcion(){
        return Descripcion;
    }

    public void setDescripcion(TextView descripcion){
        Descripcion = descripcion;
    }
}
