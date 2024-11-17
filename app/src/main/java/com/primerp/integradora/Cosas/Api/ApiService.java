package com.primerp.integradora.Cosas.Api;

import com.primerp.integradora.Cosas.Responst.ApiResponse;
import com.primerp.integradora.Cosas.Responst.LoginRequest;
import com.primerp.integradora.Cosas.Responst.LoginResponse;
import com.primerp.integradora.Cosas.Responst.PassaworRequest;
import com.primerp.integradora.Cosas.Responst.RegisterRequest;
import com.primerp.integradora.Cosas.Responst.RegisterResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {
    @POST("login")
    Call<LoginResponse> loginUser(@Body LoginRequest loginRequest);
    @POST("register")
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);
    @POST("logout")
    Call<ApiResponse> logout(@Header("Authorization") String authToken);
    @GET("me")
    Call<ApiResponse> getMe(@Header("Authorization") String authToken);
    @POST("update")
    Call<ApiResponse> updateUser(
            @Header("Authorization") String authToken,
            @Body RegisterRequest registerRequest
    );
    @POST("updatePassword")
    Call<ApiResponse> updatePassword(
            @Header("Authorization") String authToken,
            @Body PassaworRequest passwordMap
    );

}
