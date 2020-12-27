package com.Nick.myapplication.fragments.perfil;

import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

public class PerfilViewModel extends ViewModel {

    FirebaseAuth mAuth;

    FirebaseDatabase database;
    DatabaseReference USERS;
    DatabaseReference ID;
    DatabaseReference DB_DESCRIPCION;
    DatabaseReference DB_EDAD;
    DatabaseReference DB_EDAD_M;
    DatabaseReference DB_EDAD_P;
    DatabaseReference DB_EDAD_m;
    DatabaseReference DB_ESTADO;

    DatabaseReference DB_GENERO;
    DatabaseReference DB_GENERO_B;
    DatabaseReference DB_NOMBRE;
    DatabaseReference DB_NOMBRE_P;
    DatabaseReference DB_PAIS;
    DatabaseReference DB_REPUTACION;
    DatabaseReference GeneroB_HOMBRE,GeneroB_MUJER,GeneroB_PAREJA_HM,GeneroB_PAREJA_MM,GeneroB_PAREJA_HH,GeneroB_OTROS;
    FirebaseStorage storage;
    StorageReference storageReference;


    public void Constructor(String userID) {

        database = FirebaseDatabase.getInstance();
        USERS = database.getReference("USERS");
        ID = USERS.child(userID);
        DB_DESCRIPCION = ID.child("DESCRIPCION");
        DB_EDAD = ID.child("EDAD");
        DB_EDAD_M = ID.child("EDAD_M");
        DB_EDAD_P = ID.child("EDAD_P");
        DB_EDAD_m = ID.child("EDAD_m");
        DB_ESTADO = ID.child("ESTADO");

        DB_GENERO = ID.child("GENERO");
        DB_GENERO_B = ID.child("GENERO_B");
        DB_NOMBRE = ID.child("NOMBRE");
        DB_NOMBRE_P = ID.child("NOMBRE_P");
        DB_PAIS = ID.child("PAIS");
        DB_REPUTACION = ID.child("REPUTACION");

        GeneroB_HOMBRE = DB_GENERO_B.child("Hombre");
        GeneroB_MUJER = DB_GENERO_B.child("Mujer");
        GeneroB_PAREJA_HM = DB_GENERO_B.child("PAREJA_HM");
        GeneroB_PAREJA_MM = DB_GENERO_B.child("PAREJA_MM");
        GeneroB_PAREJA_HH = DB_GENERO_B.child("PAREJA_HH");
        GeneroB_OTROS = DB_GENERO_B.child("OTROS");



        mAuth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference(mAuth.getCurrentUser().getUid());

    }

    public void CargaABaseDato(String descripcion, Integer edad, Integer edad_M, Integer edad_P,
                               Integer edad_m,  String genero, @NotNull String[] generos_B,
                               String nombre, String nombre_P, String pais)
    {
        DB_DESCRIPCION.setValue(descripcion);
        DB_EDAD.setValue(edad);
        DB_EDAD_M.setValue(edad_M);
        DB_EDAD_P.setValue(edad_P);
        DB_EDAD_m.setValue(edad_m);
        DB_ESTADO.setValue("On");

        DB_GENERO.setValue(genero);
        DB_NOMBRE.setValue(nombre);
        DB_NOMBRE_P.setValue(nombre_P);
        DB_PAIS.setValue(pais);
        DB_REPUTACION.setValue(50);

//if debe ser si edito el nombre a "hombre" entonces si se guerda Hombre
        if (generos_B[0].equals("Hombre")) {
            GeneroB_HOMBRE.setValue("Hombres");
        }
        if (generos_B[1].equals("Mujer")) {
            GeneroB_MUJER.setValue("Mujeres");
        }
        if (generos_B[2].equals("Pareja HM")) {
            GeneroB_PAREJA_HM.setValue("Parejas HM");
        }
        if (generos_B[3].equals("Pareja MM")) {
            GeneroB_PAREJA_MM.setValue("Parejas MM");
        }
        if (generos_B[4].equals("Pareja HH")) {
            GeneroB_PAREJA_HH.setValue("Parejas HH");
        }
        if (generos_B[5].equals("Otros")) {
            GeneroB_OTROS.setValue("Otros");
        }

    }
}