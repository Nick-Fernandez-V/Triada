package com.Nick.myapplication.fragments.tabs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.Nick.myapplication.adapters.chat.AdapterMensajes;
import com.Nick.myapplication.adapters.chat.MensajeEnviar;
import com.Nick.myapplication.adapters.chat.MensajeRecibir;
import com.Nick.myapplication.R;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class ChatFragment extends Fragment {

    View root;
    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    private DatabaseReference CHAT;
    private DatabaseReference DB_NOMBRE;
    private FirebaseStorage storage;
    DatabaseReference USERS;
    DatabaseReference ID;

    private static final int PHOTO_SEND = 1;
    private AdapterMensajes adapter;
    RecyclerView rvMensajes;

    String fotoPerfilEnviada;
    ImageView fotoPerfil;
    TextView nombre;
    EditText txtMensaje;
    ImageButton btnEnviar;
    ImageButton btnEnviarFoto;


    public View onCreateView( @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState ){
        root = inflater.inflate(R.layout.fragment_chat, container, false);
        constructor();
        cargarFotoPerfil();
        proceso();
        return root;
    }


    public void constructor(){
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        CHAT = database.getReference("CHAT");
        USERS = database.getReference("USERS");
        ID = USERS.child(currentUser.getUid());
        DB_NOMBRE = ID.child("NOMBRE");
        storage = FirebaseStorage.getInstance();

        fotoPerfil = root.findViewById(R.id.iVFotoPerfil);
        nombre = root.findViewById(R.id.tVnombre);
        txtMensaje = root.findViewById(R.id.eTMensaje);
        btnEnviar = root.findViewById(R.id.bEnviar);
        btnEnviarFoto = root.findViewById(R.id.iBFoto);

        rvMensajes = root.findViewById(R.id.rVHistorialChat);
        // Adapter crea una Instancia del Adapter de Mensajes
        LinearLayoutManager I = new LinearLayoutManager(root.getContext());
        rvMensajes.setLayoutManager(I);
        adapter = new AdapterMensajes(root.getContext());
        rvMensajes.setAdapter(adapter);
    }

    public void cargarFotoPerfil()
    {
        StorageReference       storageReference = storage.getReference(currentUser.getUid());
        final StorageReference fotoreferencia   = storageReference.child("Foto_Perfil");
        fotoreferencia.getDownloadUrl().addOnSuccessListener(uri1 -> ID.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange( @NotNull DataSnapshot dataSnapshot ){
                if (dataSnapshot.exists())
                    fotoPerfilEnviada = uri1.toString();
                Glide.with(getContext()).load(uri1).fitCenter().into(fotoPerfil);
            }

            @Override
            public void onCancelled( @NotNull DatabaseError error ){
            }
        })).addOnFailureListener(exception -> Toast.makeText(getContext(), "Error al cargar la Imagen",
                                                             Toast.LENGTH_SHORT).show());
    }

    public void proceso(){
//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
        DB_NOMBRE.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( @NotNull DataSnapshot dataSnapshot ){
                if (dataSnapshot.exists()) {
                    nombre.setText(Objects.requireNonNull(dataSnapshot.getValue()).toString());
                }
            }
            @Override
            public void onCancelled( @NotNull DatabaseError error ){
            }
        });

//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted( int positionStart, int itemCount ){
                super.onItemRangeInserted(positionStart, itemCount);
                setScrollbar();
            }
        });
//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
        CHAT.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded( @NonNull DataSnapshot snapshot, @Nullable String previousChildName ){
                MensajeRecibir m = snapshot.getValue(MensajeRecibir.class);
                adapter.addMensaje(m);
            }

            @Override
            public void onChildChanged( @NonNull DataSnapshot snapshot, @Nullable String previousChildName ){
            }

            @Override
            public void onChildRemoved( @NonNull DataSnapshot snapshot ){
            }

            @Override
            public void onChildMoved( @NonNull DataSnapshot snapshot, @Nullable String previousChildName ){
            }

            @Override
            public void onCancelled( @NonNull DatabaseError error ){
            }
        });

//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
        btnEnviar.setOnClickListener(v -> {
            CHAT.push().setValue(new MensajeEnviar(txtMensaje.getText().toString(), "", "1", ServerValue.TIMESTAMP,currentUser.getUid()));
            txtMensaje.setText("");
        });
//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
        btnEnviarFoto.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.setType("image/jpeg");
            i.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
            startActivityForResult(Intent.createChooser(i, "Selecciona una foto"), PHOTO_SEND);
        });
    }
//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX
    @Override
    public void onActivityResult( int requestCode, int resultCode, @Nullable Intent data ){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_SEND && resultCode == RESULT_OK) {
            assert data != null;
            Uri uri = data.getData();

            StorageReference storageReferencechat = storage.getReference("Imagenes_chat");
            final StorageReference fotoreferencia =
                    storageReferencechat.child(uri.getLastPathSegment());

            fotoreferencia.putFile(uri).addOnSuccessListener(taskSnapshot ->
                                                             fotoreferencia.getDownloadUrl().addOnSuccessListener(uri1 -> {
                                                               MensajeEnviar m =
                                                                 new MensajeEnviar("Te ha enviado una foto", uri1.toString(), "2", ServerValue.TIMESTAMP,currentUser.getUid());
                                                               CHAT.push().setValue(m);
                                                             }).addOnFailureListener(exception ->
                                                                                     Toast.makeText(getContext(), "Error al cargar la Imagen",
                                                                                                    Toast.LENGTH_SHORT).show()));
        }
    }
//XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX

    private void setScrollbar(){
        rvMensajes.scrollToPosition(adapter.getItemCount() - 1);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }
}