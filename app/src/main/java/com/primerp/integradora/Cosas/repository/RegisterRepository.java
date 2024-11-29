package com.primerp.integradora.Cosas.repository;


import android.content.Context;

import com.primerp.integradora.Cosas.Api.ApiService;
import com.primerp.integradora.Cosas.Class.RetrofitClient;
import com.primerp.integradora.Cosas.Responst.RegisterRequest;
import com.primerp.integradora.Cosas.Responst.RegisterResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterRepository {

    private final ApiService apiService;

    public RegisterRepository(Context context) {
        apiService = RetrofitClient.getInstance(context).getApiService();
    }

    public void registerUser(String usuario_nom, String email, String password, String nombres, String apellidoPaterno, String apellidoMaterno, String telefono, RegistrationCallback callback) {
        RegisterRequest request = new RegisterRequest(usuario_nom, email, password, nombres, apellidoPaterno, apellidoMaterno, telefono);
        apiService.registerUser(request).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    callback.onComplete(true);
                } else {
                    callback.onComplete(false);
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                callback.onComplete(false);
            }
        });
    }

    public interface RegistrationCallback {
        void onComplete(boolean success);
    }
}

