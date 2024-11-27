package com.primerp.integradora.Cosas.ViewModelFactory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.primerp.integradora.Cosas.viewmodel.RegisterViewModel;

public class RegisterViewModelFactory implements ViewModelProvider.Factory {

    private final Context context;

    public RegisterViewModelFactory(Context context) {
        this.context = context.getApplicationContext(); // Evitar fugas de memoria
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegisterViewModel.class)) {
            return (T) new RegisterViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}