package com.primerp.integradora.Cosas.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.primerp.integradora.R;

import java.util.Arrays;
import java.util.List;

import kotlinx.coroutines.CoroutineScope;

public class HomeViewModel extends ViewModel {
    private final MutableLiveData<List<Integer>> carruselImages = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isButtonClicked = new MutableLiveData<>(false);

    public HomeViewModel() {
        // Initialize the list of images for the carousel
        List<Integer> images = Arrays.asList(
                R.drawable.imagen1,
                R.drawable.imagen2,
                R.drawable.imagen3
        );
        carruselImages.setValue(images);
    }

    public LiveData<List<Integer>> getCarruselImages() {
        return carruselImages;
    }

    public LiveData<Boolean> isButtonClicked() {
        return isButtonClicked;
    }

    public void onButtonClick() {
        isButtonClicked.setValue(true);
    }
}

