package com.primerp.integradora.Cosas.Class;

import android.content.Context;
import android.util.Log;

import com.primerp.integradora.Cosas.Api.ApiService;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private ApiService apiService;
    private static final String BASE_URL = "http://192.168.126.200:8003/api/v1/";

    private RetrofitClient(Context context) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request originalRequest = chain.request();
                        Request.Builder builder = originalRequest.newBuilder();

                        String token = new SessionManager(context).getToken();
                        Log.d("DEBUG", "Token desde interceptor: " + token);
                        if (token != null) {
                            builder.header("Authorization", "Bearer " + token);
                        }

                        Request newRequest = builder.build();
                        return chain.proceed(newRequest);
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiService = retrofit.create(ApiService.class);
    }
    public static synchronized RetrofitClient getInstance(Context context) {
        if (instance == null) {
            instance = new RetrofitClient(context);
        }
        return instance;
    }
    public ApiService getApiService() {
        return apiService;
    }
}
