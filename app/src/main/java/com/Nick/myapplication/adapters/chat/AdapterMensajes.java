package com.Nick.myapplication.adapters.chat;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.Nick.myapplication.R;
import com.Nick.myapplication.adapters.chat.HolderMensaje;
import com.Nick.myapplication.adapters.chat.MensajeRecibir;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AdapterMensajes extends RecyclerView.Adapter<HolderMensaje> {

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private List<MensajeRecibir> listMensaje = new ArrayList<>();
    private Context context;
    private View v;

    TextView mensaje;
    TextView hora;
    ImageView fotoMensaje;
    int numeroactual = 0;
    String usuarioRemitente;
    String usuarioActual;
    public AdapterMensajes(Context context) {
        this.context = context;
    }


    public void addMensaje(MensajeRecibir m)
    {
     listMensaje.add(m);
     notifyItemInserted(listMensaje.size());
    }

    @NonNull
    @Override
    public HolderMensaje onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        usuarioActual =  currentUser.getUid();

//me salta un numeroactual a 128 cuando subo de chat

        if(numeroactual<getItemCount())
        usuarioRemitente = listMensaje.get(numeroactual).getUsuario();

        int size = getItemCount();
        int numero = numeroactual;

        if (usuarioRemitente.equals(usuarioActual) )
        {v = LayoutInflater.from(context).inflate(R.layout.card_view_m_enviado, parent, false);
        }else {
         v = LayoutInflater.from(context).inflate(R.layout.card_view_m_recibido, parent, false);
        }

        return new HolderMensaje(v);
    }


    @Override
    public void onBindViewHolder( @NonNull HolderMensaje holder,int position ){

       // usuarioRemitente = listMensaje.get(position-1).getUsuario();

        if (usuarioRemitente.equals(usuarioActual)){
            mensaje = v.findViewById(R.id.tVCardTextEnviado);
            hora = v.findViewById(R.id.tVCardHoraEnviado);
            fotoMensaje = v.findViewById(R.id.iVCardFotoEnviado);

        }else {
            fotoMensaje = v.findViewById(R.id.iVCardFotoRecibido);
            mensaje =   v.findViewById(R.id.tVCardTextRecibido);
            hora =   v.findViewById(R.id.tVCardHoraRecibida);

        }


        if (listMensaje.get(position).getType_mensaje().equals("1")) {
            fotoMensaje.setVisibility(View.GONE);
            mensaje.setVisibility(View.VISIBLE);
            hora.setVisibility(View.VISIBLE);
            mensaje.setText(listMensaje.get(position).getMensaje());
        }
        else {
            if (listMensaje.get(position).getType_mensaje().equals("2")) {
                fotoMensaje.setVisibility(View.VISIBLE);
                mensaje.setVisibility(View.GONE);
                hora.setVisibility(View.GONE);
                Glide.with(context).load(listMensaje.get(position).getUrlFoto()).into(fotoMensaje);
            }
        }
        Long             codigoHora = listMensaje.get(position).getHora();
        Date             date       = new Date(codigoHora);
        SimpleDateFormat sDF        = new SimpleDateFormat("h:mm a");
        hora.setText(sDF.format(date));

    //    numeroactual = position;
        if(numeroactual<getItemCount())
          numeroactual++;

        if (numeroactual>getItemCount())
            numeroactual=position;

    }

/*
            if (listMensaje.get(position).getType_mensaje().equals("1")) {
                holder.getFotoMensaje().setVisibility(View.GONE);
                holder.getMensaje().setVisibility(View.VISIBLE);
                holder.getHora().setVisibility(View.VISIBLE);
                holder.getMensaje().setText(listMensaje.get(position).getMensaje());
            }
            else {
                if (listMensaje.get(position).getType_mensaje().equals("2")) {
                    holder.getFotoMensaje().setVisibility(View.VISIBLE);
                    holder.getMensaje().setVisibility(View.GONE);
                    holder.getHora().setVisibility(View.GONE);
                    Glide.with(context).load(listMensaje.get(position).getUrlFoto()).into(holder.getFotoMensaje());
                }
            }
            Long             codigoHora = listMensaje.get(position).getHora();
            Date             date       = new Date(codigoHora);
            SimpleDateFormat sDF        = new SimpleDateFormat("h:mm a");
            holder.getHora().setText(sDF.format(date));
*/

    @Override
    public int getItemCount() {
        return listMensaje.size();
    }
}
