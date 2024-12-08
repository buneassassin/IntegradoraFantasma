package com.primerp.integradora.Cosas.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.primerp.integradora.Cosas.Responst.ApiResponse;
import com.primerp.integradora.Cosas.Responst.PassaworRequest;
import com.primerp.integradora.Cosas.repository.Repository;

public class EditContrasenaDialogViewModel extends ViewModel {
    private final Repository repository;

    public EditContrasenaDialogViewModel(Context context) {
        repository = new Repository(context);
    }

    public LiveData<ApiResponse> updatePassword(String token, PassaworRequest request) {
        return repository.updatePassword(token, request);
    }
}
