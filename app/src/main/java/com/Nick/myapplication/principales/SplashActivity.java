package com.Nick.myapplication.principales;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.Nick.myapplication.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate( Bundle savedInstanceState ){
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        Animation animationAr = AnimationUtils.loadAnimation(this,R.anim.desplazamiento_arriba);
        Animation animationAb = AnimationUtils.loadAnimation(this,R.anim.desplazamiento_abajo);

        TextView by = findViewById(R.id.tVBy);
        TextView triada = findViewById(R.id.tVTriada);
        ImageView logo = findViewById(R.id.iVLogo);

        by.setAnimation(animationAr);
        triada.setAnimation(animationAr);
        logo.setAnimation(animationAb);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run(){
                Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(i);
                finish();
            }
        },3000);
    }
}