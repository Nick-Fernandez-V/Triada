package com.Nick.myapplication.registrar;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.Nick.myapplication.R;

import static com.Nick.myapplication.R.id.cBHombre;
import static com.Nick.myapplication.R.id.cBMujer;
import static com.Nick.myapplication.R.id.cBPareja;
import static com.Nick.myapplication.R.id.cBOtros;
import static com.Nick.myapplication.R.id.cBParejaHH;
import static com.Nick.myapplication.R.id.cBParejaMM;
import static com.Nick.myapplication.R.id.edadMax;
import static com.Nick.myapplication.R.id.edadMin;

public class Registro1Activity extends AppCompatActivity {

    SeekBar edadmin;
    SeekBar edadmax;
    boolean[] generoB = new boolean[6];

    CheckBox cbHombre;
    CheckBox cbMujer;
    CheckBox cbPareja;
    CheckBox cbParejaMM;
    CheckBox cbParejaHH;
    CheckBox cbOtros;


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro1);

        edadmin = findViewById(edadMin);
        edadmax =  findViewById(edadMax);
        cbHombre =  findViewById(cBHombre);
        cbMujer =  findViewById(cBMujer);
        cbPareja =  findViewById(cBPareja);
        cbParejaMM =findViewById(cBParejaMM);
        cbParejaHH =  findViewById(cBParejaHH);
        cbOtros = findViewById(cBOtros);

        Registro1Activity.this.calibrar( findViewById(edadMin),  findViewById(R.id.textEdadMin));
        Registro1Activity.this.calibrar( findViewById(edadMax),  findViewById(R.id.textEdadMax));
    }


    public void calibrar(SeekBar seekBar, TextView Views)
    {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override public void onStopTrackingTouch(SeekBar seekBar) { }
            @Override public void onStartTrackingTouch(SeekBar seekBar) { }
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


    public void BotonRegistro2(View view)
    {
        int progressmin = edadmin.getProgress();
        int progressMax = edadmax.getProgress();

        if (progressmin <= progressMax) {
            if (cbHombre.isChecked() || cbMujer.isChecked() || cbPareja.isChecked() || cbParejaMM.isChecked() || cbParejaHH.isChecked() || cbOtros.isChecked()) {

                generoB[0] = cbHombre.isChecked();
                generoB[1] = cbMujer.isChecked();
                generoB[2] = cbPareja.isChecked();
                generoB[3] = cbParejaMM.isChecked();
                generoB[4] = cbParejaHH.isChecked();
                generoB[5] = cbOtros.isChecked();

                Intent x = new Intent(getApplicationContext(), Registro2Activity.class);
                x.putExtra("Edadm", edadmin.getProgress());
                x.putExtra("EdadM", edadmax.getProgress());
                x.putExtra("generoB", generoB);
                startActivity(x);
            }
            else {
                Toast.makeText(getApplicationContext(), "Selecciona lo que buscas",
                               Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(getApplicationContext(), "Edad Minima es mayor que la Maxima",
                           Toast.LENGTH_SHORT).show();
        }
    }
}
