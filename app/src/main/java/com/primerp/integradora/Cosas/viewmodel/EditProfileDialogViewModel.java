package com.primerp.integradora.Cosas.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.primerp.integradora.Cosas.Modelos.User;
import com.primerp.integradora.Cosas.Responst.RegisterRequest;
import com.primerp.integradora.Cosas.repository.Repository;

public class EditProfileDialogViewModel extends ViewModel {
    private final Repository repository;

    public EditProfileDialogViewModel(Context context) {
        repository = new Repository(context);
    }
    public LiveData<User> getUserData() {
        return repository.loadUserData();
    }

    public LiveData<Boolean> updateUser(RegisterRequest request) {
        return repository.updateUser(request);
    }
}
