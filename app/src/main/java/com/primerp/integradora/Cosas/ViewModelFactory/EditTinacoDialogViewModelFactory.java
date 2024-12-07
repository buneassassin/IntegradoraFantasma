package com.primerp.integradora.Cosas.ViewModelFactory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.primerp.integradora.Cosas.viewmodel.EditTinacoDialogViewModel;

public class EditTinacoDialogViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public EditTinacoDialogViewModelFactory(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(EditTinacoDialogViewModel.class)) {
            return (T) new EditTinacoDialogViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
