package com.primerp.integradora.Cosas.ViewModelFactory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.primerp.integradora.Cosas.viewmodel.ForgotPasswordViewModel;

public class ForgotPasswordViewModelFactory implements ViewModelProvider.Factory{
    private final Context context;

    public ForgotPasswordViewModelFactory(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ForgotPasswordViewModel.class)) {
            return (T) new ForgotPasswordViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
