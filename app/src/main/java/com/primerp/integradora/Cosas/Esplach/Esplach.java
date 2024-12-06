package com.primerp.integradora.Cosas.Esplach;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.primerp.integradora.Cosas.Api.ApiService;
import com.primerp.integradora.Cosas.Class.RetrofitClient;
import com.primerp.integradora.Cosas.Responst.ApiResponse;
import com.primerp.integradora.Login;
import com.primerp.integradora.MainActivity;
import com.primerp.integradora.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Esplach extends AppCompatActivity {

    private static final int MIN_SPLASH_TIME_MS = 7000; // Tiempo mínimo en milisegundos
    private boolean isRedirected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_esplach);

        SharedPreferences preferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String token = preferences.getString("auth_token", null);

        Handler handler = new Handler();
        long startTime = System.currentTimeMillis();

        if (token != null) {
            validateToken(token, handler, startTime);
        } else {
            // Si no hay token, redirigir después del tiempo mínimo
            handler.postDelayed(() -> redirectToLogin(), MIN_SPLASH_TIME_MS);
        }
    }

    private void validateToken(String token, Handler handler, long startTime) {
        ApiService apiService = RetrofitClient.getInstance(this).getApiService();
        Call<ApiResponse> call = apiService.getMe("Bearer " + token);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                long elapsedTime = System.currentTimeMillis() - startTime;
                long remainingTime = MIN_SPLASH_TIME_MS - elapsedTime;

                handler.postDelayed(() -> {
                    if (!isRedirected) {
                        if (response.isSuccessful() && response.body() != null) {
                            redirectToMain();
                        } else {
                            redirectToLogin();
                        }
                    }
                }, Math.max(remainingTime, 0)); // Esperar el tiempo restante si es necesario
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                long elapsedTime = System.currentTimeMillis() - startTime;
                long remainingTime = MIN_SPLASH_TIME_MS - elapsedTime;

                handler.postDelayed(() -> {
                    if (!isRedirected) {
                        redirectToLogin();
                    }
                }, Math.max(remainingTime, 0));
            }
        });
    }

    private void redirectToMain() {
        if (!isRedirected) {
            isRedirected = true; // Evitar redirecciones múltiples
            Intent intent = new Intent(Esplach.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void redirectToLogin() {
        if (!isRedirected) {
            isRedirected = true; // Evitar redirecciones múltiples
            Intent intent = new Intent(Esplach.this, Login.class);
            startActivity(intent);
            finish();
        }
    }
}
