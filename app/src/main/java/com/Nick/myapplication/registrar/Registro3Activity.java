package com.Nick.myapplication.registrar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.Nick.myapplication.R;
import com.bumptech.glide.Glide;
import de.hdodenhof.circleimageview.CircleImageView;

public class Registro3Activity extends AppCompatActivity {

    String  dato,foto = "nulo";
    EditText nombre,descripcion;
    TextView pais;

    CircleImageView iVFotoPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro3);

        nombre = findViewById(R.id.editTextNombre);
        descripcion = findViewById((R.id.editTextTextMultiLine));
        iVFotoPerfil = findViewById(R.id.imageViewPerfil);
        pais = findViewById(R.id.textViewPais);
        String[] countries = getResources().getStringArray(R.array.countries);

        cambiarFotoPerfil();

        dato = getIntent().getStringExtra("dato_del_pais");
        if(!(dato == null))
        pais.setText(countries[Integer.parseInt(dato)]);

    }


    public void Registro (View view)
    {
        if(dato == null) {
            Toast.makeText(getApplicationContext(), "Ingrese un pais",
                    Toast.LENGTH_SHORT).show();
        } else {
            if(nombre.getText().toString().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Ingrese un nombre",
                        Toast.LENGTH_SHORT).show();
            }
            else {
                if(descripcion.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Ingrese una descripcion",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    if(foto.equals("nulo")){
                        Toast.makeText(getApplicationContext(), "Suba una Foto",
                                                         Toast.LENGTH_SHORT).show();
                    }else {
                        Bundle datosBundle = getIntent().getBundleExtra("dato_del_usuario");

                        Intent i = new Intent(this, Registro4Activity.class);
                        i.putExtra("dato",datosBundle);
                        i.putExtra("nombre",nombre.getText().toString());
                        i.putExtra("descripcion",descripcion.getText().toString());
                        i.putExtra("pais",pais.getText().toString());
                        i.putExtra("foto",foto);
                        startActivity(i);
                    }
                }
            }
        }
    }


    public void selecionarPais(View view)
    {   Intent reciveInten = getIntent();
        Bundle datosBundle = reciveInten.getExtras();

        Intent i = new Intent(this, SeleccionPaisActivity.class);
        i.putExtra("datosIntent",datosBundle);
        i.putExtra("de_donde_viene","Registro");
        startActivity(i);
    }


    private void cambiarFotoPerfil(){
        iVFotoPerfil.setOnClickListener(v -> {
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.setType("image/jpeg");
            i.putExtra(Intent.EXTRA_LOCAL_ONLY ,true);
            startActivityForResult(Intent.createChooser(i ,"Selecciona una foto"),1);
        });
    }


    @Override
    public void onActivityResult( int requestCode ,int resultCode ,@Nullable Intent data ){
        super.onActivityResult(requestCode ,resultCode ,data);
        if (resultCode == RESULT_OK) {
            assert data != null;
            Uri uri = data.getData();
            foto = data.getData().toString();
            Glide.with(getApplicationContext()).load(uri).fitCenter().into(iVFotoPerfil);
        }
    }
}