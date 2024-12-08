package com.primerp.integradora.Cosas.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.primerp.integradora.Cosas.Responst.TinacoRequest;
import com.primerp.integradora.Cosas.Responst.TinacoResponse;
import com.primerp.integradora.Cosas.repository.Repository;

public class TinacoViewModel extends ViewModel {
    private final Repository repository;

    public TinacoViewModel(Context context) {
        repository = new Repository(context);
    }

    public LiveData<TinacoResponse> addTinaco(String nombre) {
        return repository.addTinaco(new TinacoRequest(nombre));
    }
}
