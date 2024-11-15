package com.primerp.integradora;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.primerp.integradora.Cosas.Api.ApiService;
import com.primerp.integradora.Cosas.Responst.RegisterRequest;
import com.primerp.integradora.Cosas.Responst.RegisterResponse;
import com.primerp.integradora.Cosas.Class.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    private EditText usernameEditText, emailEditText, passwordEditText;
    private Button registerButton;
    private boolean isRegistrationSuccessful = false; // Bandera para verificar el éxito del registro

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Comprobar si el token está almacenado
        String token = getSharedPreferences("user_prefs", MODE_PRIVATE).getString("auth_token", null);

        if (token != null) {
            // Si el token existe, redirigir al MainActivity
            Intent intent = new Intent(Register.this, MainActivity.class);
            startActivity(intent);
            finish();
            return; // Detener el resto del código
        }

        // Si no hay token, mostrar la pantalla de registro
        setContentView(R.layout.activity_register);

        usernameEditText = findViewById(R.id.username);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        registerButton = findViewById(R.id.registerButton);

        // Configurar el botón con el método OnClickListener
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRegistrationSuccessful) {
                    // Si el registro fue exitoso, redirige al inicio de sesión
                    onRegisterClick(view);
                } else {
                    // Intenta registrar al usuario
                    registerUser();
                }
            }
        });
    }


    private void registerUser() {
        String usuario_nom = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (usuario_nom.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        RegisterRequest registerRequest = new RegisterRequest(usuario_nom, email, password);
        ApiService apiService = RetrofitClient.getInstance().createService(ApiService.class);

        Call<RegisterResponse> call = apiService.registerUser(registerRequest);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        isRegistrationSuccessful = true; // Cambiar la bandera a true
                        Toast.makeText(Register.this, "Registro exitoso. Ahora puedes iniciar sesión.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Register.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
                        // Inspecciona el cuerpo del error
                        String errorBody = response.errorBody().string();
                        Toast.makeText(Register.this, "Error en la respuesta del servidor: " + errorBody, Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(Register.this, "Error al procesar la respuesta del servidor", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                Toast.makeText(Register.this, "Error en la conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onRegisterClick(View view) {
        // Redirige a la pantalla de inicio de sesión
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish(); // Cierra la actividad actual
    }
}
