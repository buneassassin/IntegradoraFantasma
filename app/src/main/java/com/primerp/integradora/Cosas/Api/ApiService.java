package com.primerp.integradora.Cosas.Api;

import com.primerp.integradora.Cosas.Modelos.Tinacos;
import com.primerp.integradora.Cosas.Responst.ApiResponse;
import com.primerp.integradora.Cosas.Responst.LoginRequest;
import com.primerp.integradora.Cosas.Responst.LoginResponse;
import com.primerp.integradora.Cosas.Responst.PassaworRequest;
import com.primerp.integradora.Cosas.Responst.RegisterRequest;
import com.primerp.integradora.Cosas.Responst.RegisterResponse;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface ApiService {
    // Link para el registro
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
    // link para rescuperar contraseña
    @POST("updatePassword")
    Call<ApiResponse> updatePassword(
            @Header("Authorization") String authToken,
            @Body PassaworRequest passwordMap
    );
    @POST("reset-password")
    Call<ApiResponse> resetPassword(@Body RegisterRequest registerRequest);

    //Link para las imgenes
    @Multipart
    @POST("imagen")
    Call<ApiResponse> uploadImage(
            @Header("Authorization") String authToken,
            @Part MultipartBody.Part image
    );

    @GET("imagen")
    Call<ApiResponse> getimagen(@Header("Authorization") String authToken);
    //Link para el tinaco
    @GET("tinaco")
    Call<List<Tinacos>> getTinaco(@Header("Authorization") String authToken);
    @POST("tinaco")
    Call<ApiResponse> addTinaco(
            @Header("Authorization") String authToken,
            @Body Map<String, String> request
    );
    @DELETE("tinaco/{id}")
    Call<ApiResponse> deleteTinaco(
            @Header("Authorization") String authToken,
            @Body Map<String, String> request
    );
    @GET("tinaco/{id}")
    Call<ApiResponse> getTinacoById(
            @Header("Authorization") String authToken);
    @PUT("tinaco/{id}")
    Call<ApiResponse> updateTinaco(
            @Header("Authorization") String authToken,
            @Body Map<String, String> request
    );



}
