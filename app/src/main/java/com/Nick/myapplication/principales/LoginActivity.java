package com.Nick.myapplication.principales;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.Nick.myapplication.R;
import com.Nick.myapplication.registrar.Registro1Activity;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    public FirebaseAuth mAuth;
    private EditText emailLogin,passwordLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailLogin = findViewById(R.id.correoLogin);
        emailLogin.setText("");
        passwordLogin = findViewById(R.id.password);
        passwordLogin.setText("");
        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            Intent x = new Intent(getApplicationContext(), MenuApp.class);
            startActivity(x);
        }
    }


    public void InicioSesion(View view) {
        if (emailLogin.getText().toString().trim().isEmpty()) {
            Toast.makeText(getApplicationContext(), "Campo Correo Vacio",
                           Toast.LENGTH_SHORT).show();
        }
        else {
            if (passwordLogin.getText().toString().trim().isEmpty()) {
                Toast.makeText(getApplicationContext(), "Campo Contraseña Vacio",
                               Toast.LENGTH_SHORT).show();
            }
            else {

                mAuth.signInWithEmailAndPassword(emailLogin.getText().toString().trim(), passwordLogin.getText().toString())
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                Intent x = new Intent(getApplicationContext(), MenuApp.class);
                                startActivity(x);
                            }
                            else {

                                Toast.makeText(getApplicationContext(), "Correo o Contraseña Incorrecta",
                                               Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
    }


    public void Registro(View view)
    {
        Intent x = new Intent(this, Registro1Activity.class);
        startActivity(x);
    }
}