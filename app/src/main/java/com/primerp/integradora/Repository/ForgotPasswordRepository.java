package com.primerp.integradora.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.primerp.integradora.Cosas.Api.ApiService;
import com.primerp.integradora.Model.ApiResponseModel;
import com.primerp.integradora.Model.ForgotPasswordModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordRepository {
    private final ApiService apiService;

    public ForgotPasswordRepository(ApiService apiService) {
        this.apiService = apiService;
    }

    public LiveData<ApiResponseModel> resetPassword(String email) {
        MutableLiveData<ApiResponseModel> liveData = new MutableLiveData<>();
        ForgotPasswordModel request = new ForgotPasswordModel(email);
package com.primerp.integradora.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.primerp.integradora.Cosas.Api.ApiService;
import com.primerp.integradora.Model.ApiResponseModel;
import com.primerp.integradora.Model.ForgotPasswordModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

        public class ForgotPasswordRepository {
            private final ApiService apiService;

            public ForgotPasswordRepository(ApiService apiService) {
                this.apiService = apiService;
            }

            public LiveData<ApiResponseModel> resetPassword(String email) {
                MutableLiveData<ApiResponseModel> liveData = new MutableLiveData<>();
                ForgotPasswordModel request = new ForgotPasswordModel(email);

                apiService.resetPassword(request).enqueue(new Callback<ApiResponseModel>() {
                    @Override
                    public void onResponse(Call<ApiResponseModel> call, Response<ApiResponseModel> response) {
                        if (response.isSuccessful()) {
                            liveData.postValue(response.body());
                        } else {
                            liveData.postValue(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiResponseModel> call, Throwable t) {
                        liveData.postValue(null);
                    }
                });

                return liveData;
            }
        }
        apiService.resetPassword(request).enqueue(new Callback<ApiResponseModel>() {
            @Override
            public void onResponse(Call<ApiResponseModel> call, Response<ApiResponseModel> response) {
                if (response.isSuccessful()) {
                    liveData.postValue(response.body());
                } else {
                    liveData.postValue(null);
                }
            }

            @Override
            public void onFailure(Call<ApiResponseModel> call, Throwable t) {
                liveData.postValue(null);
            }
        });

        return liveData;
    }
}