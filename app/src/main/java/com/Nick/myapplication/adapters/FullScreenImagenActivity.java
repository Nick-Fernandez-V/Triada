package com.Nick.myapplication.adapters;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.Nick.myapplication.R;
import com.Nick.myapplication.adapters.GaleriaImagenAdapter;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

public class FullScreenImagenActivity extends AppCompatActivity {
    ImageView imagen;
    GaleriaImagenAdapter galeriaImagenAdapter;
    FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference USERS;
    DatabaseReference ID;

    FirebaseStorage storage;
    StorageReference storageReference;

    @Override
    protected void onCreate( Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference(mAuth.getCurrentUser().getUid());

        database = FirebaseDatabase.getInstance();
        USERS = database.getReference("USERS");
        String userID = mAuth.getCurrentUser().getUid();
        ID = USERS.child(userID);


        setContentView(R.layout.activity_full_screen_imagen);
        imagen = findViewById(R.id.imagenFull);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Visualizador de Imagen");

        Intent i             = getIntent();
        int    identificador = i.getExtras().getInt("identificador");


        if (identificador == 1) {
            int posicion = i.getExtras().getInt("idimagen");
            galeriaImagenAdapter = new GaleriaImagenAdapter(this);
            imagen.setImageResource(galeriaImagenAdapter.imagenesArray[posicion]);
        }
        if (identificador == 2) {
            StorageReference fotoreferencia = storageReference.child("Foto_Perfil");
            fotoreferencia.getDownloadUrl().addOnSuccessListener(uri1 -> ID.addValueEventListener(new ValueEventListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onDataChange( @NotNull DataSnapshot dataSnapshot ){
                    if (dataSnapshot.exists()) {
                        Glide.with(getApplicationContext()).load(uri1).fitCenter().into(imagen);
                    }
                }

                @Override
                public void onCancelled( @NotNull DatabaseError error ){
                }
            })).addOnFailureListener(exception -> Toast.makeText(getApplicationContext(), "Error al cargar la Imagen",
                                                                 Toast.LENGTH_SHORT).show());
        }

    }
}