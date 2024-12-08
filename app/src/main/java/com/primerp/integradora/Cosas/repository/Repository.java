package com.primerp.integradora.Cosas.repository;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.primerp.integradora.Cosas.Api.ApiService;
import com.primerp.integradora.Cosas.Class.RetrofitClient;
import com.primerp.integradora.Cosas.Class.SessionManager;
import com.primerp.integradora.Cosas.Modelos.Tinacos;
import com.primerp.integradora.Cosas.Modelos.User;
import com.primerp.integradora.Cosas.Responst.ApiResponse;
import com.primerp.integradora.Cosas.Responst.LoginRequest;
import com.primerp.integradora.Cosas.Responst.LoginResponse;
import com.primerp.integradora.Cosas.Responst.PassaworRequest;
import com.primerp.integradora.Cosas.Responst.RegisterRequest;
import com.primerp.integradora.Cosas.Responst.RegisterResponse;
import com.primerp.integradora.Cosas.Responst.TinacoRequest;
import com.primerp.integradora.Cosas.Responst.TinacoResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {
    private final ApiService apiService;
    private final SessionManager sessionManager;
    public Repository(Context context) {
        apiService = RetrofitClient.getInstance(context).getApiService();
        sessionManager = new SessionManager(context);
    }
    //////////////////////////////////////////////////
    public LiveData<LoginResponse> loginUser(LoginRequest request) {
        MutableLiveData<LoginResponse> liveData = new MutableLiveData<>();
        apiService.loginUser(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.postValue(response.body());
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });
        return liveData;
    }
    //////////////////////////////////////////////////
    public void registerUser(String usuario_nom, String email, String password, String nombres, String apellidoPaterno, String apellidoMaterno, String telefono, Repository.RegistrationCallback callback) {
        RegisterRequest request = new RegisterRequest(usuario_nom, email, password, nombres, apellidoPaterno, apellidoMaterno, telefono);
        apiService.registerUser(request).enqueue(new Callback<RegisterResponse>() {
            @Override
            public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    callback.onComplete(true);
                } else {
                    callback.onComplete(false);
                }
            }

            @Override
            public void onFailure(Call<RegisterResponse> call, Throwable t) {
                callback.onComplete(false);
            }
        });
    }
    public interface RegistrationCallback {
        void onComplete(boolean success);
    }
    //////////////////////////////////////////////////
    public LiveData<TinacoResponse> addTinaco(TinacoRequest request) {
        MutableLiveData<TinacoResponse> liveData = new MutableLiveData<>();
        String token = sessionManager.getToken();
        String authToken = "Bearer " + token;

        apiService.addTinaco(authToken, request).enqueue(new Callback<TinacoResponse>() {
            @Override
            public void onResponse(Call<TinacoResponse> call, Response<TinacoResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    liveData.postValue(response.body());
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<TinacoResponse> call, Throwable t) {
                liveData.postValue(null);
            }
        });

        return liveData;
    }
    //////////////////////////////////////////////////
    public void getTinacoById(int tinacoId, ApiCallback<TinacoResponse> callback) {
        String token = sessionManager.getToken();
        String authToken = "Bearer " + token;
        Call<TinacoResponse> call = apiService.getTinacoById(authToken, tinacoId);
        call.enqueue(new Callback<TinacoResponse>() {
            @Override
            public void onResponse(Call<TinacoResponse> call, Response<TinacoResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<TinacoResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
    //////////////////////////////////////////////////
    public void deleteTinaco(int tinacoId, ApiCallback<TinacoResponse> callback) {
        String token = sessionManager.getToken();
        String authToken = "Bearer " + token;
        Call<TinacoResponse> call = apiService.deleteTinaco(authToken, tinacoId);
        call.enqueue(new Callback<TinacoResponse>() {
            @Override
            public void onResponse(Call<TinacoResponse> call, Response<TinacoResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(null);
                } else {
                    callback.onError(response.message());
                }
            }

            @Override
            public void onFailure(Call<TinacoResponse> call, Throwable t) {
                callback.onError(t.getMessage());
            }
        });
    }
    public interface ApiCallback<T> {
        void onSuccess(T data);

        void onError(String error);
    }
    //////////////////////////////////////////////////
    public LiveData<ApiResponse> resetPassword(String email) {
        MutableLiveData<ApiResponse> result = new MutableLiveData<>();
        RegisterRequest request = new RegisterRequest(email);
        apiService.resetPassword(request).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    result.postValue(response.body());
                } else {
                    result.postValue(null); // or custom error handling
                    Log.e("ForgotPasswordRepo", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                result.postValue(null); // Handle failure
                Log.e("ForgotPasswordRepo", "Failure: " + t.getMessage(), t);
            }
        });
        return result;
    }
    public LiveData<ApiResponse> updatePassword(String token, PassaworRequest request) {
        MutableLiveData<ApiResponse> responseLiveData = new MutableLiveData<>();
        apiService.updatePassword(token, request).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    responseLiveData.postValue(response.body());
                } else {
                    ApiResponse errorResponse = new ApiResponse();
                    errorResponse.setMessage("Error: " + response.message());
                    responseLiveData.postValue(errorResponse);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                ApiResponse errorResponse = new ApiResponse();
                errorResponse.setMessage("Error: " + t.getMessage());
                responseLiveData.postValue(errorResponse);
            }
        });
        return responseLiveData;
    }
    /////////////////////////////////////////////////
    public LiveData<List<Tinacos>> getTinacos() {
        MutableLiveData<List<Tinacos>> liveData = new MutableLiveData<>();
        String token = sessionManager.getToken();
        String authToken = "Bearer " + token;
        apiService.getTinaco(authToken).enqueue(new Callback<List<Tinacos>>() {
            @Override
            public void onResponse(Call<List<Tinacos>> call, Response<List<Tinacos>> response) {
                if (response.isSuccessful() && response.body()!= null) {
                    liveData.postValue(response.body());
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<Tinacos>> call, Throwable t) {
                liveData.postValue(null);
            }
        });
        return liveData;
    }
    /////////////////////////////////////////////////

    public interface UsersCallback {
        void onSuccess(List<User> users);
        void onError(String errorMessage);
    }

    public void getUsersWithTinacos(UsersCallback callback) {
        String token = sessionManager.getToken();
        String authToken = "Bearer " + token;
        apiService.getusuariosConTinacos(authToken).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    callback.onSuccess(response.body());
                } else {
                    callback.onError("Error al obtener usuarios");
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                callback.onError("Error de red: " + t.getMessage());
            }
        });
    }
    ////////////////////////////////////////////////
    public LiveData<User> loadUserData() {
        MutableLiveData<User> userLiveData = new MutableLiveData<>();
        String token = sessionManager.getToken();
        if (token == null || token.isEmpty()) {
            return userLiveData;
        }

        String authToken = "Bearer " + token;
        apiService.getMe(authToken).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userLiveData.setValue(response.body().getUser());
                } else {
                    userLiveData.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                userLiveData.setValue(null);
            }
        });

        return userLiveData;
    }

    public LiveData<Boolean> updateUser(RegisterRequest request) {
        MutableLiveData<Boolean> updateStatus = new MutableLiveData<>();
        String token = sessionManager.getToken();

        if (token == null || token.isEmpty()) {
            updateStatus.setValue(false);
            return updateStatus;
        }

        String authToken = "Bearer " + token;
        apiService.updateUser(authToken, request).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                updateStatus.setValue(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                updateStatus.setValue(false);
            }
        });

        return updateStatus;
    }
    ////////////////////////////////////////////////
    public Call<TinacoResponse> getTinacoById(String authToken, int tinacoId) {
        return apiService.getTinacoById(authToken, tinacoId);
    }

    public Call<TinacoResponse> updateTinaco(String authToken, int tinacoId, TinacoRequest request) {
        return apiService.updateTinaco(authToken, tinacoId, request);
    }
}
