package com.primerp.integradora.Cosas.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.primerp.integradora.Cosas.Modelos.Tinacos;
import com.primerp.integradora.Cosas.Responst.TinacoResponse;
import com.primerp.integradora.Cosas.repository.Repository;

public class TinacoDetalleViewModel extends ViewModel {
    private final Repository repository;
    private final MutableLiveData<Boolean> deleteSuccess = new MutableLiveData<>();
    private final MutableLiveData<Tinacos> tinacoLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorMessage = new MutableLiveData<>();

    public TinacoDetalleViewModel(Context context) {
        repository = new Repository(context);
    }
    public LiveData<Boolean> getDeleteSuccess() {
        return deleteSuccess;
    }
    public LiveData<Tinacos> getTinacoLiveData() {
        return tinacoLiveData;
    }

    public LiveData<String> getErrorMessage() {
        return errorMessage;
    }

    public void loadTinacoData(int tinacoId) {
        repository.getTinacoById(tinacoId, new Repository.ApiCallback<TinacoResponse>() {
            @Override
            public void onSuccess(TinacoResponse data) {
                tinacoLiveData.setValue(data.getTinaco());
            }

            @Override
            public void onError(String error) {
                errorMessage.setValue(error);
            }
        });
    }

    public void deleteTinaco(int tinacoId) {
        repository.deleteTinaco(tinacoId, new Repository.ApiCallback<TinacoResponse>() {
            @Override
            public void onSuccess(TinacoResponse data) {
                deleteSuccess.postValue(true);
            }

            @Override
            public void onError(String error) {
                errorMessage.setValue(error);
            }
        });
    }
}
