package com.primerp.integradora.Cosas.ViewModelFactory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.primerp.integradora.Cosas.viewmodel.TinacoDetalleViewModel;

public class TinacoDetalleViewModelFactory  implements ViewModelProvider.Factory {
    private final Context context;

    public TinacoDetalleViewModelFactory(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public <T extends androidx.lifecycle.ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TinacoDetalleViewModel.class)) {
            return (T) new TinacoDetalleViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
