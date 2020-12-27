package com.Nick.myapplication.adapters.chat;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.Nick.myapplication.R;

public class HolderMensaje extends RecyclerView.ViewHolder {

    private TextView mensaje;
    private TextView hora;
    private ImageView fotoMensaje;


    public HolderMensaje(@NonNull View itemView){
        super(itemView);

    mensaje = itemView.findViewById(R.id.tVCardTextEnviado);
    hora = itemView.findViewById(R.id.tVCardHoraEnviado);
    fotoMensaje = itemView.findViewById(R.id.iVCardFotoEnviado);

    }

    public ImageView getFotoMensaje(){
        return fotoMensaje;
    }

    public TextView getMensaje(){
        return mensaje;
    }

    public TextView getHora(){
        return hora;
    }

    public void setFotoMensaje( ImageView fotoMensaje ){
        this.fotoMensaje = fotoMensaje;
    }

    public void setMensaje( TextView mensaje ){
        this.mensaje = mensaje;
    }

    public void setHora( TextView hora ){
        this.hora = hora;
    }
}
