package com.Nick.myapplication.fragments.perfil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.Nick.myapplication.adapters.FullScreenImagenActivity;
import com.Nick.myapplication.principales.LoginActivity;
import com.Nick.myapplication.R;
import com.Nick.myapplication.registrar.SeleccionPaisActivity;
import com.Nick.myapplication.adapters.GaleriaImagenAdapter;
import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import org.jetbrains.annotations.NotNull;
import java.util.Objects;
import de.hdodenhof.circleimageview.CircleImageView;
import static android.app.Activity.RESULT_OK;

public class PerfilFragment extends Fragment {
    PerfilViewModel perfilViewModel;
    GridView gridViewGaleria;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    int posicionGaleria = 0;

    TextView tVNombre, tVEdad, tVGenero, tVPais, tVGeneroB, tVEdadm, tVEdadM, tVDescripcion,
            tVEstado;
    Button botonCerrarSesion, botonEdit, botonSave;
    ImageButton iBGaleria;
    String userID, nombreNuevo;
    ProgressBar pBreputacion;
    EditText eTnombrePerfil;
    View root;
    CircleImageView iVFotoPerfil;
    String generoFinal;
    String dato;
    String Nombre, NombreP, Genero, Pais, Descripcion, Estado, Foto ;
    Integer Edad, EdadP, Edadm, EdadM, Reputacion;
    String[] GeneroB;


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //Cosas Necesarias
        root = inflater.inflate(R.layout.fragment_perfil, container, false);
        llenadoformulario();
        //para que se llene la primera vez
        eTnombrePerfil.setText(Objects.requireNonNull(mAuth.getCurrentUser()).getDisplayName());

        perfilViewModel.Constructor(userID);

        gridViewGaleria.setAdapter(new GaleriaImagenAdapter(getContext()));
/*    PARA WEBEAR como cambiar la imagen de perfil con imag local
        int xqwe = R.drawable.ico_pareja_hombres;

         iVFotoPerfil.setImageResource(xqwe);
*/
        StorageReference fotoreferencia = perfilViewModel.storageReference.child("Foto_Perfil");
        cargarFotoPerfil(fotoreferencia,1);
        cambiarFotoPerfil();

        String[] countries = getResources().getStringArray(R.array.countries);

        llamarOnClick(botonCerrarSesion);
        llamarOnClick(botonEdit);
        llamarOnClick(botonSave);

        //onClickPais();

        dato = requireActivity().getIntent().getStringExtra("dato_del_pais");
        if(!(dato == null))
         tVPais.setText(countries[Integer.parseInt(dato)]);

