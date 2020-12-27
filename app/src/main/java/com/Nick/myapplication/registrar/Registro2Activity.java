package com.Nick.myapplication.registrar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.Nick.myapplication.R;

import static com.Nick.myapplication.R.id.seekEdadeEl;
import static com.Nick.myapplication.R.id.seekEdadElla;
import static com.Nick.myapplication.R.id.rBHombre;
import static com.Nick.myapplication.R.id.rBMujer;
import static com.Nick.myapplication.R.id.rBOtros;
import static com.Nick.myapplication.R.id.rBParejaHH;
import static com.Nick.myapplication.R.id.rBParejaHM;
import static com.Nick.myapplication.R.id.rBParejaMM;
import static com.Nick.myapplication.R.id.textEdadNumeroEl;
import static com.Nick.myapplication.R.id.textEdadNumeroElla;

public class Registro2Activity extends AppCompatActivity {

    RadioButton rbHombre, rbMujer, rbParejaHM, rbParejaMM, rbParejaHH, rbOtros;
    TextView Viewel, Viewella, tvEdadEl, tvEdadElla;
    SeekBar edad, edadP;
    String genero;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro2);

        ConstructorRegistro2();

        Registro2Activity.this.calibrarB(edad, tvEdadEl);
        Registro2Activity.this.calibrarB(edadP, tvEdadElla);
    }

    public void ConstructorRegistro2() {
        rbHombre = findViewById(rBHombre);
        rbMujer =  findViewById(rBMujer);
        rbParejaHM = findViewById(rBParejaHM);
        rbParejaMM = findViewById(rBParejaMM);
        rbParejaHH = findViewById(rBParejaHH);
        rbOtros = findViewById(rBOtros);

        edad = findViewById(seekEdadeEl);
        edadP = findViewById(seekEdadElla);

        Viewel = findViewById(R.id.textViewEl);
        Viewella = findViewById(R.id.textViewElla);
        tvEdadEl = findViewById(textEdadNumeroEl);
        tvEdadElla = findViewById(textEdadNumeroElla);
    }

    public void calibrarB(SeekBar seekBar, TextView Views)
    {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress < 18) {
                    seekBar.setProgress(18);
                }
                else {
                    Views.setText(String.valueOf(progress));
                }
            }
        });
    }

    public void radioButtonClic(View view)
    {
        if (rbHombre.isChecked() || rbMujer.isChecked() || rbOtros.isChecked()) {
            Viewel.setVisibility(View.GONE);
            Viewella.setVisibility(View.GONE);
            edadP.setVisibility(View.GONE);
            tvEdadElla.setVisibility(View.GONE);
        }
        if (rbParejaHM.isChecked() || rbParejaMM.isChecked() || rbParejaHH.isChecked()) {
            if (rbParejaHM.isChecked() || rbParejaMM.isChecked()) {
                Viewel.setVisibility(View.GONE);
                Viewella.setVisibility(View.VISIBLE);
            }
            else {
                Viewel.setVisibility(View.VISIBLE);
                Viewella.setVisibility(View.GONE);
            }
            edadP.setVisibility(View.VISIBLE);
            tvEdadElla.setVisibility(View.VISIBLE);
        }
    }

    public void registro3(View view) {
        if (rbHombre.isChecked() || rbMujer.isChecked() || rbOtros.isChecked() ||
            rbParejaHM.isChecked() || rbParejaMM.isChecked() || rbParejaHH.isChecked())
        {

            if (rbHombre.isChecked()) {
                genero = "Hombre";
            }
            if (rbMujer.isChecked()) {
                genero = "Mujer";
            }
            if (rbParejaHM.isChecked()) {
                genero = "Pareja HM";
            }
            if (rbParejaMM.isChecked()) {
                genero = "Pareja MM";
            }
            if (rbParejaHH.isChecked()) {
                genero = "Pareja HH";
            }
            if (rbOtros.isChecked()) {
                genero = "Otros";
            }

            int       edadm   = getIntent().getIntExtra("Edadm", 18),
                      edadM   = getIntent().getIntExtra("EdadM", 70);
            boolean[] generoB = getIntent().getBooleanArrayExtra("generoB");
            Intent    x       = new Intent(getApplicationContext(), Registro3Activity.class);
            x.putExtra("Edad", edad.getProgress());
            x.putExtra("EdadP", edadP.getProgress());
            x.putExtra("Genero", genero);
            x.putExtra("Edadm", edadm);
            x.putExtra("EdadM", edadM);
            x.putExtra("GeneroB", generoB);
            startActivity(x);
        }
        else {
            Toast.makeText(getApplicationContext(), "Selecciona tu Genero?",
                           Toast.LENGTH_SHORT).show();
        }
    }

}