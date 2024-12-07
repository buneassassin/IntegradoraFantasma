package com.primerp.integradora.Cosas.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.primerp.integradora.Cosas.Responst.ApiResponse;
import com.primerp.integradora.Cosas.repository.Repository;

public class ForgotPasswordViewModel extends ViewModel {
    private final Repository repository;
    private final MutableLiveData<String> emailError = new MutableLiveData<>();

    public ForgotPasswordViewModel(Context context) {
        repository = new Repository(context);
    }

    public LiveData<String> getEmailError() {
        return emailError;
    }

    public LiveData<ApiResponse> resetPassword(String email) {
        if (email.isEmpty()) {
            emailError.setValue("Por favor, ingrese su correo electrónico");
            return null;
        } else {
            emailError.setValue(null);
            return repository.resetPassword(email);
        }
    }
}
