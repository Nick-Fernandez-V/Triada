package com.Nick.myapplication.fragments.tabs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.Nick.myapplication.R;
import com.Nick.myapplication.adapters.Utilidades;
import com.Nick.myapplication.adapters.amigos.AdapterAmigos;
import com.Nick.myapplication.adapters.amigos.InfoDeAmigo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class AmigosFragment extends Fragment {
    View root;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;
    FirebaseDatabase database;
    DatabaseReference USERS;
    FirebaseStorage storage;
    StorageReference fotoreferencia;

    RecyclerView rVlistaAmigos;
    ArrayList<InfoDeAmigo> listaDeAmigos;
    AdapterAmigos adapterAmigos;

    ImageButton bList, bGrid;
    String[] listAmigo = new String[250];

    int numeroMaxAmigos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        root = inflater.inflate(R.layout.fragment_amigos, container, false);

        constructor();
        RecolectarAmigos();
        llenadoDeDatos();
        ClickListener();

        return root;
    }

    private void ClickListener(){

        bList.setOnClickListener(v -> {
            Utilidades.visualizacion = Utilidades.LIST;
            recicler();
        });

        bGrid.setOnClickListener(v -> {
            Utilidades.visualizacion = Utilidades.GRID;
            recicler();
        });
    }

    public void recicler(){
        if (Utilidades.visualizacion == Utilidades.LIST) {
            rVlistaAmigos.setLayoutManager(new LinearLayoutManager(root.getContext()));
        }
        else {
            rVlistaAmigos.setLayoutManager(new GridLayoutManager(root.getContext(), 3));
        }
        adapterAmigos = new AdapterAmigos(root.getContext(), listaDeAmigos);
        adapterAmigos.setOnClickListener(v -> {

        });
        rVlistaAmigos.setAdapter(adapterAmigos);
        adapterAmigos.notifyDataSetChanged();
    }

    public void constructor(){
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        USERS = database.getReference("USERS");
        storage = FirebaseStorage.getInstance();

        bList = root.findViewById(R.id.bLista);
        bGrid = root.findViewById(R.id.bGrid);
        rVlistaAmigos = root.findViewById(R.id.listaAmigos);
        listaDeAmigos = new ArrayList<>();

    }


    public void RecolectarAmigos(){

        USERS.child(currentUser.getUid()).child("AMIGO").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                if (snapshot.exists()) {
                    numeroMaxAmigos = 0;
                    for (DataSnapshot ds: snapshot.getChildren()) {
                        listAmigo[numeroMaxAmigos] = ds.getValue().toString();
                        numeroMaxAmigos++;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error){
                Toast.makeText(getContext(), error.toString(),
                               Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void llenadoDeDatos(){
        USERS.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot){
                for (DataSnapshot dsc: snapshot.getChildren()) {
                    for (int c = 0; c < numeroMaxAmigos; c++) {
                        if (dsc.child("USUARIO").getValue().toString().equals(listAmigo[c])) {
                            fotoreferencia =
                                    storage.getReference(listAmigo[c]).child("Foto_Perfil");
                            fotoreferencia.getDownloadUrl().addOnSuccessListener(uri -> {
                                String
                                        nombre = dsc.child("NOMBRE").getValue().toString(),
                                        edad = dsc.child("EDAD").getValue().toString(),
                                        genero = dsc.child("GENERO").getValue().toString(),
                                        descripcion =
                                                dsc.child("DESCRIPCION").getValue().toString(),
                                        pais = dsc.child("PAIS").getValue().toString(),
                                        estado = dsc.child("ESTADO").getValue().toString(),
                                        foto = uri.toString();
                                listaDeAmigos.add(new InfoDeAmigo(nombre, edad, genero, descripcion, pais, estado, foto));
                                recicler();
                            });
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error){
            }
        });

    }


}