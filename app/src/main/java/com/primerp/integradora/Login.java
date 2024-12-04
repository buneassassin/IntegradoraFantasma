package com.primerp.integradora;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import com.primerp.integradora.Cosas.Api.ApiService;
import com.primerp.integradora.Cosas.Dialog.ForgotPasswordActivity;
import com.primerp.integradora.Cosas.Responst.ApiResponse;
import com.primerp.integradora.Cosas.Responst.LoginRequest;
import com.primerp.integradora.Cosas.Responst.LoginResponse;
import com.primerp.integradora.Cosas.Class.RetrofitClient;
import com.primerp.integradora.Cosas.ViewModelFactory.LoginViewModelFactory;
import com.primerp.integradora.Cosas.viewmodel.LoginViewModel;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private LoginViewModel loginViewModel;
    private ProgressBar progressBar;
    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String token = getSharedPreferences("user_prefs", MODE_PRIVATE).getString("auth_token", null);
        if (token != null) {
            validateToken(token);
        } else {
            setContentView(R.layout.activity_login);
            initViews();
        }

        setContentView(R.layout.activity_login);

        // Configurar ViewModel
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory(this)).get(LoginViewModel.class);

        // Inicializar vistas
        emailEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        progressBar = findViewById(R.id.progressBar);
        loginButton = findViewById(R.id.loginButton);

        loginButton.setOnClickListener(view -> loginUser());
    }

    private void loginUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        loginButton.setVisibility(View.GONE);

        LoginRequest loginRequest = new LoginRequest(email, password);

        // Observar respuesta del ViewModel
        loginViewModel.loginUser(loginRequest).observe(this, response -> {
            progressBar.setVisibility(View.GONE);
            loginButton.setVisibility(View.VISIBLE);

            if (response != null && response.isSuccess()) {
                getSharedPreferences("user_prefs", MODE_PRIVATE).edit().putString("auth_token", response.getToken()).apply();
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, response != null ? response.getMessage() : "Error intentar logerse verefique las credenciales", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void initViews() {
        emailEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });
    }
    private void validateToken(String token) {

        ApiService apiService = RetrofitClient.getInstance(this).getApiService();
        Call<ApiResponse> call = apiService.getMe("Bearer " + token);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {

                if (response.isSuccessful() && response.body() != null) {

                    Intent intent = new Intent(Login.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {

                    setContentView(R.layout.activity_login);
                    initViews();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(Login.this, "Error al validar el token: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                setContentView(R.layout.activity_login);
                initViews();
            }
        });
    }
    public void onForgotregistrationClick(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
    public void onForgotPasswordClick(View view) {
        Intent intent = new Intent(this, ForgotPasswordActivity.class);
        startActivity(intent);
    }
}