package com.primerp.integradora.Cosas.Api;

import com.primerp.integradora.Cosas.Modelos.Sensores;
import com.primerp.integradora.Cosas.Modelos.Tinacos;
import com.primerp.integradora.Cosas.Modelos.User;
import com.primerp.integradora.Cosas.Responst.AdminResponse;
import com.primerp.integradora.Cosas.Responst.ApiResponse;
import com.primerp.integradora.Cosas.Responst.LoginRequest;
import com.primerp.integradora.Cosas.Responst.LoginResponse;
import com.primerp.integradora.Cosas.Responst.NotificacionResponse;
import com.primerp.integradora.Cosas.Responst.PassaworRequest;
import com.primerp.integradora.Cosas.Responst.RegisterRequest;
import com.primerp.integradora.Cosas.Responst.RegisterResponse;
import com.primerp.integradora.Cosas.Responst.ReporteResponse;
import com.primerp.integradora.Cosas.Responst.ReporteResponsePorFecha;
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
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

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
            String authToken
    );
    @POST("tinaco")
    Call<TinacoResponse> addTinaco(
            @Header("Authorization") String authToken,
            @Body TinacoRequest request
    );
    @DELETE("tinaco/{id}")
    Call<TinacoResponse> deleteTinaco(
            @Header("Authorization") String authToken,
            @Path("id") int tinacoId
    );
    @GET("tinaco/{id}")
    Call<TinacoResponse> getTinacoById(
            @Header("Authorization") String authToken,
            @Path("id") int tinacoId
    );
    @PUT("tinaco/{id}")
    Call<TinacoResponse> updateTinaco(
            @Header("Authorization") String authToken,
            @Path("id") int tinacoId,
            @Body TinacoRequest request
    );
    //----------------------------------------------------------------//
    //            TODO Links para el Sensores;                         //
    //----------------------------------------------------------------//
    @POST("kaboom")
    Call<TinacoResponse> getph(
            @Header("Authorization") String authToken,
            @Body Map<String, Object> body
    );

    @POST("ph")
    Call<TinacoResponse> getph(
            @Header("Authorization") String authToken,
            @Body Map<String, Object> body
    );
    @POST("turbidez")
    Call<TinacoResponse> getturbidez(
            @Header("Authorization") String authToken,
            @Body Map<String, Object> body
    );
    @POST("tds")
    Call<TinacoResponse> gettds(
            @Header("Authorization") String authToken,
            @Body Map<String, Object> body
    );
    @POST("ultrasonico")
    Call<TinacoResponse> getultrasonico(
            @Header("Authorization") String authToken,
            @Body Map<String, Object> body
    );
    @POST("temperatura")
    Call<TinacoResponse> gettemperatura(
            @Header("Authorization") String authToken,
            @Body Map<String, Object> body
    );
    @GET("reporte-datos")
    Call<ReporteResponse> obtenerDatos(
            @Header("Authorization") String authToken
    );
    @GET("reporte-datos-fecha") // Cambia la ruta según tu configuración
    Call<ReporteResponsePorFecha> obtenerDatosPorFecha(
            @Header("Authorization") String authToken
    );
    @POST("reporte-datos-sensor") // Cambia la ruta según tu configuración
    Call<ApiResponse> obtenerdatossensor(
            @Header("Authorization") String authToken,
            @Body Map<String, Object> body

    );

    //----------------------------------------------------------------//
    //          TODO Link de notificacion                            //
    //----------------------------------------------------------------//
    @GET("notifications")
    Call<NotificacionResponse> getNotifications(
            @Header("Authorization") String token
    );

    @DELETE("notifications/{id}")
    Call<Void> deleteNotification(
            @Header("Authorization") String authToken,
            @Path("id") int notificationId
    );

    //----------------------------------------------------------------//
    //          TODO Link de admin                                    //
    //----------------------------------------------------------------//
    @GET("admin-action")
    Call<ApiResponse> getAdmin(
            @Header("Authorization") String token
    );
    @GET("usuariosConTinacos")
    Call<List<User>> getusuariosConTinacos(
            @Header("Authorization") String token
    );
    @POST("desactivarUsuario")
    Call<ApiResponse> postdesactivarUsuario(@Header("Authorization") String authHeader, @Body Map<String, String> email);

    @POST("cambiarRol")
    Call<ApiResponse> postcambiarRol(
            @Header("Authorization") String token,
            @Body Map<String, String> roleData
    );

    @GET("getUserStatistics")
    Call<AdminResponse> getUserStatistics(
            @Header("Authorization") String token
    );
    @GET("obtenerRol")
    Call<AdminResponse> getobtenerRol(
            @Header("Authorization") String token
    );
    @POST("EnviarNotificacionesGeneral")
    Call<ApiResponse> postEnviarNotificacionesGeneral(
            @Header("Authorization") String authToken,
            @Body Map<String, String> notification
    );
    @GET("gettype")
    Call<AdminResponse> getgettype(
            @Header("Authorization") String authToken
    );
}
