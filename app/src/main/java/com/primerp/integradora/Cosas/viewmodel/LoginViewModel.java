package com.primerp.integradora.Cosas.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.primerp.integradora.Cosas.Api.ApiService;
import com.primerp.integradora.Cosas.Responst.LoginRequest;
import com.primerp.integradora.Cosas.Responst.LoginResponse;
import com.primerp.integradora.Cosas.repository.Repository;

public class LoginViewModel extends ViewModel {
    private final Repository repository;

    public LoginViewModel(Context context) {
        this.repository = new Repository(context);
    }

    public LiveData<LoginResponse> loginUser(LoginRequest request) {
        return repository.loginUser(request);
    }
}