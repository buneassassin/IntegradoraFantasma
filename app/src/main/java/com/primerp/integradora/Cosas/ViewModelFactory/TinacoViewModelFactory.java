package com.primerp.integradora.Cosas.ViewModelFactory;

import android.content.Context;

import androidx.lifecycle.ViewModelProvider;

import com.primerp.integradora.Cosas.viewmodel.TinacoViewModel;

public class TinacoViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public TinacoViewModelFactory(Context context) {
        this.context = context;
    }

    @Override
    public <T extends androidx.lifecycle.ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(TinacoViewModel.class)) {
            return (T) new TinacoViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
