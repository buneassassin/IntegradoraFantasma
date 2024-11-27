package com.primerp.integradora;

import com.primerp.integradora.Cosas.Api.ApiService;
import com.primerp.integradora.Cosas.Class.RetrofitClient;
import com.primerp.integradora.Cosas.Responst.LoginRequest;
import com.primerp.integradora.Cosas.Responst.LoginResponse;
import com.primerp.integradora.Cosas.Responst.RegisterRequest;
import com.primerp.integradora.Cosas.Responst.RegisterResponse;
import com.primerp.integradora.Cosas.Responst.TinacoResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import retrofit2.Call;
import retrofit2.Response;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ApiServiceTest {

    @Mock
    private ApiService apiService;

    @Before
    public void setup() {
        apiService = mock(ApiService.class); // Mockea el apiService
        RetrofitClient retrofitClient = mock(RetrofitClient.class);
        when(retrofitClient.getApiService()).thenReturn(apiService);
    }

    @Test
    public void testLoginUser() throws Exception {
        // Configuración de datos para la prueba
        LoginRequest loginRequest = new LoginRequest("bune_assassin@hotmail.com", "1234567891");

        // Crear el mock de la respuesta
        LoginResponse mockResponse = new LoginResponse();
        mockResponse.setMessage("Login successful");

        // Crear el mock de la llamada
        Call<LoginResponse> mockedCall = mock(Call.class);
        when(mockedCall.execute()).thenReturn(Response.success(mockResponse));

        // Configuración del servicio mockeado
        ApiService mockApiService = mock(ApiService.class);
        when(mockApiService.loginUser(loginRequest)).thenReturn(mockedCall);

        // Ejecutar la llamada
        Response<LoginResponse> response = mockApiService.loginUser(loginRequest).execute();

        // Verificaciones
        assertNotNull(response.body());
        assertEquals("Login successful", response.body().getMessage());
    }




    @Test
    public void testRegisterUser() throws Exception {
        // Datos simulados para el registro
        RegisterRequest registerRequest = new RegisterRequest("jose", "jose@hotmail.com", "123456789", "lolo", "maria", "carlos", "123456789");
        RegisterResponse mockResponse = new RegisterResponse();
        mockResponse.setMessage("Usuario registrado exitosamente");

        // Configurar respuesta simulada
        Call<RegisterResponse> mockedCall = mock(Call.class);
        when(mockedCall.execute()).thenReturn(Response.success(mockResponse));
        when(apiService.registerUser(registerRequest)).thenReturn(mockedCall);

        // Ejecutar la llamada
        Response<RegisterResponse> response = apiService.registerUser(registerRequest).execute();

        // Verificaciones
        assertNotNull(response.body());
        assertEquals("Usuario registrado exitosamente", response.body().getMessage());
    }


    @Test
    public void testGetTinacoById() throws Exception {
        // Datos simulados para el ID del tinaco
        int tinacoId = 1;
        String authToken = "Bearer token123";
        TinacoResponse mockResponse = new TinacoResponse("Tinaco 1 details", 1, "TinacoName");

        // Configurar respuesta simulada
        Call<TinacoResponse> mockedCall = mock(Call.class);
        when(mockedCall.execute()).thenReturn(Response.success(mockResponse));
        when(apiService.getTinacoById(authToken, tinacoId)).thenReturn(mockedCall);

        // Ejecutar la llamada
        Response<TinacoResponse> response = apiService.getTinacoById(authToken, tinacoId).execute();

        // Verificaciones
        assertNotNull(response.body());

    }

    @Test
    public void testDeleteNotification() throws Exception {
        // Datos simulados para eliminar notificación
        int notificationId = 123;
        String authToken = "Bearer token123";

        // Configurar respuesta simulada
        Call<Void> mockedCall = mock(Call.class);
        when(mockedCall.execute()).thenReturn(Response.success(null));
        when(apiService.deleteNotification(authToken, notificationId)).thenReturn(mockedCall);

        // Ejecutar la llamada
        Response<Void> response = apiService.deleteNotification(authToken, notificationId).execute();

        // Verificaciones
        assertTrue(response.isSuccessful());
    }
}
