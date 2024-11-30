package com.primerp.integradora.Cosas.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.primerp.integradora.Cosas.Responst.LoginRequest;
import com.primerp.integradora.Cosas.Responst.LoginResponse;
import com.primerp.integradora.Cosas.repository.LoginRepository;

public class LoginViewModel extends ViewModel {
    private final LoginRepository repository;

    public LoginViewModel(Context context) {
        this.repository = new LoginRepository(context);
    }

    public LiveData<LoginResponse> loginUser(LoginRequest request) {
        return repository.loginUser(request);
    }
}