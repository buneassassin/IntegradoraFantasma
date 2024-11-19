package com.primerp.integradora.Cosas.Esplach;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;

import androidx.appcompat.app.AppCompatActivity;

import com.primerp.integradora.Login;
import com.primerp.integradora.R;

public class Esplach extends AppCompatActivity {

    private SharedPreferences preferences; // Declarar como variable de clase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializar SharedPreferences
        preferences = getSharedPreferences("SplashPrefs", MODE_PRIVATE);
        boolean isSplashShown = preferences.getBoolean("isSplashShown", false);

        if (isSplashShown) {
            Intent intent = new Intent(Esplach.this, Login.class);
            startActivity(intent);
            finish();
        } else {
            esplach();
        }
    }

    public void esplach() {
        setContentView(R.layout.activity_esplach);
        new CountDownTimer(8000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("isSplashShown", true);
                editor.apply();

                Intent intent = new Intent(Esplach.this, Login.class);
                startActivity(intent);
                finish();
            }
        }.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("isSplashShown", false);
        editor.apply();
    }
}
