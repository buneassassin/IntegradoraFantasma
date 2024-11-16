package com.primerp.integradora.Cosas.Esplach;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.primerp.integradora.Login;
import com.primerp.integradora.MainActivity;
import com.primerp.integradora.R;

public class Esplach extends AppCompatActivity {

    private TextView timerTextView; // Referencia al TextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esplach);


        // Contador de 3 segundos
        new CountDownTimer(5000, 1000) {
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                Intent intent = new Intent(Esplach.this, Login.class);
                startActivity(intent);
                finish();
            }
        }.start();


    }
}
