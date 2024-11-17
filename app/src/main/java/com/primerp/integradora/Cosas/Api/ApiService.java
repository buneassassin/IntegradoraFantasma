package com.primerp.integradora.Cosas.Api;

import com.primerp.integradora.Cosas.Responst.ApiResponse;
import com.primerp.integradora.Cosas.Responst.LoginRequest;
import com.primerp.integradora.Cosas.Responst.LoginResponse;
import com.primerp.integradora.Cosas.Responst.PassaworRequest;
import com.primerp.integradora.Cosas.Responst.RegisterRequest;
import com.primerp.integradora.Cosas.Responst.RegisterResponse;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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
    @POST("reset-password")
    Call<ApiResponse> resetPassword(@Body RegisterRequest registerRequest);
    @Multipart
    @POST("imagen")
    Call<ApiResponse> uploadImage(
            @Header("Authorization") String authToken,
            @Part MultipartBody.Part image
    );

    @GET("imagen")
    Call<ApiResponse> getimagen(@Header("Authorization") String authToken);

}
