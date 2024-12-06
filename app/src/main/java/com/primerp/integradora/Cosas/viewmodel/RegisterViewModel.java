package com.primerp.integradora.Cosas.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.primerp.integradora.Cosas.repository.Repository;


public class RegisterViewModel extends ViewModel {

    private final Repository repository;
    private final MutableLiveData<Boolean> registrationStatus = new MutableLiveData<>();

    public RegisterViewModel(Context context) {
        repository = new Repository(context);
    }

    public LiveData<Boolean> getRegistrationStatus() {
        return registrationStatus;
    }

    public void registerUser(String usuario_nom, String email, String password, String nombres, String apellidoPaterno, String apellidoMaterno, String telefono) {
        repository.registerUser(usuario_nom, email, password, nombres, apellidoPaterno, apellidoMaterno, telefono, registrationStatus::postValue);
    }
}
