package com.Nick.myapplication.registrar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.Nick.myapplication.principales.LoginActivity;
import com.Nick.myapplication.R;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class Registro4Activity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email,password,passwordConfirmation;
    FirebaseDatabase database;
    DatabaseReference USERS,ID,DB_DESCRIPCION,DB_EDAD,DB_EDAD_M,DB_EDAD_P,DB_EDAD_m,DB_ESTADO,DB_REPUTACION;
    DatabaseReference DB_GENERO,DB_GENERO_B,GeneroB_HOMBRE,GeneroB_MUJER,GeneroB_PAREJA_HM;
    DatabaseReference GeneroB_PAREJA_MM,GeneroB_PAREJA_HH,GeneroB_OTROS,DB_NOMBRE,DB_NOMBRE_P,DB_PAIS;

    int Edad,EdadP,Edadm,EdadM;
    boolean[] GeneroB;
    String Nombre,NombreP,Genero,Pais,Descripcion,Foto;
    FirebaseStorage  storage;
    TextView datosPresentacion;
    CircleImageView fotoPresentacion;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro4);

        mAuth = FirebaseAuth.getInstance();
        storage  = FirebaseStorage.getInstance();

        email = findViewById(R.id.correoRegistro);
        password = findViewById(R.id.contraseñaRegistro);
        passwordConfirmation = findViewById(R.id.contraseñaConfirmar);

        Intent reciveInten = getIntent();
        Bundle datosBundle = reciveInten.getBundleExtra("dato");

        Edadm = datosBundle.getInt("Edadm");
        EdadM = datosBundle.getInt("EdadM");
        GeneroB = datosBundle.getBooleanArray("GeneroB");
        Edad = datosBundle.getInt("Edad");
        EdadP = datosBundle.getInt("EdadP");
        Genero = datosBundle.getString("Genero");
        Nombre =  getIntent().getStringExtra("nombre");
        Descripcion =  getIntent().getStringExtra("descripcion");
        Pais =  getIntent().getStringExtra("pais");
        Foto =  getIntent().getStringExtra("foto");

        datosPresentacion = findViewById(R.id.tVpresentacion);
        fotoPresentacion= findViewById(R.id.cIVPresentacion);

        uri = Uri.parse(Foto);
        Glide.with(getApplicationContext()).load(uri).fitCenter().into(fotoPresentacion);
        datosPresentacion.setText(Nombre+" "+Edad+". "+Pais);


    }


    public void registrarUsuario(View view)
    {
        if (password.getText().toString().equals(passwordConfirmation.getText().toString()))
        {
            mAuth.createUserWithEmailAndPassword(email.getText().toString().trim(), password.getText().toString())
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {

                            String clavePrimariaDB = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
                            Constructor(clavePrimariaDB);
                            CargaABaseDato(Descripcion,Edad,EdadM,EdadP,Edadm,
                                                          Genero,GeneroB,Nombre,NombreP,Pais);

                            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                            Toast.makeText(getApplicationContext(), "Bienvenido: "+Nombre, Toast.LENGTH_SHORT).show();
                            startActivity(i);

                        } else {
                            Toast.makeText(getApplicationContext(), "Falló la autentificación.",Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        else {
            Toast.makeText(this, "No coinciden la contraseña", Toast.LENGTH_SHORT).show();
        }

    }

    public void Constructor( String userID) {
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
    }

    public void CargaABaseDato(String descripcion, Integer edad, Integer edad_M, Integer edad_P,
                               Integer edad_m,  String genero, @NotNull boolean[] generos_B,
                               String nombre, String nombre_P, String pais)
    {
        StorageReference fotoreferencia = storage.getReference(mAuth.getCurrentUser().getUid()).child("Foto_Perfil");
        fotoreferencia.putFile(uri);

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

        if (generos_B[0]) {
            GeneroB_HOMBRE.setValue("Hombre");
        }
        if (generos_B[1]) {
            GeneroB_MUJER.setValue("Mujer");
        }
        if (generos_B[2]) {
            GeneroB_PAREJA_HM.setValue("Pareja HM");
        }
        if (generos_B[3]) {
            GeneroB_PAREJA_MM.setValue("Pareja MM");
        }
        if (generos_B[4]) {
            GeneroB_PAREJA_HH.setValue("Pareja HH");
        }
        if (generos_B[5]) {
            GeneroB_OTROS.setValue("Otros");
        }

    }
}