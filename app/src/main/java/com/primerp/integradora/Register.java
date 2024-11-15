package com.primerp.integradora;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Register extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button loginButton = findViewById(R.id.registerButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Llama a la actividad de inicio cuando se haga clic en el botón
                Intent intent = new Intent(Register.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void onRegistrationPromptClick(View view) {
        // Lógica para redirigir a la pantalla de registro
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
}