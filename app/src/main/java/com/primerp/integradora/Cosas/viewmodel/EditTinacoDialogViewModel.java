package com.primerp.integradora.Cosas.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.primerp.integradora.Cosas.Modelos.Tinacos;
import com.primerp.integradora.Cosas.Responst.TinacoRequest;
import com.primerp.integradora.Cosas.Responst.TinacoResponse;
import com.primerp.integradora.Cosas.repository.Repository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTinacoDialogViewModel extends ViewModel {
    private final Repository repository;
    private final MutableLiveData<Tinacos> tinacoLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public EditTinacoDialogViewModel(Context context) {
        repository = new Repository(context);
    }

    public LiveData<Tinacos> getTinacoLiveData() {
        return tinacoLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public void fetchTinacoById(String authToken, int tinacoId) {
        repository.getTinacoById(authToken, tinacoId).enqueue(new Callback<TinacoResponse>() {
            @Override
            public void onResponse(Call<TinacoResponse> call, Response<TinacoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    tinacoLiveData.postValue(response.body().getTinaco());
                } else {
                    errorLiveData.postValue("Error al obtener datos del tinaco: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<TinacoResponse> call, Throwable t) {
                errorLiveData.postValue("Error de red: " + t.getMessage());
            }
        });
    }

    public void updateTinaco(String authToken, int tinacoId, TinacoRequest request) {
        repository.updateTinaco(authToken, tinacoId, request).enqueue(new Callback<TinacoResponse>() {
            @Override
            public void onResponse(Call<TinacoResponse> call, Response<TinacoResponse> response) {
                if (!response.isSuccessful()) {
                    errorLiveData.postValue("Error al actualizar el tinaco: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<TinacoResponse> call, Throwable t) {
                errorLiveData.postValue("Error al actualizar el tinaco: " + t.getMessage());
            }
        });
    }
}
