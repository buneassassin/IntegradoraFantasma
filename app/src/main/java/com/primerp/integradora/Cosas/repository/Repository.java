package com.primerp.integradora.Cosas.repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.primerp.integradora.Cosas.Api.ApiService;
import com.primerp.integradora.Cosas.Class.RetrofitClient;
import com.primerp.integradora.Cosas.Class.SessionManager;
import com.primerp.integradora.Cosas.Responst.LoginRequest;
import com.primerp.integradora.Cosas.Responst.LoginResponse;
import com.primerp.integradora.Cosas.Responst.RegisterRequest;
import com.primerp.integradora.Cosas.Responst.RegisterResponse;
import com.primerp.integradora.Cosas.Responst.TinacoRequest;
import com.primerp.integradora.Cosas.Responst.TinacoResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private final ApiService apiService;
    private final SessionManager sessionManager;
    public Repository(Context context) {
        apiService = RetrofitClient.getInstance(context).getApiService();
        sessionManager = new SessionManager(context);
    }
    //////////////////////////////////////////////////
    public LiveData<LoginResponse> loginUser(LoginRequest request) {
        MutableLiveData<LoginResponse> liveData = new MutableLiveData<>();
        apiService.loginUser(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.postValue(response.body());
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });
        return liveData;
    }
    //////////////////////////////////////////////////
    public void registerUser(String usuario_nom, String email, String password, String nombres, String apellidoPaterno, String apellidoMaterno, String telefono, Repository.RegistrationCallback callback) {
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
    //////////////////////////////////////////////////
    public LiveData<TinacoResponse> addTinaco(TinacoRequest request) {
        MutableLiveData<TinacoResponse> liveData = new MutableLiveData<>();
        String token = sessionManager.getToken();
        String authToken = "Bearer " + token;

        apiService.addTinaco(authToken, request).enqueue(new Callback<TinacoResponse>() {
            @Override
            public void onResponse(Call<TinacoResponse> call, Response<TinacoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.postValue(response.body());
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<TinacoResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });

        return liveData;
    }
    //////////////////////////////////////////////////
    public void getTinacoById(int tinacoId, ApiCallback<TinacoResponse> callback) {
        String token = sessionManager.getToken();
        String authToken = "Bearer " + token;
        Call<TinacoResponse> call = apiService.getTinacoById(authToken, tinacoId);
        call.enqueue(new Callback<TinacoResponse>() {
            @Override
            public void onResponse(Call<TinacoResponse> call, Response<TinacoResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<TinacoResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
    //////////////////////////////////////////////////
    public void deleteTinaco(int tinacoId, ApiCallback<TinacoResponse> callback) {
        String token = sessionManager.getToken();
        String authToken = "Bearer " + token;
        Call<TinacoResponse> call = apiService.deleteTinaco(authToken, tinacoId);
        call.enqueue(new Callback<TinacoResponse>() {
            @Override
            public void onResponse(Call<TinacoResponse> call, Response<TinacoResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<TinacoResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
    public interface ApiCallback<T> {
        void onSuccess(T data);

        void onError(String error);
    }
    //////////////////////////////////////////////////

}
