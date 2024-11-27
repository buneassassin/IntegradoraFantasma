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

    private EditText usuario_nomEditText, emailEditText, passwordEditText;
    private EditText nombresEditText, apellidoPaternoEditText, apellidoMaternoEditText, telefonoEditText;
    private Button registerButton;
    private boolean isRegistrationSuccessful = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String token = getSharedPreferences("user_prefs", MODE_PRIVATE).getString("auth_token", null);

        if (token != null) {
            Intent intent = new Intent(Register.this, MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_register);

        usuario_nomEditText = findViewById(R.id.usuario_nom);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        nombresEditText = findViewById(R.id.nombres);
        apellidoPaternoEditText = findViewById(R.id.a_p);
        apellidoMaternoEditText = findViewById(R.id.a_m);
        telefonoEditText = findViewById(R.id.telefono);

        registerButton = findViewById(R.id.registerButton);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isRegistrationSuccessful) {
                    onRegisterClick(view);
                } else {
                    registerUser();
                }
            }
        });
    }

    private void registerUser() {
        String usuario_nom = usuario_nomEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String nombres = nombresEditText.getText().toString().trim();
        String apellidoPaterno = apellidoPaternoEditText.getText().toString().trim();
        String apellidoMaterno = apellidoMaternoEditText.getText().toString().trim();
        String telefono = telefonoEditText.getText().toString().trim();

        if (usuario_nom.isEmpty() || email.isEmpty() || password.isEmpty() ||
                nombres.isEmpty() || apellidoPaterno.isEmpty() || apellidoMaterno.isEmpty() || telefono.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        RegisterRequest registerRequest = new RegisterRequest(usuario_nom, email, password, nombres, apellidoPaterno, apellidoMaterno, telefono);
        ApiService apiService = RetrofitClient.getInstance(this).getApiService();

        Call<RegisterResponse> call = apiService.registerUser(registerRequest);
        call.enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        isRegistrationSuccessful = true;
                        Toast.makeText(Register.this, "Registro exitoso. Ahora puedes iniciar sesión.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(Register.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    try {
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
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }
}
