package com.primerp.integradora.Cosas.ViewModelFactory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.primerp.integradora.Cosas.viewmodel.EditContrasenaDialogViewModel;

public class EditContrasenaDialogViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public EditContrasenaDialogViewModelFactory(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(EditContrasenaDialogViewModel.class)) {
            return (T) new EditContrasenaDialogViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
