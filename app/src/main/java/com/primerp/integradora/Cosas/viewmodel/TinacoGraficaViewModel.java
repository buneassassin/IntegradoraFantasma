package com.primerp.integradora.Cosas.viewmodel;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.primerp.integradora.Cosas.Api.ApiService;
import com.primerp.integradora.Cosas.Class.RetrofitClient;
import com.primerp.integradora.Cosas.Responst.TinacoResponse;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TinacoGraficaViewModel {

    private final ApiService apiService;
    private final MutableLiveData<String> nivelSuciedad = new MutableLiveData<>();
    private final MutableLiveData<String> turbidez = new MutableLiveData<>();
    private final MutableLiveData<String> temperatura = new MutableLiveData<>();
    private final MutableLiveData<String> nivelUltrasonico = new MutableLiveData<>();
    private final MutableLiveData<String> tds = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public TinacoGraficaViewModel(ApiService apiService) {
        this.apiService = apiService;
    }

    public LiveData<String> getNivelSuciedad() {
        return nivelSuciedad;
    }

    public LiveData<String> getTurbidez() {
        return turbidez;
    }

    public LiveData<String> getTemperatura() {
        return temperatura;
    }

    public LiveData<String> getNivelUltrasonico() {
        return nivelUltrasonico;
    }

    public LiveData<String> getTds() {
        return tds;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void fetchTinacoData(String authToken, int tinacoId) {
        fetchPh(authToken, tinacoId);
        fetchTurbidez(authToken, tinacoId);
        fetchTemperatura(authToken, tinacoId);
        fetchUltrasonico(authToken, tinacoId);
        fetchTds(authToken, tinacoId);
    }

    private void fetchPh(String authToken, int tinacoId) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("tinaco_id", tinacoId);
        apiService.getph(authToken, requestBody).enqueue(new Callback<TinacoResponse>() {
            @Override
            public void onResponse(Call<TinacoResponse> call, Response<TinacoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    nivelSuciedad.setValue(response.body().getMensaje());
                } else {
                    errorMessage.setValue("Error al obtener PH: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<TinacoResponse> call, Throwable t) {
                errorMessage.setValue("Fallo en PH: " + t.getMessage());
            }
        });
    }

    private void fetchTurbidez(String authToken, int tinacoId) {
        // Similar a fetchPh
    }

    private void fetchTemperatura(String authToken, int tinacoId) {
        // Similar a fetchPh
    }

    private void fetchUltrasonico(String authToken, int tinacoId) {
        // Similar a fetchPh
    }

    private void fetchTds(String authToken, int tinacoId) {
        // Similar a fetchPh
    }
}
