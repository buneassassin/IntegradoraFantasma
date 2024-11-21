package com.primerp.integradora.Cosas.Api;

import com.primerp.integradora.Cosas.Modelos.Notificaciones;
import com.primerp.integradora.Cosas.Modelos.Tinacos;
import com.primerp.integradora.Cosas.Responst.ApiResponse;
import com.primerp.integradora.Cosas.Responst.LoginRequest;
import com.primerp.integradora.Cosas.Responst.LoginResponse;
import com.primerp.integradora.Cosas.Responst.NotificacionRequest;
import com.primerp.integradora.Cosas.Responst.NotificacionResponse;
import com.primerp.integradora.Cosas.Responst.PassaworRequest;
import com.primerp.integradora.Cosas.Responst.RegisterRequest;
import com.primerp.integradora.Cosas.Responst.RegisterResponse;
import com.primerp.integradora.Cosas.Responst.TinacoRequest;
import com.primerp.integradora.Cosas.Responst.TinacoResponse;

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
    //----------------------------------------------------------------//
    //                  TODO Links para el registro                   //
    //----------------------------------------------------------------//

    @POST("login")
    Call<LoginResponse> loginUser(
            @Body LoginRequest loginRequest
    );
    @POST("register")
    Call<RegisterResponse> registerUser(
            @Body RegisterRequest registerRequest
    );
    @POST("logout")
    Call<ApiResponse> logout(
            @Header("Authorization")
            String authToken
    );
    @GET("me")
    Call<ApiResponse> getMe(
            @Header("Authorization")
            String authToken
    );
    @POST("update")
    Call<ApiResponse> updateUser(
            @Header("Authorization") String authToken,
            @Body RegisterRequest registerRequest
    );
    //----------------------------------------------------------------//
    //              TODO links para rescuperar contraseña             //
    //----------------------------------------------------------------//

    @POST("updatePassword")
    Call<ApiResponse> updatePassword(
            @Header("Authorization") String authToken,
            @Body PassaworRequest passwordMap
    );
    @POST("reset-password")
    Call<ApiResponse> resetPassword(
            @Body RegisterRequest registerRequest
    );
    //----------------------------------------------------------------//
    //             TODO Links para las imgenes                        //
    //----------------------------------------------------------------//
    @Multipart
    @POST("imagen")
    Call<ApiResponse> uploadImage(
            @Header("Authorization") String authToken,
            @Part MultipartBody.Part image
    );

    @GET("imagen")
    Call<ApiResponse> getimagen(
            @Header("Authorization")
            String authToken
    );
    //----------------------------------------------------------------//
    //            TODO Links para el tinaco;                         //
    //----------------------------------------------------------------//

    @GET("tinaco")
    Call<List<Tinacos>> getTinaco(
            @Header("Authorization")
            String authToken)
            ;
    @POST("tinaco")
    Call<TinacoResponse> addTinaco(
            @Header("Authorization") String authToken,
            @Body TinacoRequest request
    );
    @DELETE("tinaco/{id}")
    Call<TinacoResponse> deleteTinaco(
            @Header("Authorization") String authToken,
            @Body TinacoRequest request
    );
    @GET("tinaco/{id}")
    Call<TinacoResponse> getTinacoById(
            @Header("Authorization") String authToken);
    @PUT("tinaco/{id}")
    Call<TinacoResponse> updateTinaco(
            @Header("Authorization") String authToken,
            @Body TinacoRequest request
    );
    //----------------------------------------------------------------//
    //          TODO Link de notificacion                            //
    //----------------------------------------------------------------//
    @GET("notifications")
    Call<NotificacionResponse> getNotifications(@Header("Authorization") String token);

    @GET("notification/{id}")
    Call<NotificacionResponse> getNotificationById(
            @Header("Authorization") String authToken,
            @Body NotificacionRequest request
    );
    @DELETE("notification/{id}")
    Call<NotificacionResponse> deleteNotification(
            @Header("Authorization") String authToken,
            @Body NotificacionRequest request
    );



}
