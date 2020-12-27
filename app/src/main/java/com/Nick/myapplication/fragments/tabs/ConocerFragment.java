package com.Nick.myapplication.fragments.tabs;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.Nick.myapplication.R;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ConocerFragment extends Fragment {
    View root;
    FirebaseDatabase database;
    DatabaseReference USERS;
    DatabaseReference ID;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    TextView tVpresentacion;
    Button bChat;
    ImageButton iBYes,iBNo,iBCorazon;

    ImageView iVpresentacion;

    String[] listId = new String[250];
    String[] listEdad = new  String[250];
    String[] listEdadm = new  String[250];
    String[] listEdadM = new  String[250];
    String[] listGenero = new String[250];
    String[] listNombre = new String[250];


    int apuntador = 0,maximo,corazoncheck = 0;
    FirebaseStorage storage;

    @Override
    public View onCreateView( LayoutInflater inflater ,ViewGroup container ,Bundle savedInstanceState )
    { root = inflater.inflate(R.layout.fragment_conocer ,container ,false);

        constructor();
        recolectarUsuarios();
        Boton_Si_o_NO();
        return root;
    }


    public void constructor(){
        database = FirebaseDatabase.getInstance();
        USERS = database.getReference("USERS");
        // USERS.orderByChild("EDAD_M");
        tVpresentacion = root.findViewById(R.id.tVPresentacionVotacion);
        iBYes = root.findViewById(R.id.iBYes);
        iBCorazon = root.findViewById(R.id.iBCorazon);
        iBNo = root.findViewById(R.id.iBNo);
        bChat = root.findViewById(R.id.button3);
        iVpresentacion = root.findViewById(R.id.imageView13);
        storage = FirebaseStorage.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }

    public void  recolectarUsuarios(){
        USERS.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange( @NonNull DataSnapshot snapshot ){
                if (snapshot.exists())
                {
                    maximo = 0;
                    for (DataSnapshot ds: snapshot.getChildren())
                    {


                        if (!( currentUser.getUid().trim().equals(ds.child("USUARIO").getValue())))
                        {
                      //  if(!(ds.child("AMIGOS").child("MOoFxA2SAVYF7fOTi79QRwoFxHa2").exists()))
                            {
                            listId[maximo] = Objects.requireNonNull(ds.child("USUARIO").getValue()).toString();
                            listEdad[maximo] = Objects.requireNonNull(ds.child("EDAD").getValue()).toString();
                            listEdadm[maximo] =  Objects.requireNonNull(ds.child("EDAD_m").getValue()).toString();
                            listEdadM[maximo] =  Objects.requireNonNull(ds.child("EDAD_M").getValue()).toString();
                            listGenero[maximo] = Objects.requireNonNull(ds.child("GENERO").getValue()).toString();
                            listNombre[maximo] = Objects.requireNonNull(ds.child("NOMBRE").getValue()).toString();
                            maximo++;
                            }
                        }

                        //maximo = snapshot.getValue(Integer.class);
                    }
                }
                if (apuntador == 0){
                StorageReference fotoreferencia = storage.getReference(listId[0]).child("Foto_Perfil");
                ID = USERS.child(listId[0]);
                cargarFotoPerfil(fotoreferencia);
                tVpresentacion.setText(listNombre[0]+" "+listEdad[0] + ". " + listGenero[0]);
                }
            }
            @Override
            public void onCancelled( @NonNull DatabaseError error ){
                Toast.makeText(getContext(), error.toString(),
                               Toast.LENGTH_SHORT).show();
            }
        });
    }





    public void Boton_Si_o_NO(){

        iBYes.setOnClickListener(yes -> ClipBoton("LIKE"));

        iBCorazon.setOnClickListener(v -> ClipBoton("CORAZON"));

        iBNo.setOnClickListener(No -> ClipBoton("NOLIKE"));

        bChat.setOnClickListener(x -> ClipBoton("AMIGO"));

    }


    public void ClipBoton(String carpeta){
        ID = USERS.child(currentUser.getUid());
        if (carpeta.length() == 4) //LIKE
        {
            ID.child("LIKE").child(listId[apuntador]).setValue(listId[apuntador]);
            ID.child("NOLIKE").child(listId[apuntador]).removeValue();
            CambiarPersona();
        }
        if (carpeta.length() == 5) //AMIGO
        {
            ID.child("AMIGO").child(listId[apuntador]).setValue(listId[apuntador]);
            ID.child("NOLIKE").child(listId[apuntador]).removeValue();
            CambiarPersona();
        }
        if (carpeta.length() == 6) //NOLIKE
        {   ID.child("NOLIKE").child(listId[apuntador]).setValue(listId[apuntador]);
            ID.child("LIKE").child(listId[apuntador]).removeValue();
            ID.child("CORAZON").child(listId[apuntador]).removeValue();
            CambiarPersona();
        }


        if (carpeta.length() == 7) //CORAZON
        {
              if (corazoncheck == 1)
            {ID.child("CORAZON").child(listId[apuntador]).removeValue();
             iBCorazon.setImageResource(R.drawable.corazon);
                corazoncheck--;
            }
            else {

                ID.child("CORAZON").child(listId[apuntador]).setValue(listId[apuntador]);
                ID.child("NOLIKE").child(listId[apuntador]).removeValue();
            //    int i = getResources().getIdentifier("drawable-v24/corazondown",null,null);
                iBCorazon.setImageResource(R.drawable.corazondown);
                corazoncheck++;
              //  iBCorazon.getBackground().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
            }
        }

    }


    public  void CambiarPersona()
    {     apuntador++;
         if (apuntador < maximo) {
             tVpresentacion.setText(listNombre[apuntador] + " " + listEdad[apuntador] + ". " + listGenero[apuntador]);
             StorageReference fotoreferencia = storage.getReference(listId[apuntador]).child("Foto_Perfil");
             ID = USERS.child(listId[apuntador]);
             cargarFotoPerfil(fotoreferencia);
         }else
             {
                apuntador =0;
                tVpresentacion.setText(listNombre[apuntador] + " " + listEdad[apuntador] + ". " + listGenero[apuntador]);
                 StorageReference fotoreferencia = storage.getReference(listId[apuntador]).child("Foto_Perfil");
                 ID = USERS.child(listId[apuntador]);
                 cargarFotoPerfil(fotoreferencia);
             }
        iBCorazon.setImageResource(R.drawable.corazon);
        corazoncheck = 0;
    }

    public void cargarFotoPerfil(  StorageReference fotoreferenciaS)
    {
        fotoreferenciaS.getDownloadUrl().addOnSuccessListener(uri1 -> ID.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange( @NotNull DataSnapshot dataSnapshot ){
                if (dataSnapshot.exists())
                Glide.with(getContext()).load(uri1).fitCenter().into(iVpresentacion);
            }
            @Override
            public void onCancelled( @NotNull DatabaseError error ){
            }
        })).addOnFailureListener(exception -> Toast.makeText(getContext(), "Error al cargar la Imagen",
                                                             Toast.LENGTH_SHORT).show());
    }


}