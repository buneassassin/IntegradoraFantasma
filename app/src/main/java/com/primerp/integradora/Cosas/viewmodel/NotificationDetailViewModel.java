package com.primerp.integradora.Cosas.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationDetailViewModel extends ViewModel {
    private final MutableLiveData<String> title = new MutableLiveData<>();
    private final MutableLiveData<String> message = new MutableLiveData<>();
    private final MutableLiveData<String> date = new MutableLiveData<>();

    public void setNotificationDetails(String title, String message, String date) {
        this.title.setValue(title);
        this.message.setValue(message);
        this.date.setValue(date);
    }

    public LiveData<String> getTitle() {
        return title;
    }

    public LiveData<String> getMessage() {
        return message;
    }

    public LiveData<String> getDate() {
        return date;
    }

}