        return root;
    }


    public void llenadoformulario()
    {
        //Conecion al DataBase
        perfilViewModel = new ViewModelProvider(this).get(PerfilViewModel.class);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        if (currentUser == null) throw new AssertionError();
        userID = currentUser.getUid();
        //Botones
        botonCerrarSesion = root.findViewById(R.id.buttonDeCerrarSesion);
        botonEdit = root.findViewById(R.id.buttonEdit);
        botonSave = root.findViewById(R.id.buttonSave);
        eTnombrePerfil = root.findViewById(R.id.editTextNombreP);
        iBGaleria =  root.findViewById(R.id.imageButton);
        gridViewGaleria = root.findViewById(R.id.galeriaImagenes);
        //Pantalla
        iVFotoPerfil = root.findViewById(R.id.iVPerfil);
        tVNombre = root.findViewById(R.id.textViewNombreP);
        tVEdad = root.findViewById(R.id.tVEdad);
        tVGenero = root.findViewById(R.id.tVGenero);
        tVPais = root.findViewById(R.id.tVPais);
        tVGeneroB = root.findViewById(R.id.tVGeneroB);
        tVEdadm = root.findViewById(R.id.tVEdadm);
        tVEdadM = root.findViewById(R.id.tVEdadM);
        pBreputacion = root.findViewById(R.id.pBReputacion);
        tVDescripcion = root.findViewById(R.id.tVDescripcion);
        tVEstado = root.findViewById(R.id.tVEstado);
        //ARREGLAR
        //En vez de Get a TextView debe Ser al EditText
        Descripcion = tVDescripcion.getText().toString();
        Edad = Integer.parseInt(tVEdad.getText().toString());
        EdadP = 0;
        Edadm = Integer.parseInt(tVEdadm.getText().toString());
        EdadM = Integer.parseInt(tVEdadM.getText().toString());
        Estado = "Online";
        Foto = "foto";
        Genero = tVGenero.getText().toString();
        /*   GeneroB[0] = tVGeneroB.getText().toString();*/
        Nombre = tVNombre.getText().toString();
        NombreP = " ";
        Pais = tVPais.getText().toString();
        Reputacion = pBreputacion.getProgress();
    }


    public void cambiarFotoPerfil(){

        iVFotoPerfil.setOnLongClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.setType("image/jpeg");
            i.putExtra(Intent.EXTRA_LOCAL_ONLY ,true);
            startActivityForResult(Intent.createChooser(i ,"Selecciona una foto"),1);
            return false;
        });

        iBGaleria.setOnClickListener(v -> {

            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.setType("image/jpeg");
            i.putExtra(Intent.EXTRA_LOCAL_ONLY ,true);
            startActivityForResult(Intent.createChooser(i ,"Selecciona una foto"),2);
        });

        iVFotoPerfil.setOnClickListener(v -> {
            Intent i = new Intent(getContext(),FullScreenImagenActivity.class);
            i.putExtra("identificador",2);
            startActivity(i);
        });

        gridViewGaleria.setOnItemClickListener(( parent ,view ,position ,id ) -> {
            Intent i = new Intent(
                    getContext(),FullScreenImagenActivity.class);
            i.putExtra("identificador",1);
            i.putExtra("idimagen",position);
            startActivity(i);
        });



    }

    @Override
    public void onActivityResult( int requestCode ,int resultCode ,@Nullable Intent data ){
        super.onActivityResult(requestCode ,resultCode ,data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            assert data != null;
            Uri uri = data.getData();

                final StorageReference fotoreferencia = perfilViewModel.storageReference.child("Foto_Perfil");
            fotoreferencia.putFile(uri).addOnSuccessListener(taskSnapshot ->
                                                                     cargarFotoPerfil(fotoreferencia, requestCode));
        }
        if (resultCode == RESULT_OK && requestCode == 2){
            assert data != null;
            Uri uri = data.getData();
            /* Aqui debe se guarda la foto en Galeria en la nube pero con un mismo nombre y no se refleja en la galeria*/
            final StorageReference fotoreferencia = perfilViewModel.storageReference.child("Fotos_Galeria").child(uri.getLastPathSegment());
            fotoreferencia.putFile(uri).addOnSuccessListener(taskSnapshot -> aniadirFotoGaleria(uri));
        }
    }


    public void aniadirFotoGaleria( Uri uri ) {
       // Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver() ,uri);
     //   imageView.setImageBitmap(bitmap);
    }


    public void cargarFotoPerfil( StorageReference fotoreferencia,int requestCode)
    {
        fotoreferencia.getDownloadUrl().addOnSuccessListener(uri1 -> perfilViewModel.ID.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot){
                if (dataSnapshot.exists()) {
                    if (requestCode == 1)
                    Glide.with(getContext()).load(uri1).fitCenter().into(iVFotoPerfil);
                    if (requestCode == 2)
                        Glide.with(getContext()).load(uri1).fitCenter().into(iVFotoPerfil);
                }
            }

            @Override
            public void onCancelled(@NotNull DatabaseError error) {
            }
        })).addOnFailureListener(exception -> Toast.makeText(getContext() ,"Error al cargar la Imagen" ,
                                                                 Toast.LENGTH_SHORT).show());

    }

    public void onClickPais() {
        tVPais.setOnClickListener(v -> {
            Intent i = new Intent(getContext(), SeleccionPaisActivity.class);
            i.putExtra("de_donde_viene", "Perfil");
            startActivity(i);
        });
    }


    public void llamarOnClick(Button boton) {
        boton.setOnClickListener(v -> {
            String out        = boton.getText().toString().trim();
            String bottonOut  = "out";
            String bottonEdit = "Edit";
            String bottonSave = "Save";

            if (out.equals(bottonOut)) {
                mAuth.signOut();
                if (mAuth.getCurrentUser() == null) {
                    Intent x = new Intent(getActivity(), LoginActivity.class);
                    startActivity(x);
                }
            }

            if (out.equals(bottonEdit)) {
                boton.setVisibility(View.GONE);
                tVNombre.setVisibility(View.GONE);
                botonSave.setVisibility(View.VISIBLE);
                eTnombrePerfil.setVisibility(View.VISIBLE);
            }

            if (out.equals(bottonSave)) {
                boton.setVisibility(View.GONE);
                botonEdit.setVisibility(View.VISIBLE);
                tVNombre.setVisibility(View.VISIBLE);
                actualizarNombre();
                eTnombrePerfil.setVisibility(View.GONE);
            }
        });
    }


    public void actualizarNombre() {
        nombreNuevo = eTnombrePerfil.getText().toString();
        tVNombre.setText(nombreNuevo);
    }


    public void onStart() {
        super.onStart();
        updateUI();
    }

    private void updateUI(){

        perfilViewModel.ID.addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    tVNombre.setText(Objects.requireNonNull(dataSnapshot.child("NOMBRE").getValue()).toString());
                    tVEdad.setText(Objects.requireNonNull(dataSnapshot.child("EDAD").getValue()).toString());
                    tVGenero.setText(Objects.requireNonNull(dataSnapshot.child("GENERO").getValue()).toString());
                    tVPais.setText(Objects.requireNonNull(dataSnapshot.child("PAIS").getValue()).toString());
                    //MODIFICAR ACA ABAJO PARA QUE NO SALGAN EL GENERO TOD MONTADO

                /*    String generoH = dataSnapshot.child("GENERO_B").child("Hombre").getValue().toString();
                    String generoM = dataSnapshot.child("GENERO_B").child("Mujer").getValue().toString();
                    String generoPHM = dataSnapshot.child("GENERO_B").child("PAREJA_HM").getValue().toString();
                    String generoPMM = dataSnapshot.child("GENERO_B").child("PAREJA_MM").getValue().toString();
                    String generoPHH = dataSnapshot.child("GENERO_B").child("PAREJA_HH").getValue().toString();
                    String generoO = dataSnapshot.child("GENERO_B").child("OTROS").getValue().toString();
*/
                    // generoFinal = generoM;

                   // tVGeneroB.setText(Objects.requireNonNull(dataSnapshot.child("GENERO_B").child("Mujer").getValue()).toString()+"es de");
                    tVEdadm.setText(Objects.requireNonNull(dataSnapshot.child("EDAD_m").getValue()).toString()+" a");
                    tVEdadM.setText(Objects.requireNonNull(dataSnapshot.child("EDAD_M").getValue()).toString()+" años");
                    pBreputacion.setProgress(Integer.parseInt(Objects.requireNonNull(dataSnapshot.child("REPUTACION").getValue()).toString()));
                    tVDescripcion.setText(Objects.requireNonNull(dataSnapshot.child("DESCRIPCION").getValue()).toString());
                    tVEstado.setText(Objects.requireNonNull(dataSnapshot.child("ESTADO").getValue()).toString());
                }
            }

            @Override
            public void onCancelled(@NotNull DatabaseError error) {
            }
        });
    }
}