package com.Nick.myapplication.registrar;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.Nick.myapplication.R;
import com.Nick.myapplication.registrar.Registro3Activity;
import com.Nick.myapplication.fragments.perfil.PerfilFragment;

import java.util.ArrayList;
import java.util.Arrays;

public class SeleccionPaisActivity extends AppCompatActivity {
   private String dato;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seleccion_pais);

        ListView list                  = findViewById(R.id.country_list);
        String[]          countries    = getResources().getStringArray(R.array.countries);
        ArrayList<String> country_list = new ArrayList<>(Arrays.asList(countries));
        ArrayAdapter<String> adapter   =
                new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, country_list);

        list.setAdapter(adapter);

        list.setOnItemClickListener((parent, item, position, id) -> {

            dato = getIntent().getStringExtra("de_donde_viene");
            String perfil = "Perfil", registro = "Registro";

            if (dato.equals(perfil)) {
                Intent i;
                i = new Intent(getApplicationContext(), PerfilFragment.class);
                i.putExtra("dato_del_pais", String.valueOf(position));
                startActivity(i);
            }
            if (dato.equals(registro)) {
                Intent reciveInten = getIntent();
                Bundle datosBundle = reciveInten.getBundleExtra("datosIntent");

                Intent i;
                i = new Intent(getApplicationContext(), Registro3Activity.class);
                i.putExtra("dato_del_usuario", datosBundle);
                i.putExtra("dato_del_pais", String.valueOf(position));
                startActivity(i);
            }
        });
    }

}