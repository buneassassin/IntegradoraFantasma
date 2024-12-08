package com.primerp.integradora.Cosas.ViewModelFactory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.primerp.integradora.Cosas.viewmodel.AdminManageViewModel;

public class AdminManageViewModelFactory implements ViewModelProvider.Factory {
    private final Context context;

    public AdminManageViewModelFactory(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(AdminManageViewModel.class)) {
            return (T) new AdminManageViewModel(context);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
