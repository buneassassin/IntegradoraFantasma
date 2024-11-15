package com.primerp.integradora.Cosas.Class;

import com.primerp.integradora.Cosas.Api.ApiService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private ApiService apiService;

    // Aquí debe ir la URL base
    private static final String BASE_URL = "http://192.168.113.184:8003/api/v1/";

    private RetrofitClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public static synchronized RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public ApiService createService(Class<ApiService> apiServiceClass) {
        return apiService;
    }
}
