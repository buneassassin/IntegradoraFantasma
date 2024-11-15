package com.primerp.integradora.Cosas.Api;

import com.primerp.integradora.Cosas.Responst.LoginRequest;
import com.primerp.integradora.Cosas.Responst.LoginResponse;
import com.primerp.integradora.Cosas.Responst.RegisterRequest;
import com.primerp.integradora.Cosas.Responst.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);
    @POST("register")
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);

}
