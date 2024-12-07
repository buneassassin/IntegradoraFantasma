package com.primerp.integradora.Cosas.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.primerp.integradora.Cosas.Modelos.Tinacos;
import com.primerp.integradora.Cosas.repository.Repository;

import java.util.List;

public class DashboardViewModel extends ViewModel {
    private final Repository repository;
    private final MutableLiveData<String> TinacosError = new MutableLiveData<>();

    public DashboardViewModel(Context context) {
        repository = new Repository(context);
    }

    public LiveData<List<Tinacos>> getTinacos() {
        return repository.getTinacos();
    }

    public MutableLiveData<String> getErrorLiveData() {
        return TinacosError;
    }


}