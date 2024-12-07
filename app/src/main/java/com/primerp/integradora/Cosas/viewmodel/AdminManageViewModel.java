package com.primerp.integradora.Cosas.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.primerp.integradora.Cosas.Modelos.User;
import com.primerp.integradora.Cosas.repository.Repository;

import java.util.List;

public class AdminManageViewModel extends ViewModel {
    private final Repository userRepository;
    private final MutableLiveData<List<User>> usersLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> errorLiveData = new MutableLiveData<>();

    public AdminManageViewModel(Context context) {
        userRepository = new Repository(context);
    }

    public LiveData<List<User>> getUsersLiveData() {
        return usersLiveData;
    }

    public LiveData<String> getErrorLiveData() {
        return errorLiveData;
    }

    public void fetchUsers() {
        userRepository.getUsersWithTinacos(new Repository.UsersCallback() {
            @Override
            public void onSuccess(List<User> users) {
                usersLiveData.postValue(users);
            }

            @Override
            public void onError(String errorMessage) {
                errorLiveData.postValue(errorMessage);
            }
        });
    }
}
