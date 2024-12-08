package com.primerp.integradora.Cosas.ViewModelFactory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.primerp.integradora.Cosas.viewmodel.EditProfileDialogViewModel;

public class EditProfileDialogViewModelFactory implements ViewModelProvider.Factory{
    private final Context context;

    public EditProfileDialogViewModelFactory(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(EditProfileDialogViewModel.class)) {
            return (T) new EditProfileDialogViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
