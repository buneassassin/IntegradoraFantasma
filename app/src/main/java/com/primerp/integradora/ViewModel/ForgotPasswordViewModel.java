package com.primerp.integradora.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.primerp.integradora.Model.ApiResponseModel;
import com.primerp.integradora.Repository.ForgotPasswordRepository;

public class ForgotPasswordViewModel extends ViewModel {
    private final ForgotPasswordRepository repository;

    public ForgotPasswordViewModel(ForgotPasswordRepository repository) {
        this.repository = repository;
    }

    public LiveData<ApiResponseModel> resetPassword(String email) {
        return repository.resetPassword(email);
    }
}