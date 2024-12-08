package com.primerp.integradora.Cosas.Dialog;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.primerp.integradora.Cosas.Adapter.SensorAdapter;
import com.primerp.integradora.Cosas.Api.ApiService;
import com.primerp.integradora.Cosas.Class.RetrofitClient;
import com.primerp.integradora.Cosas.Class.SessionManager;
import com.primerp.integradora.Cosas.Modelos.Sensores;
import com.primerp.integradora.Cosas.Responst.ApiResponse;
import com.primerp.integradora.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SensoresActivity extends AppCompatActivity {
    private LinearLayout emptyView;
    private RecyclerView recyclerView;
    private SensorAdapter sensorAdapter;
    private ProgressBar progressBar;
    private SessionManager sessionManager;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensores);

        ImageView backIcon = findViewById(R.id.iconback);
        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.rv_sensor);
        emptyView = findViewById(R.id.empty_view);

        sessionManager = new SessionManager(this);
        apiService = RetrofitClient.getInstance(this).getApiService();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        sensorAdapter = new SensorAdapter(new ArrayList<>());
        recyclerView.setAdapter(sensorAdapter);

        backIcon.setOnClickListener(v -> finish());

        cargarsensores();
    }

    private void cargarsensores() {
        progressBar.setVisibility(View.VISIBLE);

        String token = sessionManager.getToken();
        if (token == null || token.isEmpty()) {
            progressBar.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Token inválido", Toast.LENGTH_SHORT).show();
            return;
        }

        String authToken = "Bearer " + token;
        String nombreSensor = getIntent().getStringExtra("nombre_sensor");
        Toast.makeText(this, "Nombre del sensor: " + nombreSensor, Toast.LENGTH_SHORT).show();

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("nombre", nombreSensor);

        Call<ApiResponse> call = apiService.obtenerdatossensor(authToken, requestBody);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    List<Sensores> sensoresList = apiResponse.getData();

                    if (sensoresList != null && !sensoresList.isEmpty()) {
                        emptyView.setVisibility(View.GONE);
                        recyclerView.setVisibility(View.VISIBLE);

                        // Actualiza los datos del adaptador
                        sensorAdapter.updateData(sensoresList);
                    } else {
                        emptyView.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                        Toast.makeText(SensoresActivity.this, "No hay datos disponibles", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    emptyView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    Toast.makeText(SensoresActivity.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                Toast.makeText(SensoresActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
